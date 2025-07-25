package com.franquias.Controller;

import java.util.List;

import com.franquias.Model.entities.Usuários.Gerente;
import com.franquias.Model.entities.Usuários.Usuario;

public class DonoController {

    private AplicacaoPrincipal app;

    public DonoController(AplicacaoPrincipal app){
        this.app = app;
    }
    
    public void cadastrarGerente(String nome, String cpf, String email, String senha) {
        List<Usuario> todosUsuarios = usuarioPersistence.findAll();
        
        Gerente novoGerente = new Gerente(nome, cpf, email, senha);
        todosUsuarios.add(novoGerente);
        
        usuarioPersistence.save(todosUsuarios);
    }
}
