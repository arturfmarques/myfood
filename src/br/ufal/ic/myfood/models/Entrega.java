package br.ufal.ic.myfood.models;

import java.io.Serializable;
import java.util.List;

public class Entrega implements Serializable {
    private int id;
    private String cliente;
    private String empresa;
    private int pedido;
    private int idEntregador;
    private String entregador;
    private String destino;
    private List<String> produtos;

    public Entrega(int id, String cliente, String empresa, int pedido, int idEntregador, String entregador, String destino, List<String> produtos) {
        this.id = id;
        this.cliente = cliente;
        this.empresa = empresa;
        this.pedido = pedido;
        this.idEntregador = idEntregador;
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

    public int getPedido() {
        return pedido;
    }

    public int getIdEntregador() {
        return idEntregador;
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