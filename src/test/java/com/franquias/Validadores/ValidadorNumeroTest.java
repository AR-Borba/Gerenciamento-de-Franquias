package com.franquias.Validadores;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.franquias.Utils.ValidadorNumero;

public class ValidadorNumeroTest {
    
    @Test
    void retornaTrueIntPositivo() {
        boolean resultado;

        resultado = ValidadorNumero.intIsPositivo(1);

        assertTrue(resultado);
    }

    @Test
    void retornaFalseIntPositivo() {
        boolean resultado;

        resultado = ValidadorNumero.intIsPositivo(0);

        assertFalse(resultado);
    }

    @Test
    void retornaTrueIntNaoNegativo() {
        boolean resultado;

        resultado = ValidadorNumero.intIsNaoNegativo(0);

        assertTrue(resultado);
    }

    @Test
    void retornaFalseIntNaoNegativo() {
        boolean resultado;

        resultado = ValidadorNumero.intIsNaoNegativo(-1);

        assertFalse(resultado);
    }

    @Test
    void retornaTrueLongNaoNegativo() {
        boolean resultado;

        resultado = ValidadorNumero.longIsNaoNegativo(0L);

        assertTrue(resultado);
    }

    @Test
    void retornaFalseLongNegativo() {
        boolean resultado;

        resultado = ValidadorNumero.longIsNaoNegativo(-1L);

        assertFalse(resultado);
    }

    @Test
    void retornaTrueBigDecimalPositivo() {
        boolean resultado;

        resultado = ValidadorNumero.bigDecimalIsPositivo(BigDecimal.ONE);

        assertTrue(resultado);
    }

    @Test
    void retornaFalseBigDecimalPositivo() {
        boolean resultado;

        resultado = ValidadorNumero.bigDecimalIsPositivo(BigDecimal.ZERO);

        assertFalse(resultado);
    }

    @Test
    void retornaTrueBigDecimalNaoNegativo() {
        boolean resultado;

        resultado = ValidadorNumero.bigDecimalIsNaoNegativo(BigDecimal.ZERO);

        assertTrue(resultado);
    }

    @Test
    void retornaFalseBigDecimalNaoNegativo() {
        boolean resultado;

        resultado = ValidadorNumero.bigDecimalIsNaoNegativo(BigDecimal.ONE.subtract(BigDecimal.TWO));

        assertFalse(resultado);
    }
}
