package com.franquias.View;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.franquias.Controller.AplicacaoPrincipal;
import com.franquias.Controller.GerenteController;
import com.franquias.View.PaineisGerente.*;

public class PainelGerente extends PainelBase {

    private GerenteController controller;

    public PainelGerente(AplicacaoPrincipal app, GerenteController controller) {
        super(app);
        this.controller = controller;

        construirLayout();
    }

    @Override
    public JMenu getMenu() {
        JMenu menuOpcoes = new JMenu("Opções");

        JMenuItem itemGerenciarEquipe = new JMenuItem("Gerenciar Equipe");
        itemGerenciarEquipe.addActionListener(e -> mostrarSubPainel("GERENCIAR_EQUIPE"));
        
        JMenuItem itemControlarPedidos = new JMenuItem("Controlar Pedidos");
        itemControlarPedidos.addActionListener(e -> mostrarSubPainel("CONTROLAR_PEDIDOS"));
        
        JMenuItem itemEstoque = new JMenuItem("Gerenciar Estoque");
        itemEstoque.addActionListener(e -> mostrarSubPainel("GERENCIAR_ESTOQUE"));
        
        JMenuItem itemRelatorios = new JMenuItem("Gerar Relatórios");
        itemRelatorios.addActionListener(e -> mostrarSubPainel("GERAR_RELATORIOS"));
        
        menuOpcoes.add(itemGerenciarEquipe);
        menuOpcoes.add(itemControlarPedidos);
        menuOpcoes.add(itemEstoque);
        menuOpcoes.add(itemRelatorios);

        return menuOpcoes;
    }

    @Override
    protected void registrarSubPaineis(JPanel painelDeCards) {
        PainelGerenciarEquipe painelGerenciarEquipe = new PainelGerenciarEquipe(controller);
        PainelControlarPedidos painelControlarPedidos = new PainelControlarPedidos(controller);
        PainelGerenciarEstoque painelGerenciarEstoque = new PainelGerenciarEstoque(controller);
        PainelGerarRelatorios painelGerarRelatorios = new PainelGerarRelatorios(controller);

        painelDeCards.add(painelGerenciarEquipe, "GERENCIAR_EQUIPE");
        painelDeCards.add(painelControlarPedidos, "CONTROLAR_PEDIDOS");
        painelDeCards.add(painelGerenciarEstoque, "GERENCIAR_ESTOQUE");
        painelDeCards.add(painelGerarRelatorios, "GERAR_RELATORIOS");
    }
}
