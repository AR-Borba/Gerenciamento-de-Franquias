package com.franquias.Models;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.franquias.Model.Produto;
import com.franquias.Model.entities.Pedido;

public class PedidoTest {
    
    Map<Produto, Integer> produtos;
    Produto prod1;
    Produto prod2;
    Produto prod3;
    Produto prod4;

    Pedido pedido;

    @BeforeEach
    void setup() {
        prod1 = new Produto("Calça", new BigDecimal("40.15"), 5, 1L);
        prod2 = new Produto("Camisa", new BigDecimal("30.00"), 5, 2L);
        prod3 = new Produto("Cuecas", new BigDecimal("14.99"), 5, 3L);
        prod4 = new Produto("Meia", new BigDecimal("7.50"), 5, 4L);
    
        

        pedido = new Pedido();
        pedido.setTaxa(new BigDecimal("15.00"));
    }

    @Test
    void deveCalcularValorTotalCorretamente() {
        pedido.adicionarItem(prod1, 2);
        pedido.adicionarItem(prod2, 3);
        pedido.adicionarItem(prod3, 4);

        pedido.calcularEAtualizaValorTotal();

        BigDecimal valorEsperado = new BigDecimal("245.26");

        assertEquals(pedido.getValorTotal(), valorEsperado);
    }

    @Test
    void deveAdicionarItemExistenteCalcularNovamente() {
        pedido.adicionarItem(prod1, 2);
        pedido.adicionarItem(prod2, 3);
        pedido.adicionarItem(prod3, 4);
        pedido.adicionarItem(prod4, 2);

        pedido.adicionarItem(prod1, 4);

        pedido.calcularEAtualizaValorTotal();

        BigDecimal valorEsperado = new BigDecimal("340.56");

        assertEquals(pedido.getValorTotal(), valorEsperado);
    }
}