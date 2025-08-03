package com.franquias.View.PaineisVendedor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.Flow;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;

import com.franquias.Controller.PedidoController;
import com.franquias.Model.Produto;
import com.franquias.Model.entities.Pedido;
import com.franquias.Model.enums.FormaDePagamento;
import com.franquias.Model.enums.ModalidadeEntrega;
import com.franquias.Model.enums.StatusPedido;

public class DialogAlterarPedido extends JDialog{
    private JTextField tfCliente;
    private JFormattedTextField tfTaxas;
    private JComboBox<ModalidadeEntrega> fieldModalidadeEntrega;
    private JComboBox<FormaDePagamento> fieldFormaPagamento;

    private Map<Produto, Integer> produtosNoPedido;
    private JTable tabelaProdutosPedido;
    private DefaultTableModel modeloTabelaProdutosNoPedido;
    private String cliente;
    private BigDecimal taxa;
    private ModalidadeEntrega modalidadeEntrega;
    private FormaDePagamento formaDePagamento;
    
    PedidoController pedidoController;
    Pedido pedido;

    boolean salvo;

    public DialogAlterarPedido(Frame parent, Pedido pedidoSendoEditado, PedidoController pedidoController) {
        super(parent, "Editando Pedido ID: " + pedidoSendoEditado.getId(), true);
        this.pedido = pedidoSendoEditado;
        this.pedidoController = pedidoController;

        configurarUI();
        preencherCampos();
    }

    private void configurarUI() {
        setSize(400, 300);
        setLayout(new BorderLayout(10, 10));
        
        this.add(dadosCliente(), BorderLayout.WEST);
        this.add(criarPainelProdutos());
        this.add(painelAcoes());

        pack();
        setLocationRelativeTo(getParent());
    }

    private JPanel dadosCliente() {
        JPanel painelCliente = new JPanel();

        painelCliente.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        setLocationRelativeTo(null);
        
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

        NumberFormatter decimalFormatter = new NumberFormatter(decimalFormat);
        decimalFormatter.setValueClass(BigDecimal.class); // << A CLASSE É BigDecimal
        decimalFormatter.setAllowsInvalid(false);

        tfTaxas = new JFormattedTextField(decimalFormatter);
        tfCliente = new JTextField(20);
        fieldModalidadeEntrega = new JComboBox<>(ModalidadeEntrega.values());
        fieldFormaPagamento = new JComboBox<>(FormaDePagamento.values());

        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCliente.add(new JLabel("Cliente:"), gbc);
        gbc.gridx = 1;
        painelCliente.add(tfCliente, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        painelCliente.add(new JLabel("Taxas:"), gbc);
        gbc.gridx = 1;
        painelCliente.add(tfTaxas, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        painelCliente.add(fieldModalidadeEntrega, gbc);

        gbc.gridx = 1;
        painelCliente.add(fieldFormaPagamento, gbc);

        return painelCliente;
    }

    private JPanel criarPainelProdutos() {
        JPanel painelProdutos = new JPanel(new BorderLayout());

        modeloTabelaProdutosNoPedido = new DefaultTableModel();
        modeloTabelaProdutosNoPedido.addColumn("Qtd");
        modeloTabelaProdutosNoPedido.addColumn("Produto");
        modeloTabelaProdutosNoPedido.addColumn("Preço");

        tabelaProdutosPedido = new JTable(modeloTabelaProdutosNoPedido);
        painelProdutos.add(new JScrollPane(tabelaProdutosPedido), BorderLayout.CENTER);

        JButton btnExcluir = new JButton("Excluir Item");
        btnExcluir.addActionListener(e -> excluir());
        JButton btnAdicionar = new JButton("Adicionar Item");
        btnAdicionar.addActionListener(e -> adicionar());

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        painelBotoes.add(btnExcluir, BorderLayout.SOUTH);
        painelBotoes.add(btnAdicionar, BorderLayout.SOUTH);

        painelProdutos.add(painelBotoes, BorderLayout.SOUTH);

        return painelProdutos;
    }

    private void desenhaTabelaDeProdutos(JPanel painelProdutos) {
        
    }

    private void carregarDadosNaTabela() {
        modeloTabelaProdutosNoPedido.setRowCount(0);

        produtosNoPedido = pedido.getItens();

        for(Map.Entry<Produto, Integer> produtoEqtd : produtosNoPedido.entrySet()) {
            Object[] rowData = {
                produtoEqtd.getValue(),
                produtoEqtd.getKey().getProduto(),
                produtoEqtd.getKey().getPreco(),
            };
            modeloTabelaProdutosNoPedido.addRow(rowData);
        }
    }

    private JPanel painelAcoes() {
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        
        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> dispose());
        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> onSalvar());

        
        painelBotoes.add(btnCancelar);
        painelBotoes.add(btnSalvar);
    
        return painelBotoes;
    }

    private void excluir() {

        int selectedRow = tabelaProdutosPedido.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha", "Erro de validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nomePordutoSelecionado = (String) modeloTabelaProdutosNoPedido.getValueAt(selectedRow, 1);
        
        Produto produtoParaRemover = null;
        for(Produto produto : produtosNoPedido.keySet()) {
            if(produto.getProduto().equals(nomePordutoSelecionado)) {
                produtoParaRemover = produto;
                break;
            }
        }

        if(produtoParaRemover != null) {
            produtosNoPedido.remove(produtoParaRemover);

            carregarDadosNaTabela();

            pedido.calcularEAtualizaValorTotal();

            JOptionPane.showMessageDialog(this, "Produto removido com sucesso", "Remoção de Produto", JOptionPane.INFORMATION_MESSAGE);
            // atualizar estoque?
        }
    }

    private void adicionar() {
        DialogAdicionarProduto dialog = new DialogAdicionarProduto(null);

        long idNovoProduto = dialog.getId();
        int qtdNovoProduto = dialog.getQtd();

        pedidoController.adicionarItem(pedido, idNovoProduto, qtdNovoProduto);
    }

    private void preencherCampos() {
        tfCliente.setText(pedido.getCliente().getNome());
        tfTaxas.setValue(pedido.getTaxa());
        fieldModalidadeEntrega.setSelectedItem(pedido.getModalidadeDeEntrega());
        fieldFormaPagamento.setSelectedItem(pedido.getFormaDePagamento());
    }

    private void onSalvar() {
        this.cliente = tfCliente.getText();
        this.taxa = (BigDecimal) tfTaxas.getValue();
        this.formaDePagamento = (FormaDePagamento) this.fieldFormaPagamento.getSelectedItem();
        this.modalidadeEntrega = (ModalidadeEntrega) this.fieldModalidadeEntrega.getSelectedItem();

        if(cliente.isBlank() || taxa == null || formaDePagamento == null || modalidadeEntrega == null) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios", "Erro de validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // pedido.setCliente(tfCliente.getText());
        pedido.setTaxa((BigDecimal) tfTaxas.getValue());
        pedido.setModalidadeDeEntrega(modalidadeEntrega);
        pedido.setFormaDePagamento(formaDePagamento);
        pedido.setStatusPedido(StatusPedido.CONCLUIDO);

        pedidoController.solicitarAlteracao(pedido);

        this.salvo = true;
        dispose();
    }

    public boolean foiSalvo() {
        return this.salvo;
    }

    public String getCliente() {
        return this.cliente;
    }

    public BigDecimal getTaxa() {
        return this.taxa;
    }

    public ModalidadeEntrega getModalidadeEntrega() {
        return this.modalidadeEntrega;
    }

    public FormaDePagamento getFormaDePagamento() {
        return this.formaDePagamento;
    }
}