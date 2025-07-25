package com.franquias.Controller;

import java.util.List;
import java.util.ArrayList;

import com.franquias.Model.entities.Vendedor;

public class GerenteController {

    private AplicacaoPrincipal app;

    public GerenteController(AplicacaoPrincipal app) {
        this.app = app;
    }

    public List<Vendedor> getEquipeDeVendasOrdenadaPorVendas() {
        // Implementar lógica para obter a equipe de vendas ordenada por vendas
        return new ArrayList<Vendedor>();
    }

    public void editarVendedor(long idVendedor) {
        // Implementar lógica para editar um vendedor
    }

    public void removerVendedor(long idVendedor) {
        // Implementar lógica para remover um vendedor
    }

    public void adicionarVendedor(Vendedor vendedor) {
        // Implementar lógica para adicionar um novo vendedor
    }
}
