package br.ufal.ic.myfood.models;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

public class DadosSistema implements Serializable {
    private Map<Integer, Usuario> usuarios;
    private Map<Integer, Empresa> empresas;
    private Map<Integer, Produto> produtos;
    private Map<Integer, Pedido> pedidos;
    private Map<Integer, Entrega> entregas;

    private int proximoIdDeUsuario;
    private int proximoIdDeEmpresa;
    private int proximoIdDeProduto;
    private int proximoNumeroDePedido;
    private int proximoIdDeEntrega;

    public DadosSistema() {
        this.usuarios = new LinkedHashMap<>();
        this.empresas = new LinkedHashMap<>();
        this.produtos = new LinkedHashMap<>();
        this.pedidos = new LinkedHashMap<>();
        this.entregas = new LinkedHashMap<>();
        this.proximoIdDeUsuario = 1;
        this.proximoIdDeEmpresa = 1;
        this.proximoIdDeProduto = 1;
        this.proximoNumeroDePedido = 1;
        this.proximoIdDeEntrega = 1;
    }

    public Map<Integer, Usuario> getUsuarios() {
        return usuarios;
    }

    public Map<Integer, Empresa> getEmpresas() {
        return empresas;
    }

    public Map<Integer, Produto> getProdutos() {
        return produtos;
    }

    public Map<Integer, Pedido> getPedidos() {
        return pedidos;
    }

    public Map<Integer, Entrega> getEntregas() {
        return entregas;
    }

    public int getProximoIdDeUsuario() {
        return proximoIdDeUsuario;
    }

    public void setProximoIdDeUsuario(int proximoIdDeUsuario) {
        this.proximoIdDeUsuario = proximoIdDeUsuario;
    }

    public int getProximoIdDeEmpresa() {
        return proximoIdDeEmpresa;
    }

    public void setProximoIdDeEmpresa(int proximoIdDeEmpresa) {
        this.proximoIdDeEmpresa = proximoIdDeEmpresa;
    }

    public int getProximoIdDeProduto() {
        return proximoIdDeProduto;
    }

    public void setProximoIdDeProduto(int proximoIdDeProduto) {
        this.proximoIdDeProduto = proximoIdDeProduto;
    }

    public int getProximoNumeroDePedido() {
        return proximoNumeroDePedido;
    }

    public void setProximoNumeroDePedido(int proximoNumeroDePedido) {
        this.proximoNumeroDePedido = proximoNumeroDePedido;
    }

    public int getProximoIdDeEntrega() {
        return proximoIdDeEntrega;
    }

    public void setProximoIdDeEntrega(int proximoIdDeEntrega) {
        this.proximoIdDeEntrega = proximoIdDeEntrega;
    }
}