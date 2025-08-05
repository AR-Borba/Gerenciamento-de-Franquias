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
import com.franquias.Utils.ValidadorNumero;
import com.franquias.Utils.ValidadorTexto;

public class DialogFormularioProduto extends JDialog {
    private JTextField produtoField;
    private JFormattedTextField precoField;
    private JFormattedTextField qtdEstoqueField;
    
    private Produto produto;

    private boolean salvo;

    public DialogFormularioProduto(Frame parent) {
        super(parent, "Editando produto", true);
        this.produto = new Produto();

        desenhaUi();
    }

    public DialogFormularioProduto(Frame parent, Produto produtoSendoEditado) {
        super(parent, "Editando produto", true);
        this.produto = produtoSendoEditado;

        desenhaUi();
        preencherCampos();
    }

    private void desenhaUi() {
        setSize(400, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // this.produto = produto;
        
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

        produtoField = new JTextField(20);
        precoField = new JFormattedTextField(decimalFormatter);
        precoField.setColumns(10);
        qtdEstoqueField = new JFormattedTextField(integerFormatter);
        qtdEstoqueField.setColumns(3);

        gbc.gridy = 0;
        gbc.gridx = 0;
        add(new JLabel("Produto:"), gbc);
        gbc.gridx = 1;
        add(produtoField, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Preço:"), gbc);
        gbc.gridx = 1;
        add(precoField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Quantidade em estoque:"), gbc);
        gbc.gridx = 1;
        add(qtdEstoqueField, gbc);

        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> onSalvar());
        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(e -> onCancelar());

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(cancelarButton, gbc);
        gbc.gridx = 1;
        add(salvarButton, gbc);
    }

    private void preencherCampos() {
        produtoField.setText(produto.getProduto());
        precoField.setValue(produto.getPreco());
        qtdEstoqueField.setValue(produto.getQuantidadeEstoque());
    }

    private void onSalvar() {
        String produto = produtoField.getText();
        BigDecimal preco = (BigDecimal) precoField.getValue();
        Integer quantidadeEmEstoque = (Integer) qtdEstoqueField.getValue();

        if(!ValidadorTexto.temTamanhoMinimo(produto, 2)) {
            JOptionPane.showMessageDialog(this, "Produto deve ter mais de 2 caracteres!", "Erro de validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(!ValidadorNumero.intIsNaoNegativo(quantidadeEmEstoque)) {
            JOptionPane.showMessageDialog(this, "Quantidade não pode ser negativa!", "Erro de validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(!ValidadorNumero.bigDecimalIsPositivo(preco)) {
            JOptionPane.showMessageDialog(this, "Preço tem que ser maior que 0.00!", "Erro de validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.produto.setProduto(produto);
        this.produto.setPreco(preco);
        this.produto.setQuantidadeEstoque(quantidadeEmEstoque);

        this.salvo = true;
        dispose();
    }

    private void onCancelar() {
        this.salvo = false;
        this.produto = null;
        dispose();
    }

    public boolean foiSalvo() {
        return this.salvo;
    }

    public Produto getProduto() {
        return this.produto;
    }
}
