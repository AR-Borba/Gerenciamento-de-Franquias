package com.franquias.Controller;

import javax.swing.*;
import java.awt.*;

import com.franquias.Persistence.ClientePersistence;
import com.franquias.Persistence.DonoPersistence;
import com.franquias.Persistence.FranquiaPersistence;
import com.franquias.Persistence.GerentePersistence;
import com.franquias.Persistence.PedidoPersistence;
import com.franquias.Persistence.ProdutoPersistence;
import com.franquias.Persistence.VendedorPersistence;
import com.franquias.View.*;

public class AplicacaoPrincipal {

    public JFrame telaPricipal;
    private JMenuBar menuBarPrincipal;
    private JMenu menuAtualDoPainel;

    public JPanel painelDeConteudo;
    public CardLayout cardLayout;

    private DonoPersistence donoPersistence = new DonoPersistence();
    private FranquiaPersistence franquiaPersistence = new FranquiaPersistence();
    private GerentePersistence gerentePersistence = new GerentePersistence();
    private VendedorPersistence vendedorPersistence = new VendedorPersistence();
    private ProdutoPersistence produtoPersistence = new ProdutoPersistence();
    private PedidoPersistence pedidoPersistence = new PedidoPersistence();
    private ClientePersistence clientePersistence = new ClientePersistence();

    private LoginController loginController;
    private VendedorController vendedorController;
    private GerenteController gerenteController;
    private DonoController donoController;
    private PedidoController pedidoController;

    
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
        
        menuSistema.add(itemDeslogar);
        menuBarPrincipal.add(menuSistema);

        telaPricipal.setJMenuBar(menuBarPrincipal);

        cardLayout = new CardLayout();
        painelDeConteudo = new JPanel(cardLayout);
        
        loginController = new LoginController(this, donoPersistence, gerentePersistence, vendedorPersistence);
        vendedorController = new VendedorController(this, pedidoPersistence, produtoPersistence, vendedorPersistence, clientePersistence);
        gerenteController = new GerenteController(this, vendedorPersistence, produtoPersistence, pedidoPersistence);
        donoController = new DonoController(this, franquiaPersistence, gerentePersistence);
        pedidoController = new PedidoController(pedidoPersistence, produtoPersistence);
        
        PainelLogin painelLogin = new PainelLogin(loginController);
        PainelVendedor painelVendedor = new PainelVendedor(this, vendedorController, pedidoController);
        PainelGerente painelGerente = new PainelGerente(this, gerenteController, pedidoController);
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

    public VendedorController getVendedorController() {
        return vendedorController;
    }
    
    public GerenteController getGerenteController() {
        return gerenteController;
    }
    
    public DonoController getDonoController() {
        return donoController;
    }
    
    public PedidoController getPedidoController() {
        return pedidoController;
    }
}
