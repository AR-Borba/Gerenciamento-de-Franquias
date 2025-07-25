package com.franquias.View.PaineisGerente;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;

import com.franquias.Model.Produto;
import com.franquias.Model.entities.Usuários.Vendedor;

public class DialogCadastroProduto extends JDialog {
    JTextField idField;
    JTextField produtoField;
    JFormattedTextField precoField;
    JFormattedTextField qtdEstoqueField;

    Produto produto;

    public DialogCadastroProduto(Frame parent) {
        super(parent, "Cadastro de Novo Produto", true);
        setSize(400, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // formatação correta ao coletar os dados
        NumberFormat integerFormat = NumberFormat.getIntegerInstance();
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

        NumberFormatter integerFormatter = new NumberFormatter(integerFormat);
        integerFormatter.setValueClass(Integer.class); // Define que o valor interno será um Integer
        integerFormatter.setAllowsInvalid(false); // NÃO permite que o usuário digite caracteres inválidos (letras, etc.)
        integerFormatter.setMinimum(0); // Opcional: define um valor mínimo

        NumberFormatter decimalFormatter = new NumberFormatter(decimalFormat);
        decimalFormatter.setValueClass(BigDecimal.class); // << A CLASSE É BigDecimal
        decimalFormatter.setAllowsInvalid(false);
        
        setLocationRelativeTo(null);
    
        idField = new JTextField(5);
        produtoField = new JTextField(20);
        precoField = new JFormattedTextField(decimalFormatter);
        precoField.setColumns(10);
        qtdEstoqueField = new JFormattedTextField(integerFormat);
        qtdEstoqueField.setColumns(3);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        add(idField, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Produto:"), gbc);
        gbc.gridx = 1;
        add(produtoField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Preço:"), gbc);
        gbc.gridx = 1;
        add(precoField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(new JLabel("Quantidade em estoque:"), gbc);
        gbc.gridx = 1;
        add(qtdEstoqueField, gbc);

        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> onSalvar());
        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(e -> onCancelar());

        gbc.gridy = 4;
        gbc.gridx = 0;
        add(salvarButton, gbc);
        gbc.gridx = 1;
        add(cancelarButton, gbc);
    }

    private void onSalvar() {
            this.produto = new Produto(
            idField.getText(),
            produtoField.getText(),
            (BigDecimal) precoField.getValue(),
            (Integer) qtdEstoqueField.getValue()
        );

        dispose();
        JOptionPane.showMessageDialog(this, "Produto cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    
    }

    private void onCancelar() {
        this.produto = null;
        dispose();
    }

    public Produto getProduto() {
        return this.produto;
    }
}
