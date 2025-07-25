package com.franquias.Model;

import java.math.BigDecimal;
import java.util.Objects;

public class Produto {
    private String id;
    private String produto;
    private BigDecimal preco;
    private int quantidadeEstoque;

    public Produto(String id, String produto, BigDecimal preco, int quantidadeEstoque) {
        this.id = id;
        this.produto = produto;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }
    public String getId() {
        return id;
    }
    private void setId(String id) {
        this.id = id;
    }
    public String getProduto() {
        return produto;
    }
    public void setProduto(String produto) {
        this.produto = produto;
    }
    public BigDecimal getPreco() {
        return preco;
    }
    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }
    public int getquantidadeEstoque() {
        return quantidadeEstoque;
    }
    public void setquantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id == produto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
