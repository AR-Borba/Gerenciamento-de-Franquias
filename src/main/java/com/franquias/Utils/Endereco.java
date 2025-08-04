package com.franquias.Utils;

import org.apache.commons.lang3.StringUtils;

import com.franquias.exceptions.CepException;

public class Endereco {
    private String rua;
    private String numero;
    private String cidade;
    private String estado;
    private String cep;

    public Endereco (String rua, String numero, String cidade, String estado, String CEP){
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = CEP;
    }

    public Endereco() {

    }

    public boolean validarCep() throws CepException {
        try {    
            return StringUtils.isNotBlank(this.cep) && this.cep.matches("\\d{5}-\\d{3}");
        } catch (Exception e) {
            System.out.print("deu erro aqui ó!");
            return false;
        }
    }

    public String formatarEndereco() {
       return String.format("%s, %s, %s - %s, %s", this.rua, this.numero, this.cidade, this.estado, this.cep);
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade() {
        return this.cidade;
    }

    public String getEstado() {
        return this.estado;
    }

    public String getRua() {
        return this.rua;
    }

    public String getNumero() {
        return this.numero;
    }

    public String getCep() {
        return this.cep;
    }
}