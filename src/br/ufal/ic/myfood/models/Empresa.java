package br.ufal.ic.myfood.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Empresa implements Serializable {
    private int id;
    private String nome;
    private String endereco;
    private int idDono;
    private List<Integer> idsProdutos;

    public Empresa(int id, String nome, String endereco, int idDono) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.idDono = idDono;
        this.idsProdutos = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public int getIdDono() {
        return idDono;
    }

    public List<Integer> getIdsProdutos() {
        return idsProdutos;
    }

    public abstract String getTipoEmpresa();
}