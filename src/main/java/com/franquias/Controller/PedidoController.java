package com.franquias.Controller;

import com.franquias.Model.Produto;
import com.franquias.Model.entities.Pedido;
import com.franquias.Model.enums.StatusPedido;
import com.franquias.Persistence.PedidoPersistence;
import com.franquias.Persistence.ProdutoPersistence;
import com.franquias.exceptions.EstoqueInsuficienteException;

public class PedidoController {

    private PedidoPersistence pedidoPersistence;
    private ProdutoPersistence produtoPersistence;

    public PedidoController(PedidoPersistence pedidoPersistence, ProdutoPersistence produtoPersistence) {
        this.pedidoPersistence = pedidoPersistence;
        this.produtoPersistence = produtoPersistence;
    }

    public void solicitarAlteracao(Pedido pedido) {
        pedido.setStatusPedido(StatusPedido.PENDENTE_ALTERACAO);
    }

    public void solicitarCancelamento(Pedido pedido) {
        pedido.setStatusPedido(StatusPedido.PENDENTE_EXCLUSAO);
    }

    public void adicionarItem(Pedido pedido, long idProduto, int qtd) throws EstoqueInsuficienteException {
        Produto produto = produtoPersistence.buscarPorId(idProduto);

        if (produto.getQuantidadeEstoque() < qtd) {
            throw new EstoqueInsuficienteException("Estoque insuficiente para o produto: " + produto.getProduto()
                    + ". Disponível: " + produto.getQuantidadeEstoque());
        }

        pedido.adicionarItem(produto, qtd);
    }

    public void removerItem(Pedido pedido, long idProduto) {
        Produto produto = produtoPersistence.buscarPorId(idProduto);

        pedido.removerItem(produto);
    }

    public void salvarAlteracoes(Pedido pedido) {
        pedido.calcularEAtualizaValorTotal();
        pedido.setStatusPedido(StatusPedido.CONCLUIDO);

        pedidoPersistence.update(pedido);
    }

    public void removerItemDoPedido(Pedido pedido, long idProduto) {
        Produto produtoParaRemover = produtoPersistence.buscarPorId(idProduto);

        if (pedido != null && produtoParaRemover != null) {
            pedido.removerItem(produtoParaRemover);

            pedido.calcularEAtualizaValorTotal();

            System.out.println("Item removido do pedido em memória.");
        }
    }
}
