package com.franquias.View.PaineisDono;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.franquias.Controller.DonoController;
import com.franquias.Model.entities.Franquia;

public class PainelGerenciarFranquias extends JPanel {
    private JFrame framePrincipal;
    private DonoController controller;
    private JTable tabelaFranquias;
    private DefaultTableModel modeloTabelaFranquias;

    public PainelGerenciarFranquias(DonoController controller, JFrame framePrincipal) {
        this.framePrincipal = framePrincipal;
        this.controller = controller;
        setLayout(new BorderLayout(5, 5));

        criarTabelaFranquias();
        criarPainelAcoes();
        carregarDadosNaTabela();
    }

    public void criarTabelaFranquias() {
        modeloTabelaFranquias = new DefaultTableModel();
        modeloTabelaFranquias.addColumn("Estado");
        modeloTabelaFranquias.addColumn("Cidade");
        modeloTabelaFranquias.addColumn("Gerente");
        modeloTabelaFranquias.addColumn("Receita acumulada");

        tabelaFranquias = new JTable(modeloTabelaFranquias);
        add(new JScrollPane(tabelaFranquias), BorderLayout.CENTER);
    }

    public void carregarDadosNaTabela() {
        modeloTabelaFranquias.setRowCount(0); // Limpa a tabela antes de adicionar novos dados

        List<Franquia> franquias = controller.getUnidades();

        for (Franquia franquia : franquias) {
            Object[] rowData = {
                    franquia.getEstado(),
                    franquia.getCidade(),
                    franquia.getGerente(),
                    franquia.getReceita(),
            };
            modeloTabelaFranquias.addRow(rowData);
        }
    }

    public void criarPainelAcoes() {
        JPanel painelAcoes = new JPanel();
        painelAcoes.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");
        JButton btnnAdicionar = new JButton("Adicionar");

        btnEditar.addActionListener(e -> editaFranquia());
        btnRemover.addActionListener(e -> removeFranquia());
        btnnAdicionar.addActionListener(e -> adicionaFranquia());

        painelAcoes.add(btnEditar);
        painelAcoes.add(btnRemover);
        painelAcoes.add(btnnAdicionar);

        add(painelAcoes, BorderLayout.SOUTH);
    }

    private void adicionaFranquia() {
        DialogCadastroFranquia dialog = new DialogCadastroFranquia(framePrincipal);
        dialog.setVisible(true);

        Franquia novaFranquia = dialog.getFranquia();
        if (novaFranquia != null) {
            controller.cadastrarFranquia(novaFranquia);
        }
    }

    private void removeFranquia() {
        int selectedRow = tabelaFranquias.getSelectedRow();
        if (selectedRow != -1) {
            long idFranquia = (long) modeloTabelaFranquias.getValueAt(selectedRow, 0);
            controller.removerFranquia(idFranquia);
            modeloTabelaFranquias.removeRow(selectedRow);
        } else {
            JOptionPane.showMessageDialog(framePrincipal, "Nenhuma franquia selecionado para remoção.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editaFranquia() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editaFranquia'");
    }
}
