package com.franquias.Model.entities;

import com.franquias.Utils.Endereco;

public class Franquia {
    public Endereco endereco;
    public long gerenteId;
    public long id;

    public Franquia(){
        this.endereco = new Endereco();
        this.gerenteId = -1;
    }
    
    public Franquia(Endereco endereco, long gerenteId){
        this.endereco = endereco;
        this.gerenteId = gerenteId;
        this.id = 0;
    }

    public void setGerenteId(long gerenteId) {
        this.gerenteId = gerenteId;
    }

    public long getGerenteId() {
        return this.gerenteId;
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
