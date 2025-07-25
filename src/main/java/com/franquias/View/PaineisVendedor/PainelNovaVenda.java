package com.franquias.View.PaineisVendedor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.franquias.Controller.VendedorController;
import com.franquias.Model.Produto;
import com.franquias.Model.enums.FormaDePagamento;
import com.franquias.Model.enums.ModalidadeEntrega;

public class PainelNovaVenda extends JPanel {
    private VendedorController controller;

    private Map<Produto, Integer> itensDoPedido;
    private JList<String> listaProdutosVisual;
    private DefaultListModel<String> listModel;

    JLabel lblTotalValor;

    public PainelNovaVenda(VendedorController controller) {
        this.controller = controller;

        this.setLayout(new BorderLayout(5, 5));

        this.itensDoPedido = new HashMap<>();
        this.listModel = new DefaultListModel<>();
        
        desenhaFormularioDeProdutos();
        desenhaListaDeProdutos();
        desenhaRodape();
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
        JTextField tfCodigo = new JTextField(15);
        painelFormulario.add(tfCodigo, gbc);

        // linha 1: quantidade
        gbc.gridy = 1;

        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        painelFormulario.add(new JLabel("Qtd: "), gbc);

        gbc.gridx = 1;
        JTextField tfQtd = new JTextField("1", 3);
        painelFormulario.add(tfQtd, gbc);

        // linha 2: botão adicionar
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton btAdicionar = new JButton("Adicionar Produto");
        
        btAdicionar.addActionListener(e -> controller.adicionarItemAoPedido(tfCodigo.getText(), Integer.parseInt(tfQtd.getText())));

        painelFormulario.add(btAdicionar, gbc);

        this.add(painelFormulario, BorderLayout.WEST);
    }

    private void desenhaListaDeProdutos() {
        JPanel painelLista = new JPanel(new BorderLayout());

        listaProdutosVisual = new JList<>(listModel);

        painelLista.add(new JScrollPane(listaProdutosVisual), BorderLayout.CENTER);

        JPanel painelTotalLista = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelTotalLista.add(new JLabel("Total do Pedido: "));
        lblTotalValor = new JLabel("R$ 0,00");
        lblTotalValor.setFont(new Font("Arial", Font.BOLD, 16));
        painelTotalLista.add(lblTotalValor);

        painelLista.add(painelTotalLista, BorderLayout.SOUTH);

        this.add(painelLista, BorderLayout.EAST);
    }

    private void desenhaRodape() {
        JPanel painelRodape = new JPanel();
        painelRodape.setLayout(new GridBagLayout());
        painelRodape.setPreferredSize(new Dimension(600,  80));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JPanel painelCliente = new JPanel();
        painelCliente.setLayout(new FlowLayout());
        painelCliente.add(new JLabel("Cliente: "));
        painelCliente.add(new JTextField(15));
        JComboBox<FormaDePagamento> formaPagamento = new JComboBox<>(FormaDePagamento.values());
        painelCliente.add(formaPagamento);
        gbc.gridy = 0;

        painelRodape.add(painelCliente, gbc);

        JPanel painelEntregaTaxa = new JPanel();
        painelEntregaTaxa.setLayout(new FlowLayout());
        JComboBox<ModalidadeEntrega> formaEntrega = new JComboBox<>(ModalidadeEntrega.values());
        painelEntregaTaxa.add(formaEntrega);
        painelEntregaTaxa.add(new JLabel("Taxa"));
        painelEntregaTaxa.add(new JTextField(8));
        
        gbc.gridy = 1;
        painelRodape.add(painelEntregaTaxa, gbc);

        JPanel painelButton = new JPanel();
        painelButton.setLayout(new GridBagLayout());
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 3;
        painelButton.add(new JLabel(), gbc);
        gbc.gridx = 3;
        gbc.weightx = 0;
        gbc.gridwidth = 0;
        painelButton.add(new JButton("Finalizar"), gbc);

        gbc = new GridBagConstraints();
        gbc.gridy = 1;
        painelRodape.add(painelButton, gbc);

        this.add(painelRodape, BorderLayout.SOUTH);
    }
}
