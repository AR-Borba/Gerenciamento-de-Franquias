package com.franquias.View.PaineisVendedor;

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

import com.franquias.Controller.PedidoController;
import com.franquias.Controller.VendedorController;
import com.franquias.Model.entities.Pedido;
import com.franquias.Model.enums.StatusPedido;
import com.franquias.View.PainelAtualizavel;

public class PainelHistoricoVenda extends JPanel implements PainelAtualizavel {
    private JFrame framePrincipal;
    private VendedorController controller;
    private PedidoController pedidoController;

    private JTable tabelaPedidos;
    private DefaultTableModel modeloTabelaPedidos;

    public PainelHistoricoVenda(VendedorController controller, JFrame framePrincipal, PedidoController pedidoController) {
        this.framePrincipal = framePrincipal;
        this.controller = controller;
        this.pedidoController = pedidoController;
        this.setLayout(new BorderLayout(5, 5));

        criarPainelOpcoes();
        criarTabelaPedidos();
        criarPainelAcoes();
    }

    private void criarTabelaPedidos() {
        modeloTabelaPedidos = new DefaultTableModel();
        modeloTabelaPedidos.addColumn("ID");
        modeloTabelaPedidos.addColumn("Cliente");
        modeloTabelaPedidos.addColumn("Valor Total");
        modeloTabelaPedidos.addColumn("Status");

        tabelaPedidos = new JTable(modeloTabelaPedidos);
        add(new JScrollPane(tabelaPedidos), BorderLayout.CENTER);
    }

    @Override
    public void carregarDados() {
        modeloTabelaPedidos.setRowCount(0);

        List<Pedido> pedidos = controller.getPedidosVendedor(); 

        for(Pedido pedido : pedidos) {
            Object[] rowData = {
                pedido.getId(),
                pedido.getCliente(),
                pedido.getValorTotal(),
                pedido.getStatusPedido()
            };
            modeloTabelaPedidos.addRow(rowData);
        }
    }
    
    private void carregarPedidosPendentesNaTabela() {
        modeloTabelaPedidos.setRowCount(0);

        List<Pedido> pedidos = controller.getPedidosVendedorPendentesAlteracao(); 

        for(Pedido pedido : pedidos) {
            Object[] rowData = {
                pedido.getId(),
                pedido.getCliente(),
                pedido.getValorTotal(),
                pedido.getStatusPedido()
            };
            modeloTabelaPedidos.addRow(rowData);
        }
    }

    private void criarPainelOpcoes() {
        JPanel painelOpcoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

        JButton btnVerTodosOsPedidos = new JButton("Todos os Pedidos");
        JButton btnPedidosComEstoqueBaixo = new JButton("Pedidos Aguardando Alteração");

        btnVerTodosOsPedidos.addActionListener(e -> carregarDados());
        btnPedidosComEstoqueBaixo.addActionListener(e -> carregarPedidosPendentesNaTabela());

        painelOpcoes.add(btnVerTodosOsPedidos);
        painelOpcoes.add(btnPedidosComEstoqueBaixo);

        add(painelOpcoes, BorderLayout.NORTH);
    }

    private void criarPainelAcoes() {
        JPanel painelAcoes = new JPanel();
        painelAcoes.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        
        JButton btnSolicitarExclusao = new JButton("SolicitarExclusao");
        JButton btnEditar = new JButton("Editar");
        JButton btnnSolicitarAlteracao = new JButton("SolicitarAlteracao");

        btnSolicitarExclusao.addActionListener(e -> solicitarExclusao());
        btnEditar.addActionListener(e -> editarPedidoselecionado());
        btnnSolicitarAlteracao.addActionListener(e -> solicitarAlteracaoPedido());
        
        painelAcoes.add(btnSolicitarExclusao);
        painelAcoes.add(btnEditar);
        painelAcoes.add(btnnSolicitarAlteracao);
        
        add(painelAcoes, BorderLayout.SOUTH);
    }

    private void solicitarExclusao() {
        int selectedRow = tabelaPedidos.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(framePrincipal, "Nenhum Pedido selecionado para edição.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Object idObject = modeloTabelaPedidos.getValueAt(selectedRow, 0);

        long idPedido = ((Number) idObject).longValue();

        Pedido pedidoParaExcluir = controller.buscarPedidoPorId(idPedido);
        
        if(pedidoParaExcluir != null) {
            pedidoParaExcluir.setStatusPedido(StatusPedido.PENDENTE_EXCLUSAO);
            JOptionPane.showMessageDialog(framePrincipal, "Pedido Para Exclusão Enviado", "Solicita Exclusão", JOptionPane.INFORMATION_MESSAGE);
        }
        else
            JOptionPane.showMessageDialog(framePrincipal, "Pedido Para Exclusão Com Erro", "Solicita Exclusão", JOptionPane.ERROR_MESSAGE);
    }

    private void editarPedidoselecionado() {
        int selectedRow = tabelaPedidos.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(framePrincipal, "Nenhum Pedido selecionado para edição.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Object idObject = modeloTabelaPedidos.getValueAt(selectedRow, 0);

        long idPedido = ((Number) idObject).longValue();

        Pedido pedidoParaAlterar = controller.buscarPedidoPorId(idPedido);
        
        if(pedidoParaAlterar != null && pedidoParaAlterar.getStatusPedido() == StatusPedido.EM_ALTERACAO) {
            DialogAlterarPedido dialog = new DialogAlterarPedido(framePrincipal, pedidoParaAlterar, pedidoController);
            dialog.setVisible(true);

            if(dialog.foiSalvo()) {
                controller.atualizarPedido(pedidoParaAlterar);
                carregarDados();
            }
        }
        else {
            JOptionPane.showMessageDialog(this, "Este pedido não está autorizado para edição. Solicite a alteração primeiro.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void solicitarAlteracaoPedido() {
        int selectedRow = tabelaPedidos.getSelectedRow();
        if(selectedRow == -1) {
            JOptionPane.showMessageDialog(framePrincipal, "Nenhum Pedido selecionado para edição.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        long idPedido = (long) modeloTabelaPedidos.getValueAt(selectedRow, 0);
        Pedido pedidoParaAlterar = controller.buscarPedidoPorId(idPedido);
        
        if(pedidoParaAlterar != null) {
            if(pedidoParaAlterar.getStatusPedido() != StatusPedido.CONCLUIDO) {
                JOptionPane.showMessageDialog(this, "Só é possível solicitar alteração de pedidos com status 'ATIVO'.", "Ação Inválida", JOptionPane.WARNING_MESSAGE);
                return; 
            }

            pedidoParaAlterar.setStatusPedido(StatusPedido.PENDENTE_ALTERACAO);

            controller.atualizarPedido(pedidoParaAlterar);
            carregarDados();

            JOptionPane.showMessageDialog(framePrincipal, "Solicitação de Alteração Enviado ao Gerente", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
