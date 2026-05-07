package br.ufal.ic.myfood.models;

import java.util.ArrayList;
import java.util.List;

public class Entregador extends Usuario {
    private String veiculo;
    private String placa;
    private List<Integer> idsEmpresas;
    private boolean emEntrega;

    public Entregador(int id, String nome, String email, String senha, String endereco, String veiculo, String placa) {
        super(id, nome, email, senha, endereco);
        this.veiculo = veiculo;
        this.placa = placa;
        this.idsEmpresas = new ArrayList<>();
        this.emEntrega = false;
    }

    public String getVeiculo() {
        return veiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public List<Integer> getIdsEmpresas() {
        return idsEmpresas;
    }

    public boolean isEmEntrega() {
        return emEntrega;
    }

    public void setEmEntrega(boolean emEntrega) {
        this.emEntrega = emEntrega;
    }

    @Override
    public boolean ehDono() {
        return false;
    }

    @Override
    public boolean ehEntregador() {
        return true;
    }
}