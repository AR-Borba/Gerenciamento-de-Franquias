package com.franquias.Utils;

import com.google.gson.annotations.SerializedName;

public class Endereco {
    @SerializedName("logradouro")
    private String rua;

    private String numero;

    @SerializedName("localidade")
    private String cidade;

    @SerializedName("uf")
    private String estado;

    @SerializedName("cep")
    private String cep;

    public Endereco(String rua, String numero, String cidade, String estado, String CEP) {
        this.rua = rua;
        this.numero = numero;
        this.cidade = cidade;
        this.estado = estado;
        this.cep = CEP;
    }

    public Endereco() {

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