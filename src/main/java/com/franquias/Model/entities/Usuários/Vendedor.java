package com.franquias.Model.entities.Usuários;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Vendedor extends Usuario {
    private long id;
    private List<Long> idPedidos;
    private long franquiaId;

    public Vendedor(String nome, String email, String senha, String cpf, long franquiaId) {
        super(nome, email, senha, cpf);
        this.id = 0;
        this.idPedidos = new ArrayList<>();
        this.franquiaId = franquiaId;
    }

    public Vendedor() {

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

    public void adicionarPedidoPorId(long idPedido) {
        if (this.idPedidos == null) {
            this.idPedidos = new ArrayList<>();
        }
        this.idPedidos.add(idPedido);
    }

    public List<Long> getListaIdPedidos() {
        if (this.idPedidos == null)
            this.idPedidos = new ArrayList<>();
        return Collections.unmodifiableList(this.idPedidos);
    }
}
