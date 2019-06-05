package tqs.api;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.validateMockitoUsage;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tqs.api.models.Cidade;

@RunWith(MockitoJUnitRunner.class)
public class DependeciasTest {
	
	Requests requests = new Requests();
	@Mock Requests requestsMocked = new Requests();
	@Autowired Requests requestsWired = new Requests();
	@Mock Cidade c;
	
	MockMvc mockMvc;
	
	@Test
	public void testarSeMockAdiciona() {
		int tamanho = this.requests.cidades.size();
		this.requests.cidades.add(c);
		assertTrue(this.requests.cidades.size() > tamanho);
		validateMockitoUsage();
	}
	
	@Test
	public void testarSeNomeDeMockEstaCorreto() {
		Mockito.when(c.getNome()).thenReturn("Paris");
		assertEquals(c.getNome(), "Paris");
		validateMockitoUsage();
	}
	
	@Test
	public void testarDevolverCidade() throws Exception
	{
		Cidade cidadeReal = new Cidade("Macau", 20, 8);
		when(requestsMocked.devolverCidade(eq("Agueda"))).thenReturn(cidadeReal);
		
		this.mockMvc = standaloneSetup(requestsMocked).build();
		
		mockMvc.perform(get("/devolverCidade/Agueda").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.nome", is(cidadeReal.getNome())));
		validateMockitoUsage();
	}
	
	@After
	public void validate() {
	    validateMockitoUsage();
	}
	
}
