package com.franquias.Utils;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;

public class ValidadorCPF {

    public static boolean isValido(String cpf) {
        if(cpf == null || cpf.isBlank())
            return false;

        CPFValidator validator = new CPFValidator();
        try{
            validator.assertValid(cpf);
            return true;
        } catch(InvalidStateException e) {
            return false;
        }
    } 
}