package com.franquias.Model.entities;

import com.franquias.Model.entities.Usuários.Gerente;
import com.franquias.Utils.Endereco;

public class Franquia {
    String nome;
    Endereco endereco;
    Gerente gerente;
    
    public Franquia(String nome, Endereco endereco, Gerente gerente){
        this.nome = nome;
        this.endereco = endereco;
        this.gerente = gerente;
    }

    public void setGerente(Gerente gerente) {
        this.gerente = gerente;
    }

    public Gerente getGerente() {
        return gerente;
    }


}
