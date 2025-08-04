package com.franquias.View.PaineisDono;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.franquias.Controller.DonoController;

import com.franquias.Model.entities.Usuários.Vendedor;;

public class PainelRankingVendedores extends JPanel{
    private JFrame framePrincipal;
    private DonoController controller;
    private JTable tabelaVendedores;
    private DefaultTableModel modeloTabelaVendedores;    

    public PainelRankingVendedores(DonoController controller, JFrame framePrincipal) {
        this.framePrincipal = framePrincipal;
        this.controller = controller;
        setLayout(new BorderLayout(5, 5));

        criarTabelaVendedores();
        carregarDadosNaTabela();
    }

    public void criarTabelaVendedores() {
        modeloTabelaVendedores = new DefaultTableModel();
        modeloTabelaVendedores.addColumn("ID");
        modeloTabelaVendedores.addColumn("Nome");
        modeloTabelaVendedores.addColumn("Id Franquia");
        modeloTabelaVendedores.addColumn("Receita acumulada");

        tabelaVendedores = new JTable(modeloTabelaVendedores);
        add(new JScrollPane(tabelaVendedores), BorderLayout.CENTER);
    }

    public void carregarDadosNaTabela() {
        modeloTabelaVendedores.setRowCount(0);

        List<Vendedor> vendedores = controller.getRankingVendedores();

        for (Vendedor vendedor : vendedores) {
            Object[] rowData = {
                    vendedor.getId(),
                    vendedor.getNome(),
                    vendedor.getFranquiaId(),
                    vendedor.getReceita()
            };
            modeloTabelaVendedores.addRow(rowData);
        }
    }
}
