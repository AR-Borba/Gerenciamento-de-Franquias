package com.franquias.Controller;

import javax.naming.AuthenticationException;
import javax.swing.JOptionPane;

import com.franquias.Model.entities.Usuários.Dono;
import com.franquias.Model.entities.Usuários.Gerente;
import com.franquias.Model.entities.Usuários.Vendedor;
import com.franquias.Persistence.DonoPersistence;
import com.franquias.Persistence.GerentePersistence;
import com.franquias.Persistence.VendedorPersistence;
import com.franquias.View.PainelCadastroDono;

public class LoginController {

    private AplicacaoPrincipal app;
    private VendedorPersistence vendedorPersistence;
    private GerentePersistence gerentePersistence;
    private DonoPersistence donoPersistence;

    public LoginController(AplicacaoPrincipal app, DonoPersistence dp, GerentePersistence gp, VendedorPersistence vp) {
        this.app = app;
        this.donoPersistence = dp;
        this.gerentePersistence = gp;
        this.vendedorPersistence = vp;
    }

    // public void realizarLogin(String email, String senha) {
    //     if("gerente@gmail.com".equals(email) && "1234".equals(senha))
    //         app.mostrarTela("GERENTE");
    //     else if("dono@gmail.com".equals(email) && "1234".equals(senha))
    //         app.mostrarTela("DONO");
    //     else if("vendedor@gmail.com".equals(email) && "1234".equals(senha))
    //         app.mostrarTela("VENDEDOR");
    // }

    public void realizarLogin(String email, String senha) {
        try {
            if(email.isBlank() || senha.isBlank()) {
                throw new AuthenticationException("Email e senha são obrigatórios");
            }

            Dono dono = donoPersistence.findByEmailAndPassword(email, senha);
            if(dono != null) {
                JOptionPane.showMessageDialog(null, "Bem vindo " + dono.getNome() + "!", "Autenticação Bem Sucedida!", JOptionPane.INFORMATION_MESSAGE);
                app.getDonoController().iniciarSessao(dono);
                app.mostrarTela("DONO");
                return;
            }

            Gerente gerente = gerentePersistence.findByEmailAndPassword(email, senha);
            if(gerente != null) {
                JOptionPane.showMessageDialog(null, "Bem vindo " + gerente.getNome() + "!", "Autenticação Bem Sucedida!", JOptionPane.INFORMATION_MESSAGE);
                app.getGerenteController().iniciarSessao(gerente);
                app.mostrarTela("GERENTE");
                return;
            }

            Vendedor vendedor = vendedorPersistence.findByEmailAndPassword(email, senha);
            if(vendedor != null) {
                JOptionPane.showMessageDialog(null, "Bem vindo " + vendedor.getNome() + "!", "Autenticação Bem Sucedida!", JOptionPane.INFORMATION_MESSAGE);
                app.getVendedorController().iniciarSessao(vendedor);
                app.mostrarTela("VENDEDOR");
                return;
            }

            throw new AuthenticationException("Email ou senha inválidos.");

        } catch(AuthenticationException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro de autenticação!",JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean emailJaExiste(String email) {
        
        Dono dono = donoPersistence.findByEmail(email);
        if(dono != null)
            return true;

        Gerente gerente = gerentePersistence.findByEmail(email);
        if(gerente != null)
            return true;
        
        Vendedor vendedor = vendedorPersistence.findByEmail(email);
        if(vendedor != null)
            return true;

        return false;
    }
}