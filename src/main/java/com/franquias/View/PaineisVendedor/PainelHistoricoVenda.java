// package com.franquias.View.PaineisVendedor;

// import java.awt.BorderLayout;
// import java.awt.FlowLayout;
// import java.util.List;

// import javax.swing.JButton;
// import javax.swing.JFrame;
// import javax.swing.JOptionPane;
// import javax.swing.JPanel;
// import javax.swing.JScrollPane;
// import javax.swing.JTable;
// import javax.swing.table.DefaultTableModel;

// import com.franquias.Controller.GerenteController;
// import com.franquias.Controller.VendedorController;
// import com.franquias.Model.entities.Pedido;
// import com.franquias.Model.entities.Usuários.Vendedor;
// // import com.franquias.View.PaineisGerente.DialogFormularioPedido;
// import com.franquias.Model.enums.StatusPedido;

// public class PainelHistoricoVenda extends JPanel {
//     private JFrame framePrincipal;
//     private VendedorController controller;
//     private Vendedor vendedor;

//     private JTable tabelaPedidos;
//     private DefaultTableModel modeloTabelaPedidos;

//     public PainelHistoricoVenda(VendedorController controller, JFrame framePrincipal) {
//         this.framePrincipal = framePrincipal;
//         this.controller = controller;
//         this.setLayout(new BorderLayout(5, 5));

//         criarPainelOpcoes();
//         criarTabelaPedidos();
//         criarPainelAcoes();

//         carregarDadosNaTabela();
//     }

//     private void criarTabelaPedidos() {
//         modeloTabelaPedidos = new DefaultTableModel();
//         modeloTabelaPedidos.addColumn("ID");
//         modeloTabelaPedidos.addColumn("Cliente");
//         modeloTabelaPedidos.addColumn("Valor Total");
//         modeloTabelaPedidos.addColumn("Status");

//         tabelaPedidos = new JTable(modeloTabelaPedidos);
//         add(new JScrollPane(tabelaPedidos), BorderLayout.CENTER);
//     }

//     private void carregarDadosNaTabela() {
//         modeloTabelaPedidos.setRowCount(0);

//         List<Pedido> pedidos = controller.getPedidos(); 

//         for(Pedido pedido : pedidos) {
//             Object[] rowData = {
//                 pedido.getId(),
//                 pedido.getCliente(),
//                 pedido.getValorTotal(),
//                 pedido.getStatusPedido()
//             };
//             modeloTabelaPedidos.addRow(rowData);
//         }
//     }

//     private void criarPainelOpcoes() {
//         JPanel painelOpcoes = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

//         JButton btnVerTodosOsPedidos = new JButton("Todos os Pedidos");
//         JButton btnPedidosComEstoqueBaixo = new JButton("Pedidos Aguardando Alteração");

//         btnVerTodosOsPedidos.addActionListener(e -> carregarDadosNaTabela());
//         btnPedidosComEstoqueBaixo.addActionListener(e -> carregarDadosNaTabela());

//         painelOpcoes.add(btnVerTodosOsPedidos);
//         painelOpcoes.add(btnPedidosComEstoqueBaixo);

//         add(painelOpcoes, BorderLayout.NORTH);
//     }


//     private void criarPainelAcoes() {
//         JPanel painelAcoes = new JPanel();
//         painelAcoes.setLayout(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        
//         JButton btnSolicitarExclusao = new JButton("SolicitarExclusao");
//         JButton btnEditar = new JButton("Editar");
//         JButton btnnSolicitarAlteracao = new JButton("SolicitarAlteracao");

//         btnEditar.addActionListener(e -> solicitarExclusao());
//        // btnEditar.addActionListener(e -> editarPedidoselecionado());
//        // btnnSolicitarAlteracao.addActionListener(e -> solicitarAlteracaoPedido());
        
//         painelAcoes.add(btnSolicitarExclusao);
//         painelAcoes.add(btnEditar);
//         painelAcoes.add(btnnSolicitarAlteracao);
        
//         add(painelAcoes, BorderLayout.SOUTH);
//     }

//     private void solicitarExclusao() {
//         int selectedRow = tabelaPedidos.getSelectedRow();
//         if(selectedRow != -1) {
//             JOptionPane.showMessageDialog(framePrincipal, "Nenhum Pedido selecionado para edição.", "Erro", JOptionPane.ERROR_MESSAGE);
//             return;
//         }
//         Object idObject = modeloTabelaPedidos.getValueAt(selectedRow, 0);

//         long idPedido = ((Number) idObject).longValue();

//         Pedido pedidoParaExcliir = controller.buscarPedidoPorId(idPedido);
        
//         if(pedidoParaExcliir != null) {
//             pedidoParaExcliir.setStatusPedido(StatusPedido.PENDENTE_EXCLUSAO);
//         }
//     }

//     //private void editarPedidoselecionado() {
//         int selectedRow = tabelaPedidos.getSelectedRow();
//         if (selectedRow == -1) {
//             JOptionPane.showMessageDialog(framePrincipal, "Nenhum Pedido selecionado para edição.", "Erro", JOptionPane.ERROR_MESSAGE);
//             return;
//         }
//         Object idObject = modeloTabelaPedidos.getValueAt(selectedRow, 0);

//         long idPedido = ((Number) idObject).longValue();

//         Pedido pedidoParaEditar = controller.buscarPedidoPorId(idPedido);

//         if(pedidoParaEditar != null) {
//             DialogFormularioPedido dialog = new DialogFormularioPedido(framePrincipal, pedidoParaEditar);
//             dialog.setVisible(true);
            
//             // Pedido PedidoAtualizado = dialog.grtPedido();

//             if(dialog.foiSalvo()) {
//                 controller.editarPedido(pedidoParaEditar);
//                 carregarDadosNaTabela();
//                 JOptionPane.showMessageDialog(this, "Pedido editado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
//             }
//         }
//     }

//     //private void solicitarAlteracaoPedido() {
//         int selectedRow = tabelaPedidos.getSelectedRow();
//         if(selectedRow != -1) {
//             JOptionPane.showMessageDialog(framePrincipal, "Nenhum Pedido selecionado para edição.", "Erro", JOptionPane.ERROR_MESSAGE);
//             return;
//         }
//         Object idObject = modeloTabelaPedidos.getValueAt(selectedRow, 0);

//         long idPedido = ((Number) idObject).longValue();

//         Pedido pedidoParaAlterar = controller.buscarPedidoPorId(idPedido);
        
//         if(pedidoParaAlterar != null) {
//             pedidoParaAlterar.setStatusPedido(StatusPedido.PENDENTE_ALTERACAO);
//         }
//     }
// }
