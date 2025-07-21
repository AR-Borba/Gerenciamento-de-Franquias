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

        cardLayout = new CardLayout();
        painelDeConteudo = new JPanel(cardLayout);

        PainelLogin painelLogin = new PainelLogin(this);
        PainelVendedor painelVendedor = new PainelVendedor(this);

        painelDeConteudo.add(painelLogin, "LOGIN");
        painelDeConteudo.add(painelVendedor, "VENDEDOR");

        telaPricipal.add(painelDeConteudo);
        cardLayout.show(painelDeConteudo, "VENDEDOR");

        telaPricipal.setLocationRelativeTo(null);
        telaPricipal.setVisible(true);

    }

    public boolean realizarLogin(String email, String senha) {
        if("adm@gmail.com".equals(email) && "1234".equals(senha))
            return true;
        return false;
    }

    // public void tentarLogar() throws {
    //     String email = tfEmail.getText();
    //     EmailValidator validador = new EmailValidator.getInstance();
    //     if(!validador.isValid(email))
    //         throw new EmailException();
            

    //     String senha = new String(pfSenha.getPassword());
    // }
}
