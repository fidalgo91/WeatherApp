package tqs.api;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.json.JSONException;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import tqs.api.models.Cidade;

@CacheConfig(cacheManager="cacheManager")
@CrossOrigin
@RestController
public class Requests {

	ArrayList<Cidade> cidades = new ArrayList<Cidade>();
	
    public Requests ()
    {
        this.cidades.add(new Cidade("Aveiro", 40.64427, -8.64554));
        this.cidades.add(new Cidade("Lisbon", 38.71667, -9.13333));
        this.cidades.add(new Cidade("Oporto", 41.14961, -8.61099));
        this.cidades.add(new Cidade("Beja", 38.01506, -7.86323));
        this.cidades.add(new Cidade("Azores", 38.65, -27.21667));
        this.cidades.add(new Cidade("Viana do Castelo", 41.69323, -8.83287));
    }
    @Cacheable("result")
    @RequestMapping("/pesquisar/{nome}")
    public Object pesquisar(@PathVariable String nome) throws IOException, JSONException
    {
    	Cidade cidadeAPesquisar = new Cidade();

        for (Cidade c : this.cidades)
        {
            if (c.getNome().equals(nome))
            {
                cidadeAPesquisar = c;
            }
        }
        
        URL url = new URL("https://api.darksky.net/forecast/6ecb9b96f185b1fb63ffdfd087f65f84/" + cidadeAPesquisar.getLatitude() + "," + cidadeAPesquisar.getLongitude() + "?lang=pt&units=si");
  
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setUseCaches(true);
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
        	response.append(inputLine);
        }
        in.close();
        
        String result = new Gson().toJson(response);
        return result;
    }
	
    @RequestMapping(method = RequestMethod.POST, value="/adicionarCidade/{nome}/{latitude}/{longitude}")
    public ArrayList<Cidade> adicionarCidade(@PathVariable("nome") String nome, @PathVariable("latitude") Double latitude, @PathVariable("longitude") Double longitude)
    {
    	Cidade cidade = new Cidade();
    	cidade.setNome(nome);
    	cidade.setLatitude(latitude);
    	cidade.setLongitude(longitude);
    	this.cidades.add(cidade);
    	return this.cidades;
    }
    
    @RequestMapping("/devolverCidade/{nome}")
    public Cidade devolverCidade(@PathVariable String nome)
    {
    	Cidade cidadeEscolhida = new Cidade();
    	for (Cidade cidade : this.cidades) {
			if (cidade.getNome().equals(nome))
			{
				cidadeEscolhida = cidade;
				break;
			}
		}
    	return cidadeEscolhida;
    }
    
    @RequestMapping("/devolverCidades")
    public ArrayList<Cidade> devolverCidades()
    {
		return this.cidades;
    }
    
	@RequestMapping("/start")
	public void start() throws IOException, URISyntaxException
	{
		URL res = getClass().getClassLoader().getResource("HTML/index.html");
		File htmlFile = Paths.get(res.toURI()).toFile();
        Desktop.getDesktop().browse(htmlFile.toURI());
	}
	
}
