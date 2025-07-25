package com.franquias.View;

import java.awt.BorderLayout;
import java.awt.CardLayout;

import javax.swing.JPanel;

import com.franquias.Controller.AplicacaoPrincipal;

public abstract class PainelBase extends JPanel {
    
    protected AplicacaoPrincipal app;

    private CardLayout cardLayoutConteudo;
    private JPanel painelConteudo;

    public PainelBase(AplicacaoPrincipal app) {
        super(new BorderLayout(5, 5));
        this.app = app;
    }

    public final void construirLayout() {
        JPanel painelAcoes = criarPainelAcoes();
        this.add(painelAcoes, BorderLayout.NORTH);
        
        cardLayoutConteudo = new CardLayout();
        painelConteudo = new JPanel(cardLayoutConteudo);

        registrarSubPaineis(painelConteudo);

        this.add(painelConteudo, BorderLayout.CENTER);
    }

    protected void mostrarSubPainel(String nomeDoCard) {
        cardLayoutConteudo.show(painelConteudo, nomeDoCard);
    }

    protected abstract JPanel criarPainelAcoes();

    protected abstract void registrarSubPaineis(JPanel painelDeCards);
}
