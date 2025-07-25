package com.franquias.Controller;

import java.util.List;
import java.util.ArrayList;

import com.franquias.Model.Produto;
import com.franquias.Model.entities.Pedido;
import com.franquias.Model.entities.Usuários.Vendedor;
import com.franquias.Persistence.VendedorPersistence;

public class GerenteController {

    private AplicacaoPrincipal app;
    private VendedorPersistence vendedorPersistence;

    public GerenteController(AplicacaoPrincipal app) {
        this.app = app;
        vendedorPersistence = new VendedorPersistence();
    }

    public List<Vendedor> getEquipeDeVendasOrdenadaPorVendas() {
        List<Vendedor> vendedores;

        vendedores = vendedorPersistence.findAll();

        return vendedores;
    }

    public void editarVendedor(long idVendedor) {
        // Implementar lógica para editar um vendedor
    }

    public void removerVendedor(long idVendedor) {
        // Implementar lógica para remover um vendedor
    }

    public void adicionarVendedor(Vendedor vendedor) {
        vendedorPersistence.adicionarVendedor(vendedor);
    }

    public List<Pedido> getPedidos() {
        // Implementar lógica para obter a lista de pedidos
        return new ArrayList<Pedido>();
    }

    public List<Produto> getProdutos() {
        // Implementar lógica para obter a lista de produtos
        return new ArrayList<Produto>();
    }

    public void editarProduto(long idProduto) {
        // Implementar lógica para editar produto
    }

    public void removerProduto(long idProduto) {
        // Implementar lógica para remover um produto
    }

    public void adicionarProduto(Produto produto) {
        // Implementar lógica para adicioar um novo produto
    }
}
