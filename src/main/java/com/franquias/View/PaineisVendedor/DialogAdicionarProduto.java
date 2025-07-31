package com.franquias.View.PaineisVendedor;

import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class DialogAdicionarProduto extends JDialog{
    
    long idNovoProduto;
    int qtdNovoProduto;

    JTextField idField;
    JTextField qtdField;

    boolean salvo;

    public DialogAdicionarProduto(Frame parent) {
        super(parent, "Adcionadno produto", true);

        configurarUI(); 
    }

    private void configurarUI() {
        idField = new JTextField();
        qtdField = new JTextField();

        this.add(new JLabel("Id"));
        this.add(idField);

        this.add(new JLabel("Quantidade"));
        this.add(qtdField);

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        btnSalvar.addActionListener(e -> onSalvar());
        btnCancelar.addActionListener(e -> onCancelar());
    }

    public long getId() {
        return this.idNovoProduto;
    }

    public int getQtd() {
        return this.qtdNovoProduto;
    }

    private void onSalvar() {
        this.salvo = true;
        dispose();
    }

    private void onCancelar() {
        this.salvo = false;
        dispose();
    }

    public boolean getSalvo() {
        return this.getSalvo();
    }
}
