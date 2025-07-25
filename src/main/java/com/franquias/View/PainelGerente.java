package com.franquias.View;

import javax.swing.JPanel;

import com.franquias.Controller.AplicacaoPrincipal;
import com.franquias.Controller.GerenteController;

public class PainelGerente extends PainelBase {

    private GerenteController controller;

    public PainelGerente(AplicacaoPrincipal app, GerenteController controller) {
        super(app);
        this.controller = controller;
    }

    @Override
    protected JPanel criarPainelAcoes() {
        // Implementar ações específicas do gerente
        return new JPanel();
    }

    @Override
    protected JPanel criarPainelConteudo() {
        // Implementar conteúdo específico do gerente
        return new JPanel();
    }
}
