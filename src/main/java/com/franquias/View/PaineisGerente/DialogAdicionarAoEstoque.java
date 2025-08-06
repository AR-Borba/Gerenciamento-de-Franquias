package com.franquias.View.PaineisGerente;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.franquias.Utils.ValidadorNumero;

public class DialogAdicionarAoEstoque extends JDialog {
    private JTextField tfQtd;
    private int quantidadeAdicionada = 0;

    private boolean salvo = false;

    public DialogAdicionarAoEstoque() {
        configurarUI();
    }

    private void configurarUI() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = 0;
        gbc.gridx = 0;
        add(new JLabel("Quantidade"), gbc);
        gbc.gridx = 1;
        tfQtd = new JTextField(3);
        add(tfQtd, gbc);

        JButton btnCancelar = new JButton("Cancelar");
        JButton btnSalvar = new JButton("Salvar");

        btnCancelar.addActionListener(e -> dispose());
        btnSalvar.addActionListener(e -> onSalvar());

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(btnCancelar, gbc);
        gbc.gridx = 1;
        add(btnSalvar, gbc);
    }

    private void onSalvar() {
        try {
            int qtd = Integer.parseInt(tfQtd.getText());

            if (!ValidadorNumero.intIsPositivo(qtd)) {
                JOptionPane.showMessageDialog(this, "A quantidade deve ser um número positivo.", "Erro de Validação",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.quantidadeAdicionada = qtd;
            this.salvo = true;
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Por favor, digite um número válido para a quantidade.",
                    "Erro de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    public int getQtd() {
        return this.quantidadeAdicionada;
    }

    public boolean foiSalvo() {
        return salvo;
    }
}
