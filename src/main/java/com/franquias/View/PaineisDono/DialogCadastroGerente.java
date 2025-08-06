package com.franquias.View.PaineisDono;

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

import com.franquias.Model.entities.Usuários.Gerente;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class DialogCadastroGerente extends JDialog {
    private JTextField nomeField;
    private JFormattedTextField cpfField;
    private JTextField emailField;
    private JPasswordField senhaField;

    private Gerente gerente;
    private boolean salvo;

    public DialogCadastroGerente(Frame parent) {
        super(parent, "Editando gerente", true);
        gerente = new Gerente();
        configurarUI();
    }

    public DialogCadastroGerente(Frame parent, Gerente gerenteSendoEditado) {
        super(parent, "Editando gerente", true);
        this.gerente = gerenteSendoEditado;
        configurarUI();
        preencherCampos();
    }

    public void configurarUI() {
        setSize(400, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        setLocationRelativeTo(null);

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
        senhaField = new JPasswordField(20);

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

        pack();
    }

    private void preencherCampos() {
        nomeField.setText(gerente.getNome());
        cpfField.setValue(gerente.getCpf());
        emailField.setText(gerente.getEmail());
    }

    private void onSalvar() {
        String nome = nomeField.getText();
        String cpf = (String) cpfField.getValue();
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());

        if (nome.isBlank() || cpf == null || cpf.isBlank() || email.isBlank()) {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios", "Erro de validação",
                    JOptionPane.ERROR_MESSAGE);
        }

        EmailValidator emailValidator = EmailValidator.getInstance();
        if (!emailValidator.isValid(email)) {
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

        this.gerente.setNome(nome);
        this.gerente.setCpf(cpf);
        this.gerente.setEmail(email);

        if (!senha.isBlank())
            gerente.setSenha(senha);

        this.salvo = true;
        dispose();
        JOptionPane.showMessageDialog(this, "Gerente cadastrado com sucesso!", "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void onCancelar() {
        this.salvo = false;
        this.gerente = null;
        dispose();
    }

    public Gerente getGerente() {
        return this.gerente;
    }

    public boolean foiSalvo() {
        return this.salvo;
    }
}
