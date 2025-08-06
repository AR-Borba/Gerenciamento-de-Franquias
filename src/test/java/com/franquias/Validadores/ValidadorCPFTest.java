package com.franquias.Validadores;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.franquias.Utils.ValidadorCPF;

public class ValidadorCPFTest {

    @Test
    void retornaTrueParaCpfValidoComMascara() {
        boolean resultado;

        resultado = ValidadorCPF.isValido("984.359.090-26");

        assertTrue(resultado);
    }

    @Test
    void retornaTrueParaCpfValidoSemMascara() {
        boolean resultado;

        resultado = ValidadorCPF.isValido("98435909026");

        assertTrue(resultado);
    }

    @Test
    void retornaFalseParaCpfInvalido() {
        boolean resultado;

        resultado = ValidadorCPF.isValido("11111111111");

        assertFalse(resultado);
    }

    @Test
    void retornaFalseParaCpfIncompleto() {
        boolean resultado;

        resultado = ValidadorCPF.isValido("98435909");

        assertFalse(resultado);
    }

    @Test
    void retornaFalseParaCpfEmBranco() {
        boolean resultado;

        resultado = ValidadorCPF.isValido("  ");

        assertFalse(resultado);
    }
}
