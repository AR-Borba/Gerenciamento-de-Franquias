package com.franquias.View;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import com.franquias.Controller.AplicacaoPrincipal;

public abstract class PainelBase extends JPanel {
    
    protected AplicacaoPrincipal app;

    public PainelBase(AplicacaoPrincipal app) {
        this.app = app;
    }

    public final void construirLayout() {
        JPanel painelAcoes = criarPainelAcoes();
        JPanel painelConteudo = criarPainelConteudo();

        this.add(painelAcoes, BorderLayout.NORTH);
        this.add(painelConteudo, BorderLayout.CENTER);
    }

    protected abstract JPanel criarPainelAcoes();

    protected abstract JPanel criarPainelConteudo();
}
