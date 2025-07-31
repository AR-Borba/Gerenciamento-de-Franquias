package com.franquias.Persistence;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.franquias.Model.entities.Usuários.Vendedor;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class VendedorPersistence implements Persistence<Vendedor> {

    private static final String PATH = "data" + File.separator + "vendedor.json";
    private final Gson gson = new Gson();
    private List<Vendedor> vendedoresEmMemoria;
    private long proximoId;

    public VendedorPersistence() {
        this.vendedoresEmMemoria = new ArrayList<>();
        carregarDoArquivo();
        determinarProximoId();
    }

    private void carregarDoArquivo() {
        String json = Arquivo.le(PATH);
        if (json != null && !json.trim().isEmpty()) {
            Type tipoLista = new TypeToken<List<Vendedor>>() {}.getType();
            List<Vendedor> vendedoresDoArquivo = gson.fromJson(json, tipoLista);
            if (vendedoresDoArquivo != null) {
                this.vendedoresEmMemoria.addAll(vendedoresDoArquivo);
            }
        }
    }

    private void determinarProximoId() {
        if(!this.vendedoresEmMemoria.isEmpty()) {
            long maiorId = this.vendedoresEmMemoria.stream().mapToLong(Vendedor::getId).max().getAsLong();

            this.proximoId = maiorId + 1;
        } else {
            this.proximoId = 1;
        }
    }

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
        return this.vendedoresEmMemoria;
    }

    public void adicionarVendedor(Vendedor novoVendedor) {
        novoVendedor.setId(this.proximoId);
        this.vendedoresEmMemoria.add(novoVendedor);
        this.proximoId++;
        save(this.vendedoresEmMemoria);
    }

    public void removerVendedor(long idVendedor) {
        boolean foiRemovido = this.vendedoresEmMemoria.removeIf(vendedor -> vendedor.getId() == idVendedor);

        if(foiRemovido) {
            save(vendedoresEmMemoria);
        }
    }

    public void update(Vendedor vendedorAtualizando) {
        int index = -1;

        for(int i = 0; i < vendedoresEmMemoria.size(); i++) {
            if(vendedoresEmMemoria.get(i).getId() == vendedorAtualizando.getId()) {
                index = i;
                break;
            }
        }

        if(index != -1) {
            vendedoresEmMemoria.set(index, vendedorAtualizando);
            save(vendedoresEmMemoria);
        }
    }

    public Vendedor buscarPorId(long idVendedor) {
        return vendedoresEmMemoria.stream().filter(v -> v.getId() == idVendedor).findFirst().orElse(null);
    }
}
