package com.franquias.Model.entities;

import java.time.LocalDateTime;

import java.util.List;

import com.franquias.Model.*;
import com.franquias.Model.enums.*;

public class Pedido {
    private List<Produto> produtos;
    private String cliente;
    private LocalDateTime datahora;
    private FormaDePagamento formaDePagamento;
    private double taxas;
    private double valorTotal;
    private ModalidadeEntrega modalidadeDeEntrega;
    private StatusPedido statusPedido;

    public Pedido(List<Produto> produtos, String cliente, LocalDateTime datahora,
            FormaDePagamento formaDePagamento, double taxas, double valorTotal, ModalidadeEntrega modalidadeDeEntrega,
            StatusPedido statusPedido) {
        this.produtos = produtos;
        this.cliente = cliente;
        this.datahora = datahora;
        this.formaDePagamento = formaDePagamento;
        this.taxas = taxas;
        this.valorTotal = valorTotal;
        this.modalidadeDeEntrega = modalidadeDeEntrega;
        this.statusPedido = statusPedido;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
    public String getCliente() {
        return cliente;
    }
    public LocalDateTime getDatahora() {
        return datahora;
    }
    public FormaDePagamento getFormaDePagamento() {
        return formaDePagamento;
    }
    public double getTaxas() {
        return taxas;
    }
    public double getValorTotal() {
        return valorTotal;
    }
    public ModalidadeEntrega getModalidadeDeEntrega() {
        return modalidadeDeEntrega;
    }
    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public double calcularSubtotal() {
        double total = 0;

        for(Produto produto : produtos)
            total += produto.getPreco() * produto.getQuantidade();

        total += taxas;

        return total;
    }
}
