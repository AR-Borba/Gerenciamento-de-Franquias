package com.franquias.View.PaineisDono;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.franquias.Model.entities.Franquia;
import com.franquias.Model.entities.Usuários.Gerente;
import com.franquias.Utils.Endereco;

public class DialogCadastroFranquia extends JDialog {
    private JTextField ruaField;
    private JTextField numeroField;
    private JTextField cidadeField;
    private JTextField estadoField;
    private JTextField cepField;
    private JTextField gerenteField;

    private Gerente gerente;
    private Endereco endereco;
    private Franquia franquia;

    public DialogCadastroFranquia(Frame parent) {
        super(parent, "Cadastro de Nova Franquia", true);
        setSize(400, 300);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        setLocationRelativeTo(null);
        
        // Inicializar campos de texto
        ruaField = new JTextField(20);
        numeroField = new JTextField(20);
        cidadeField = new JTextField(20);
        estadoField = new JTextField(20);
        cepField = new JTextField(20);
        gerenteField = new JTextField(20);
        
        // Adicionar componentes ao diálogo (layout e outros componentes omitidos)

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("Rua:"), gbc);
        gbc.gridx = 1;
        add(ruaField, gbc);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Numero:"), gbc);
        gbc.gridx = 1;
        add(numeroField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(new JLabel("Cidade:"), gbc);
        gbc.gridx = 1;
        add(cidadeField, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        add(estadoField, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        add(new JLabel("CEP:"), gbc);
        gbc.gridx = 1;
        add(cepField, gbc);

        gbc.gridy = 6;
        gbc.gridx = 0;
        add(new JLabel("Gerente:"), gbc);
        gbc.gridx = 1;
        add(gerenteField, gbc);

        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> onSalvar());
        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(e -> onCancelar());

        gbc.gridy = 7;
        gbc.gridx = 0;
        add(cancelarButton, gbc);
        gbc.gridx = 1;
        add(salvarButton, gbc);

        // Exibir o diálogo
        pack();
    }

    private void onSalvar(){
        this.endereco = new Endereco(
            ruaField.getText(), 
            numeroField.getText(), 
            cidadeField.getText(), 
            estadoField.getText(), 
            cepField.getText() 
        );

        this.gerente = null;

        this.franquia = new Franquia(
            this.endereco,
            this.gerente);
        
    }
    
    private void onCancelar(){
        this.franquia = null;
        dispose();
    }
    
    public Franquia getFranquia() {
        return this.franquia;
    }
    
}
