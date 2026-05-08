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

    private int proximoIdUsuario;
    private int proximoIdEmpresa;
    private int proximoIdProduto;
    private int proximoNumeroPedido;
    private int proximoIdEntrega;

    public DadosSistema() {
        this.usuarios = new LinkedHashMap<>();
        this.empresas = new LinkedHashMap<>();
        this.produtos = new LinkedHashMap<>();
        this.pedidos = new LinkedHashMap<>();
        this.entregas = new LinkedHashMap<>();
        this.proximoIdUsuario = 1;
        this.proximoIdEmpresa = 1;
        this.proximoIdProduto = 1;
        this.proximoNumeroPedido = 1;
        this.proximoIdEntrega = 1;
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

    public int getProximoIdUsuario() {
        return proximoIdUsuario;
    }

    public void setProximoIdUsuario(int proximoIdUsuario) {
        this.proximoIdUsuario = proximoIdUsuario;
    }

    public int getProximoIdEmpresa() {
        return proximoIdEmpresa;
    }

    public void setProximoIdEmpresa(int proximoIdEmpresa) {
        this.proximoIdEmpresa = proximoIdEmpresa;
    }

    public int getProximoIdProduto() {
        return proximoIdProduto;
    }

    public void setProximoIdProduto(int proximoIdProduto) {
        this.proximoIdProduto = proximoIdProduto;
    }

    public int getProximoNumeroPedido() {
        return proximoNumeroPedido;
    }

    public void setProximoNumeroPedido(int proximoNumeroPedido) {
        this.proximoNumeroPedido = proximoNumeroPedido;
    }

    public int getProximoIdEntrega() {
        return proximoIdEntrega;
    }

    public void setProximoIdEntrega(int proximoIdEntrega) {
        this.proximoIdEntrega = proximoIdEntrega;
    }
}