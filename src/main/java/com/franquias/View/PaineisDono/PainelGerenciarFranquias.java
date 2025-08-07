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
import com.franquias.exceptions.ValidationException;

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
        modeloTabelaFranquias.addColumn("ID");
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
                    franquia.getId(),
                    franquia.getEstado(),
                    franquia.getCidade(),
                    controller.getNomeGerente(franquia),
                    controller.getReceita(franquia),
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
        DialogCadastroFranquia dialog = new DialogCadastroFranquia(framePrincipal, controller);
        dialog.setVisible(true);

        if (dialog.foiSalvo()) {
            Franquia novaFranquia = dialog.getFranquia();

            try {
                controller.cadastrarFranquia(novaFranquia);
                carregarDadosNaTabela();
                JOptionPane.showMessageDialog(this, "Franquia cadastrada com sucesso!", "Sucesso",JOptionPane.INFORMATION_MESSAGE);
            } catch (ValidationException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }
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
        int selectedRow = tabelaFranquias.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(framePrincipal, "Nenhuma franquia selecionada para edição.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        long idFranquia = (long) modeloTabelaFranquias.getValueAt(selectedRow, 0);

        Franquia franquiaParaEditar = controller.buscarFranquiaPorId(idFranquia);

        if (franquiaParaEditar != null) {
            DialogCadastroFranquia dialog = new DialogCadastroFranquia(framePrincipal, franquiaParaEditar, controller);
            dialog.setVisible(true);

            if (dialog.foiSalvo()) {
                controller.editarFranquia(franquiaParaEditar);
                carregarDadosNaTabela();
                JOptionPane.showMessageDialog(this, "Franquia editada com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
