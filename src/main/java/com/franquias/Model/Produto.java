package com.franquias.Model;

import com.franquias.Model.enums.TipoDeProduto;

public class Produto {
    private TipoDeProduto produto;
    private double preco;
    private int quantidade;
    
    public Produto(TipoDeProduto produto, double preco, int quantidade) {
        this.produto = produto;
        this.preco = preco;
        this.quantidade = quantidade;
    }
    public TipoDeProduto getProduto() {
        return produto;
    }
    public void setProduto(TipoDeProduto produto) {
        this.produto = produto;
    }
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
