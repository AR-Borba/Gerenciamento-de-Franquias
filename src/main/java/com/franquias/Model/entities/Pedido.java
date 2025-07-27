package com.franquias.Model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import com.franquias.Model.*;
import com.franquias.Model.entities.Usuários.Vendedor;
import com.franquias.Model.enums.*;

public class Pedido {
    private long id;
    private Map<Produto, Integer> produtos;
    private String cliente;
    private transient LocalDateTime dataHora;
    private String dataHoraFormatada;
    private FormaDePagamento formaDePagamento;
    private BigDecimal taxas;
    private BigDecimal valorTotal;
    private ModalidadeEntrega modalidadeDeEntrega;
    private StatusPedido statusPedido;

    public Pedido() {

    }

    public Pedido(Vendedor vendedor) {
        this.produtos = Map.of(); // Inicializa com um mapa vazio
        this.cliente = "";
        this.dataHora = LocalDateTime.now();
        atualizarDataHoraFormatada();
        this.formaDePagamento = FormaDePagamento.DINHEIRO; // Valor padrão
        this.taxas = BigDecimal.ZERO; // Valor padrão
        this.modalidadeDeEntrega = ModalidadeEntrega.RETIRADA_NA_LOJA; // Valor padrão
        this.statusPedido = StatusPedido.ATIVO; // Valor padrão
    }

    public Pedido(Map<Produto, Integer> produtos, String cliente, LocalDateTime datahora,
            FormaDePagamento formaDePagamento, BigDecimal taxas, ModalidadeEntrega modalidadeDeEntrega,
            StatusPedido statusPedido) {
        this.produtos = produtos;
        this.cliente = cliente;
        this.dataHora = LocalDateTime.now();
        atualizarDataHoraFormatada();
        this.formaDePagamento = formaDePagamento;
        this.taxas = taxas;
        this.modalidadeDeEntrega = modalidadeDeEntrega;
        this.statusPedido = statusPedido;

        calcularEAtualizaValorTotal();
    }

    private void atualizarDataHoraFormatada() {
        if (this.dataHora != null) {
            this.dataHoraFormatada = this.dataHora.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }

    private void atualizarDataHoraPelaString() {
        if (this.dataHoraFormatada != null && !this.dataHoraFormatada.isEmpty()) {
            this.dataHora = LocalDateTime.parse(this.dataHoraFormatada, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        }
    }

    public Map<Produto, Integer> getProdutos() {
        return produtos;
    }

    public String getCliente() {
        return cliente;
    }

    public long getId() {
        return this.id;
    }

    public LocalDateTime getDatahora() {
        if (this.dataHora == null && this.dataHoraFormatada != null) {
            atualizarDataHoraPelaString();
        }
        return this.dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
        atualizarDataHoraFormatada();
    }

    public FormaDePagamento getFormaDePagamento() {
        return formaDePagamento;
    }

    public BigDecimal getTaxas() {
        return taxas;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public ModalidadeEntrega getModalidadeDeEntrega() {
        return modalidadeDeEntrega;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void calcularEAtualizaValorTotal() {
        BigDecimal subtotalItens = BigDecimal.ZERO;

        for (Map.Entry<Produto, Integer> entrada : produtos.entrySet()) {
            Produto produto = entrada.getKey();
            Integer quantidade = entrada.getValue();

            BigDecimal precoProduto = produto.getPreco();

            BigDecimal subtotal = precoProduto.multiply(new BigDecimal(quantidade));

            subtotalItens = subtotalItens.add(subtotal);
        }

        this.valorTotal = subtotalItens.add(taxas);
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }
}
