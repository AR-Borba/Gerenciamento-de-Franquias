package com.franquias.Model;

import java.math.BigDecimal;
import java.util.Objects;

public class Produto {
    private long id;
    private String produto;
    private BigDecimal preco;
    private int quantidadeEstoque;

    public Produto() {
        
    }

    public Produto(String produto, BigDecimal preco, int quantidadeEstoque, long id) {
        this.produto = produto;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
        this.id = id;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
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
    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }
    public void setQuantidadeEstoque(int quantidadeEstoque) {
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
