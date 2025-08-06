package com.franquias.View.PaineisGerente;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.franquias.Controller.PedidoController;
import com.franquias.Model.Produto;
import com.franquias.Model.entities.Pedido;

public class DialogVisualizarPedido extends JDialog {

    private Map<Produto, Integer> produtosNoPedido;
    private JTable tabelaProdutosPedido;
    private DefaultTableModel modeloTabelaProdutosNoPedido;

    PedidoController pedidoController;
    Pedido pedido;

    public DialogVisualizarPedido(Frame parent, Pedido pedidoSendoEditado, PedidoController pedidoController) {
        super(parent, "Editando Pedido ID: " + pedidoSendoEditado.getId(), true);
        this.pedido = pedidoSendoEditado;
        this.pedidoController = pedidoController;

        configurarUI();
        carregarDadosNaTabela();
    }

    private void configurarUI() {
        setLayout(new BorderLayout(10, 10));
        ((JPanel) getContentPane()).setBorder(new EmptyBorder(10, 10, 10, 10));

        add(criarPainelDadosGerais(), BorderLayout.NORTH);
        add(criarPainelProdutos(), BorderLayout.CENTER);
        add(criarPainelAcoes(), BorderLayout.SOUTH);

        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(getParent());
    }

    private JPanel criarPainelDadosGerais() {
        JPanel painel = new JPanel(new GridLayout(0, 1, 0, 5)); // GridLayout para empilhar verticalmente
        painel.setBorder(BorderFactory.createTitledBorder("Dados do Pedido"));

        painel.add(new JLabel("Vendedor: " + pedido.getVendedorResponsavel().getNome()));
        painel.add(new JLabel("Cliente: " + pedido.getCliente().getNome()));
        painel.add(new JLabel("Data/Hora: " + pedido.getDataHoraFormatada()));
        painel.add(new JLabel("Modalidade de Entrega: " + pedido.getModalidadeDeEntrega()));
        painel.add(new JLabel("Forma de Pagamento: " + pedido.getFormaDePagamento()));
        painel.add(new JLabel(String.format("Taxas: R$ %.2f", pedido.getTaxas())));

        return painel;
    }

    private JPanel criarPainelProdutos() {
        JPanel painelProdutos = new JPanel(new BorderLayout());

        modeloTabelaProdutosNoPedido = new DefaultTableModel();
        modeloTabelaProdutosNoPedido.addColumn("Qtd");
        modeloTabelaProdutosNoPedido.addColumn("Produto");
        modeloTabelaProdutosNoPedido.addColumn("Preço");

        tabelaProdutosPedido = new JTable(modeloTabelaProdutosNoPedido);
        painelProdutos.add(new JScrollPane(tabelaProdutosPedido), BorderLayout.CENTER);

        return painelProdutos;
    }

    private void carregarDadosNaTabela() {
        modeloTabelaProdutosNoPedido.setRowCount(0);

        produtosNoPedido = pedido.getItens();

        for (Map.Entry<Produto, Integer> produtoEqtd : produtosNoPedido.entrySet()) {
            Object[] rowData = {
                    produtoEqtd.getValue(),
                    produtoEqtd.getKey().getProduto(),
                    produtoEqtd.getKey().getPreco(),
            };
            modeloTabelaProdutosNoPedido.addRow(rowData);
        }
    }

    private JPanel criarPainelAcoes() {
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());

        painelBotoes.add(btnFechar);

        return painelBotoes;
    }
}