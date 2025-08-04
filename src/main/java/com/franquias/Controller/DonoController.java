package com.franquias.Controller;

import java.util.ArrayList;
import java.util.List;

import com.franquias.Model.entities.Usuários.Dono;
import com.franquias.Model.entities.Usuários.Gerente;
import com.franquias.Model.entities.Franquia;
import com.franquias.Persistence.GerentePersistence;
import com.franquias.Utils.Endereco;
import com.franquias.Persistence.DonoPersistence;
import com.franquias.Persistence.FranquiaPersistence;

public class DonoController {
    private DonoPersistence donoPersistence;
    private FranquiaPersistence franquiaPersistence;
    private GerentePersistence gerentePersistence;
    private AplicacaoPrincipal app;
    private Dono donoLogado;

    public DonoController(AplicacaoPrincipal app, DonoPersistence donoPersistence, FranquiaPersistence franquiaPersistence, GerentePersistence gerentePersistence) {
        this.donoPersistence = donoPersistence;
        this.franquiaPersistence = franquiaPersistence;
        this.gerentePersistence = gerentePersistence;
        this.app = app; 
    }

    public void iniciarSessao(Dono dono) {
        this.donoLogado = dono;
        System.out.println("Sessão iniciada para o Dono: " + donoLogado.getNome());
        
        // verificarFranquiasSemGerenteEnotificar();
    }

    public void cadastrarDono(String nome, String cpf, String email, String senha) {
        Dono novoDono = new Dono(nome, cpf, email, senha);
        donoPersistence.adicionarDono(novoDono);
    }

    public void cadastrarGerente(Gerente gerente) {
        gerentePersistence.adicionarGerente(gerente);
    }

    public void removerGerente(long idGerente) {
        gerentePersistence.removerGerente(idGerente);
    }

    public List<Gerente> getGerentes() {
        return gerentePersistence.findAll();
    }

    public void cadastrarFranquia(Endereco endereco, Gerente gerenteResponsavel) {
        FranquiaPersistence franquiaPersistence = new FranquiaPersistence();
        List<Franquia> todasFranquias = franquiaPersistence.findAll();
        
        Franquia novaFranquia = new Franquia(endereco, gerenteResponsavel);
        
        gerenteResponsavel.setFranquiaId(novaFranquia.getId());

        gerentePersistence.update(gerenteResponsavel);

        todasFranquias.add(novaFranquia);
        franquiaPersistence.save(todasFranquias);
    }

    public List<Franquia> verificarFranquiasSemGerente() {
        List<Franquia> todasFranquias = franquiaPersistence.findAll();
        List<Franquia> franquiasSemGerente = new ArrayList<>();


        for (Franquia franquia : todasFranquias) {
            if (franquia.getGerente() == null) {
                franquiasSemGerente.add(franquia);
            }
        }
        return franquiasSemGerente;
    }

    public List<Franquia> getUnidades() {
        return franquiaPersistence.findAll();
    }

    public void cadastrarFranquia(Franquia novaFranquia) {
        franquiaPersistence.adicionarFranquia(novaFranquia);
    }

    public Gerente buscarGerentePorId(long id) {
        return gerentePersistence.buscarPorId(id);
    }

    public void editarGerente(Gerente gerenteParaEditar) {
        gerentePersistence.update(gerenteParaEditar);
    }

    public void removerFranquia(long idFranquia) {
        franquiaPersistence.removerFranquia(idFranquia);
    }

    public Franquia buscarFranquiaPorId(long idFranquia) {
        return franquiaPersistence.buscarPorId(idFranquia);
    }

    public void editarFranquia(Franquia franquiaParaEditar) {
        franquiaPersistence.update(franquiaParaEditar);
    }

    // public RelatorioDesempenhoUnidade gerarRelatorioParaFranquia(Franquia
    // franquia) {
    // double faturamentoBruto = 0.0;
    // int numeroDePedidos = franquia.getPedidos().size();

    // for (Pedido pedido : franquia.getPedidos()) {
    // faturamentoBruto += pedido.getValorTotal();
    // }

    // double ticketMedio = (numeroDePedidos > 0) ? faturamentoBruto /
    // numeroDePedidos : 0;

    // return new RelatorioDesempenhoUnidade(faturamentoBruto, numeroDePedidos,
    // ticketMedio);
    // }

    // public List<Vendedor> getRankingVendedores(Franquia franquia) {
    // List<Vendedor> vendedoresDaFranquia = new
    // ArrayList<>(franquia.getVendedores());

    // vendedoresDaFranquia.sort((v1, v2) ->
    // Double.compare(v2.getValorTotalVendas(), v1.getValorTotalVendas()));

    // return vendedoresDaFranquia;
    // }
}
