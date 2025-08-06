package com.franquias.Model.entities.Usuários;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Gerente extends Usuario {
    private long id;
    private List<Long> idVendedores;
    private long franquiaId;

    public Gerente(String nome, String email, String senha, String cpf) {
        super(nome, email, senha, cpf);
        this.id = 0;
        this.idVendedores = new ArrayList<>();
    }

    public Gerente(){
    
    }

    // public void adicionarVendedor(Vendedor vendedor) {
    //     if(this.idVendedores == null) {
    //         this.idVendedores = new ArrayList<>();
    //     }
    //     this.idVendedores.add(vendedor.getId());
    // }

    public void adicionarVendedorPorId(long idVendedor) {
        if(this.idVendedores == null) {
            this.idVendedores = new ArrayList<>();
        }
        this.idVendedores.add(idVendedor);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFranquiaId() {
        return this.franquiaId;
    }

    public void setFranquiaId(long idFranquia) {
        this.franquiaId = idFranquia;
    }

    public List<Long> getListaIdVendedores() {
        return Collections.unmodifiableList(this.idVendedores);
    }

    @Override
    public String toString() {
        return getNome(); 
    }
}
