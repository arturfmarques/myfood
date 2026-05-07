package br.ufal.ic.myfood.models;

public class Farmacia extends Empresa {
    private boolean aberto24Horas;
    private int numeroFuncionarios;

    public Farmacia(int id, String nome, String endereco, int idDono, boolean aberto24Horas, int numeroFuncionarios) {
        super(id, nome, endereco, idDono);
        this.aberto24Horas = aberto24Horas;
        this.numeroFuncionarios = numeroFuncionarios;
    }

    public boolean isAberto24Horas() {
        return aberto24Horas;
    }

    public int getNumeroFuncionarios() {
        return numeroFuncionarios;
    }

    @Override
    public String getTipoEmpresa() {
        return "farmacia";
    }
}