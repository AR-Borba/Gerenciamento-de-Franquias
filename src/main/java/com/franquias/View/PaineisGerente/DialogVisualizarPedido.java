package com.franquias.View.PaineisGerente;

import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.imageio.plugins.jpeg.JPEGHuffmanTable;
import javax.swing.JDialog;

import com.franquias.Model.entities.Pedido;

public class DialogVisualizarPedido extends JDialog{

    private Pedido pedido;
    
    public DialogVisualizarPedido(Frame parent, Pedido pedidoSendoEditado) {
        super(parent, "Visualizar Pedido", true);

        this.pedido = pedidoSendoEditado;
    }

    private void desenhaUI() {
        setSize(400, 300);
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        
    }
}
