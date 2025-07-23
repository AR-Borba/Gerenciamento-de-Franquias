package com.franquias.Model.entities;

import java.time.LocalDateTime;
import java.util.List;

import com.franquias.Model.Produto;
import com.franquias.Model.enums.FormaDePagamento;
import com.franquias.Model.enums.ModalidadeEntrega;
import com.franquias.Model.enums.StatusPedido;

public class Vendedor {
    String nome;
    //email e senha de login
    List<Pedido> pedidos;

    public Vendedor(String nome){
        this.nome = nome;
    }
    
    public void cadastrarPedido(List<Produto> produtos, String cliente, LocalDateTime datahora, FormaDePagamento formaDePagamento, double taxas, double valorTotal, ModalidadeEntrega modalidadeDeEntrega, StatusPedido statusPedido){
        Pedido pedido = new Pedido(produtos, cliente, datahora, formaDePagamento, taxas, modalidadeDeEntrega, statusPedido);
        pedidos = List.of(pedido);
    }

    public void visualizarPedidos(){

    }

    public void editarPedidos(){

    }
}
