package com.franquias.View.PaineisGerente;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import com.franquias.Controller.GerenteController;
import com.franquias.Model.Produto;

public class PainelGerenciarEstoque extends JPanel {

    private JFrame framePrincipal;
    private GerenteController controller;

    private JTable tabelaProdutos;
    private DefaultTableModel modeloTabelaProdutos;

    public PainelGerenciarEstoque(GerenteController controller, JFrame framePrincipal) {
        this.framePrincipal = framePrincipal;
        this.controller = controller;
        this.setLayout(new BorderLayout(5, 5));

        criarPainelOpcoes();
        criarTabelaProdutos();
        criarPainelAcoes();

        carregarDadosNaTabela();
    }

    private void criarTabelaProdutos() {
        modeloTabelaProdutos = new DefaultTableModel();
        modeloTabelaProdutos.addColumn("Qtd. em Estoque");
        modeloTabelaProdutos.addColumn("Produto");
        modeloTabelaProdutos.addColumn("Preço");

        tabelaProdutos = new JTable(modeloTabelaProdutos);
        add(new JScrollPane(tabelaProdutos), BorderLayout.CENTER);
    }

    private void carregarDadosNaTabela() {
        modeloTabelaProdutos.setRowCount(0);

        List<Produto> produtos = controller.getProdutos(); 

        for(Produto produto : produtos) {
            Object[] rowData = {
                produto.getquantidadeEstoque(),
                produto.getProduto(),
                produto.getPreco()
            };
            modeloTabelaProdutos.addRow(rowData);
        }
    }

    private void criarPainelOpcoes() {
        JPanel painelOpcoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JButton btnVerTodosOsProdutos = new JButton("Todos os Produtos");
        JButton btnProdutosComEstoqueBaixo = new JButton("Produtos com Estoque Baixo");

        btnVerTodosOsProdutos.addActionListener(e -> carregarDadosNaTabela());
        btnProdutosComEstoqueBaixo.addActionListener(e -> carregarDadosNaTabela());

        painelOpcoes.add(btnVerTodosOsProdutos);
        painelOpcoes.add(btnProdutosComEstoqueBaixo);

        add(painelOpcoes, BorderLayout.NORTH);
    }


    private void criarPainelAcoes() {
        JPanel painelAcoes = new JPanel();
        painelAcoes.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");
        JButton btnnAdicionar = new JButton("Adicionar");

        btnEditar.addActionListener(e -> editarProduto());
        btnRemover.addActionListener(e -> removerProduto());
        btnnAdicionar.addActionListener(e -> adicionarProduto());
        
        painelAcoes.add(btnEditar);
        painelAcoes.add(btnRemover);
        painelAcoes.add(btnnAdicionar);
        
        add(painelAcoes, BorderLayout.SOUTH);
    }

    private void editarProduto() {
        int selectedRow = tabelaProdutos.getSelectedRow();
        if (selectedRow != -1) {
            long idProduto = (long) modeloTabelaProdutos.getValueAt(selectedRow, 0);
            controller.editarProduto(idProduto);
            carregarDadosNaTabela();
        } else {
            // Exibir mensagem de erro ou aviso
            JOptionPane.showMessageDialog(framePrincipal, "Nenhum produto selecionado para edição.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void removerProduto() {
        int selectedRow = tabelaProdutos.getSelectedRow();
        if (selectedRow != -1) {
            long idProduto = (long) modeloTabelaProdutos.getValueAt(selectedRow, 0);
            controller.removerProduto(idProduto);
            modeloTabelaProdutos.removeRow(selectedRow);
        } else {
            // Exibir mensagem de erro ou aviso
            JOptionPane.showMessageDialog(framePrincipal, "Nenhum produto selecionado para remoção.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarProduto() {
        DialogCadastroProduto dialog = new DialogCadastroProduto(framePrincipal);
        dialog.setVisible(true);

        Produto novoProduto = dialog.getProduto();
        if(novoProduto != null) {
            controller.adicionarProduto(novoProduto);
            carregarDadosNaTabela();
        }
    }
}
