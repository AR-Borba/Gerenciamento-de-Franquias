package com.franquias.Persistence;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.franquias.Model.entities.Cliente;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ClientePersistence implements Persistence<Cliente> {

    private static final String PATH = "data" + File.separator + "cliente.json";
    private final Gson gson = new Gson();
    private long proximoId;

    private List<Cliente> clientesEmMemoria;

    public ClientePersistence() {
        clientesEmMemoria = new ArrayList<>();
        carregarDoArquivo();
        determinarProximoId();
    }

    private void carregarDoArquivo() {
        String json = Arquivo.le(PATH);
        if (json != null && !json.trim().isEmpty()) {
            Type tipoLista = new TypeToken<List<Cliente>>() {
            }.getType();
            List<Cliente> clientesDoArquivo = gson.fromJson(json, tipoLista);
            if (clientesDoArquivo != null) {
                this.clientesEmMemoria.addAll(clientesDoArquivo);
            }
        }
    }

    private void determinarProximoId() {
        if (!this.clientesEmMemoria.isEmpty()) {
            long maiorId = this.clientesEmMemoria.stream().mapToLong(Cliente::getId).max().getAsLong();

            this.proximoId = maiorId + 1;
        } else {
            this.proximoId = 1;
        }
    }

    @Override
    public void save(List<Cliente> itens) {
        String json = gson.toJson(itens);

        File diretorio = new File(DIRECTORY);
        if (!diretorio.exists())
            diretorio.mkdirs();

        Arquivo.salva(PATH, json);
    }

    @Override
    public List<Cliente> findAll() {
        return this.clientesEmMemoria;
    }

    public void add(Cliente novoCliente) {
        novoCliente.setId(proximoId);
        clientesEmMemoria.add(novoCliente);
        proximoId++;
        save(clientesEmMemoria);
    }

    public Cliente findByCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            return null;
        }

        return clientesEmMemoria.stream()
                .filter(cliente -> cliente.getCpf().equals(cpf))
                .findFirst()
                .orElse(null);
    }
}
