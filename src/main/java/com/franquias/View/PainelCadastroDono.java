package com.franquias.View;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import com.franquias.Controller.AplicacaoPrincipal;
import com.franquias.Controller.DonoController;

public class PainelCadastroDono extends JPanel{

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

        GridBagConstraints gbc; // Apenas declare aqui

        // nome
        gbc = new GridBagConstraints(); // Reseta para os padrões
        gbc.insets = new Insets(5, 5, 5, 5); // Define uma margem
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 0; // Linha 0
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

        // cpf
        try {
            MaskFormatter cpfFormatter = new MaskFormatter("###.###.###-##");
            cpfFormatter.setPlaceholderCharacter('_');

            tfCpf = new JFormattedTextField(cpfFormatter);
            tfCpf.setColumns(14);
        } catch(ParseException e) {
            tfCpf = new JFormattedTextField();
        }
        gbc = new GridBagConstraints(); // Reseta para os padrões
        gbc.insets = new Insets(5, 5, 5, 5); // Define uma margem
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 2; // Linha 0
        painelCadastro.add(new JLabel("CPF:"), gbc);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        // JTextField tfCpf = new JTextField(15);
        painelCadastro.add(tfCpf, gbc);

        // email
        gbc = new GridBagConstraints(); // Reseta para os padrões
        gbc.insets = new Insets(5, 5, 5, 5); // Define uma margem
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 4; // Linha 0
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

        //--- Linha 1: Senha ---
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 6; // Linha 1
        painelCadastro.add(new JLabel("Senha:"), gbc);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; // Coluna 1
        gbc.gridy = 7; // Linha 1
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        pfSenha = new JPasswordField(15);
        painelCadastro.add(pfSenha, gbc);

        //--- Linha 1: Botão Entrar ---
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 5, 5); // Mais margem no topo
        gbc.gridy = 8; // Linha 1
        gbc.gridx = 0; // Começa na coluna 0
        // JButton btCancelar = new JButton("Cancelar");
        // btCancelar.addActionListener(e -> dispose());

        JButton btnCadastrar = new JButton("Cadastrar");

        btnCadastrar.addActionListener(e -> {
            String nome = tfNome.getText();
            String cpf = tfCpf.getText();
            String email = tfEmail.getText();
            String senha = new String(pfSenha.getPassword());

            if(email.isBlank() || senha.isBlank() || email.isBlank() || senha.isBlank()) {
                JOptionPane.showMessageDialog(this, "Informações não podem estar em branco.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            donoController.cadastrarDono(nome, cpf, email, senha);
            app.mostrarTela("LOGIN");
        });

        painelCadastro.add(btnCadastrar, gbc);
        
        this.add(painelCadastro);
    }
    
}