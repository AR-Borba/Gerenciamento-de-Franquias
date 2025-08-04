package com.franquias.View;

import javax.swing.*;

import java.awt.*;

import com.franquias.Controller.*;
import com.franquias.Model.entities.Usuários.Vendedor;
import com.franquias.Persistence.VendedorPersistence;
import com.franquias.View.PaineisVendedor.*;

public class PainelVendedor extends PainelBase {

    private VendedorController controller;

    private PainelHistoricoVenda painelHistoricoVenda;
    private PainelVenda painelVenda;

    PedidoController pedidoController;


    public PainelVendedor(AplicacaoPrincipal app, VendedorController controller, PedidoController pedidoController) {
        super(app);
        this.controller = controller;
        this.setLayout(new BorderLayout(5, 5));

        this.pedidoController = pedidoController;

        // VendedorPersistence vendedorPersistence = new VendedorPersistence();
        // Vendedor vendedor = vendedorPersistence.buscarPorId(1);
        // controller.iniciarSessao(vendedor);

        construirLayout();

        this.setVisible(true);
    }

    @Override
    public JMenu getMenu() {
        JMenu menuVendas = new JMenu("Vendas");

        JMenuItem itemNovaVenda = new JMenuItem("Nova Venda");
        JMenuItem itemMinhasVendas = new JMenuItem("Minhas Vendas");
        itemNovaVenda.addActionListener(e -> mostrarSubPainel("NOVA_VENDA"));
        itemMinhasVendas.addActionListener(e -> {
            if(painelHistoricoVenda != null)
                painelHistoricoVenda.carregarDados();
            mostrarSubPainel("HISTORICO_VENDAS");
        });

        menuVendas.add(itemNovaVenda);
        menuVendas.add(itemMinhasVendas);

        return menuVendas;
    }

    @Override
    protected void registrarSubPaineis(JPanel painelDeCards) {
        this.painelVenda = new PainelVenda(controller, app.getFramePrincipal());
        this.painelHistoricoVenda = new PainelHistoricoVenda(controller, app.getFramePrincipal(), pedidoController);

        painelDeCards.add(painelVenda, "NOVA_VENDA");
        painelDeCards.add(painelHistoricoVenda, "HISTORICO_VENDAS");
    }
}
    