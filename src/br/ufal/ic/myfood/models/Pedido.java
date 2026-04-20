package br.ufal.ic.myfood.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {
    private int numero;
    private int idCliente;
    private int idEmpresa;
    private String estado;
    private List<Integer> idsProdutos;

    public Pedido(int numero, int idCliente, int idEmpresa) {
        this.numero = numero;
        this.idCliente = idCliente;
        this.idEmpresa = idEmpresa;
        this.estado = "aberto";
        this.idsProdutos = new ArrayList<>();
    }

    public int getNumero() {
        return numero;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public String getEstado() {
        return estado;
    }

    public List<Integer> getIdsProdutos() {
        return idsProdutos;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}