package com.franquias.View.PaineisGerente;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import com.franquias.Controller.GerenteController;

public class PainelGerenciarEstoque extends JPanel {

    private GerenteController controller;

    public PainelGerenciarEstoque(GerenteController controller) {
        this.controller = controller;

        this.setLayout(new BorderLayout(5, 5));
    }
}
