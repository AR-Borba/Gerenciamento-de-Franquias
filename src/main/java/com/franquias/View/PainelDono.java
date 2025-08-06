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

public class PainelDono extends PainelBase {
    private DonoController controller;

    public PainelDono(AplicacaoPrincipal app, DonoController controller) {
        super(app);
        this.controller = controller;

        construirLayout();
    }

    @Override
    public JMenu getMenu() {
        JMenu menuOpcoes = new JMenu("Opções");

        JMenuItem itemGerenciarGerentes = new JMenuItem("Gerenciar Gerentes");
        itemGerenciarGerentes.addActionListener(e -> mostrarSubPainel("GERENCIAR_GERENTES"));

        JMenuItem itemGerenciarFranquias = new JMenuItem("Gerenciar Franquias");
        itemGerenciarFranquias.addActionListener(e -> mostrarSubPainel("GERENCIAR_FRANQUIAS"));

        JMenuItem itemIndicadoresFinanceiros = new JMenuItem("Indicadores Financeiros");
        itemIndicadoresFinanceiros.addActionListener(e -> mostrarSubPainel("INDICADORES_FINANCEIROS"));

        JMenuItem itemRankingVendedores = new JMenuItem("Ranking Vendedores");
        itemRankingVendedores.addActionListener(e -> mostrarSubPainel("RANKING_VENDEDORES"));

        menuOpcoes.add(itemGerenciarGerentes);
        menuOpcoes.add(itemGerenciarFranquias);
        menuOpcoes.add(itemRankingVendedores);
        menuOpcoes.add(itemIndicadoresFinanceiros);

        return menuOpcoes;
    }

    @Override
    protected void registrarSubPaineis(JPanel painelDeCards) {
        PainelGerenciarGerentes painelGerenciarGerentes = new PainelGerenciarGerentes(controller,
                app.getFramePrincipal());
        PainelGerenciarFranquias painelGerenciarFranquias = new PainelGerenciarFranquias(controller,
                app.getFramePrincipal());
        PainelIndicadoresFinanceiros painelIndicadoresFinanceiros = new PainelIndicadoresFinanceiros(controller);
        PainelRankingVendedores painelRankingVendedores = new PainelRankingVendedores(controller,
                app.getFramePrincipal());

        painelDeCards.add(painelGerenciarGerentes, "GERENCIAR_GERENTES");
        painelDeCards.add(painelGerenciarFranquias, "GERENCIAR_FRANQUIAS");
        painelDeCards.add(painelIndicadoresFinanceiros, "INDICADORES_FINANCEIROS");
        painelDeCards.add(painelRankingVendedores, "RANKING_VENDEDORES");
    }
}
