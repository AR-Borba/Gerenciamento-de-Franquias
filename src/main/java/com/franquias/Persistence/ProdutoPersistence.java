package com.franquias.Persistence;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.franquias.Model.Produto;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ProdutoPersistence implements Persistence<Produto> {

    private static final String PATH = "data" + File.separator + "produto.json";
    private final Gson gson = new Gson();

    private List<Produto> produtosEmMemoria;
    private long proximoId;

    public ProdutoPersistence() {
        this.produtosEmMemoria = new ArrayList<>();
        carregarDoArquivo();
        determinarProximoId();
    }

    private void carregarDoArquivo() {
        String json = Arquivo.le(PATH);
        if (json != null && !json.trim().isEmpty()) {
            Type tipoLista = new TypeToken<List<Produto>>() {
            }.getType();
            List<Produto> produtoesDoArquivo = gson.fromJson(json, tipoLista);
            if (produtoesDoArquivo != null) {
                this.produtosEmMemoria.addAll(produtoesDoArquivo);
            }
        }
    }

    private void determinarProximoId() {
        if (!this.produtosEmMemoria.isEmpty()) {
            long maiorId = this.produtosEmMemoria.stream().mapToLong(Produto::getId).max().getAsLong();

            this.proximoId = maiorId + 1;
        } else {
            this.proximoId = 1;
        }
    }

    @Override
    public void save(List<Produto> itens) {
        String json = gson.toJson(itens);

        File diretorio = new File(DIRECTORY);
        if (!diretorio.exists())
            diretorio.mkdirs();

        Arquivo.salva(PATH, json);
    }

    @Override
    public List<Produto> findAll() {
        return this.produtosEmMemoria;
    }

    public void adicionarProduto(Produto novoproduto) {
        novoproduto.setId(this.proximoId);
        this.produtosEmMemoria.add(novoproduto);
        this.proximoId++;
        save(this.produtosEmMemoria);
    }

    public void adicionarAoEstoque(Produto produto, int qtd) {
        int novoEstoque = produto.getQuantidadeEstoque() + qtd;
        produto.setQuantidadeEstoque(novoEstoque);
        update(produto);
    }

    public void removerProduto(long idproduto) {
        boolean foiRemovido = this.produtosEmMemoria.removeIf(produto -> produto.getId() == idproduto);

        if (foiRemovido) {
            save(produtosEmMemoria);

        }
    }

    public void update(Produto produto) {
        Produto produtoAntigo = buscarPorId(produto.getId());

        if (produtoAntigo != null) {
            produtosEmMemoria.remove(produtoAntigo);
            produtosEmMemoria.add(produto);
            save(produtosEmMemoria);
        }
    }

    public Produto buscarPorId(long idProduto) {
        return produtosEmMemoria.stream().filter(p -> p.getId() == idProduto).findFirst().orElse(null);
    }
}
