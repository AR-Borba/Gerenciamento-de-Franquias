package com.franquias.Controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
            FranquiaPersistence franquiaPersistence, GerentePersistence gerentePersistence, PedidoPersistence pedidoPersistence,
            VendedorPersistence vendedorPersistence) {
        this.app = app;
        this.loginController = loginController;
        this.donoPersistence = donoPersistence;
        this.franquiaPersistence = franquiaPersistence;
        this.gerentePersistence = gerentePersistence;
        this.pedidoPersistence = pedidoPersistence;
        this.vendedorPersistence = vendedorPersistence;
    }

    public void iniciarSessao(Dono dono) {
        this.donoLogado = dono;
        System.out.println("Sessão iniciada para o Dono: " + donoLogado.getNome());

        // verificarFranquiasSemGerenteEnotificar();
    }

    public void cadastrarDono(String nome, String cpf, String email, String senha) throws ValidationException {
        if(loginController.emailJaExiste(email)) {
            throw new ValidationException("O email " + email + " já está em uso no sistema");
        }
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

    public List<String> getNomesGerentes() {
        List<String> nomes = new ArrayList<>();
        for(Gerente gerente : gerentePersistence.findAll())
            nomes.add(gerente.getNome());
        return nomes;
    }

    public long getIdGerente(String nome) {
        for(Gerente gerente : gerentePersistence.findAll()){
            if(nome == gerente.getNome())
                return gerente.getId();
        }
        return -1;
    }

    public void cadastrarFranquia(Endereco endereco, long iDgerenteResponsavel) {
        List<Franquia> todasFranquias = franquiaPersistence.findAll();
        
        Franquia novaFranquia = new Franquia(endereco, iDgerenteResponsavel);
        
        Gerente gerenteDaFranquia = gerentePersistence.buscarPorId(iDgerenteResponsavel);
        gerenteDaFranquia.setFranquiaId(novaFranquia.getId());

        todasFranquias.add(novaFranquia);
        franquiaPersistence.save(todasFranquias);
    }

    public List<Franquia> verificarFranquiasSemGerente() {
        List<Franquia> todasFranquias = franquiaPersistence.findAll();
        List<Franquia> franquiasSemGerente = new ArrayList<>();

        for (Franquia franquia : todasFranquias) {
            if (franquia.getGerenteId() == -1) {
                franquiasSemGerente.add(franquia);
            }
        }
        return franquiasSemGerente;
    }

    public List<Franquia> getUnidades() {
        return franquiaPersistence.findAll();
    }

    public void cadastrarFranquia(Franquia novaFranquia) throws ValidationException{
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

    public BigDecimal getReceita(Franquia franquia) {
        BigDecimal total = BigDecimal.ZERO;
        return total;
    }

    public Map<Long, BigDecimal> calcularFaturamentoPorFranquia() {
        List<Pedido> todosOsPedidos = pedidoPersistence.findAll();

        return todosOsPedidos.stream()
            .filter(pedido -> pedido.getStatusPedido() == StatusPedido.CONCLUIDO)
            .collect(Collectors.groupingBy(
                Pedido::getFranquiaId,
                Collectors.mapping(Pedido::getValorTotal, 
                                 Collectors.reducing(BigDecimal.ZERO, BigDecimal::add))
            ));
    }

    public Map<Long, Long> calcularTotalPedidosPorFranquia() {
        List<Pedido> todosOsPedidos = pedidoPersistence.findAll();

        return todosOsPedidos.stream()
            .filter(pedido -> pedido.getStatusPedido() == StatusPedido.CONCLUIDO)
            .collect(Collectors.groupingBy(
                Pedido::getFranquiaId, 
                Collectors.counting()
            ));
    }

    public Map<Long, BigDecimal> calcularTicketMedioPorFranquia() {
        Map<Long, BigDecimal> faturamentoPorFranquia = calcularFaturamentoPorFranquia();
        Map<Long, Long> totalPedidosPorFranquia = calcularTotalPedidosPorFranquia();

        Map<Long, BigDecimal> ticketMedioPorFranquia = new HashMap<>();

        for (Long franquiaId : faturamentoPorFranquia.keySet()) {
            BigDecimal faturamento = faturamentoPorFranquia.get(franquiaId);
            Long contagemPedidos = totalPedidosPorFranquia.getOrDefault(franquiaId, 0L);

            if (contagemPedidos > 0) {
                BigDecimal contagemBigDecimal = new BigDecimal(contagemPedidos);
                
                BigDecimal ticketMedio = faturamento.divide(contagemBigDecimal, 2, RoundingMode.HALF_UP);
                
                ticketMedioPorFranquia.put(franquiaId, ticketMedio);
            }
        }
        return ticketMedioPorFranquia;
    }

    public String getNomeGerente(Franquia franquia) {
        for(Franquia unidades : franquiaPersistence.findAll()){
            if(unidades.getId() == franquia.getId())
                return unidades.getNomeGerente();
        }
        return "-";
    }
}
