package com.franquias.Utils;

import com.franquias.Utils.Endereco;
import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ViaCepService {

    public Endereco getEndereco(String cep) throws Exception {
        Endereco endereco = null;
        HttpGet request = new HttpGet("https://viacep.com.br/ws/" + cep + "/json/");

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // Converte a resposta JSON em uma String
                String result = EntityUtils.toString(entity);
                
                // Usa o Gson para converter a String JSON no seu objeto Endereco
                Gson gson = new Gson();
                endereco = gson.fromJson(result, Endereco.class);
            }
        }
        
        return endereco;
    }
}