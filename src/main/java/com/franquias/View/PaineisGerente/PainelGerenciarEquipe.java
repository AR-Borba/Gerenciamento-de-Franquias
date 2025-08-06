package com.franquias.View.PaineisGerente;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.math.BigDecimal;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.franquias.Controller.GerenteController;
import com.franquias.Model.entities.Usuários.Vendedor;
import com.franquias.exceptions.ValidationException;

public class PainelGerenciarEquipe extends JPanel {

    private JFrame framePrincipal;

    private GerenteController controller;
    private JTable tabelaRankingEquipe;
    private DefaultTableModel modeloTabelaRanking;

    public PainelGerenciarEquipe(GerenteController controller, JFrame framePrincipal) {
        this.framePrincipal = framePrincipal;
        this.controller = controller;
        setLayout(new BorderLayout(5, 5));

        criarTabelaRankingEquipe();
        criarPainelAcoes();
        carregarDados();
    }

    public void criarTabelaRankingEquipe() {
        modeloTabelaRanking = new DefaultTableModel();
        modeloTabelaRanking.addColumn("ID");
        modeloTabelaRanking.addColumn("Nome");
        modeloTabelaRanking.addColumn("Total de Vendas");

        tabelaRankingEquipe = new JTable(modeloTabelaRanking);
        add(new JScrollPane(tabelaRankingEquipe), BorderLayout.CENTER);
    }

    public void carregarDados() { // lembrar de carregar ordenado por vendas
        modeloTabelaRanking.setRowCount(0); // Limpa a tabela antes de adicionar novos dados

        List<Vendedor> vendedores = controller.getEquipeDeVendasOrdenadaPorVendas();

        for (Vendedor vendedor : vendedores) {
            BigDecimal totalVendas = controller.calcularTotalVendasPorVendedor(vendedor);

            Object[] rowData = {
                    vendedor.getId(),
                    vendedor.getNome(),
                    totalVendas
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

        btnEditar.addActionListener(e -> editarVendedorSelecionado());
        btnRemover.addActionListener(e -> removerVendedorSelecionado());
        btnnAdicionar.addActionListener(e -> adicionarVendedor());

        painelAcoes.add(btnEditar);
        painelAcoes.add(btnRemover);
        painelAcoes.add(btnnAdicionar);

        add(painelAcoes, BorderLayout.SOUTH);
    }

    private void adicionarVendedor() {
        DialogFormularioVendedor dialog = new DialogFormularioVendedor(framePrincipal);
        dialog.setVisible(true);

        if (dialog.foiSalvo()) {
            Vendedor novoVendedor = dialog.getVendedor();

            try {
                controller.adicionarVendedor(novoVendedor);

                carregarDados();

                JOptionPane.showMessageDialog(this, "Vendedor '" + novoVendedor.getNome() + "' cadastrado com sucesso!",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);

            } catch (ValidationException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removerVendedorSelecionado() {
        int selectedRow = tabelaRankingEquipe.getSelectedRow();
        if (selectedRow != -1) {
            long idVendedor = (long) modeloTabelaRanking.getValueAt(selectedRow, 0);
            controller.removerVendedor(idVendedor);
            carregarDados();
        } else {
            // Exibir mensagem de erro ou aviso
            JOptionPane.showMessageDialog(framePrincipal, "Nenhum vendedor selecionado para remoção.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarVendedorSelecionado() {
        int selectedRow = tabelaRankingEquipe.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(framePrincipal, "Nenhum vendedor selecionado para edição.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        long idVendedor = (long) modeloTabelaRanking.getValueAt(selectedRow, 0);

        Vendedor vendedorParaEditar = controller.buscarVendedorPorId(idVendedor);

        if (vendedorParaEditar != null) {
            DialogFormularioVendedor dialog = new DialogFormularioVendedor(framePrincipal, vendedorParaEditar);
            dialog.setVisible(true);

            // Vendedor vendedorAtualizado = dialog.getVendedor();

            if (dialog.foiSalvo()) {
                controller.editarVendedor(vendedorParaEditar);
                carregarDados();
                JOptionPane.showMessageDialog(this, "Vendedor editado com sucesso!", "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }
}
