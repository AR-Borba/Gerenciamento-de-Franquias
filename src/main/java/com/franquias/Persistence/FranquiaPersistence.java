package com.franquias.Persistence;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.franquias.Model.entities.Franquia;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FranquiaPersistence implements Persistence<Franquia> {

    private static final String PATH = DIRECTORY + File.separator + "franquias.json";
    private final Gson gson = new Gson();

    @Override
    public void save(List<Franquia> itens) {
        String json = gson.toJson(itens);

        File diretorio = new File(DIRECTORY);
        if(!diretorio.exists())
            diretorio.mkdirs();

        Arquivo.salva(PATH, json);
    }

    @Override
    public List<Franquia> findAll() {
        String json = Arquivo.le(PATH);
        List<Franquia> itens = new ArrayList<>();

        if(!json.trim().equals("")) {
            Type tipoLista = new TypeToken<List<Franquia>>() {}.getType();
            itens = gson.fromJson(json, tipoLista);

                if(itens == null) 
                    itens = new ArrayList<>();
        }
        return itens;
    }
}
