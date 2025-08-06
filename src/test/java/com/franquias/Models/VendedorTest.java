package com.franquias.Models;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.franquias.Model.entities.Usuários.Vendedor;

public class VendedorTest {
    Vendedor vendedor;

    @BeforeEach
    void setUp() {
        vendedor = new Vendedor("Vendedor Teste", "teste@email.com", "123", "12345678900", 1L);
    }
    
    @Test
    void deveAdicionarIdDeUmPedidoCorretamente() {
        vendedor.adicionarPedidoPorId(101L);
        
        assertFalse(vendedor.getListaIdPedidos().isEmpty());
        assertEquals(1, vendedor.getListaIdPedidos().size());
        assertEquals(101L, vendedor.getListaIdPedidos().get(0));
    }

    @Test
    void deveAdicionarIdDeMultiplosPedidosCorretamente() {
        vendedor.adicionarPedidoPorId(101L);
        vendedor.adicionarPedidoPorId(102L);
        vendedor.adicionarPedidoPorId(103L);
        vendedor.adicionarPedidoPorId(104L);
        
        assertEquals(4, vendedor.getListaIdPedidos().size());
        assertTrue(vendedor.getListaIdPedidos().contains(101L));
        assertTrue(vendedor.getListaIdPedidos().contains(102L));
        assertTrue(vendedor.getListaIdPedidos().contains(103L));
        assertTrue(vendedor.getListaIdPedidos().contains(104L));
    }

    @Test
    void getListIdDeveVirImutavel() {
        vendedor.adicionarPedidoPorId(101L);

        List<Long> pedidosVendedor = vendedor.getListaIdPedidos();
    
        assertThrows(UnsupportedOperationException.class, () -> pedidosVendedor.add(401L));
    }
}