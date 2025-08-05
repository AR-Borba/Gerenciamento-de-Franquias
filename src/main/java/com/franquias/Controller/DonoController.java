package com.franquias.Controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.naming.AuthenticationException;

import com.franquias.Model.entities.Usuários.Dono;
import com.franquias.Model.entities.Usuários.Gerente;
import com.franquias.Model.entities.Usuários.Vendedor;
import com.franquias.Model.enums.StatusPedido;
import com.franquias.Model.entities.Franquia;
import com.franquias.Model.entities.Pedido;
import com.franquias.Persistence.GerentePersistence;
import com.franquias.Persistence.PedidoPersistence;
import com.franquias.Persistence.VendedorPersistence;
import com.franquias.Utils.Endereco;
import com.franquias.exceptions.ValidationException;
import com.franquias.Persistence.DonoPersistence;
import com.franquias.Persistence.FranquiaPersistence;

public class DonoController {
    private AplicacaoPrincipal app;
    private LoginController loginController;
    private DonoPersistence donoPersistence;
    private FranquiaPersistence franquiaPersistence;
    private GerentePersistence gerentePersistence;
    private VendedorPersistence vendedorPersistence;
    private PedidoPersistence pedidoPersistence;
    private Dono donoLogado;

    public DonoController(AplicacaoPrincipal app, LoginController loginController, DonoPersistence donoPersistence,
            FranquiaPersistence franquiaPersistence, GerentePersistence gerentePersistence) {
        this.app = app;
        this.loginController = loginController;
        this.donoPersistence = donoPersistence;
        this.franquiaPersistence = franquiaPersistence;
        this.gerentePersistence = gerentePersistence;
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

    public void cadastrarGerente(Gerente gerente) throws ValidationException {
        if(loginController.emailJaExiste(gerente.getEmail())) {
            throw new ValidationException("O email " + gerente.getEmail() + " já está em uso no sistema");
        }

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

    public List<Vendedor> getRankingVendedores() {
        List<Vendedor> vendedores = vendedorPersistence.findAll();
        Comparator<Vendedor> comparadorPorVendas = Comparator
                .comparing(vendedor -> calcularTotalVendasPorVendedor(vendedor));
        vendedores.sort(comparadorPorVendas.reversed());
        return vendedores;
    }

    public BigDecimal calcularTotalVendasPorVendedor(Vendedor vendedor) {
        if (vendedor.getListaIdPedidos() == null) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = BigDecimal.ZERO;
        for (Long idPedido : vendedor.getListaIdPedidos()) {
            Pedido pedido = pedidoPersistence.buscarPorId(idPedido);

            if (pedido != null && pedido.getStatusPedido() == StatusPedido.CONCLUIDO) {
                total = total.add(pedido.getValorTotal());
            }
        }
        return total;
    }

    public Number getReceita(Franquia franquia) {
        return 10;
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

}
