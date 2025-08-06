package com.franquias.ControllerTest;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.franquias.Controller.VendedorController;
import com.franquias.Model.Produto;
import com.franquias.Persistence.ProdutoPersistence;
import com.franquias.exceptions.EstoqueInsuficienteException;

public class VendedorControllerTest {
   
    VendedorController vendedorController;
    ProdutoPersistence produtoPersistence;
    Produto prod1;
    Produto prod2;

    @BeforeEach
    void setUp() {
        vendedorController = new VendedorController(null, null, null, null, null);
        vendedorController.iniciarNovoPedido();

        produtoPersistence = new ProdutoPersistence();
        prod1 = new Produto("Calça", new BigDecimal("40.15"), 5, 1L);
        prod2 = new Produto("Camisa", new BigDecimal("30.00"), 5, 2L);
    
        produtoPersistence.adicionarProduto(prod1);
        produtoPersistence.adicionarProduto(prod2);
    }

    @Test
    void deveAdicionarItemComEstoqueSuficiente() {
        
        assertDoesNotThrow(() -> {
            vendedorController.adicionarItemAoPedido(1L, 5);
        });

        assertEquals(1, vendedorController.getPedidoAtual().getItens().size());
    }

    @Test
    void deveAdicionarItemComEstoqueInsuficiente() {
        
        assertThrows(EstoqueInsuficienteException.class, () -> {
            vendedorController.adicionarItemAoPedido(1L, 11);
        });
    }
}
