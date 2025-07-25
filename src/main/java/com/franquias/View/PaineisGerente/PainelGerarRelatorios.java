package com.franquias.View.PaineisGerente;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.franquias.Controller.GerenteController;

public class PainelGerarRelatorios extends JPanel {

    private JFrame framePrincipal;
    private GerenteController controller;

    public PainelGerarRelatorios(GerenteController controller, JFrame framePrincipal) {
        this.framePrincipal = framePrincipal;
        this.controller = controller;
    }
}
