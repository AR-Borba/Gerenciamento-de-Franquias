package com.franquias.Model.entities.Usuários;

public class Gerente extends Usuario {
    long id;

    public Gerente(String nome, String cpf, String email, String senha) {
        super(nome, email, senha, cpf);
        this.id = 0;
    }

    public Gerente(){
    
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void cadastrarVendedor(){

    }

    public void editarVendedor(){

    }

    public void removerVendedor(){
    
    }

    public void visualizarVendedores(){

    }

    public void visualizarPedidos(){

    }

    public void editarPedidos(){

    }

    public void cadastrarProduto(){

    }

    public void historicoVendas(){

    }
}
