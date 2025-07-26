package com.franquias.View.PaineisDono;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.franquias.Model.entities.Usuários.Gerente;

public class DialogCadastroGerente extends JDialog {
    private JTextField nomeField;
    private JTextField cpfField;
    private JTextField emailField;
    private JTextField senhaField;

    private Gerente gerente;

    public DialogCadastroGerente(Frame parent) {
        super(parent, "Cadastro de Novo Gerente", true);
        setSize(400, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        setLocationRelativeTo(null);
        
        // Inicializar campos de texto
        nomeField = new JTextField(20);
        cpfField = new JTextField(14);
        emailField = new JTextField(30);
        senhaField = new JTextField(20);
        
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
        add(cancelarButton, gbc);
        gbc.gridx = 1;
        add(salvarButton, gbc);

        // Exibir o diálogo
        pack();
    }

    private void onSalvar() {
        this.gerente = new Gerente(
            nomeField.getText(),
            emailField.getText(),
            senhaField.getText(),
            cpfField.getText()
        );

        dispose();
        JOptionPane.showMessageDialog(this, "Gerente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onCancelar() {
        this.gerente = null;
        dispose();
    }

    public Gerente getGerente() {
        return this.gerente;
    }   
}
