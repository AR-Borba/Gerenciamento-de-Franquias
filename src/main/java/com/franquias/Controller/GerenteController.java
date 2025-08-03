package com.franquias.Controller;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;

import com.franquias.Model.Produto;
import com.franquias.Model.entities.Pedido;
import com.franquias.Model.entities.Usuários.Gerente;
import com.franquias.Model.entities.Usuários.Vendedor;
import com.franquias.Model.enums.StatusPedido;
import com.franquias.Persistence.GerentePersistence;
import com.franquias.Persistence.PedidoPersistence;
import com.franquias.Persistence.ProdutoPersistence;
import com.franquias.Persistence.VendedorPersistence;

public class GerenteController {

    private AplicacaoPrincipal app;
    private GerentePersistence gerentePersistence;
    private VendedorPersistence vendedorPersistence;
    private ProdutoPersistence produtoPersistence;
    private PedidoPersistence pedidoPersistence;
    private Gerente gerenteLogado;

    public GerenteController(AplicacaoPrincipal app, 
                             VendedorPersistence vendedorPersistence,
                             ProdutoPersistence produtoPersistence,
                             PedidoPersistence pedidoPersistence) {
        this.app = app;
        this.gerentePersistence = new GerentePersistence();
        this.vendedorPersistence = vendedorPersistence;
        this.produtoPersistence = produtoPersistence;
        this.pedidoPersistence = pedidoPersistence;
    }

    public void iniciarSessao(Gerente gerente) {
        this.gerenteLogado = gerente;
    }

    public List<Vendedor> getEquipeDeVendasOrdenadaPorVendas() {
        if(gerenteLogado == null)
            return new ArrayList<>();

        long idFranquia = gerenteLogado.getFranquiaId();

        List<Vendedor> vendedores = vendedorPersistence.findByFranquia  (idFranquia);

        Comparator<Vendedor> comparadorPorVendas = Comparator.comparing(
            vendedor -> calcularTotalVendasPorVendedor(vendedor)
        );

        vendedores.sort(comparadorPorVendas);

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

        gerenteLogado.adicionarVendedor(vendedor);

        gerentePersistence.update(gerenteLogado);
    }

     public void autorizarAlteracaoPedido(Pedido pedido) {
        if (pedido != null && pedido.getStatusPedido() == StatusPedido.PENDENTE_ALTERACAO) {
            pedido.setStatusPedido(StatusPedido.EM_ALTERACAO);
            pedidoPersistence.update(pedido); // Salva a alteração
        }
    }
    
    public void autorizarExclusaoPedido(Pedido pedido) {
        if (pedido != null && pedido.getStatusPedido() == StatusPedido.PENDENTE_EXCLUSAO) {
            pedido.setStatusPedido(StatusPedido.CANCELADO);
            pedidoPersistence.update(pedido); // Salva a alteração
        }
    }

    public List<Pedido> getPedidos() {

        if(gerenteLogado == null)
            return new ArrayList<>();


        long idFranquia = gerenteLogado.getFranquiaId();

        return pedidoPersistence.findByFranquia(idFranquia);
    }

    public List<Pedido> getPedidosPendentes() {
        if(gerenteLogado == null)
            return new ArrayList<>();


        long idFranquia = gerenteLogado.getFranquiaId();

        List<Pedido> pedidosFranquia = pedidoPersistence.findByFranquia(idFranquia);

        List<Pedido> pedidosFranquiaPendentes = new ArrayList<>();

        for(Pedido pedido : pedidosFranquia)
            if(pedido.getStatusPedido() == StatusPedido.PENDENTE_ALTERACAO || pedido.getStatusPedido() == StatusPedido.PENDENTE_EXCLUSAO)
                pedidosFranquiaPendentes.add(pedido);

        return pedidosFranquiaPendentes;
    }

    public Pedido buscarPedidoPorId(long idPedido) {
        return pedidoPersistence.buscarPorId(idPedido);
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
