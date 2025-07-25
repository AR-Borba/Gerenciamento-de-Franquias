package com.franquias.View;

import javax.swing.*;
import java.awt.*;

import com.franquias.Controller.AplicacaoPrincipal;
import com.franquias.Controller.LoginController;
import com.franquias.Model.enums.TipoDeConta;

public class PainelLogin extends JPanel {

    JPasswordField pfSenha;
    JTextField tfEmail;

    public PainelLogin(LoginController controller) {
        this.setLayout(new GridBagLayout());
        JPanel painelLogin = new JPanel();
        painelLogin.setLayout(new GridBagLayout());
        painelLogin.setBorder(BorderFactory.createTitledBorder("Login"));
        painelLogin.setBackground(Color.lightGray);

        painelLogin.setPreferredSize(new Dimension(250, 200));

        GridBagConstraints gbc; // Apenas declare aqui

        //--- Linha 0: Email ---
        gbc = new GridBagConstraints(); // Reseta para os padrões
        gbc.insets = new Insets(5, 5, 5, 5); // Define uma margem
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 0; // Linha 0
        painelLogin.add(new JLabel("Email:"), gbc);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; // Coluna 1
        gbc.gridy = 1; // Linha 0
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Faz o campo esticar horizontalmente
        gbc.weightx = 1.0; // Permite que a coluna 1 cresça com a janela
        tfEmail = new JTextField(15);
        painelLogin.add(tfEmail, gbc);

        //--- Linha 1: Senha ---
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 2; // Linha 1
        painelLogin.add(new JLabel("Senha:"), gbc);

        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; // Coluna 1
        gbc.gridy = 3; // Linha 1
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        pfSenha = new JPasswordField(15);
        painelLogin.add(pfSenha, gbc);

        //--- Linha 1: Botão Entrar ---
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 5, 5); // Mais margem no topo
        gbc.gridx = 0; // Começa na coluna 0
        gbc.gridy = 4; // Linha 1
        gbc.gridwidth = 2; // << AQUI SIM: Ocupa 2 colunas para centralizar
        JButton btEntrar = new JButton("Entrar");

        btEntrar.addActionListener(e -> {
            String email = tfEmail.getText();
            String senha = new String(pfSenha.getPassword());

            if(email.isBlank() || senha.isBlank()) {
                JOptionPane.showMessageDialog(this, "Email e senha não podem estar em branco.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            controller.realizarLogin(email, senha);
        });

        painelLogin.add(btEntrar, gbc);
        
        this.add(painelLogin);
    }
    
}
