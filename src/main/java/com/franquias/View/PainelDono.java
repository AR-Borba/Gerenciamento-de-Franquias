package com.franquias.View;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.franquias.Controller.AplicacaoPrincipal;
import com.franquias.Controller.DonoController;
import com.franquias.View.PaineisDono.PainelGerenciarGerentes;
import com.franquias.View.PaineisDono.PainelGerenciarFranquias;
import com.franquias.View.PaineisDono.PainelIndicadoresFinanceiros;
import com.franquias.View.PaineisDono.PainelRankingVendedores;

public class PainelDono extends PainelBase{
        private DonoController controller;

    public PainelDono(AplicacaoPrincipal app, DonoController controller) {
        super(app);
        this.controller = controller;

        construirLayout();
    }

    @Override
    public JMenu getMenu() {
        JMenu menuOpcoes = new JMenu("Opções");

        JMenuItem itemGerenciarEquipe = new JMenuItem("Gerenciar Gerentes");
        itemGerenciarEquipe.addActionListener(e -> mostrarSubPainel("GERENCIAR_GERENTES"));
        
        JMenuItem itemControlarPedidos = new JMenuItem("Gerenciar Franquias");
        itemControlarPedidos.addActionListener(e -> mostrarSubPainel("GERENCIAR_FRANQUIAS"));
        
        JMenuItem itemRelatorios = new JMenuItem("Indicadores Financeiros");
        itemRelatorios.addActionListener(e -> mostrarSubPainel("INDICADORES_FINANCEIROS"));

        JMenuItem itemEstoque = new JMenuItem("Ranking Vendedores");
        itemEstoque.addActionListener(e -> mostrarSubPainel("RANKING_VENDEDORES"));
        
        menuOpcoes.add(itemGerenciarEquipe);
        menuOpcoes.add(itemControlarPedidos);
        menuOpcoes.add(itemEstoque);
        menuOpcoes.add(itemRelatorios);

        return menuOpcoes;
    }

    @Override
    protected void registrarSubPaineis(JPanel painelDeCards) {
        PainelGerenciarGerentes painelGerenciarEquipe = new PainelGerenciarGerentes(controller);
        PainelGerenciarFranquias painelControlarPedidos = new PainelGerenciarFranquias(controller);
        PainelIndicadoresFinanceiros painelGerenciarEstoque = new PainelIndicadoresFinanceiros(controller);
        PainelRankingVendedores painelGerarRelatorios = new PainelRankingVendedores(controller);

        painelDeCards.add(painelGerenciarEquipe, "GERENCIAR_GERENTES");
        painelDeCards.add(painelControlarPedidos, "GERENCIAR_FRANQUIAS");
        painelDeCards.add(painelGerenciarEstoque, "INDICADORES_FINANCEIROS");
        painelDeCards.add(painelGerarRelatorios, "RANKING_VENDEDORES");
    }
}
