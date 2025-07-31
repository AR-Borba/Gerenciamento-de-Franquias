package com.franquias.Controller;

public class LoginController {

    private AplicacaoPrincipal app;

    public LoginController(AplicacaoPrincipal app) {
        this.app = app;
    }

    public void realizarLogin(String email, String senha) {
        if("gerente@gmail.com".equals(email) && "1234".equals(senha))
            app.mostrarTela("GERENTE");
        else if("dono@gmail.com".equals(email) && "1234".equals(senha))
            app.mostrarTela("DONO");
        else if("vendedor@gmail.com".equals(email) && "1234".equals(senha))
            app.mostrarTela("VENDEDOR");
    }
}