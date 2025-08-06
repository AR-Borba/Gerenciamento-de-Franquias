package com.franquias.View;

import java.awt.*;
import java.math.BigDecimal;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.NumberFormatter;
import java.text.DecimalFormat;

import com.franquias.Controller.PedidoController;
import com.franquias.Model.entities.Pedido;
import com.franquias.Model.Produto;
import com.franquias.Model.enums.FormaDePagamento;
import com.franquias.Model.enums.ModalidadeEntrega;
import com.franquias.exceptions.EstoqueInsuficienteException;

public class DialogAlterarPedido extends JDialog {

    private PedidoController pedidoController;
    private Pedido pedido;

    // Componentes da UI
    private JComboBox<FormaDePagamento> cbFormaPagamento;
    private JComboBox<ModalidadeEntrega> cbModalidadeEntrega;
    private JFormattedTextField tfTaxas;
    private JTextField tfIdProduto;
    private JTextField tfQtdProduto;
    private JTable tabelaItens;
    private DefaultTableModel modeloTabela;
    private JLabel lblTotal;

    private boolean salvo = false;

    public DialogAlterarPedido(Frame parent, Pedido pedidoSendoEditado, PedidoController controller) {
        super(parent, "Editando Pedido ID: " + pedidoSendoEditado.getId(), true);

        this.pedido = pedidoSendoEditado;
        this.pedidoController = controller;

        configurarUI();
        preencherCamposIniciais();
        carregarItensNaTabela();
    }

    private void configurarUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel painelSuperior = criarPainelSuperior();
        JPanel painelCentral = criarPainelCentral();
        JPanel painelRodape = criarPainelRodape();

        add(painelSuperior, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);
        add(painelRodape, BorderLayout.SOUTH);

        pack();
        setMinimumSize(getSize());
        setLocationRelativeTo(getParent());
    }

    // --- MÉTODOS DE CONSTRUÇÃO DA UI ---

    private JPanel criarPainelSuperior() {
        JPanel painel = new JPanel(new BorderLayout());

        painel.add(criarPainelAdicionarProduto(), BorderLayout.WEST);
        painel.add(criarPainelDadosGerais(), BorderLayout.CENTER);

        return painel;
    }

    private JPanel criarPainelAdicionarProduto() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Adicionar/Editar Item"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        tfIdProduto = new JTextField(5);
        tfQtdProduto = new JTextField("1", 5);
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Carregar p/ Edição");

        btnAdicionar.addActionListener(e -> adicionarItem());
        btnEditar.addActionListener(e -> carregarItemParaEdicao());

        gbc.gridy = 0;
        gbc.gridx = 0;
        painel.add(new JLabel("ID Produto:"), gbc);
        gbc.gridy = 0;
        gbc.gridx = 1;
        painel.add(tfIdProduto, gbc);
        gbc.gridy = 1;
        gbc.gridx = 0;
        painel.add(new JLabel("Quantidade:"), gbc);
        gbc.gridy = 1;
        gbc.gridx = 1;
        painel.add(tfQtdProduto, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnAdicionar);
        painel.add(painelBotoes, gbc);

        return painel;
    }

    private JPanel criarPainelDadosGerais() {
        JPanel painel = new JPanel(new GridBagLayout());
        painel.setBorder(BorderFactory.createTitledBorder("Dados Gerais do Pedido"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        cbModalidadeEntrega = new JComboBox<>(ModalidadeEntrega.values());
        cbFormaPagamento = new JComboBox<>(FormaDePagamento.values());

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        NumberFormatter decimalFormatter = new NumberFormatter(decimalFormat);
        decimalFormatter.setValueClass(BigDecimal.class);
        decimalFormatter.setAllowsInvalid(false);
        tfTaxas = new JFormattedTextField(decimalFormatter);

        gbc.anchor = GridBagConstraints.EAST;
        gbc.gridy = 0;
        gbc.gridx = 0;
        painel.add(new JLabel("Forma de Pagamento:"), gbc);
        gbc.gridy = 1;
        gbc.gridx = 0;
        painel.add(new JLabel("Modalidade de Entrega:"), gbc);
        gbc.gridy = 2;
        gbc.gridx = 0;
        painel.add(new JLabel("Taxas (R$):"), gbc);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.gridx = 1;

        gbc.gridy = 0;
        painel.add(cbFormaPagamento, gbc);
        gbc.gridy = 1;
        painel.add(cbModalidadeEntrega, gbc);
        gbc.gridy = 2;
        painel.add(tfTaxas, gbc);

        return painel;
    }

    private JPanel criarPainelCentral() {
        JPanel painel = new JPanel(new BorderLayout(5, 5));
        painel.setBorder(BorderFactory.createTitledBorder("Itens no Pedido"));

        String[] colunas = { "ID Produto", "Nome", "Qtd", "Subtotal (R$)" };
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaItens = new JTable(modeloTabela);
        painel.add(new JScrollPane(tabelaItens), BorderLayout.CENTER);

        JPanel painelAcoesItens = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnRemover = new JButton("Remover Item Selecionado");
        btnRemover.addActionListener(e -> removerItem());
        painelAcoesItens.add(btnRemover);
        painel.add(painelAcoesItens, BorderLayout.SOUTH);

        return painel;
    }

    private JPanel criarPainelRodape() {
        JPanel painel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        lblTotal = new JLabel("Total: R$ 0.00");
        lblTotal.setFont(lblTotal.getFont().deriveFont(Font.BOLD, 16f));

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(e -> onCancelar());

        JButton btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.addActionListener(e -> onSalvar());

        painel.add(lblTotal);
        painel.add(btnCancelar);
        painel.add(btnSalvar);
        return painel;
    }

    private void preencherCamposIniciais() {
        tfTaxas.setValue(pedido.getTaxas());
        cbModalidadeEntrega.setSelectedItem(pedido.getModalidadeDeEntrega());
        cbFormaPagamento.setSelectedItem(pedido.getFormaDePagamento());
    }

    private void carregarItensNaTabela() {
        modeloTabela.setRowCount(0);
        for (Map.Entry<Produto, Integer> entrada : pedido.getItens().entrySet()) {
            Produto p = entrada.getKey();
            Integer qtd = entrada.getValue();
            BigDecimal subtotal = p.getPreco().multiply(BigDecimal.valueOf(qtd));
            modeloTabela.addRow(new Object[] { p.getId(), p.getProduto(), qtd, subtotal });
        }
        atualizarTotal();
    }

    private void atualizarTotal() {
        pedido.calcularEAtualizaValorTotal();
        lblTotal.setText(String.format("Total: R$ %.2f", pedido.getValorTotal()));
    }

    private void adicionarItem() {
        try {
            long idProduto = Long.parseLong(tfIdProduto.getText());
            int qtdProduto = Integer.parseInt(tfQtdProduto.getText());

            if (qtdProduto <= 0) { // Alterado para <= 0, pois 0 também é inválido
                JOptionPane.showMessageDialog(this, "A quantidade deve ser maior que zero!", "Erro de Validação",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            pedidoController.adicionarItem(pedido, idProduto, qtdProduto);

            carregarItensNaTabela();

            tfIdProduto.setText("");
            tfQtdProduto.setText("1");
            tfIdProduto.requestFocus();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código e quantidade devem ser números!", "Erro de Validação",
                    JOptionPane.WARNING_MESSAGE);
        } catch (EstoqueInsuficienteException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Estoque Insuficiente!", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void carregarItemParaEdicao() {
        int selectedRow = tabelaItens.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um item na tabela para editar.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        long idProduto = (long) modeloTabela.getValueAt(selectedRow, 0);
        Integer qtdAtual = (Integer) modeloTabela.getValueAt(selectedRow, 2);

        pedidoController.removerItemDoPedido(pedido, idProduto);

        carregarItensNaTabela();

        tfIdProduto.setText(String.valueOf(idProduto));
        tfQtdProduto.setText(String.valueOf(qtdAtual));

        tfQtdProduto.requestFocus();
        tfQtdProduto.selectAll();
    }

    private void removerItem() {
        int selectedRow = tabelaItens.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um item na tabela para remover.",
                    "Nenhum Item Selecionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Tem certeza que deseja remover o item selecionado do pedido?",
                "Confirmar Remoção",
                JOptionPane.YES_NO_OPTION);

        if (confirmacao == JOptionPane.YES_OPTION) {
            long idProduto = (long) modeloTabela.getValueAt(selectedRow, 0);

            pedidoController.removerItemDoPedido(pedido, idProduto);

            carregarItensNaTabela();

            JOptionPane.showMessageDialog(this, "Item removido com sucesso.", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void onSalvar() {
        if (tfTaxas.getValue() == null) {
            JOptionPane.showMessageDialog(this, "Taxa é obtrigatório.", "Erro de Validação",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        pedido.setTaxa((BigDecimal) tfTaxas.getValue());
        pedido.setModalidadeDeEntrega((ModalidadeEntrega) cbModalidadeEntrega.getSelectedItem());
        pedido.setFormaDePagamento((FormaDePagamento) cbFormaPagamento.getSelectedItem());

        pedidoController.salvarAlteracoes(pedido);

        this.salvo = true;
        dispose();
    }

    private void onCancelar() {
        this.salvo = false;
        dispose();
    }

    public boolean foiSalvo() {
        return this.salvo;
    }
}