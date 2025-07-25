package com.franquias.Controller;

import com.franquias.Model.Produto;
import com.franquias.Model.entities.Pedido;
import com.franquias.Model.entities.Vendedor;

public class VendedorController {
    
    private AplicacaoPrincipal app;
    private Vendedor vendedorLogado;
    private Pedido pedidoAtual;

    public VendedorController(AplicacaoPrincipal app) {
        this.app = app;
    }

    public void iniciarSessao(Vendedor vendedor) {
        this.vendedorLogado = vendedor;
        this.inciarNovoPedido();
    }

    public void iniciarNovoPedido() {
        this.pedidoAtual = new Pedido(vendedorLogado);
    }

    public Produto adicionarItemAoPedido(String codProduto, int quantidade) {
        // buscar o produto pelo código
        // adiciona ao pedidoAtual
        // retorn para view

        return null; // substituir por lógica real
    }

    public void finalizaPedido() { // recebe todos os dados do pedido
        // salva o pedido em um arquivo
        this.pedidoAtual = null; // limpa o pedido atual
    }

    public void deslogar() {
        this.vendedorLogado = null;
        this.pedidoAtual = null;
        app.mostrarTela("LOGIN");
    }
}
