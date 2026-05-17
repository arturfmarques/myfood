package br.ufal.ic.myfood.models;

public class Restaurante extends Empresa {
    private String tipoCozinha;

    public Restaurante(int id, String nome, String endereco, int idDoDono, String tipoCozinha) {
        super(id, nome, endereco, idDoDono);
        this.tipoCozinha = tipoCozinha;
    }

    public String getTipoCozinha() {
        return tipoCozinha;
    }

    @Override
    public String getTipoEmpresa() {
        return "restaurante";
    }
}