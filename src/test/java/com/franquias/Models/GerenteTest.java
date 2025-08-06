package com.franquias.Models;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.franquias.Model.entities.Usuários.Gerente;

public class GerenteTest {
    Gerente gerente;

    @BeforeEach
    void setUp() {
        gerente = new Gerente("Gerente Teste", "teste@email.com", "123", "12345678900");
    }
    
    @Test
    void deveAdicionarIdDeUmVendedorCorretamente() {
        gerente.adicionarVendedorPorId(101L);
        
        assertFalse(gerente.getListaIdVendedores().isEmpty());
        assertEquals(1, gerente.getListaIdVendedores().size());
        assertEquals(101L, gerente.getListaIdVendedores().get(0));
    }

    @Test
    void deveAdicionarIdDeMultiplosvendedoresCorretamente() {
        gerente.adicionarVendedorPorId(101L);
        gerente.adicionarVendedorPorId(102L);
        gerente.adicionarVendedorPorId(103L);
        gerente.adicionarVendedorPorId(104L);
        
        assertEquals(4, gerente.getListaIdVendedores().size());
        assertTrue(gerente.getListaIdVendedores().contains(101L));
        assertTrue(gerente.getListaIdVendedores().contains(102L));
        assertTrue(gerente.getListaIdVendedores().contains(103L));
        assertTrue(gerente.getListaIdVendedores().contains(104L));
    }

    @Test
    void getListIdDeveVirImutavel() {
        gerente.adicionarVendedorPorId(101L);

        List<Long> vendedores = gerente.getListaIdVendedores();
    
        assertThrows(UnsupportedOperationException.class, () -> vendedores.add(401L));
    }
}