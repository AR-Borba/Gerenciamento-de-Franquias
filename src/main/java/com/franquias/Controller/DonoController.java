package com.franquias.Controller;

import java.util.ArrayList;
import java.util.List;

import com.franquias.Model.entities.Usuários.Gerente;
import com.franquias.Model.entities.Usuários.Vendedor;
import com.franquias.Model.entities.Franquia;
import com.franquias.Model.entities.Pedido;
import com.franquias.Persistence.GerentePersistence;
import com.franquias.Utils.Endereco;
import com.franquias.Persistence.FranquiaPersistence;

public class DonoController {

    private AplicacaoPrincipal app;

    public DonoController(AplicacaoPrincipal app){
        this.app = app;
    }
    
    public void cadastrarGerente(Gerente gerente) {        
        GerentePersistence gerentePersistence = new GerentePersistence();
       
        List<Gerente> novosGerentes;
        novosGerentes = List.of(gerente);
       
        gerentePersistence.save(novosGerentes);
    }

    public void removerGerente(Gerente gerenteParaRemover) {
        FranquiaPersistence franquiaPersistence = new FranquiaPersistence();
        List<Franquia> todasFranquias = franquiaPersistence.findAll();
        
        for (Franquia franquia : todasFranquias) {
            if (franquia.getGerente() != null && franquia.getGerente().equals(gerenteParaRemover)) {
                franquia.setGerente(null);
            }
        }
        
        GerentePersistence gerentePersistence = new GerentePersistence();
        List<Gerente> todosGerentes = gerentePersistence.findAll();
        todosGerentes.remove(gerenteParaRemover);
        
        gerentePersistence.save(todosGerentes);
        franquiaPersistence.save(todasFranquias);
    }

    public List<Gerente> getGerentes(){
        GerentePersistence gerentePersistence = new GerentePersistence();
        List<Gerente> gerentes = gerentePersistence.findAll();
        return gerentes;
    }

    public void cadastrarFranquia(String nome, Endereco endereco, Gerente gerenteResponsavel) {
        FranquiaPersistence franquiaPersistence = new FranquiaPersistence();
        List<Franquia> todasFranquias = franquiaPersistence.findAll();
        
        Franquia novaFranquia = new Franquia(nome, endereco, gerenteResponsavel);
        
        todasFranquias.add(novaFranquia);
        franquiaPersistence.save(todasFranquias);
    }

    public List<Franquia> verificarFranquiasSemGerente() {
        FranquiaPersistence franquiaPersistence = new FranquiaPersistence();
        List<Franquia> todasFranquias = franquiaPersistence.findAll();
        List<Franquia> franquiasSemGerente = new ArrayList<>();
        
        for (Franquia franquia : todasFranquias) {
            if (franquia.getGerente() == null) {
                franquiasSemGerente.add(franquia);
            }
        }
        return franquiasSemGerente;
    }

    // public RelatorioDesempenhoUnidade gerarRelatorioParaFranquia(Franquia franquia) {
    //     double faturamentoBruto = 0.0;
    //     int numeroDePedidos = franquia.getPedidos().size();

    //     for (Pedido pedido : franquia.getPedidos()) {
    //         faturamentoBruto += pedido.getValorTotal();
    //     }

    //     double ticketMedio = (numeroDePedidos > 0) ? faturamentoBruto / numeroDePedidos : 0;

    //     return new RelatorioDesempenhoUnidade(faturamentoBruto, numeroDePedidos, ticketMedio);
    // }

    // public List<Vendedor> getRankingVendedores(Franquia franquia) {
    //     List<Vendedor> vendedoresDaFranquia = new ArrayList<>(franquia.getVendedores());
        
    //     vendedoresDaFranquia.sort((v1, v2) -> Double.compare(v2.getValorTotalVendas(), v1.getValorTotalVendas()));
        
    //     return vendedoresDaFranquia;
    // }
}
