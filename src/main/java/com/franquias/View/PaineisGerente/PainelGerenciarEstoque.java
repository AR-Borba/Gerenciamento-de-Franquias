package com.franquias.View.PaineisGerente;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

import com.franquias.Controller.GerenteController;
import com.franquias.Model.Produto;
import com.franquias.Model.entities.Usuários.Vendedor;
import com.franquias.View.PainelAtualizavel;

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

        carregarDados();
    }

    private void criarTabelaProdutos() {
        modeloTabelaProdutos = new DefaultTableModel();
        modeloTabelaProdutos.addColumn("ID");
        modeloTabelaProdutos.addColumn("Qtd. em Estoque");
        modeloTabelaProdutos.addColumn("Produto");
        modeloTabelaProdutos.addColumn("Preço");

        tabelaProdutos = new JTable(modeloTabelaProdutos);
        add(new JScrollPane(tabelaProdutos), BorderLayout.CENTER);
    }

    public void carregarDados() {
        modeloTabelaProdutos.setRowCount(0);

        List<Produto> produtos = controller.getProdutos(); 

        for(Produto produto : produtos) {
            Object[] rowData = {
                produto.getId(),
                produto.getQuantidadeEstoque(),
                produto.getProduto(),
                produto.getPreco()
            };
            modeloTabelaProdutos.addRow(rowData);
        }
    }
    
    private void carregarDadosEstoqueBaixo() {
        modeloTabelaProdutos.setRowCount(0);

        List<Produto> produtos = controller.getProdutosComEstoqueBaixo(11); 

        for(Produto produto : produtos) {
            Object[] rowData = {
                produto.getId(),
                produto.getQuantidadeEstoque(),
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

        btnVerTodosOsProdutos.addActionListener(e -> carregarDados());
        btnProdutosComEstoqueBaixo.addActionListener(e -> carregarDadosEstoqueBaixo());

        painelOpcoes.add(btnVerTodosOsProdutos);
        painelOpcoes.add(btnProdutosComEstoqueBaixo);

        add(painelOpcoes, BorderLayout.NORTH);
    }


    private void criarPainelAcoes() {
        JPanel painelAcoes = new JPanel();
        painelAcoes.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));

        JButton btnAdicionarAoEstoque = new JButton("Adicionar ao Estoque");
        JButton btnEditar = new JButton("Editar");
        JButton btnRemover = new JButton("Remover");
        JButton btnnAdicionar = new JButton("Adicionar");

        btnAdicionarAoEstoque.addActionListener(e -> adicionarAoEstoque());
        btnEditar.addActionListener(e -> editarProdutoSelecionado());
        btnRemover.addActionListener(e -> removerProdutoSelecionado());
        btnnAdicionar.addActionListener(e -> adicionarProduto());
        
        painelAcoes.add(btnEditar);
        painelAcoes.add(btnRemover);
        painelAcoes.add(btnnAdicionar);
        
        add(painelAcoes, BorderLayout.SOUTH);
    }

    private void adicionarAoEstoque() {
        DialogAdicionarAoEstoque dialog = new DialogAdicionarAoEstoque();
        dialog.setVisible(true);

        if(dialog.foiSalvo()) {
            int selectedRow = tabelaProdutos.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(framePrincipal, "Nenhum Produto selecionado para edição.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            Object idObject = modeloTabelaProdutos.getValueAt(selectedRow, 0);

        long idProduto = ((Number) idObject).longValue();

        Produto produto = controller.buscarProdutoPorId(idProduto);

            controller.adicionarAoEstoque(produto, dialog.getQtd());
            carregarDados();
        }
    }

    private void editarProdutoSelecionado() {
        int selectedRow = tabelaProdutos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(framePrincipal, "Nenhum Produto selecionado para edição.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Object idObject = modeloTabelaProdutos.getValueAt(selectedRow, 0);

        long idProduto = ((Number) idObject).longValue();

        Produto ProdutoParaEditar = controller.buscarProdutoPorId(idProduto);

        if(ProdutoParaEditar != null) {
            DialogFormularioProduto dialog = new DialogFormularioProduto(framePrincipal, ProdutoParaEditar);
            dialog.setVisible(true);
            
            // Produto ProdutoAtualizado = dialog.grtPRoduto();

            if(dialog.foiSalvo()) {
                controller.editarProduto(ProdutoParaEditar);
                carregarDados();
                JOptionPane.showMessageDialog(this, "Produto editado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        }
            
    }

    private void removerProdutoSelecionado() {
        int selectedRow = tabelaProdutos.getSelectedRow();
        if (selectedRow != -1) {
            long idProduto = (long) tabelaProdutos.getValueAt(selectedRow, 0);
            controller.removerProduto(idProduto);
            carregarDados();
        } else {
            // Exibir mensagem de erro ou aviso
            JOptionPane.showMessageDialog(framePrincipal, "Nenhum Produto selecionado para remoção.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void adicionarProduto() {
        DialogFormularioProduto dialog = new DialogFormularioProduto(framePrincipal);
        dialog.setVisible(true);

        Produto novoProduto = dialog.getProduto();
        if(novoProduto != null) {
            controller.adicionarProduto(novoProduto);
            carregarDados();
        }
    }
}
