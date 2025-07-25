package com.franquias.View;

import javax.swing.*;

import java.awt.*;

import com.franquias.Controller.*;
import com.franquias.View.PaineisVendedor.PainelHistoricoVenda;
import com.franquias.View.PaineisVendedor.PainelNovaVenda;

public class PainelVendedor extends PainelBase {

    private VendedorController controller;

    JLabel lblTotalValor;
    // private final int V_GAP = 10;
    // private final int H_GAP = 5;

    public PainelVendedor(AplicacaoPrincipal app, VendedorController controller) {
        super(app);
        this.controller = controller;
        this.setLayout(new BorderLayout(5, 5));

        construirLayout();

        this.setVisible(true);
    }

    @Override
    public JMenu getMenu() {
        JMenu menuVendas = new JMenu("Vendas");

        JMenuItem itemNovaVenda = new JMenuItem("Nova Venda");
        JMenuItem itemMinhasVendas = new JMenuItem("Minhas Vendas");
        itemNovaVenda.addActionListener(e -> mostrarSubPainel("NOVA_VENDA"));
        itemMinhasVendas.addActionListener(e -> mostrarSubPainel("HISTORICO_VENDAS"));

        menuVendas.add(itemNovaVenda);
        menuVendas.add(itemMinhasVendas);

        return menuVendas;
    }

    @Override
    protected void registrarSubPaineis(JPanel painelDeCards) {
        PainelNovaVenda painelNovaVenda = new PainelNovaVenda(controller);
        PainelHistoricoVenda painelHistoricoVenda = new PainelHistoricoVenda(controller);

        painelDeCards.add(painelNovaVenda, "NOVA_VENDA");
        painelDeCards.add(painelHistoricoVenda, "HISTORICO_VENDAS");
    }
}
    