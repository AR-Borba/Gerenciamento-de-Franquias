package com.franquias.View.PaineisVendedor;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import org.apache.commons.validator.routines.EmailValidator;

import com.franquias.Model.entities.Cliente;
import com.franquias.Utils.ValidadorCPF;
import com.franquias.Utils.ValidadorTexto;

public class DialogCadastrarCliente extends JDialog{
    
    private JTextField nomeField;
    private JFormattedTextField cpfField;
    private JTextField emailField;

    Cliente cliente;

    boolean salvo;

    public DialogCadastrarCliente(Frame parent) {
        super(parent, "Editando cliente", true);
        cliente = new Cliente();
        configurarUI();
    }

    private void configurarUI() {
        setSize(400, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // this.cliente = cliente;

        setLocationRelativeTo(null);

        // mascára para CPF
        try {
            MaskFormatter cpfFormatter = new MaskFormatter("###.###.###-##");
            cpfFormatter.setPlaceholderCharacter('_');

            cpfField = new JFormattedTextField(cpfFormatter);
            cpfField.setColumns(14);
        } catch (ParseException e) {
            cpfField = new JFormattedTextField();
        }

        nomeField = new JTextField(20);
        emailField = new JTextField(30);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        add(nomeField, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("CPF:"), gbc);
        gbc.gridx = 1;
        add(cpfField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> onSalvar());
        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(e -> onCancelar());

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(cancelarButton, gbc);
        gbc.gridx = 1;
        add(salvarButton, gbc);

        // Exibir o diálogo
        pack();
    }

    private void onSalvar() {
        String nome = nomeField.getText();
        String cpf = (String) cpfField.getValue();
        String email = emailField.getText();

        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(email)) {
            JOptionPane.showMessageDialog(this, "Email inválido", "Erro de validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!ValidadorTexto.temTamanhoMinimo(nome, 2)) {
            JOptionPane.showMessageDialog(this, "Nome deve ter mais de 2 caracteres!", "Erro de validação",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!ValidadorCPF.isValido(cpf)) {
            JOptionPane.showMessageDialog(this, "CPF inválido", "Erro de validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.cliente.setNome(nome);
        this.cliente.setCpf(cpf);
        this.cliente.setEmail(email);

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

    public Cliente getCliente() {
        return this.cliente;
    }
}
