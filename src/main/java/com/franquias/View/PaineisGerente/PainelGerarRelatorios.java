package com.franquias.View.PaineisGerente;

import java.awt.BorderLayout;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.franquias.Controller.GerenteController;
import com.franquias.Model.entities.Cliente;
import com.franquias.Model.entities.Pedido;
import com.franquias.Model.entities.Usuários.Vendedor;

public class PainelGerarRelatorios extends JPanel {

     private GerenteController controller;
    private JTable tabelaComprasCliente;
    private DefaultTableModel modeloTabelaClientes;

    public PainelGerarRelatorios(GerenteController controller, JFrame framePrincipal) {
        this.controller = controller;
        setLayout(new BorderLayout(5, 5));

        criarTabelaComprasCliente();
    }

    private void criarTabelaComprasCliente() {
        modeloTabelaClientes = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        modeloTabelaClientes.addColumn("ID");
        modeloTabelaClientes.addColumn("Nome");
        modeloTabelaClientes.addColumn("Nº de Compras"); // A coluna agora é a contagem

        tabelaComprasCliente = new JTable(modeloTabelaClientes);
        add(new JScrollPane(tabelaComprasCliente), BorderLayout.CENTER);
    }
    
    public void carregarDados() {
        modeloTabelaClientes.setRowCount(0); // Limpa a tabela

        // 1. Pede ao controller o mapa já processado com a contagem de pedidos
        Map<Cliente, Long> contagem = controller.getContagemDePedidosPorCliente();

        // 2. Itera sobre o mapa e o ordena pelo número de compras (do maior para o menor)
        contagem.entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .forEach(entry -> {
                Cliente cliente = entry.getKey();
                Long numeroDeCompras = entry.getValue();

                Object[] rowData = {
                    cliente.getId(),
                    cliente.getNome(),
                    numeroDeCompras
                };
                modeloTabelaClientes.addRow(rowData);
            });
    }

    // O método extrairClientesUnicos() não é mais necessário aqui.
}
