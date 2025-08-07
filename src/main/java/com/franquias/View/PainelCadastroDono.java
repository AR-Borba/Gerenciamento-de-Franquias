package com.franquias.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import org.apache.commons.validator.routines.EmailValidator;

import com.franquias.Controller.AplicacaoPrincipal;
import com.franquias.Controller.DonoController;
import com.franquias.exceptions.ValidationException;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class PainelCadastroDono extends JPanel {

    DonoController donoController;

    private JTextField tfNome;
    private JFormattedTextField tfCpf;
    private JTextField tfEmail;
    private JPasswordField pfSenha;

    public PainelCadastroDono(AplicacaoPrincipal app, DonoController donoController) {
        this.donoController = donoController;

        this.setLayout(new GridBagLayout());
        JPanel painelCadastro = new JPanel();
        painelCadastro.setLayout(new GridBagLayout());
        painelCadastro.setBorder(BorderFactory.createTitledBorder("Cadastro Dono"));
        painelCadastro.setBackground(Color.lightGray);

        painelCadastro.setPreferredSize(new Dimension(250, 300));

        GridBagConstraints gbc;

        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelCadastro.add(new JLabel("Nome:"), gbc);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        tfNome = new JTextField(15);
        painelCadastro.add(tfNome, gbc);

        
        try {
            MaskFormatter cpfFormatter = new MaskFormatter("###.###.###-##");
            cpfFormatter.setPlaceholderCharacter('_');

            tfCpf = new JFormattedTextField(cpfFormatter);
            tfCpf.setColumns(14);
        } catch (ParseException e) {
            tfCpf = new JFormattedTextField();
        }
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 2;
        painelCadastro.add(new JLabel("CPF:"), gbc);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelCadastro.add(tfCpf, gbc);

        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 4;
        painelCadastro.add(new JLabel("Email:"), gbc);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        tfEmail = new JTextField(15);
        painelCadastro.add(tfEmail, gbc);

        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 6;
        painelCadastro.add(new JLabel("Senha:"), gbc);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        pfSenha = new JPasswordField(15);
        painelCadastro.add(pfSenha, gbc);

        
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 5, 5);
        gbc.gridy = 8;
        gbc.gridx = 0;

        JButton btnCadastrar = new JButton("Cadastrar");

        btnCadastrar.addActionListener(e -> {
            String nome = tfNome.getText();
            String cpf = tfCpf.getText();
            String email = tfEmail.getText();
            String senha = new String(pfSenha.getPassword());

            if (email.isBlank() || senha.isBlank() || email.isBlank() || senha.isBlank()) {
                JOptionPane.showMessageDialog(this, "Informações não podem estar em branco.", "Erro",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            EmailValidator emailValidator = EmailValidator.getInstance();
                if(!emailValidator.isValid(email)) {
                    JOptionPane.showMessageDialog(this, "Email inválido", "Erro de validação", JOptionPane.ERROR_MESSAGE);
                    return;
            }
            
            try {
                CPFValidator validator = new CPFValidator();
                validator.assertValid(cpf);
            } catch (InvalidStateException p) {
                JOptionPane.showMessageDialog(this, "CPF inválido", "Erro de validação", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                donoController.cadastrarDono(nome, cpf, email, senha);
            } catch (ValidationException p) {
                JOptionPane.showMessageDialog(this, p.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }
            app.mostrarTela("LOGIN");
        });
        

        painelCadastro.add(btnCadastrar, gbc);

        this.add(painelCadastro);
    }

}