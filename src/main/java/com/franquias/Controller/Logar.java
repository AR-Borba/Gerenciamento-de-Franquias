package com.franquias.Controller;

import com.franquias.View.TelaFranquia;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Logar implements ActionListener {
    
    private final TelaFranquia tela;

    public Logar(TelaFranquia tela) {
        this.tela = tela;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tela.tentarLogar();
    }
}
