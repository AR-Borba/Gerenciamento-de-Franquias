package com.franquias.View;

import org.apache.commons.validator.routines.EmailValidator;

import javax.swing.*;

import com.franquias.Controller.Logar;
import com.franquias.exceptions.EmailException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaFranquia {
    private JFrame tela;
    private final int WIDTH = 500;
    private final int HEIGHT = 400;
    private final int V_GAP = 10;
    private final int H_GAP = 5;

    JPasswordField pfSenha;
    JTextField tfEmail;

    public void desenha() {

        tela = new JFrame("Franquia");
        tela.setLayout(new BorderLayout());
        tela.setSize(WIDTH, HEIGHT);
        tela.setDefaultCloseOperation(tela.EXIT_ON_CLOSE);
        
        desenhaTelaLogin();

        tela.setVisible(true);
    }

    public void desenhaTelaLogin() {
        JPanel painel = new JPanel();
        painel.setLayout (new GridLayout(5, 1, H_GAP, V_GAP));
        painel.setBorder(BorderFactory.createTitledBorder("Login"));
        painel.setPreferredSize(new Dimension(WIDTH/2, HEIGHT/3));

        JLabel lbEmail = new JLabel("Email:");
        tfEmail = new JTextField(10);

        JLabel lbSenha = new JLabel("Senha:");
        pfSenha = new JPasswordField(10);

        JButton btEntrar = new JButton("Entrar");
        btEntrar.addActionListener(new Logar(this));

        painel.add(lbEmail);
        painel.add(tfEmail);
        painel.add(lbSenha);
        painel.add(pfSenha);
        painel.add(btEntrar);

        JPanel wrapperPanel = new JPanel();

        wrapperPanel.add(painel);

        tela.getContentPane().add(wrapperPanel, BorderLayout.CENTER);
    }

    public void tentarLogar() throws {
        String email = tfEmail.getText();
        EmailValidator validador = new EmailValidator.getInstance();
        if(!validador.isValid(email))
            throw new EmailException();
            

        String senha = new String(pfSenha.getPassword());
    }
}
