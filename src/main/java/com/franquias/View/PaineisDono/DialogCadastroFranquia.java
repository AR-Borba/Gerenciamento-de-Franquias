package com.franquias.View.PaineisDono;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import com.franquias.Model.entities.Franquia;
import com.franquias.Model.entities.Usuários.Gerente;
import com.franquias.Model.enums.Estados;
import com.franquias.Utils.Endereco;
import com.franquias.exceptions.CepException;

public class DialogCadastroFranquia extends JDialog {
    private JTextField ruaField;
    private JTextField numeroField;
    private JTextField cidadeField;
    private JTextField cepField;
    private JTextField gerenteField;
    private JComboBox<Estados> estadoField; 

    private Gerente gerente;
    private Endereco endereco;
    private Franquia franquia;
    private boolean salvo;

    public DialogCadastroFranquia(Frame parent){
        super(parent, "Criando Franquia", true);
        endereco = new Endereco();
        franquia = new Franquia();
        configurarUI();
    }

    public DialogCadastroFranquia(Frame parent, Franquia franquiaSendoEditada){
        super(parent, "Editando Franquia", true);
        this.endereco = franquiaSendoEditada.endereco;
        this.franquia = franquiaSendoEditada;
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
            MaskFormatter cepFormatter = new MaskFormatter("#####-###");
            cepFormatter.setPlaceholderCharacter('_');

            cepField = new JFormattedTextField(cepFormatter);
            cepField.setColumns(14);
        } catch(ParseException e) {
            cepField = new JFormattedTextField();
        }
        
        ruaField = new JTextField(20);
        numeroField = new JTextField(20);
        cidadeField = new JTextField(20);
        estadoField = new JComboBox<>(Estados.values());
        gerenteField = new JTextField(20);
        
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

        pack();
    }

    private void preencherCampos () {
        ruaField.getText();
        numeroField.getText();
        cidadeField.getText();
        // estadoField.getText();
        cepField.getText();
        gerenteField.getText();

    }

    private void onSalvar() {
        String rua    = ruaField.getText(); 
        String numero = numeroField.getText(); 
        String cidade = cidadeField.getText(); 
        String estado = (String) estadoField.getSelectedItem(); 
        String cep    = cepField.getText(); 

        if(rua.isBlank() || numero.isBlank() || cidade.isBlank() || estado.isBlank() || cep.isBlank())
        {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios", "Erro de validação", JOptionPane.ERROR_MESSAGE);
        }

        this.endereco.setRua(rua);
        this.endereco.setNumero(numero);
        this.endereco.setCidade(cidade);
        this.endereco.setEstado(estado);
        this.endereco.setCep(cep);
        
        this.franquia = new Franquia(
            this.endereco,
            this.gerente);

        this.salvo = true;
        dispose();
        JOptionPane.showMessageDialog(this, "Franquia cadastrada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void onCancelar() {
        this.salvo = false;
        this.franquia = null;
        dispose();
    }

    public Franquia getFranquia() {
        return this.franquia;
    }   

    public boolean foiSalvo() {
        return this.salvo;
    }
}
