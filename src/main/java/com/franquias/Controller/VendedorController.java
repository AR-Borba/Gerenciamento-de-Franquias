package com.franquias.Controller;

import com.franquias.Model.Produto;
import com.franquias.Model.entities.Pedido;
import com.franquias.Model.entities.Usuários.Vendedor;
import com.franquias.Model.enums.StatusPedido;

public class VendedorController {
    
    private AplicacaoPrincipal app;
    private Vendedor vendedorLogado;
    private Pedido pedidoAtual;

    public VendedorController(AplicacaoPrincipal app) {
        this.app = app;
    }

    public void iniciarSessao(Vendedor vendedor) {
        this.vendedorLogado = vendedor;
        this.iniciarNovoPedido();
    }

    public void iniciarNovoPedido() {
        this.pedidoAtual = new Pedido(vendedorLogado);
    }

    public Produto adicionarItemAoPedido(String codProduto, int quantidade) {
        // Produto produto = new Produto(codProduto, codProduto, null, quantidade)
        
        // buscar o produto pelo código
        // adiciona ao pedidoAtual
        // retorn para view

        return null; // substituir por lógica real
    }

    public void finalizaPedido() { // recebe todos os dados do pedido
        // atualizar o pedido com os dados do pedidoAtual
        //pedidoAtual.setStatusPedido(StatusPedido.CONCLUIDO);
        // salvar pedido em arquivo

        // inicia novo pedido em branco
        this.iniciarNovoPedido();
    }

    public Pedido getPedidoAtual() {
        return pedidoAtual;
    }
}
