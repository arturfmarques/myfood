package br.ufal.ic.myfood.models;

import java.io.Serializable;

public class Produto implements Serializable {
    private int id;
    private String nome;
    private double valor;
    private String categoria;
    private int idEmpresa;

    public Produto(int id, String nome, double valor, String categoria, int idEmpresa) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.categoria = categoria;
        this.idEmpresa = idEmpresa;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public double getValor() {
        return valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}