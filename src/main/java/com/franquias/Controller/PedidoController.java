package com.franquias.Controller;

import com.franquias.Model.Produto;
import com.franquias.Model.entities.Pedido;
import com.franquias.Model.enums.StatusPedido;
import com.franquias.Persistence.PedidoPersistence;
import com.franquias.Persistence.ProdutoPersistence;

public class PedidoController {
    
    private PedidoPersistence pedidoPersistence;
    private ProdutoPersistence produtoPersistence;

    public PedidoController() {
        this.pedidoPersistence = new PedidoPersistence();
        this.produtoPersistence = new ProdutoPersistence();
    }

    public void solicitarAlteracao(Pedido pedido) {
        pedido.setStatusPedido(StatusPedido.PENDENTE_ALTERACAO);
    }

    public void solicitarCancelamento(Pedido pedido) {
        pedido.setStatusPedido(StatusPedido.PENDENTE_EXCLUSAO);
    }

    public void adicionarItem(Pedido pedido, long idProduto, int qtd) {
        Produto produto = produtoPersistence.buscarPorId(idProduto);

        pedido.adicionarItem(produto, qtd);
    }
    public void removerItem(Pedido pedido, long idProduto) {
        Produto produto = produtoPersistence.buscarPorId(idProduto);

        pedido.removerItem(produto);
    }


}
