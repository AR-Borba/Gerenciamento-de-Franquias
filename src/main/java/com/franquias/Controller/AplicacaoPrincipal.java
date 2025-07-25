package com.franquias.Controller;

import javax.swing.*;

import com.franquias.View.PainelLogin;
import com.franquias.View.PainelVendedor;

import java.awt.*;

public class AplicacaoPrincipal {
    public JFrame telaPricipal;
    public JPanel painelDeConteudo;
    public CardLayout cardLayout;


    public final int WIDTH = 500;
    public final int HEIGHT = 400;
    // private final int V_GAP = 10;
    // private final int H_GAP = 5;

    public void iniciar() {
        telaPricipal = new JFrame("Franquia");
        telaPricipal.setDefaultCloseOperation(telaPricipal.EXIT_ON_CLOSE);
        telaPricipal.setSize(WIDTH, HEIGHT);

        JMenuBar menuBar = new JMenuBar();
        JMenu menuSistema = new JMenu("Sistema");
        JMenuItem menuDeslogar = new JMenuItem("Deslogar");

        menuDeslogar.addActionListener(e -> mostrarTela("LOGIN"));

        menuSistema.add(menuDeslogar);
        menuBar.add(menuSistema);

        telaPricipal.setJMenuBar(menuBar);

        cardLayout = new CardLayout();
        painelDeConteudo = new JPanel(cardLayout);

        LoginController loginController = new LoginController(this);
        VendedorController vendedorController = new VendedorController(this);

        PainelLogin painelLogin = new PainelLogin(this, loginController);
        PainelVendedor painelVendedor = new PainelVendedor(this, vendedorController);

        painelDeConteudo.add(painelLogin, "LOGIN");
        painelDeConteudo.add(painelVendedor, "VENDEDOR");

        telaPricipal.add(painelDeConteudo);
        cardLayout.show(painelDeConteudo, "LOGIN");

        telaPricipal.setLocationRelativeTo(null);
        telaPricipal.setVisible(true);

    }

    public void mostrarTela(String nomePainel) {
        cardLayout.show(painelDeConteudo, nomePainel);
    }

    public boolean realizarLogin(String email, String senha) {
        if("adm@gmail.com".equals(email) && "1234".equals(senha))
            return true;
        return false;
    }
}
