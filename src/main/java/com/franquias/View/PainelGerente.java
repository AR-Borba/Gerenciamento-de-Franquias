package com.franquias.View;

import java.awt.FlowLayout;

import javax.swing.JButton;
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

        this.setVisible(true);
    }

    @Override
    protected JPanel criarPainelAcoes() {
        JPanel painelAcoes = new JPanel();
        painelAcoes.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton btGerenciarEquipe = new JButton("Gerenciar Equipe");
        btGerenciarEquipe.addActionListener(e -> mostrarSubPainel("GERENCIAR_EQUIPE"));
        painelAcoes.add(btGerenciarEquipe);

        JButton btControlarPedidos = new JButton("Controlar Pedidos");
        btControlarPedidos.addActionListener(e -> mostrarSubPainel("CONTROLAR_PEDIDOS"));
        painelAcoes.add(btControlarPedidos);

        JButton btEstoque = new JButton("Gerenciar Estoque");
        btEstoque.addActionListener(e -> mostrarSubPainel("GERENCIAR_ESTOQUE"));
        painelAcoes.add(btEstoque);

        JButton btRelatorios = new JButton("Gerar Relatórios");
        btRelatorios.addActionListener(e -> mostrarSubPainel("GERAR_RELATORIOS"));
        painelAcoes.add(btRelatorios);

        return painelAcoes;
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
