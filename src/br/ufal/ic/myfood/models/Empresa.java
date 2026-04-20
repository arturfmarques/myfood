package br.ufal.ic.myfood.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Empresa implements Serializable {
    private int id;
    private String nome;
    private String endereco;
    private String tipoCozinha;
    private int idDono;
    private List<Integer> idsProdutos;

    public Empresa(int id, String nome, String endereco, String tipoCozinha, int idDono) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.tipoCozinha = tipoCozinha;
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

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    public int getIdDono() {
        return idDono;
    }

    public List<Integer> getIdsProdutos() {
        return idsProdutos;
    }
}