package com.franquias.Model.entities;

import com.franquias.Model.entities.Usuários.Gerente;
import com.franquias.Utils.Endereco;

public class Franquia {
    public Endereco endereco;
    public Gerente gerente;
    long id;

    public Franquia(){
        this.endereco = new Endereco(); 
    }
    
    public Franquia(Endereco endereco, Gerente gerente){
        this.endereco = endereco;
        this.gerente = gerente;
        this.id = 0;
    }

    public void setGerente(Gerente gerente) {
        this.gerente = gerente;
    }

    public Gerente getGerente() {
        return this.gerente;
    }

    public String getEstado() {
        return this.endereco.getEstado();
    }

    public String getCidade() {
        return this.endereco.getCidade();
    }

    public double getReceita() {
        return 20.35;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long proximoId) {
        this.id = proximoId;
    }


}
