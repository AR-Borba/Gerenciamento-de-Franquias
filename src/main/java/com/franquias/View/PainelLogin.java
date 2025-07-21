package com.franquias.View;

import javax.swing.*;
import java.awt.*;

import com.franquias.Controller.AplicacaoPrincipal;
import com.franquias.Model.enums.TipoDeConta;

public class PainelLogin extends JPanel {

    JPasswordField pfSenha;
    JTextField tfEmail;
    
    public PainelLogin(AplicacaoPrincipal telaPrincipal) {
        this.setLayout(new GridBagLayout());
        JPanel painelLogin = new JPanel();
        painelLogin.setLayout(new GridBagLayout());
        painelLogin.setBorder(BorderFactory.createTitledBorder("Login"));
        painelLogin.setBackground(Color.lightGray);
        // setPreferredSize é opcional se você quer que o pack() ajuste o tamanho
        painelLogin.setPreferredSize(new Dimension(telaPrincipal.WIDTH / 2, telaPrincipal.HEIGHT / 2));

        GridBagConstraints gbc; // Apenas declare aqui

        //--- Linha 0: Email ---
        // Rótulo "Email"
        gbc = new GridBagConstraints(); // Reseta para os padrões
        gbc.insets = new Insets(5, 5, 5, 5); // Define uma margem
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 0; // Linha 0
        painelLogin.add(new JLabel("Email:"), gbc);

        // Campo de texto para o Email
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; // Coluna 1
        gbc.gridy = 1; // Linha 0
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Faz o campo esticar horizontalmente
        gbc.weightx = 1.0; // Permite que a coluna 1 cresça com a janela
        tfEmail = new JTextField(15);
        painelLogin.add(tfEmail, gbc);

        //--- Linha 1: Senha (ORDEM CORRIGIDA) ---
        // Rótulo "Senha"
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; // Coluna 0
        gbc.gridy = 2; // Linha 1
        painelLogin.add(new JLabel("Senha:"), gbc);

        // Campo de texto para a Senha
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; // Coluna 1
        gbc.gridy = 3; // Linha 1
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        pfSenha = new JPasswordField(15);
        painelLogin.add(pfSenha, gbc);

        //--- Seleção do tipo de conta
        // nome do componente
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        painelLogin.add(new JLabel("Conta:"), gbc);

        // campo de seleção
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 1;
        gbc.gridy = 4;
        TipoDeConta[] accountType = {TipoDeConta.DONO, TipoDeConta.GERENTE, TipoDeConta.VENDEDOR};
        JComboBox accountList = new JComboBox(accountType);
        painelLogin.add(accountList, gbc);

        //--- Linha 2: Botão Entrar ---
        gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 5, 5); // Mais margem no topo
        gbc.gridx = 0; // Começa na coluna 0
        gbc.gridy = 5; // Linha 2
        gbc.gridwidth = 2; // << AQUI SIM: Ocupa 2 colunas para centralizar
        JButton btEntrar = new JButton("Entrar");

        btEntrar.addActionListener(e -> {
            String email = tfEmail.getText();
            String senha = new String(pfSenha.getPassword());

            if(email.isBlank() || senha.isBlank()) {
                JOptionPane.showMessageDialog(this, "Email e senha não podem estar em branco.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean loginBemSucedido = telaPrincipal.realizarLogin(email, senha);

            if(!loginBemSucedido)
                JOptionPane.showMessageDialog(this, "Email ou senha inválidos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            else
                telaPrincipal.cardLayout.show(telaPrincipal.painelDeConteudo, "VENDEDOR");
        });
        painelLogin.add(btEntrar, gbc);
        
        this.add(painelLogin);
    }
}
