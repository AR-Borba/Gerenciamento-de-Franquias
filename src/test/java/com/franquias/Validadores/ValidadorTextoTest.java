package com.franquias.Validadores;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.franquias.Utils.ValidadorTexto;

public class ValidadorTextoTest {
    
    @Test
    void retornaTrueParaTextoNaoVazio() {
        boolean resultado;

        resultado = ValidadorTexto.naoEstaVazio("Enzo");

        assertTrue(resultado);
    }

    @Test
    void retornaFalseParaTextoVazio() {
        boolean resultado;

        resultado = ValidadorTexto.naoEstaVazio("  ");

        assertFalse(resultado);
    }

    @Test
    void retornTrueParaTextoMaiorQueLimite() {
        boolean resultado;

        resultado = ValidadorTexto.temTamanhoMinimo("Arthur", 2);

        assertTrue(resultado);
    }

    @Test
    void retornTrueParaTextoIgualLimite() {
        boolean resultado;

        resultado = ValidadorTexto.temTamanhoMinimo("ea", 2);

        assertTrue(resultado);
    }

    @Test
    void retornFalseParaTextoMenorQueLimite() {
        boolean resultado;

        resultado = ValidadorTexto.temTamanhoMinimo("ea", 3);

        assertFalse(resultado);
    }
}
