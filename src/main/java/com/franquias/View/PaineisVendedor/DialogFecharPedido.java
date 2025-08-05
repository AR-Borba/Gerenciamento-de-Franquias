package com.franquias.View.PaineisVendedor;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;
import java.math.BigDecimal;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.NumberFormatter;

import com.franquias.Controller.VendedorController;
import com.franquias.Model.entities.Cliente;
import com.franquias.Model.enums.FormaDePagamento;
import com.franquias.Model.enums.ModalidadeEntrega;

public class DialogFecharPedido extends JDialog{
    
    private JComboBox<Cliente> fieldCliente;
    private JButton btnAdicionarCliente; 
    private JFormattedTextField tfTaxas;
    private JComboBox<ModalidadeEntrega> fieldModalidadeEntrega;
    private JComboBox<FormaDePagamento> fieldFormaPagamento;
    private JButton btnCancelarPedido;
    private JButton btnFinalizarPedido;

    private Cliente cliente;
    private BigDecimal taxa;
    private ModalidadeEntrega modalidadeEntrega;
    private FormaDePagamento formaDePagamento;

    private Frame framePrincipal;
    private boolean salvo;
    private VendedorController controller;

    public DialogFecharPedido(Frame framePrincipal, VendedorController controller) {
        super(framePrincipal, "Finalizando Compra", true);
        this.framePrincipal = framePrincipal;
        this.controller = controller;
        desenharUi();
    }

    private void desenharUi() {
        this.setSize(400, 300);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

        NumberFormatter decimalFormatter = new NumberFormatter(decimalFormat);
        decimalFormatter.setValueClass(BigDecimal.class); // << A CLASSE É BigDecimal
        decimalFormatter.setAllowsInvalid(false);

        List<Cliente> clientes = controller.getClientesDisponiveis();

        fieldCliente = new JComboBox<>(clientes.toArray(new Cliente[0]));
        btnAdicionarCliente = new JButton("Adicionar Cliente");
        tfTaxas = new JFormattedTextField(decimalFormatter);
        tfTaxas.setColumns(10);
        fieldModalidadeEntrega = new JComboBox<>(ModalidadeEntrega.values());
        fieldFormaPagamento = new JComboBox<>(FormaDePagamento.values());
        btnCancelarPedido = new JButton("Cancelar Pedido");
        btnFinalizarPedido = new JButton("Finalizar Pedido");

        btnAdicionarCliente.addActionListener(e -> adicionarCliente());
        gbc.gridy = 0;
        gbc.gridx = 0;
        this.add(fieldCliente, gbc);
        gbc.gridx = 1;
        this.add(btnAdicionarCliente, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        this.add(new JLabel("Taxas: "), gbc);
        gbc.gridx = 1;
        this.add(tfTaxas, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        this.add(fieldFormaPagamento, gbc);
        gbc.gridx = 1;
        this.add(fieldModalidadeEntrega, gbc);

        btnCancelarPedido.addActionListener(e -> onCancelar());
        btnFinalizarPedido.addActionListener(e -> onFinalizar());

        gbc.gridy = 3;
        gbc.gridx = 0;
        this.add(btnCancelarPedido, gbc);
        gbc.gridx = 1;
        this.add(btnFinalizarPedido, gbc);
    }

    private void adicionarCliente() {
        DialogCadastrarCliente dialog = new DialogCadastrarCliente(framePrincipal);
        dialog.setVisible(true);

        Cliente novoCliente = dialog.getCliente();
        if(novoCliente != null) {
            controller.adicionarCliente(novoCliente);
            fieldCliente.addItem(novoCliente);
            fieldCliente.setSelectedItem(novoCliente);

            JOptionPane.showMessageDialog(this, "Novo cliente cadastrado e selecionado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void onCancelar() {
        this.salvo = false;
        dispose();
    }

    private void onFinalizar() {
        this.cliente = (Cliente) this.fieldCliente.getSelectedItem();
        this.taxa = (BigDecimal) tfTaxas.getValue();
        this.formaDePagamento = (FormaDePagamento) this.fieldFormaPagamento.getSelectedItem();
        this.modalidadeEntrega = (ModalidadeEntrega) this.fieldModalidadeEntrega.getSelectedItem();

        if(cliente == null || taxa == null || formaDePagamento == null || modalidadeEntrega == null) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios", "Erro de validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.salvo = true;
        dispose();
    }

    public boolean foiSalvo() {
        return this.salvo;
    }

    public Cliente getCliente() {
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
