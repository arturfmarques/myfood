package br.ufal.ic.myfood.models;

import java.io.Serializable;
import java.util.List;

public class Entrega implements Serializable {
    private int id;
    private String cliente;
    private String empresa;
    private int numeroDoPedido;
    private int idDoEntregador;
    private String entregador;
    private String destino;
    private List<String> produtos;

    public Entrega(int id, String cliente, String empresa, int numeroDoPedido, int idDoEntregador, String entregador, String destino, List<String> produtos) {
        this.id = id;
        this.cliente = cliente;
        this.empresa = empresa;
        this.numeroDoPedido = numeroDoPedido;
        this.idDoEntregador = idDoEntregador;
        this.entregador = entregador;
        this.destino = destino;
        this.produtos = produtos;
    }

    public int getId() {
        return id;
    }

    public String getCliente() {
        return cliente;
    }

    public String getEmpresa() {
        return empresa;
    }

    public int getNumeroDoPedido() {
        return numeroDoPedido;
    }

    public int getIdDoEntregador() {
        return idDoEntregador;
    }

    public String getEntregador() {
        return entregador;
    }

    public String getDestino() {
        return destino;
    }

    public List<String> getProdutos() {
        return produtos;
    }
}