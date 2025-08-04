package com.franquias.Persistence;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.franquias.Model.entities.Usuários.Dono;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DonoPersistence implements Persistence<Dono> {

    private static final String PATH = "data" + File.separator + "dono.json";
    private final Gson gson = new Gson();

    @Override
    public void save(List<Dono> itens) {
        String json = gson.toJson(itens);

        File diretorio = new File(DIRECTORY);
        if(!diretorio.exists())
            diretorio.mkdirs();

        Arquivo.salva(PATH, json);
    }

    @Override
    public List<Dono> findAll() {
        String json = Arquivo.le(PATH);
        List<Dono> itens = new ArrayList<>();

        if(!json.trim().equals("")) {
            Type tipoLista = new TypeToken<List<Dono>>() {}.getType();
            itens = gson.fromJson(json, tipoLista);

                if(itens == null) 
                    itens = new ArrayList<>();
        }
        return itens;
    }

    public boolean hasDono() {
        List<Dono> donos = findAll();
        
        return (donos.isEmpty()) ? false : true;
    }

    public Dono findByEmailAndPassword(String email, String senha) {
        
        return findAll().stream()
            .filter(d -> d.getEmail().equalsIgnoreCase(email) && d.getSenha().equals(senha))
            .findFirst()
            .orElse(null);
    }
}
