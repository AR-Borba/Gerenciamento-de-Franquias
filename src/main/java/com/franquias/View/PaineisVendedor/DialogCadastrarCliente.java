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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import org.apache.commons.validator.routines.EmailValidator;

import com.franquias.Model.entities.Cliente;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

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

        //this.cliente = cliente;
        
        setLocationRelativeTo(null);
        
        // mascára para CPF
        try {
            MaskFormatter cpfFormatter = new MaskFormatter("###.###.###-##");
            cpfFormatter.setPlaceholderCharacter('_');

            cpfField = new JFormattedTextField(cpfFormatter);
            cpfField.setColumns(14);
        } catch(ParseException e) {
            cpfField = new JFormattedTextField();
        }

        // Inicializar campos de texto
        nomeField = new JTextField(20);
        emailField = new JTextField(30);

        
        // senhaField.setText(cliente.getSenha());
        
        // Adicionar componentes ao diálogo (layout e outros componentes omitidos)

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

        if(nome.isBlank() || cpf == null || cpf.isBlank() || email.isBlank())
        {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios", "Erro de validação", JOptionPane.ERROR_MESSAGE);
        }

        EmailValidator emailValidator = EmailValidator.getInstance();
        if(!emailValidator.isValid(email)) {
            JOptionPane.showMessageDialog(this, "Email inválido", "Erro de validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            CPFValidator validator = new CPFValidator();
            validator.assertValid(cpf);
        } catch (InvalidStateException e) {
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
