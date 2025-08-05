package com.franquias.Model.entities.Usuários;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.franquias.Model.entities.Pedido;
import com.franquias.Persistence.PedidoPersistence;

public class Vendedor extends Usuario {
    private long id;
    private List<Long> idPedidos;
    private long franquiaId;
    
    public Vendedor(String nome, String email, String senha, String cpf, long franquiaId){
        super(nome, email, senha, cpf);
        this.id = 0;
        this.idPedidos = new ArrayList<>();
        this.franquiaId = franquiaId;
    }

    public Vendedor() {
        
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFranquiaId() {
        return this.franquiaId;
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
        if(this.idPedidos == null) 
            this.idPedidos = new ArrayList<>();
        return this.idPedidos;
    }

    public BigDecimal getReceita() {
        PedidoPersistence pedidoPersistence = new PedidoPersistence();
        BigDecimal acumulado = BigDecimal.ZERO;
        if (this.idPedidos != null) {
            for(long indice : this.idPedidos) {
                Pedido pedido = pedidoPersistence.buscarPorId(indice);
                if (pedido != null && pedido.getValorTotal() != null) {
                    acumulado = acumulado.add(pedido.getValorTotal());
                }
            }
        }
        return acumulado;
    }
}
