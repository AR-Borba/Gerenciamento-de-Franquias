package com.franquias.Model.entities.Usuários;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import com.franquias.Model.Produto;
import com.franquias.Model.entities.Pedido;
import com.franquias.Model.enums.FormaDePagamento;
import com.franquias.Model.enums.ModalidadeEntrega;
import com.franquias.Model.enums.StatusPedido;

public class Vendedor extends Usuario {
    long id;
    List<Pedido> pedidos;
    
    public Vendedor(String nome, String email, String senha, String cpf, long id){
        super(nome, cpf, email, senha);
        this.id = id;
        this.pedidos = new ArrayList<>();
    }
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void cadastrarPedido(Map<Produto, Integer> produtos, String cliente, LocalDateTime datahora, FormaDePagamento formaDePagamento, BigDecimal taxas, ModalidadeEntrega modalidadeDeEntrega, StatusPedido statusPedido){
        Pedido pedido = new Pedido(produtos, cliente, datahora, formaDePagamento, taxas, modalidadeDeEntrega, statusPedido);
        this.pedidos = List.of(pedido);
    }

    public void visualizarPedidos(){

    }

    public void editarPedidos(){

    }

    public BigDecimal calcularTotalVendas() {

        if(pedidos == null)
            return BigDecimal.ZERO;
        BigDecimal total = BigDecimal.ZERO;
        for (Pedido pedido : pedidos) {
            if (pedido.getStatusPedido() == StatusPedido.CONCLUIDO) {
                total = total.add(pedido.getValorTotal());
            }
        }
        return total;
    }
}
