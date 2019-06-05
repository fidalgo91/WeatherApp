package tqs.api;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tqs.api.models.Cidade;

public class CidadeTest {

	Cidade cidade = new Cidade();
	
	@Test
	public void nullTest() {
		assertEquals(cidade.getNome(), null);
	}
	
	@Test
	public void latitudeTest() {
		cidade.setLatitude((double) 40);
		assertEquals(cidade.getLatitude(), (double) 40, 0.0);
		
		cidade.setLongitude((double) -8);
		assertEquals(cidade.getLongitude(), (double) -8, 0.0);
		
		cidade.setNome("Aveiro");
		assertEquals(cidade.getNome(), "Aveiro");
	}

	@Test
	public void longitudeTest() {
		cidade.setLongitude((double) -8);
		assertEquals(cidade.getLongitude(), (double) -8, 0.0);	
	}
	
	@Test
	public void nomeTest(){
		cidade.setNome("Aveiro");
		assertEquals(cidade.getNome(), "Aveiro");
	}

}
