package com.franquias.Persistence;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.franquias.Model.entities.Usuários.Gerente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GerentePersistence implements Persistence<Gerente> {

    private static final String PATH = "data" + File.separator + "gerente.json";
    private final Gson gson = new Gson();

    @Override
    public void save(List<Gerente> itens) {
        String json = gson.toJson(itens);

        File diretorio = new File(DIRECTORY);
        if(!diretorio.exists())
            diretorio.mkdirs();

        Arquivo.salva(PATH, json);
    }

    @Override
    public List<Gerente> findAll() {
        String json = Arquivo.le(PATH);
        List<Gerente> itens = new ArrayList<>();

        if(!json.trim().equals("")) {
            Type tipoLista = new TypeToken<List<Gerente>>() {}.getType();
            itens = gson.fromJson(json, tipoLista);

                if(itens == null) 
                    itens = new ArrayList<>();
        }
        return itens;
    }
}
