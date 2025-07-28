package com.franquias.Model.entities.Usuários;

import org.apache.commons.validator.routines.EmailValidator;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public abstract class Usuario {
    String nome;
    String email;
    String senha;
    
    String cpf;
    
    
    public Usuario(){
        
    }
    
    public Usuario(String nome, String email, String senha, String cpf) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getCpf() {
        return cpf;
    }
    
    public String getSenha() {
        return senha;
    }

    public String getEmail() {
        return email;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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
