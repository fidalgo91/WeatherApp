/*
Retirado de: https://stackoverflow.com/questions/52761758/how-i-can-resolve-this-problem-classspringjunit4classrunner-cannot-be-resolve

Com este teste é possível verificar que a cache funciona, pois ao por o decorator @Cacheable o sistema guarda o resultado do metodo findByEmail, 
o mesmo acontece na aplicação na função pesquisar onde guardamos o result em cache e usamos o cacheManager em WebConfig, fazendo o mesmo percurso.

*/


package tqs.api;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Repository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class CacheTest {

  // Your repository interface
  interface MyRepo extends Repository {

    @Cacheable("sample")
    Object findByEmail(String email);
  }

  @Configuration
  @EnableCaching
  static class Config {

    // Simulating your caching configuration
    @Bean
    CacheManager cacheManager() {
      return new ConcurrentMapCacheManager("sample");
    }

    // A repository mock instead of the real proxy
    @Bean
    MyRepo myRepo() {
      return Mockito.mock(MyRepo.class);
    }
  }

  @Autowired CacheManager manager;
  @Autowired MyRepo repo;

  @Test
  public void methodInvocationShouldBeCached() {

    Object first = new Object();
    Object second = new Object();

    // Set up the mock to return *different* objects for the first and second call
    Mockito.when(repo.findByEmail(Mockito.any(String.class))).thenReturn(first, second);

    // First invocation returns object returned by the method
    Object result = repo.findByEmail("foo");
    assertThat(result, is(first));

    // Second invocation should return cached value, *not* second (as set up above)
    result = repo.findByEmail("foo");
    assertThat(result, is(first));

    // Verify repository method was invoked once
    Mockito.verify(repo, Mockito.times(1)).findByEmail("foo");
    assertThat(manager.getCache("sample").get("foo"), is(notNullValue()));

    // Third invocation with different key is triggers the second invocation of the repo method
    result = repo.findByEmail("bar");
    assertThat(result, is(second));
  }
}
