package com.franquias.Controller;

import javax.swing.*;
import java.awt.*;

import com.franquias.View.*;

public class AplicacaoPrincipal {

    public JFrame telaPricipal;
    private JMenuBar menuBarPrincipal;
    private JMenu menuAtualDoPainel;

    public JPanel painelDeConteudo;
    public CardLayout cardLayout;


    public final int WIDTH = 600;
    public final int HEIGHT = 400;
    // private final int V_GAP = 10;
    // private final int H_GAP = 5;

    public void iniciar() {
        telaPricipal = new JFrame("Franquia");
        telaPricipal.setDefaultCloseOperation(telaPricipal.EXIT_ON_CLOSE);
        telaPricipal.setSize(WIDTH, HEIGHT);

        menuBarPrincipal = new JMenuBar();
        JMenu menuSistema = new JMenu("Sistema");
        JMenuItem itemDeslogar = new JMenuItem("Deslogar");
        itemDeslogar.addActionListener(e -> mostrarTela("LOGIN"));

        //itemDeslogar.addActionListener(e -> mostrarTela("LOGIN"));

        menuSistema.add(itemDeslogar);
        menuBarPrincipal.add(menuSistema);

        telaPricipal.setJMenuBar(menuBarPrincipal);

        cardLayout = new CardLayout();
        painelDeConteudo = new JPanel(cardLayout);

        LoginController loginController = new LoginController(this);
        VendedorController vendedorController = new VendedorController(this);
        GerenteController gerenteController = new GerenteController(this);
        DonoController donoController = new DonoController(this);

        PainelLogin painelLogin = new PainelLogin(loginController);
        PainelVendedor painelVendedor = new PainelVendedor(this, vendedorController);
        PainelGerente painelGerente = new PainelGerente(this, gerenteController);
        PainelDono painelDono = new PainelDono(this, donoController);
        
        painelDeConteudo.add(painelLogin, "LOGIN");
        painelDeConteudo.add(painelVendedor, "VENDEDOR");
        painelDeConteudo.add(painelGerente, "GERENTE");
        painelDeConteudo.add(painelDono, "DONO");
        
        telaPricipal.add(painelDeConteudo);
        mostrarTela("DONO");

        telaPricipal.setLocationRelativeTo(null);
        telaPricipal.setVisible(true);

    }

    public void mostrarTela(String nomePainel) {
        if(menuAtualDoPainel != null) {
            menuBarPrincipal.remove(menuAtualDoPainel);
            menuAtualDoPainel = null;
        }

        cardLayout.show(painelDeConteudo, nomePainel);
        
        JPanel painelAtual = null;
        for(Component comp : painelDeConteudo.getComponents()) {
            if(comp.isVisible()) {
                painelAtual = (JPanel) comp;
                break;
            }
        }

        if(painelAtual instanceof PainelBase) {
            menuAtualDoPainel = ((PainelBase) painelAtual).getMenu();
            if(menuAtualDoPainel != null) {
                menuBarPrincipal.add(menuAtualDoPainel);
            }
        }

        menuBarPrincipal.revalidate();
        menuBarPrincipal.repaint();
    }

    public JFrame getFramePrincipal() {
        return telaPricipal;
    }
}
