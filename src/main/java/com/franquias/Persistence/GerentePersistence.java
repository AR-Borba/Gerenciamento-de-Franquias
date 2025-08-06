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

    private List<Gerente> gerentesEmMemoria;
    private long proximoId;

    public GerentePersistence() {
        this.gerentesEmMemoria = new ArrayList<>();
        carregarDoArquivo();
        determinarProximoId();
    }

    private void carregarDoArquivo() {
        String json = Arquivo.le(PATH);
        if (json != null && !json.trim().isEmpty()) {
            Type tipoLista = new TypeToken<List<Gerente>>() {
            }.getType();
            List<Gerente> gerentesDoArquivo = gson.fromJson(json, tipoLista);
            if (gerentesDoArquivo != null) {
                this.gerentesEmMemoria.addAll(gerentesDoArquivo);
            }
        }
    }

    private void determinarProximoId() {
        if (!this.gerentesEmMemoria.isEmpty()) {
            long maiorId = this.gerentesEmMemoria.stream().mapToLong(Gerente::getId).max().getAsLong();

            this.proximoId = maiorId + 1;
        } else {
            this.proximoId = 1;
        }
    }

    @Override
    public void save(List<Gerente> itens) {
        String json = gson.toJson(itens);

        File diretorio = new File(DIRECTORY);
        if (!diretorio.exists())
            diretorio.mkdirs();

        Arquivo.salva(PATH, json);
    }

    @Override
    public List<Gerente> findAll() {
        return gerentesEmMemoria;
    }

    public void adicionarGerente(Gerente novoGerente) {
        novoGerente.setId(this.proximoId);
        this.gerentesEmMemoria.add(novoGerente);
        this.proximoId++;
        save(this.gerentesEmMemoria);
    }

    public void removerGerente(long idgerente) {
        boolean foiRemovido = this.gerentesEmMemoria.removeIf(gerente -> gerente.getId() == idgerente);

        if (foiRemovido) {
            save(gerentesEmMemoria);
        }
    }

    public void update(Gerente gerente) {
        Gerente gerenteAntigo = buscarPorId(gerente.getId());

        if (gerenteAntigo != null) {
            gerentesEmMemoria.remove(gerenteAntigo);
            gerentesEmMemoria.add(gerente);
            save(gerentesEmMemoria);
        }
    }

    // public void registraFranquia(long id){

    // }

    public Gerente buscarPorId(long idgerente) {
        return gerentesEmMemoria.stream().filter(g -> g.getId() == idgerente).findFirst().orElse(null);
    }

    public Gerente findByEmailAndPassword(String email, String senha) {

        return gerentesEmMemoria.stream()
                .filter(g -> g.getEmail().equalsIgnoreCase(email) && g.getSenha().equals(senha))
                .findFirst()
                .orElse(null);
    }

    public Gerente findByEmail(String email) {

        return gerentesEmMemoria.stream()
                .filter(g -> g.getEmail() != null && g.getEmail().equalsIgnoreCase(email))
                .findFirst()
                .orElse(null);
    }
}
