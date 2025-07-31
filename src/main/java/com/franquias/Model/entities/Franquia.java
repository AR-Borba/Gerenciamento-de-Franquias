package com.franquias.Model.entities;

import com.franquias.Model.entities.Usuários.Gerente;
import com.franquias.Utils.Endereco;

public class Franquia {
    Endereco endereco;
    Gerente gerente;
    long id;
    
    public Franquia(Endereco endereco, Gerente gerente){
        this.endereco = endereco;
        this.gerente = gerente;
        this.id = 0;
    }

    public void setGerente(Gerente gerente) {
        this.gerente = gerente;
    }

    public Gerente getGerente() {
        return gerente;
    }

    public Object getEstado() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getEstado'");
    }

    public Object getCidade() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCidade'");
    }

    public Object getReceita() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getReceita'");
    }

    public void setId(long proximoId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }

    public long getId() {
        return this.id;
    }


}
