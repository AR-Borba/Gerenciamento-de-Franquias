package com.franquias.Utils;

import org.apache.commons.lang3.StringUtils;

public class Endereco {
    private String rua;
    private String numero;
    private String cidade;
    private String estado;
    private String cep;

    // Construtor, getters e setters (omiti para brevidade)

    public boolean validarCep() {
        // Implementar validação do CEP usando expressões regulares ou outra lógica
        return StringUtils.isNotBlank(this.cep) && this.cep.matches("\\d{5}-\\d{3}");
    }

    public String formatarEndereco() {
       return String.format("%s, %s, %s - %s, %s", this.rua, this.numero, this.cidade, this.estado, this.cep);
    }
}