package com.franquias.Controller;

public class LoginController {

    private AplicacaoPrincipal app;

    public LoginController(AplicacaoPrincipal app) {
        this.app = app;
    }

    public void realizarLogin(String email, String senha) {
        if("adm@gmail.com".equals(email) && "1234".equals(senha))
            app.mostrarTela("GERENTE");
        else
            app.mostrarTela("VENDEDOR");

    }
}