package br.ufal.ic.myfood.models;

public class Farmacia extends Empresa {
    private boolean aberto24Horas;
    private int numeroDeFuncionarios;

    public Farmacia(int id, String nome, String endereco, int idDoDono, boolean aberto24Horas, int numeroDeFuncionarios) {
        super(id, nome, endereco, idDoDono);
        this.aberto24Horas = aberto24Horas;
        this.numeroDeFuncionarios = numeroDeFuncionarios;
    }

    public boolean isAberto24Horas() {
        return aberto24Horas;
    }

    public int getNumeroDeFuncionarios() {
        return numeroDeFuncionarios;
    }

    @Override
    public String getTipoEmpresa() {
        return "farmacia";
    }
}