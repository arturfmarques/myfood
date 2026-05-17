package br.ufal.ic.myfood.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Pedido implements Serializable {
    private int numero;
    private int idDoCliente;
    private int idDaEmpresa;
    private String estado;
    private List<Integer> idsDosProdutos;

    public Pedido(int numero, int idDoCliente, int idDaEmpresa) {
        this.numero = numero;
        this.idDoCliente = idDoCliente;
        this.idDaEmpresa = idDaEmpresa;
        this.estado = "aberto";
        this.idsDosProdutos = new ArrayList<>();
    }

    public int getNumero() {
        return numero;
    }

    public int getIdDoCliente() {
        return idDoCliente;
    }

    public int getIdDaEmpresa() {
        return idDaEmpresa;
    }

    public String getEstado() {
        return estado;
    }

    public List<Integer> getIdsDosProdutos() {
        return idsDosProdutos;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}