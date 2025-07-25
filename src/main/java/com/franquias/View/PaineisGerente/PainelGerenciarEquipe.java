package com.franquias.View.PaineisGerente;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.franquias.Controller.GerenteController;
import com.franquias.Model.entities.Usuários.Vendedor;

public class PainelGerenciarEquipe extends JPanel {
    
    private GerenteController controller;
    private JTable tabelaRankingEquipe;
    private DefaultTableModel modeloTabelaRanking;

    public PainelGerenciarEquipe(GerenteController controller) {
        this.controller = controller;
        setLayout(new BorderLayout(5, 5));

        criarTabelaRankingEquipe();

        criarPainelAcoes();

        carregarDadosNaTabela();
    }

    public void criarTabelaRankingEquipe() {
        modeloTabelaRanking = new DefaultTableModel();
        modeloTabelaRanking.addColumn("ID");
        modeloTabelaRanking.addColumn("Nome");
        modeloTabelaRanking.addColumn("Total de Vendas");

        tabelaRankingEquipe = new JTable(modeloTabelaRanking);
        add(new JScrollPane(tabelaRankingEquipe), BorderLayout.CENTER);
    }

    public void carregarDadosNaTabela() {
        modeloTabelaRanking.setRowCount(0); // Limpa a tabela antes de adicionar novos dados

        List<Vendedor> vendedores = controller.getEquipeDeVendasOrdenadaPorVendas();

        for (Vendedor vendedor : vendedores) {
            Object[] rowData = {
                vendedor.getId(),
                vendedor.getNome(),
                vendedor.calcularTotalVendas()
            };
            modeloTabelaRanking.addRow(rowData);
        }
    }

    public void criarPainelAcoes() {
        JPanel painelAcoes = new JPanel();
        painelAcoes.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnRemover = new JButton("Remover");
        JButton btnnAdicionar = new JButton("Adicionar");

        // btnAtualizar.addActionListener(e -> controller.adicionarVendedor());
        // btnRemover.addActionListener(e -> controller.removerVendedorSelecionado()); 
        // btnnAdicionar.addActionListener(e -> controller.adicionarVendedor());
        
        painelAcoes.add(btnAtualizar);
        painelAcoes.add(btnRemover);
        painelAcoes.add(btnnAdicionar);
        
        add(painelAcoes, BorderLayout.SOUTH);
    }
}
