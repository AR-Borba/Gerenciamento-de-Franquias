package com.franquias.Controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.validation.ValidationException;

import com.franquias.Model.Produto;
import com.franquias.Model.entities.Cliente;
import com.franquias.Model.entities.Pedido;
import com.franquias.Model.entities.Usuários.Vendedor;
import com.franquias.Model.enums.FormaDePagamento;
import com.franquias.Model.enums.ModalidadeEntrega;
import com.franquias.Model.enums.StatusPedido;
import com.franquias.Persistence.ClientePersistence;
import com.franquias.Persistence.PedidoPersistence;
import com.franquias.Persistence.ProdutoPersistence;
import com.franquias.Persistence.VendedorPersistence;
import com.franquias.exceptions.EstoqueInsuficienteException;
import com.franquias.exceptions.ProdutoNaoEncontradoException;

public class VendedorController {

    private AplicacaoPrincipal app;
    private Vendedor vendedorLogado;
    private Pedido pedidoAtual;
    private VendedorPersistence vendedorPersistence;
    private PedidoPersistence pedidoPersistence;
    private ProdutoPersistence produtoPersistence;
    private ClientePersistence clientePersistence;

    public VendedorController(AplicacaoPrincipal app, PedidoPersistence pedidoPersistence,
            ProdutoPersistence produtoPersistence, VendedorPersistence vendedorPersistence,
            ClientePersistence clientePersistence) {
        this.app = app;
        this.pedidoPersistence = pedidoPersistence;
        this.produtoPersistence = produtoPersistence;
        this.vendedorPersistence = vendedorPersistence;
        this.clientePersistence = clientePersistence;

        this.pedidoAtual = new Pedido();
    }

    public void iniciarSessao(Vendedor vendedor) {
        this.vendedorLogado = vendedor;
        this.iniciarNovoPedido();
    }

    public void iniciarNovoPedido() {
        this.pedidoAtual = new Pedido(vendedorLogado);
    }

    public void adicionarItemAoPedido(long idProduto, int quantidade)
            throws EstoqueInsuficienteException, ProdutoNaoEncontradoException {
        Produto produto = produtoPersistence.buscarPorId(idProduto);

        if (produto == null) {
            throw new ProdutoNaoEncontradoException("Produto não encontrado!");
        }

        if (produto.getQuantidadeEstoque() < quantidade) {
            throw new EstoqueInsuficienteException("Estoque insuficiente para o produto: " + produto.getProduto()
                    + ". Disponível: " + produto.getQuantidadeEstoque());
        }

        pedidoAtual.adicionarItem(produto, quantidade);
    }

    public void removerItemDoPedido(long idProduto) {
        Produto produto = produtoPersistence.buscarPorId(idProduto);

        pedidoAtual.removerItem(produto);
    }

    public void finalizaPedido() {
        pedidoPersistence.adicionarPedido(pedidoAtual);
        this.iniciarNovoPedido();
    }

    public Pedido getPedidoAtual() {
        return pedidoAtual;
    }

    public Pedido buscarPedidoPorId(long idPedido) {
        return pedidoPersistence.buscarPorId(idPedido);
    }

    public List<Pedido> getPedidosVendedor() {
        List<Long> idPedidos = vendedorLogado.getListaIdPedidos();

        if (idPedidos == null)
            return new ArrayList<>();

        List<Pedido> pedidosVendedor = new ArrayList<>();

        Pedido pedidoId;
        for (Long idPedido : idPedidos) {
            pedidoId = pedidoPersistence.buscarPorId(idPedido);

            if (pedidoId != null)
                pedidosVendedor.add(pedidoId);
        }

        return Collections.unmodifiableList(pedidosVendedor);
    }

    public List<Pedido> getPedidosVendedorPendentesAlteracao() {
        List<Long> idPedidos = vendedorLogado.getListaIdPedidos();

        if (idPedidos == null)
            return new ArrayList<>();

        List<Pedido> pedidosVendedor = new ArrayList<>();

        Pedido pedidoId;
        for (Long idPedido : idPedidos) {
            pedidoId = pedidoPersistence.buscarPorId(idPedido);

            if (pedidoId != null && pedidoId.getStatusPedido() == StatusPedido.EM_ALTERACAO)
                pedidosVendedor.add(pedidoId);
        }

        return Collections.unmodifiableList(pedidosVendedor);
    }

    public void atualizarPedido(Pedido pedido) {
        pedidoPersistence.update(pedido);
    }

    public void finalizarPedido(Cliente cliente, BigDecimal taxa, ModalidadeEntrega modalidadeEntrega,
            FormaDePagamento formaDePagamento) {
        this.pedidoAtual.setCliente(cliente);
        this.pedidoAtual.setTaxa(taxa);
        this.pedidoAtual.setModalidadeDeEntrega(modalidadeEntrega);
        this.pedidoAtual.setFormaDePagamento(formaDePagamento);
        this.pedidoAtual.setDataHora(LocalDateTime.now());
        this.pedidoAtual.setStatusPedido(StatusPedido.CONCLUIDO);

        this.pedidoAtual.calcularEAtualizaValorTotal();

        for (Map.Entry<Produto, Integer> item : pedidoAtual.getItens().entrySet()) {
            Produto produto = item.getKey();
            int quantidadeVendida = item.getValue();

            int novoEstoque = produto.getQuantidadeEstoque() - quantidadeVendida;
            produto.setQuantidadeEstoque(novoEstoque);

            produtoPersistence.update(produto);
        }

        pedidoPersistence.adicionarPedido(pedidoAtual);

        vendedorLogado.adicionarPedidoPorId(pedidoAtual.getId());

        vendedorPersistence.update(vendedorLogado);

        iniciarNovoPedido();
    }

    public List<Cliente> getClientesDisponiveis() {
        return Collections.unmodifiableList(clientePersistence.findAll());
    }

    public void adicionarCliente(Cliente cliente) throws ValidationException {

        if (clientePersistence.findByCpf(cliente.getCpf()) != null) {
            throw new ValidationException("O CPF '" + cliente.getCpf() + "' já está cadastrado no sistema.");
        }

        clientePersistence.add(cliente);
    }
}
