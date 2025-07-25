package com.franquias.View;

import javax.swing.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import com.franquias.Controller.*;
import com.franquias.Model.Produto;
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

        // desenhaFormularioDeProdutos();
        // desenhaListaDeProdutos();    
        // desenhaRodape();

        this.setVisible(true);
    }

    @Override
    protected JPanel criarPainelAcoes() {
        JPanel painelAcoes = new JPanel();
        painelAcoes.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton btVender = new JButton("Nova Venda");
        btVender.addActionListener(e -> mostrarSubPainel("NOVA_VENDA"));

        JButton btRegistro = new JButton("Abrir Registro");
        btRegistro.addActionListener(e -> mostrarSubPainel("HISTORICO_VENDAS"));

        painelAcoes.add(btVender);
        painelAcoes.add(btRegistro);

        return painelAcoes;
    }

    @Override
    protected void registrarSubPaineis(JPanel painelDeCards) {
        PainelNovaVenda painelNovaVenda = new PainelNovaVenda(controller);
        PainelHistoricoVenda painelHistoricoVenda = new PainelHistoricoVenda(controller);

        painelDeCards.add(painelNovaVenda, "NOVA_VENDA");
        painelDeCards.add(painelHistoricoVenda, "HISTORICO_VENDAS");
    }
}
    