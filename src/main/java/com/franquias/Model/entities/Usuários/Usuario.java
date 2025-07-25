package com.franquias.Model.entities.Usuários;

import org.apache.commons.validator.routines.EmailValidator;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public abstract class Usuario {
    String nome;
    String email;
    String senha;
    String cpf;

    public Usuario(String nome, String email, String senha, String cpf) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void validarEmail(String email){
        EmailValidator validator = EmailValidator.getInstance();
        validator.isValid(email);
    }

    public void validarCPF(String cpf){
        CPFValidator validator = new CPFValidator();
        try {
            validator.assertValid(cpf);
        } catch (InvalidStateException e) {
            System.out.println(e.getInvalidMessages());
        }
    }
    
}
