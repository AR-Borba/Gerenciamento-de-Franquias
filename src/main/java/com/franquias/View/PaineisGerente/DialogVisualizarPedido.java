package com.franquias.View.PaineisGerente;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JDialog;

public class DialogVisualizarPedido extends JDialog{
    
    // JTextField 

    public DialogVisualizarPedido(Frame parent) {
        super(parent, "Visualizar Pedido", true);
        setSize(400, 300);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        // Aqui você pode adicionar componentes para visualizar os detalhes do pedido
    }
}
