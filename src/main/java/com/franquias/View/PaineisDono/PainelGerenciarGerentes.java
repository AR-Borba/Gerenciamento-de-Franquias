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

import org.apache.commons.lang3.ObjectUtils.Null;

import com.franquias.Controller.DonoController;
import com.franquias.Model.entities.Usuários.Gerente;
import com.franquias.Model.entities.Usuários.Vendedor;

public class PainelGerenciarGerentes extends JPanel {
    private JFrame framePrincipal;
    private DonoController controller;
    private JTable tabelaRankingGerentes;
    private DefaultTableModel modeloTabelaRanking;

    public PainelGerenciarGerentes(DonoController controller, JFrame framePrincipal) {
        this.framePrincipal = framePrincipal;
        this.controller = controller;
        setLayout(new BorderLayout(5, 5));

        criarTabelaRankingGerentes();
        criarPainelAcoes();
        carregarDadosNaTabela();
    }

    public void criarTabelaRankingGerentes() {
        modeloTabelaRanking = new DefaultTableModel();
        modeloTabelaRanking.addColumn("Nome");

        tabelaRankingGerentes = new JTable(modeloTabelaRanking);
        add(new JScrollPane(tabelaRankingGerentes), BorderLayout.CENTER);
    }

    public void carregarDadosNaTabela() {
        modeloTabelaRanking.setRowCount(0); // Limpa a tabela antes de adicionar novos dados

        List<Gerente> gerentes = controller.getGerentes();

        for (Gerente gerente : gerentes) {
            Object[] rowData = {
                gerente.getNome(),
            };
            modeloTabelaRanking.addRow(rowData);
        }
    }

    public void criarPainelAcoes() {
        JPanel painelAcoes = new JPanel();
        painelAcoes.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");
        JButton btnnAdicionar = new JButton("Adicionar");

        //btnEditar.addActionListener(e -> editaGerente());
        //btnRemover.addActionListener(e -> removeGerente());
        btnnAdicionar.addActionListener(e -> adicionaGerente());
        
        painelAcoes.add(btnEditar);
        painelAcoes.add(btnRemover);
        painelAcoes.add(btnnAdicionar);
        
        add(painelAcoes, BorderLayout.SOUTH);
    }
   
    private void adicionaGerente(){
        DialogCadastroGerente dialog = new DialogCadastroGerente(framePrincipal);
        dialog.setVisible(true);
        
        Gerente novoGerente = dialog.getGerente();
        if(novoGerente != null){
            controller.cadastrarGerente(novoGerente);
        }
    }

    // private void removeGerente() {
    //     int selectedRow = tabelaRankingGerentes.getSelectedRow();
    //     if (selectedRow != -1) {
    //         Gerente gerente = modeloTabelaRanking.getValueAt(selectedRow, 0);
    //         controller.removerGerente(gerente);
    //         modeloTabelaRanking.removeRow(selectedRow);
    //     } else {
    //         JOptionPane.showMessageDialog(framePrincipal, "Nenhum vendedor selecionado para remoção.", "Erro", JOptionPane.ERROR_MESSAGE);
    //     }
    // }

}
