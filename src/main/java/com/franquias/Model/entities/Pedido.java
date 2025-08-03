package com.franquias.Model.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.franquias.Model.*;
import com.franquias.Model.entities.Usuários.Vendedor;
import com.franquias.Model.enums.*;

public class Pedido {
    private long id;
    private Map<Produto, Integer> produtos;
    private transient LocalDateTime dataHora;
    private String dataHoraFormatada;
    private String cliente;
    private FormaDePagamento formaDePagamento;
    private BigDecimal taxas;
    private ModalidadeEntrega modalidadeDeEntrega;
    private StatusPedido statusPedido;
    private BigDecimal valorTotal;
    private Vendedor vendedorResponsavel;
    private long franquiaId;

    public Pedido() {
        this.produtos = new HashMap<Produto, Integer>();
    }

    public Pedido(Vendedor vendedor) {
        this.vendedorResponsavel = vendedor;
        this.franquiaId = vendedor.getFranquiaId();
        this.produtos = new HashMap<>(); // Inicializa com um mapa vazio
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

    public Map<Produto, Integer> getItens() {
        return produtos;
    }

    public void adicionarItem(Produto produto, int quantidade) {
        this.produtos.merge(produto, quantidade, Integer::sum);
    }

    public void removerItem(Produto produto) {
        if(produtos.get(produto) != null)
            this.produtos.remove(produto);
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long idPedido) {
        this.id = idPedido;
    }

    public long getFranquiaId() {
        return this.franquiaId;
    }

    public BigDecimal getTaxa() {
        return this.taxas;
    }

    public void setTaxa(BigDecimal taxa) {
        this.taxas = taxa;
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

    public void setFormaDePagamento(FormaDePagamento formaDePagamento) {
        this.formaDePagamento = formaDePagamento;
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

    public void setModalidadeDeEntrega(ModalidadeEntrega modalidadeEntrega) {
        this.modalidadeDeEntrega = modalidadeEntrega;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
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

        BigDecimal taxasAtuais =  (this.taxas == null) ? BigDecimal.ZERO : taxas;
        this.valorTotal = subtotalItens.add(taxasAtuais);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null || getClass() != obj.getClass())
            return false;

        Pedido outro = (Pedido) obj;

        return this.id == outro.getId();
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(id);
    }
}
