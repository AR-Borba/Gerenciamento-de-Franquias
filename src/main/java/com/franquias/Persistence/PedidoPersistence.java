package com.franquias.Persistence;

import com.franquias.Model.entities.Pedido;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class PedidoPersistence implements Persistence<Pedido>{
    
    private static final String PATH = DIRECTORY + File.separator + "gerente.json";
    private final Gson gson = new Gson();

    @Override
    public void save(List<Pedido> itens) {
        String json = gson.toJson(itens);

        File diretorio = new File(DIRECTORY);
        if(!diretorio.exists())
            diretorio.mkdirs();

        Arquivo.salva(PATH, json);
    }

    @Override
    public List<Pedido> findAll() {
        String json = Arquivo.le(PATH);
        List<Pedido> itens = new ArrayList<>();

        if(!json.trim().equals("")) {
            Type tipoLista = new TypeToken<List<Pedido>>() {}.getType();
            itens = gson.fromJson(json, tipoLista);

                if(itens == null) 
                    itens = new ArrayList<>();
        }
        return itens;
    }
}
