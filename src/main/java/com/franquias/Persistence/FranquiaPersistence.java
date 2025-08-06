package com.franquias.Persistence;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.franquias.Model.entities.Franquia;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FranquiaPersistence implements Persistence<Franquia> {

    private static final String PATH = "data" + File.separator + "franquias.json";
    private final Gson gson = new Gson();

    private List<Franquia> franquiasEmMemoria;
    private long proximoId;

    public FranquiaPersistence() {
        this.franquiasEmMemoria = new ArrayList<>();
        carregarDoArquivo();
        determinarProximoId();
    }

    private void carregarDoArquivo() {
        String json = Arquivo.le(PATH);
        if (json != null && !json.trim().isEmpty()) {
            Type tipoLista = new TypeToken<List<Franquia>>() {
            }.getType();
            List<Franquia> franquiasDoArquivo = gson.fromJson(json, tipoLista);
            if (franquiasDoArquivo != null) {
                this.franquiasEmMemoria.addAll(franquiasDoArquivo);
            }
        }
    }

    private void determinarProximoId() {
        if (!this.franquiasEmMemoria.isEmpty()) {
            long maiorId = this.franquiasEmMemoria.stream().mapToLong(Franquia::getId).max().getAsLong();

            this.proximoId = maiorId + 1;
        } else {
            this.proximoId = 1;
        }
    }

    @Override
    public void save(List<Franquia> itens) {
        String json = gson.toJson(itens);

        File diretorio = new File(DIRECTORY);
        if (!diretorio.exists())
            diretorio.mkdirs();

        Arquivo.salva(PATH, json);
    }

    @Override
    public List<Franquia> findAll() {
        String json = Arquivo.le(PATH);
        List<Franquia> itens = new ArrayList<>();

        if (!json.trim().equals("")) {
            Type tipoLista = new TypeToken<List<Franquia>>() {
            }.getType();
            itens = gson.fromJson(json, tipoLista);

            if (itens == null)
                itens = new ArrayList<>();
        }
        return itens;
    }

    public void adicionarFranquia(Franquia novaFranquia) {
        novaFranquia.setId(this.proximoId);
        this.franquiasEmMemoria.add(novaFranquia);
        this.proximoId++;
        save(this.franquiasEmMemoria);
    }

    public void removerFranquia(long idfranquia) {
        boolean foiRemovido = this.franquiasEmMemoria.removeIf(franquia -> franquia.getId() == idfranquia);

        if (foiRemovido) {
            save(franquiasEmMemoria);
        }
    }

    public void update(Franquia franquia) {
        Franquia franquiaAntigo = buscarPorId(franquia.getId());

        if (franquiaAntigo != null) {
            franquiasEmMemoria.remove(franquiaAntigo);
            franquiasEmMemoria.add(franquia);
            save(franquiasEmMemoria);
        }
    }

    public Franquia buscarPorId(long idfranquia) {
        return franquiasEmMemoria.stream().filter(v -> v.getId() == idfranquia).findFirst().orElse(null);
    }
}
