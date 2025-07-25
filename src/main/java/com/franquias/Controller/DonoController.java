package com.franquias.Controller;

import java.util.List;

import com.franquias.Model.entities.Usuários.Gerente;
import com.franquias.Model.entities.Usuários.Usuario;
import com.franquias.Persistence.GerentePersistence;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class DonoController {

    private AplicacaoPrincipal app;

    public DonoController(AplicacaoPrincipal app){
        this.app = app;
    }
    
    public void cadastrarGerente(String nome, String cpf, String email, String senha) {
        CPFValidator validator = new CPFValidator();
        try {
            validator.assertValid(cpf);
        } catch (InvalidStateException e) {
            System.out.println(e.getInvalidMessages());
        }
       
        Gerente novoGerente = new Gerente(nome, cpf, email, senha);
        
        GerentePersistence gerentePersistence = new GerentePersistence();
        List<Gerente> novosGerentes;
        novosGerentes = List.of(novoGerente);
        gerentePersistence.save(novosGerentes);
    }

    public void removerGerente(Gerente gerenteParaRemover) {
        List<Franquia> todasFranquias = franquiaPersistence.findAll();
        
        // Desvincula o gerente da sua franquia
        for (Franquia franquia : todasFranquias) {
            if (franquia.getGerente() != null && franquia.getGerente().equals(gerenteParaRemover)) {
                franquia.setGerente(null); // Define que a franquia agora está sem gerente
            }
        }
        
        List<Usuario> todosUsuarios = usuarioPersistence.findAll();
        todosUsuarios.remove(gerenteParaRemover);
        
        // Salva as duas listas modificadas
        usuarioPersistence.save(todosUsuarios);
        franquiaPersistence.save(todasFranquias);
    }
}
