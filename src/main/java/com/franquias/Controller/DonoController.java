package com.franquias.Controller;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import com.franquias.Persistence.FranquiaPersistence;

public class DonoController {
    private FranquiaPersistence franquiaPersistence;
    private GerentePersistence gerentePersistence;
    private VendedorPersistence vendedorPersistence;
    private PedidoPersistence pedidoPersistence;
    private AplicacaoPrincipal app;
    private Dono donoLogado;

    public DonoController(AplicacaoPrincipal app) {
        this.franquiaPersistence = new FranquiaPersistence();
        this.gerentePersistence = new GerentePersistence();
        this.vendedorPersistence = new VendedorPersistence();
        this.pedidoPersistence = new PedidoPersistence();
        this.app = app; 
    }

    public DonoController(AplicacaoPrincipal app, FranquiaPersistence franquiaPersistence, GerentePersistence gerentePersistence, VendedorPersistence vendedorPersistence, PedidoPersistence pedidoPersistence) {
        this.franquiaPersistence = franquiaPersistence;
        this.gerentePersistence = gerentePersistence;
        this.vendedorPersistence = vendedorPersistence;
        this.pedidoPersistence = pedidoPersistence;
        this.app = app; 
    }

    public void iniciarSessao(Dono dono) {
        this.donoLogado = dono;
        System.out.println("Sessão iniciada para o Dono: " + donoLogado.getNome());
        
        // verificarFranquiasSemGerenteEnotificar();
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

    public List<Vendedor> getRankingVendedores() {
        List<Vendedor> vendedores = vendedorPersistence.findAll(); 
        Comparator<Vendedor> comparadorPorVendas = Comparator.comparing(vendedor -> calcularTotalVendasPorVendedor(vendedor));
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
            // .filter(pedido -> pedido.getStatusPedido() == StatusPedido.CONCLUIDO)
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
}
