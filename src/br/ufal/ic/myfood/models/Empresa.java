package br.ufal.ic.myfood.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Empresa implements Serializable {
    private int id;
    private String nome;
    private String endereco;
    private int idDoDono;
    private List<Integer> idsDosProdutos;
    private List<Integer> idsDosEntregadores;

    public Empresa(int id, String nome, String endereco, int idDoDono) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.idDoDono = idDoDono;
        this.idsDosProdutos = new ArrayList<>();
        this.idsDosEntregadores = new ArrayList<>();
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

    public int getIdDoDono() {
        return idDoDono;
    }

    public List<Integer> getIdsDosProdutos() {
        return idsDosProdutos;
    }

    public List<Integer> getIdsDosEntregadores() {
        return idsDosEntregadores;
    }

    public abstract String getTipoEmpresa();
}