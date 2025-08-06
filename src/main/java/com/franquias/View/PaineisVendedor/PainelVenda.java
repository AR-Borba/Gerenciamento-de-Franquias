package com.franquias.View.PaineisVendedor;

import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.math.BigDecimal;

import com.franquias.Controller.VendedorController;
import com.franquias.Model.Produto;
import com.franquias.Model.entities.Cliente;
import com.franquias.Model.enums.FormaDePagamento;
import com.franquias.Model.enums.ModalidadeEntrega;
import com.franquias.Utils.ValidadorNumero;
import com.franquias.exceptions.EstoqueInsuficienteException;
import com.franquias.exceptions.ProdutoNaoEncontradoException;

public class PainelVenda extends JPanel {
    private JFrame framePrincipal;
    private VendedorController controller;

    private JTable tabelaProdutosPedido;
    private DefaultTableModel modeloTabelaProdutosNoPedido;
    private Cliente cliente;
    private FormaDePagamento formaDePagamento;
    private BigDecimal taxas;
    private ModalidadeEntrega modalidadeDeEntrega;

    JTextField tfCodigo;
    JTextField tfQtd;

    BigDecimal valorPedido;
    JLabel areaTotal;

    public PainelVenda(VendedorController vendedorController, JFrame framePrincipal) {
        this.controller = vendedorController;
        this.framePrincipal = framePrincipal;

        this.setLayout(new BorderLayout(5, 5));

        desenhaFormularioDeProdutos();
        desenhaListaDeProdutos();
        criarPainelAcoes();
    }

    private void desenhaFormularioDeProdutos() {
        JPanel painelFormulario = new JPanel();
        painelFormulario.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        // linha 0: código do produto
        gbc.gridy = 0;

        gbc.gridx = 0;
        painelFormulario.add(new JLabel("Código do Produto: "), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        tfCodigo = new JTextField(3);
        painelFormulario.add(tfCodigo, gbc);

        // linha 1: quantidade
        gbc.gridy = 1;

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelFormulario.add(new JLabel("Qtd: "), gbc);

        gbc.gridx = 1;
        tfQtd = new JTextField("1", 3);
        painelFormulario.add(tfQtd, gbc);

        // linha 2: botão adicionar
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.WEST;
        JButton btRemover = new JButton("Remover");
        JButton btAdicionar = new JButton("Adicionar");

        btRemover.addActionListener(e -> removerProdutoDoPedido());
        btAdicionar.addActionListener(e -> adicionarProdutoAoPedido());

        painelFormulario.add(btRemover, gbc);
        gbc.anchor = GridBagConstraints.EAST;
        painelFormulario.add(btAdicionar, gbc);

        this.add(painelFormulario, BorderLayout.WEST);
    }

    private void removerProdutoDoPedido() {
        int selectedRow = tabelaProdutosPedido.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(framePrincipal, "Nenhum Pedido selecionado para edição.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Object idObject = tabelaProdutosPedido.getValueAt(selectedRow, 0);

        long idProduto = ((Number) idObject).longValue();

        controller.removerItemDoPedido(idProduto);

        carregarDadosNaTabela();
        atualizarValorTotal();
    }

    private void adicionarProdutoAoPedido() {
        try {
            long idProduto = Long.parseLong(tfCodigo.getText());
            int qtdProduto = Integer.parseInt(tfQtd.getText());

            if (!ValidadorNumero.intIsPositivo(qtdProduto)) {
                JOptionPane.showMessageDialog(this, "Quantidade Inválida!", "Erro validação",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            controller.adicionarItemAoPedido(idProduto, qtdProduto);

            carregarDadosNaTabela();
            atualizarValorTotal();

            tfCodigo.setText("");
            tfQtd.setText("1");
            tfCodigo.requestFocus();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Código e quantidade devem ser números!", "Erro validação",
                    JOptionPane.WARNING_MESSAGE);
        } catch (EstoqueInsuficienteException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro validação", JOptionPane.WARNING_MESSAGE);
        } catch (ProdutoNaoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro validação", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void desenhaListaDeProdutos() {
        JPanel painelProdutos = new JPanel(new BorderLayout());
        desenhaTabelaDeProdutos(painelProdutos);
        carregarDadosNaTabela();

        desenharRodapeProdutos(painelProdutos);

        add(painelProdutos, BorderLayout.CENTER);
    }

    private void desenhaTabelaDeProdutos(JPanel painelProdutos) {
        modeloTabelaProdutosNoPedido = new DefaultTableModel();
        modeloTabelaProdutosNoPedido.addColumn("ID");
        modeloTabelaProdutosNoPedido.addColumn("Qtd");
        modeloTabelaProdutosNoPedido.addColumn("Produto");
        modeloTabelaProdutosNoPedido.addColumn("Preço");

        tabelaProdutosPedido = new JTable(modeloTabelaProdutosNoPedido);
        painelProdutos.add(new JScrollPane(tabelaProdutosPedido), BorderLayout.CENTER);
    }

    private void carregarDadosNaTabela() {
        modeloTabelaProdutosNoPedido.setRowCount(0);

        Map<Produto, Integer> produtosNoPedido = controller.getPedidoAtual().getItens();

        for (Map.Entry<Produto, Integer> produtoEqtd : produtosNoPedido.entrySet()) {
            Object[] rowData = {
                    produtoEqtd.getKey().getId(),
                    produtoEqtd.getValue(),
                    produtoEqtd.getKey().getProduto(),
                    produtoEqtd.getKey().getPreco(),
            };
            modeloTabelaProdutosNoPedido.addRow(rowData);
        }
    }

    private void atualizarValorTotal() {

        Map<Produto, Integer> produtosNoPedido = controller.getPedidoAtual().getItens();

        valorPedido = BigDecimal.ZERO;
        for (Map.Entry<Produto, Integer> produtoEqtd : produtosNoPedido.entrySet())
            valorPedido = valorPedido
                    .add(produtoEqtd.getKey().getPreco().multiply(new BigDecimal(produtoEqtd.getValue())));

        atualizarLabelTotal();
    }

    private void atualizarLabelTotal() {
        if (areaTotal != null)
            areaTotal.setText(String.format("R$%.2f", valorPedido));
    }

    private void desenharRodapeProdutos(JPanel painelProdutos) {
        JPanel painelRodapeProdutos = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        valorPedido = BigDecimal.ZERO;
        areaTotal = new JLabel("R$ " + valorPedido);
        painelRodapeProdutos.add(new JLabel("Valor Total:"));
        painelRodapeProdutos.add(areaTotal);

        painelProdutos.add(painelRodapeProdutos, BorderLayout.SOUTH);
    }

    private void criarPainelAcoes() {
        JPanel painelAcoes = new JPanel();
        painelAcoes.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

        JButton botaoFecharCompra = new JButton("Fechar Compra");

        botaoFecharCompra.addActionListener(e -> fecharCompra());

        painelAcoes.add(botaoFecharCompra);

        add(painelAcoes, BorderLayout.SOUTH);
    }

    private void fecharCompra() {
        if (controller.getPedidoAtual().getItens().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Não é possível fechar um pedido sem nenhum produto.", "Pedido vazio",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        DialogFecharPedido dialog = new DialogFecharPedido(framePrincipal, controller);
        dialog.setVisible(true);

        if (dialog.foiSalvo()) {
            this.cliente = dialog.getCliente();
            this.taxas = dialog.getTaxa();
            this.modalidadeDeEntrega = dialog.getModalidadeEntrega();
            this.formaDePagamento = dialog.getFormaDePagamento();

            controller.finalizarPedido(cliente, taxas, modalidadeDeEntrega, formaDePagamento);

            // atualizarTela();

            JOptionPane.showMessageDialog(this, "Venda finalizada e registrada com sucesso!", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
