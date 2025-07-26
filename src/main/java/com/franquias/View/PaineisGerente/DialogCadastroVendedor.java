package com.franquias.View.PaineisGerente;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import org.apache.commons.validator.routines.EmailValidator;

import java.awt.*;
import java.text.ParseException;

import com.franquias.Model.entities.Usuários.Vendedor;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class DialogCadastroVendedor extends JDialog {
    
    private JTextField nomeField;
    private JFormattedTextField cpfField;
    private JTextField emailField;
    private JPasswordField senhaField;

    private Vendedor vendedor;

    public DialogCadastroVendedor(Frame parent) {
        
        super(parent, "Cadastro de Novo Vendedor", true);
        setSize(400, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
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
        // cpfField = new JFormattedTextField(14);
        emailField = new JTextField(30);
        senhaField = new JPasswordField(20);
        
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

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(new JLabel("Senha:"), gbc);
        gbc.gridx = 1;
        add(senhaField, gbc);

        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> onSalvar());
        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(e -> onCancelar());

        gbc.gridy = 4;
        gbc.gridx = 0;
        add(salvarButton, gbc);
        gbc.gridx = 1;
        add(cancelarButton, gbc);

        // Exibir o diálogo
        pack();
    }

    private void onSalvar() {
        String nome = nomeField.getText();
        String cpf = cpfField.getText();
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());

        if(nome.isBlank() || cpf.isBlank() || email.isBlank() || senha.isBlank())
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

        this.vendedor = new Vendedor(nome, email, senha, cpf, 0);

        dispose();
        JOptionPane.showMessageDialog(this, "Vendedor cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onCancelar() {
        this.vendedor = null;
        dispose();
    }

    public Vendedor getVendedor() {
        return this.vendedor;
    }   
}
