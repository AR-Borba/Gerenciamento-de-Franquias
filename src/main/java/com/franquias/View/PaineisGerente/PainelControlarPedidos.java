package com.franquias.View.PaineisGerente;

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

import com.franquias.Controller.GerenteController;
import com.franquias.Controller.PedidoController;
import com.franquias.Model.entities.Pedido;
import com.franquias.Model.enums.StatusPedido;
import com.franquias.View.DialogAlterarPedido;

public class PainelControlarPedidos extends JPanel {

    private JFrame framePrincipal;
    private GerenteController controller;
    private PedidoController pedidoController;

    private JTable tabelaPedidos;
    private DefaultTableModel modeloTabelaPedidos;

    public PainelControlarPedidos(GerenteController controller, JFrame framePrincipal,
            PedidoController pedidoController) {
        this.framePrincipal = framePrincipal;
        this.controller = controller;
        this.pedidoController = pedidoController;
        this.setLayout(new BorderLayout());

        criarPainelOpcoes();
        criarTabelaPedidos();
        criarPainelAcoes();
    }

    private void criarTabelaPedidos() {
        modeloTabelaPedidos = new DefaultTableModel();
        modeloTabelaPedidos.addColumn("ID");
        modeloTabelaPedidos.addColumn("Status");
        modeloTabelaPedidos.addColumn("Cliente");
        modeloTabelaPedidos.addColumn("Valor Total");

        tabelaPedidos = new JTable(modeloTabelaPedidos);
        add(new JScrollPane(tabelaPedidos), BorderLayout.CENTER);
    }

    public void carregarDados() {
        modeloTabelaPedidos.setRowCount(0); // Limpa a tabela antes de adicionar novos dados

        List<Pedido> pedidos = controller.getPedidos();

        for (Pedido pedido : pedidos) {
            Object[] rowData = {
                    pedido.getId(),
                    pedido.getStatusPedido(),
                    pedido.getCliente(),
                    pedido.getValorTotal()
            };
            modeloTabelaPedidos.addRow(rowData);
        }
    }

    private void carregarPedidosPendentesNaTabela() {
        modeloTabelaPedidos.setRowCount(0); // Limpa a tabela antes de adicionar novos dados

        List<Pedido> pedidos = controller.getPedidosPendentes();

        for (Pedido pedido : pedidos) {
            Object[] rowData = {
                    pedido.getId(),
                    pedido.getStatusPedido(),
                    pedido.getCliente(),
                    pedido.getValorTotal()
            };
            modeloTabelaPedidos.addRow(rowData);
        }
    }

    private void criarPainelOpcoes() {
        JPanel painelOpcoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JButton btnVerTodosOsPedidos = new JButton("Ver Todos os Pedidos");
        JButton btnPedidosAguardandoAlteracao = new JButton("Pedidos Aguardando Alteração");

        btnVerTodosOsPedidos.addActionListener(e -> carregarDados());
        btnPedidosAguardandoAlteracao.addActionListener(e -> carregarPedidosPendentesNaTabela());

        painelOpcoes.add(btnVerTodosOsPedidos);
        painelOpcoes.add(btnPedidosAguardandoAlteracao);

        add(painelOpcoes, BorderLayout.NORTH);
    }

    private void criarPainelAcoes() {
        JPanel painelAcoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));

        JButton btnAutorizar = new JButton("Autoriza");
        btnAutorizar.addActionListener(e -> autorizar());

        JButton btnVisualizarPedido = new JButton("Visualizar Pedido");
        btnVisualizarPedido.addActionListener(e -> visualizarPedido());

        JButton btnEditaPedido = new JButton("Editar Pedido");
        btnEditaPedido.addActionListener(e -> editaPedido());

        painelAcoes.add(btnAutorizar);
        painelAcoes.add(btnVisualizarPedido);
        painelAcoes.add(btnEditaPedido);
        add(painelAcoes, BorderLayout.SOUTH);
    }

    private void autorizar() {
        int selectedRow = tabelaPedidos.getSelectedRow();
        if (selectedRow != -1) {

            Long idPedido = (Long) modeloTabelaPedidos.getValueAt(selectedRow, 0);

            Pedido pedido = controller.buscarPedidoPorId(idPedido);
            StatusPedido statusPedido = pedido.getStatusPedido();
            if (statusPedido != StatusPedido.PENDENTE_ALTERACAO && statusPedido != StatusPedido.PENDENTE_EXCLUSAO) {
                JOptionPane.showMessageDialog(framePrincipal,
                        "Selecione um pedido aguardando autorização. Status do pedido: " + pedido.getStatusPedido(),
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            } else if (statusPedido == StatusPedido.PENDENTE_ALTERACAO) {
                controller.autorizarAlteracaoPedido(pedido);
                JOptionPane.showMessageDialog(framePrincipal, "Alteração Autorizada.", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                carregarDados();
                return;
            }
            if (statusPedido == StatusPedido.PENDENTE_EXCLUSAO) {
                controller.autorizarExclusaoPedido(pedido);
                JOptionPane.showMessageDialog(framePrincipal, "Cancelamento Realizado.", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                carregarDados();
                return;
            }
        }
    }

    private void visualizarPedido() {
        int selectedRow = tabelaPedidos.getSelectedRow();
        if (selectedRow != -1) {
            Pedido pedido = controller.getPedidos().get(selectedRow);
            DialogVisualizarPedido dialog = new DialogVisualizarPedido(framePrincipal, pedido, pedidoController);
            dialog.setVisible(true);
        } else
            JOptionPane.showMessageDialog(framePrincipal, "Selecione um pedido para visualizar.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
    }

    private void editaPedido() {
        int selectedRow = tabelaPedidos.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(framePrincipal, "Nenhum Pedido selecionado para edição.", "Erro",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        Object idObject = modeloTabelaPedidos.getValueAt(selectedRow, 0);

        long idPedido = ((Number) idObject).longValue();

        Pedido pedidoParaAlterar = controller.buscarPedidoPorId(idPedido);

        if (pedidoParaAlterar != null && pedidoParaAlterar.getStatusPedido() == StatusPedido.EM_ALTERACAO) {
            DialogAlterarPedido dialog = new DialogAlterarPedido(framePrincipal, pedidoParaAlterar, pedidoController);
            dialog.setVisible(true);

            if (dialog.foiSalvo()) {
                controller.atualizarPedido(pedidoParaAlterar);
                carregarDados();
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Este pedido não está autorizado para edição. Solicite a alteração primeiro.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
