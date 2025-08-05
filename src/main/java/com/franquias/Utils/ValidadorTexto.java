package com.franquias.Utils;

public class ValidadorTexto {
    
    public static boolean naoEstaVazio(String texto) {
        return texto != null && !texto.isBlank();
    }

    public static boolean temTamanhoMinimo(String texto, int tamanhoMinimo) {
        return naoEstaVazio(texto) && texto.length() >= tamanhoMinimo;
    }
}
