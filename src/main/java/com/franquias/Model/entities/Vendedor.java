package com.franquias.Model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
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

    public void cadastrarPedido(Map<Produto, Integer> produtos, String cliente, LocalDateTime datahora, FormaDePagamento formaDePagamento, BigDecimal taxas, ModalidadeEntrega modalidadeDeEntrega, StatusPedido statusPedido){
        Pedido pedido = new Pedido(produtos, cliente, datahora, formaDePagamento, taxas, modalidadeDeEntrega, statusPedido);
        pedidos = List.of(pedido);
    }

    public void visualizarPedidos(){

    }

    public void editarPedidos(){

    }
}
