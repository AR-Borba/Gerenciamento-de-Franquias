package com.franquias.Utils;

import java.math.BigDecimal;

public class ValidadorNumero {
    
    public static boolean intIsPositivo(int numero) {
        return numero > 0;
    }

    public static boolean intIsNaoNegativo(int numero) {
        return numero >= 0;
    }

    public static boolean longIsNaoNegativo(long numero) {
        return numero >= 0;
    }

    public static boolean bigDecimalIsPositivo(BigDecimal numero) {
        return numero.compareTo(BigDecimal.ZERO) > 0;
    }

    public static boolean bigDecimalIsNaoNegativo(BigDecimal numero) {
        return numero.compareTo(BigDecimal.ZERO) >= 0;
    }
}
