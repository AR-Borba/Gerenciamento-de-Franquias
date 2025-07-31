package com.franquias.Controller;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;

import com.franquias.Model.Produto;
import com.franquias.Model.entities.Pedido;
import com.franquias.Model.entities.Usuários.Vendedor;
import com.franquias.Model.enums.StatusPedido;
import com.franquias.Persistence.PedidoPersistence;
import com.franquias.Persistence.ProdutoPersistence;
import com.franquias.Persistence.VendedorPersistence;

public class GerenteController {

    private AplicacaoPrincipal app;
    private VendedorPersistence vendedorPersistence;
    private ProdutoPersistence produtoPersistence;
    private PedidoPersistence pedidoPersistence;

    public GerenteController(AplicacaoPrincipal app) {
        this.app = app;
        this.vendedorPersistence = new VendedorPersistence();
        this.produtoPersistence = new ProdutoPersistence();
        this.pedidoPersistence = new PedidoPersistence();
    }

    public List<Vendedor> getEquipeDeVendasOrdenadaPorVendas() {
        List<Vendedor> vendedores;

        vendedores = vendedorPersistence.findAll();

        return vendedores;
    }

    public void editarVendedor(Vendedor vendedorEditado) {
        vendedorPersistence.update(vendedorEditado);
    }

    public Vendedor buscarVendedorPorId(long idVendedor) {
        return vendedorPersistence.buscarPorId(idVendedor);
    }

    public void removerVendedor(long idVendedor) {
        vendedorPersistence.removerVendedor(idVendedor);
    }

    public void adicionarVendedor(Vendedor vendedor) {
        vendedorPersistence.adicionarVendedor(vendedor);
    }

    public List<Pedido> getPedidos() {
        // Implementar lógica para obter a lista de pedidos
        return new ArrayList<Pedido>();
    }

    public List<Produto> getProdutos() {
        List<Produto> produtos;

        produtos = produtoPersistence.findAll();

        return produtos;
    }

    public void editarProduto(Produto produtoEditado) {
        produtoPersistence.update(produtoEditado);
    }

    public void removerProduto(long idProduto) {
        produtoPersistence.removerProduto(idProduto);
    }

    public void adicionarProduto(Produto produto) {
        produtoPersistence.adicionarProduto(produto);
    }

    public Produto buscarProdutoPorId(long idProduto) {
        return produtoPersistence.buscarPorId(idProduto);
    }

    public BigDecimal calcularTotalVendasPorVendedor(Vendedor vendedor) {
        if (vendedor.getListaIdPedidos() == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = BigDecimal.ZERO;
        for (Long idPedido : vendedor.getListaIdPedidos()) {
            // Busca o pedido UMA VEZ por iteração
            Pedido pedido = pedidoPersistence.buscarPorId(idPedido);
            
            // Verifica se o pedido foi encontrado e se está concluído
            if (pedido != null && pedido.getStatusPedido() == StatusPedido.CONCLUIDO) {
                total = total.add(pedido.getValorTotal());
            }
        }
        return total;
    }
}
