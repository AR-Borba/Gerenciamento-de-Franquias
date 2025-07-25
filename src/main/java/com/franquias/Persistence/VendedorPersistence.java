package com.franquias.Persistence;

import com.franquias.Model.entities.Vendedor;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class VendedorPersistence implements Persistence<Vendedor> {

    private static final String PATH = DIRECTORY + File.separator + "vendor.json";
    private final Gson gson = new Gson();

    @Override
    public void save(List<Vendedor> itens) {
        String json = gson.toJson(itens);

        File diretorio = new File(DIRECTORY);
        if(!diretorio.exists())
            diretorio.mkdirs();

        Arquivo.salva(PATH, json);
    }

    @Override
    public List<Vendedor> findAll() {
        String json = Arquivo.le(PATH);
        List<Vendedor> itens = new ArrayList<>();

        if(!json.trim().equals("")) {
            Type tipoLista = new TypeToken<List<Vendedor>>() {}.getType();
            itens = gson.fromJson(json, tipoLista);

                if(itens == null) 
                    itens = new ArrayList<>();
        }
        return itens;
    }
}
