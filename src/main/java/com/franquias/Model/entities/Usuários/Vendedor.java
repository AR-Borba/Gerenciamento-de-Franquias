package com.franquias.Model.entities.Usuários;

import java.util.ArrayList;
import java.util.List;

import com.franquias.Model.entities.Pedido;


public class Vendedor extends Usuario {
    private long id;
    private List<Long> idPedidos;
    
    public Vendedor(String nome, String email, String senha, String cpf){
        super(nome, email, senha, cpf);
        this.idPedidos = new ArrayList<>();
        idPedidos.add((long) 1);
    }

    public Vendedor() {
        
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // public void cadastrarPedido(Map<Produto, Integer> produtos, String cliente, LocalDateTime datahora, FormaDePagamento formaDePagamento, BigDecimal taxas, ModalidadeEntrega modalidadeDeEntrega, StatusPedido statusPedido){
    //     Pedido pedido = new Pedido(produtos, cliente, datahora, formaDePagamento, taxas, modalidadeDeEntrega, statusPedido);
    //     this.pedidos = List.of(pedido);
    // }

    public void adicionarPedido(Pedido pedido) {
        if(this.idPedidos == null) {
            this.idPedidos = new ArrayList<>();
        }
        this.idPedidos.add(pedido.getId());
    }

    public void adicionarPedidoPorId(long idPedido) {
        if(this.idPedidos == null) {
            this.idPedidos = new ArrayList<>();
        }
        this.idPedidos.add(idPedido);
    }

    public void visualizarPedidos(){

    }

    public void editarPedidos(){

    }

    public List<Long> getListaIdPedidos() {
        return this.idPedidos;
    }

    // public BigDecimal calcularTotalVendas() {

    //     if(idPedidos == null)
    //         return BigDecimal.ZERO;
    //     BigDecimal total = BigDecimal.ZERO;
    //     for (Long idPedido : idPedidos) {
    //         if (pedidoPersistence.buscarPorId(idPedido).getStatusPedido() == StatusPedido.CONCLUIDO) {
    //             total = total.add(pedidoPersistence.buscarPorId(idPedido).getValorTotal());
    //         }
    //     }
    //     return total;
    // }
}
