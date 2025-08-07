package com.franquias.View.PaineisDono;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.text.ParseException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.text.MaskFormatter;

import com.franquias.Controller.DonoController;
import com.franquias.Model.entities.Franquia;
import com.franquias.Model.enums.Estados;
import com.franquias.Utils.Endereco;
import com.franquias.Utils.ViaCepService;

public class DialogCadastroFranquia extends JDialog {
    private JTextField ruaField;
    private JTextField numeroField;
    private JTextField cidadeField;
    private JTextField cepField;
    private JComboBox<String> gerentesComboBox;
    private JComboBox<Estados> estadoField;

    private Endereco endereco;
    private Franquia franquia;
    private boolean salvo;

    public DialogCadastroFranquia(Frame parent, DonoController controller) {
        super(parent, "Criando Franquia", true);
        endereco = new Endereco();
        franquia = new Franquia();
        franquia.endereco = this.endereco;
        configurarUI(controller);
    }

    public DialogCadastroFranquia(Frame parent, Franquia franquiaSendoEditada, DonoController controller) {
        super(parent, "Editando Franquia", true);
        this.endereco = franquiaSendoEditada.endereco;
        this.franquia = franquiaSendoEditada;
        configurarUI(controller);
        preencherCampos();
    }

    public void configurarUI(DonoController controller) {
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
        } catch (ParseException e) {
            cepField = new JFormattedTextField();
        }

        ruaField = new JTextField(20);
        numeroField = new JTextField(20);
        cidadeField = new JTextField(20);
        estadoField = new JComboBox<>(Estados.values());
        List<String> gerentesDisponiveis = controller.getNomesGerentes();
        gerentesComboBox = new JComboBox<>(gerentesDisponiveis.toArray(new String[0]));
        
        JButton btnBuscarCep = new JButton("Buscar CEP");
        btnBuscarCep.addActionListener(e -> buscarCep());

        gbc.gridx = 0;
        gbc.gridy = 0;

        gbc.gridy = 1;
        gbc.gridx = 0;
        add(new JLabel("CEP:"), gbc);
        gbc.gridx = 1;
        add(cepField, gbc);
        gbc.gridx = 2;
        add(btnBuscarCep);

        gbc.gridy = 2;
        gbc.gridx = 0;
        add(new JLabel("Estado:"), gbc);
        gbc.gridx = 1;
        add(estadoField, gbc);

        gbc.gridy = 3;
        gbc.gridx = 0;
        add(new JLabel("Cidade:"), gbc);
        gbc.gridx = 1;
        add(cidadeField, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        add(new JLabel("Rua:"), gbc);
        gbc.gridx = 1;
        add(ruaField, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        add(new JLabel("Numero:"), gbc);
        gbc.gridx = 1;
        add(numeroField, gbc);

        gbc.gridy = 6;
        gbc.gridx = 0;
        add(new JLabel("Gerente:"), gbc);
        gbc.gridx = 1;
        add(gerentesComboBox, gbc);

        JButton salvarButton = new JButton("Salvar");
        salvarButton.addActionListener(e -> onSalvar(controller));
        JButton cancelarButton = new JButton("Cancelar");
        cancelarButton.addActionListener(e -> onCancelar());

        gbc.gridy = 7;
        gbc.gridx = 0;
        add(cancelarButton, gbc);
        gbc.gridx = 1;
        add(salvarButton, gbc);

        pack();
    }

    private void preencherCampos() {
        ruaField.setText(endereco.getRua());
        numeroField.setText(endereco.getNumero());
        cidadeField.setText(endereco.getCidade());
        cepField.setText(endereco.getCep());

        long gerenteAtual = franquia.getGerenteId();
        if (gerenteAtual != -1) {
            gerentesComboBox.setSelectedItem(gerenteAtual); //DEVE TER B.O. AQUI
        }

        String estadoDaFranquia = endereco.getEstado();
        if (estadoDaFranquia != null && !estadoDaFranquia.isBlank()) {
            try {
                estadoField.setSelectedItem(Estados.valueOf(estadoDaFranquia));
            } catch (IllegalArgumentException e) {
                System.err.println("Estado inválido no arquivo JSON: " + estadoDaFranquia);
            }
        }
    }
 
    private void onSalvar(DonoController controller) {
        String rua = ruaField.getText(); 
        String numero = numeroField.getText(); 
        String cidade = cidadeField.getText(); 
        String estado = ((Estados) estadoField.getSelectedItem()).name(); 
        String cep = cepField.getText(); 

        long IdGerenteSelecionado = controller.getIdGerente((gerentesComboBox.getSelectedItem()).toString());

        if(rua.isBlank() || numero.isBlank() || cidade.isBlank() || estado.isBlank() || cep.isBlank() ||  IdGerenteSelecionado == -1)
        {
            JOptionPane.showMessageDialog(this, "Todos os campos são obrigatórios", "Erro de validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.endereco.setRua(rua);
        this.endereco.setNumero(numero);
        this.endereco.setCidade(cidade);
        this.endereco.setEstado(estado);
        this.endereco.setCep(cep);

        this.franquia.setGerenteId(IdGerenteSelecionado); //Franquia = gerenteID
        
        this.salvo = true;
        dispose();
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

    private void buscarCep() {
        String cep = cepField.getText().replaceAll("[^0-9]", ""); // Remove máscara, se houver
        if (cep.length() != 8) {
            JOptionPane.showMessageDialog(this, "O formato do CEP é inválido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        ruaField.setText("Buscando...");
        cidadeField.setText("Buscando...");

        SwingWorker<Endereco, Void> worker = new SwingWorker<Endereco, Void>() {
            @Override
            protected Endereco doInBackground() throws Exception {
                ViaCepService viaCepService = new ViaCepService();
                return viaCepService.getEndereco(cep);
            }

            @Override
            protected void done() {
                try {
                    Endereco endereco = get();
                    if (endereco != null && endereco.getRua() != null) {
                        ruaField.setText(endereco.getRua());
                        cidadeField.setText(endereco.getCidade());
                        estadoField.setSelectedItem(Estados.valueOf(endereco.getEstado()));
                    } else {
                        JOptionPane.showMessageDialog(DialogCadastroFranquia.this, "CEP não encontrado.", "Erro",
                                JOptionPane.ERROR_MESSAGE);
                        ruaField.setText("");
                        cidadeField.setText("");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(DialogCadastroFranquia.this, "Erro ao buscar CEP: " + e.getMessage(),
                            "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
                    ruaField.setText("");
                    cidadeField.setText("");
                }
            }
        };
        worker.execute();
    }
}
