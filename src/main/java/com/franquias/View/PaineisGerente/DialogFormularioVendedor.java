package com.franquias.View.PaineisGerente;

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

import com.franquias.Model.entities.Usuários.Vendedor;
import com.franquias.Utils.ValidadorCPF;
import com.franquias.Utils.ValidadorTexto;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class DialogFormularioVendedor extends JDialog {

    private JTextField nomeField;
    private JFormattedTextField cpfField;
    private JTextField emailField;
    private JPasswordField senhaField;
    
    Vendedor vendedor;

    boolean salvo;

    public DialogFormularioVendedor(Frame parent) {   
        super(parent, "Editando vendedor", true);
        vendedor = new Vendedor();
        configurarUI();
    }

    public DialogFormularioVendedor(Frame parent, Vendedor vendedorSendoEditado) {
        super(parent, "Editando vendedor", true);
        this.vendedor = vendedorSendoEditado;
        configurarUI();
        preencherCampos();
    }

    private void configurarUI() {
        setSize(400, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //this.vendedor = vendedor;
        
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
        senhaField = new JPasswordField(20);

        
        // senhaField.setText(vendedor.getSenha());
        
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

        pack();
    }

    private void preencherCampos() {
        nomeField.setText(vendedor.getNome());
        cpfField.setValue(vendedor.getCpf());
        emailField.setText(vendedor.getEmail());
    }

    private void onSalvar() {
        String nome = nomeField.getText();
        String cpf = (String) cpfField.getValue();
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());

        
        if(!ValidadorTexto.temTamanhoMinimo(nome, 2)) {
            JOptionPane.showMessageDialog(this, "Nome deve ter mais de 2 caracteres", "Erro de validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if(!ValidadorCPF.isValido(cpf)) {
            JOptionPane.showMessageDialog(this, "CPF inválido", "Erro de validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        EmailValidator emailValidator = EmailValidator.getInstance();
        if(!emailValidator.isValid(email)) {
            JOptionPane.showMessageDialog(this, "Email inválido", "Erro de validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(!ValidadorTexto.temTamanhoMinimo(senha, 4)) {
            JOptionPane.showMessageDialog(this, "Senha deve ter mais de 4 caracteres", "Erro de validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.vendedor.setNome(nome);
        this.vendedor.setCpf(cpf);
        this.vendedor.setEmail(email);

        if(!senha.isBlank())
            vendedor.setSenha(senha);

        this.salvo = true;
        dispose();
    }

    private void onCancelar() {
        this.salvo = false;
        this.vendedor = null;
        dispose();
    }

    public boolean foiSalvo() {
        return this.salvo;
    }

    public Vendedor getVendedor() {
        return this.vendedor;
    }
}
