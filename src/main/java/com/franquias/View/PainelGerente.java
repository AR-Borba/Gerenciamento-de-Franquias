package com.franquias.View;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import com.franquias.Controller.AplicacaoPrincipal;
import com.franquias.Controller.GerenteController;
import com.franquias.Model.entities.Usuários.Gerente;
import com.franquias.Persistence.GerentePersistence;
import com.franquias.View.PaineisGerente.*;

public class PainelGerente extends PainelBase {

    private GerenteController controller;

    private PainelControlarPedidos painelControlarPedidos;
    private PainelGerarRelatorios painelGerarRelatorios;
    private PainelGerenciarEquipe painelGerenciarEquipe;
    private PainelGerenciarEstoque painelGerenciarEstoque;

    public PainelGerente(AplicacaoPrincipal app, GerenteController controller) {
        super(app);
        this.controller = controller;

        GerentePersistence gerentePersistence = new GerentePersistence();
        Gerente gerente = gerentePersistence.buscarPorId(0);
        controller.iniciarSessao(gerente);

        construirLayout();
    }

    @Override
    public JMenu getMenu() {
        JMenu menuOpcoes = new JMenu("Opções");

        JMenuItem itemGerenciarEquipe = new JMenuItem("Gerenciar Equipe");
        itemGerenciarEquipe.addActionListener(e -> {
            if(painelGerenciarEquipe != null) painelGerenciarEquipe.carregarDados();
            mostrarSubPainel("GERENCIAR_EQUIPE");
        });
        
        JMenuItem itemControlarPedidos = new JMenuItem("Controlar Pedidos");
        itemControlarPedidos.addActionListener(e -> {
            if(painelControlarPedidos != null) painelControlarPedidos.carregarDados();
            mostrarSubPainel("CONTROLAR_PEDIDOS");
        });
        
        JMenuItem itemEstoque = new JMenuItem("Gerenciar Estoque");
        itemEstoque.addActionListener(e -> {
            if(painelGerenciarEstoque != null) painelGerenciarEstoque.carregarDados();
            mostrarSubPainel("GERENCIAR_ESTOQUE");
        });
        
        JMenuItem itemRelatorios = new JMenuItem("Gerar Relatórios");
        itemRelatorios.addActionListener (e -> {
            if (painelGerarRelatorios != null) painelGerarRelatorios.carregarDados();
            mostrarSubPainel("GERAR_RELATORIOS");
        });
        
        menuOpcoes.add(itemGerenciarEquipe);
        menuOpcoes.add(itemControlarPedidos);
        menuOpcoes.add(itemEstoque);
        menuOpcoes.add(itemRelatorios);

        return menuOpcoes;
    }

    @Override
    protected void registrarSubPaineis(JPanel painelDeCards) {
        this.painelGerenciarEquipe = new PainelGerenciarEquipe(controller, app.getFramePrincipal());
        this.painelControlarPedidos = new PainelControlarPedidos(controller, app.getFramePrincipal());
        this.painelGerenciarEstoque = new PainelGerenciarEstoque(controller, app.getFramePrincipal());
        this.painelGerarRelatorios = new PainelGerarRelatorios(controller, app.getFramePrincipal());

        painelDeCards.add(painelGerenciarEquipe, "GERENCIAR_EQUIPE");
        painelDeCards.add(painelControlarPedidos, "CONTROLAR_PEDIDOS");
        painelDeCards.add(painelGerenciarEstoque, "GERENCIAR_ESTOQUE");
        painelDeCards.add(painelGerarRelatorios, "GERAR_RELATORIOS");
    }
}
