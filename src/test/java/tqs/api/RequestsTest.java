package tqs.api;

import static org.junit.Assert.*;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import tqs.api.models.Cidade;

public class RequestsTest {
	Requests requests = new Requests();

	@Test
	public void sizeOfCidades() {
		assertEquals(requests.cidades.size(), 6);
	}
	
	@Test
	public void addCidade() {
		this.requests.cidades.add(new Cidade("Veneza", 40, 6));
		assertEquals(requests.cidades.size(), 7);
	}
	
	@Test
	public void cidadeDeCidades()
	{
		this.requests.cidades.add(new Cidade("Veneza", 40, 6));
		Cidade ultimaCidade = this.requests.cidades.get(this.requests.cidades.size() - 1);
		assertEquals(ultimaCidade.getNome(), "Veneza");
	}
	
	@Test
	public void testarPathDeFicheiro() throws URISyntaxException
	{
		URL res = getClass().getClassLoader().getResource("HTML/index.html");
		File htmlFile = Paths.get(res.toURI()).toFile();
		assertEquals(htmlFile.exists(), true);
		assertEquals(htmlFile.isFile(), true);
		assertEquals(htmlFile.canRead(), true);
	}

}
