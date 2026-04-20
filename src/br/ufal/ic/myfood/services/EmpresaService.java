package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.MyFoodException;
import br.ufal.ic.myfood.models.DadosSistema;
import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.models.Usuario;

import java.util.ArrayList;
import java.util.List;

public class EmpresaService {
    private DadosSistema dados;

    public EmpresaService(DadosSistema dados) {
        this.dados = dados;
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws Exception {
        Usuario usuario = dados.getUsuarios().get(dono);

        if (usuario == null || !usuario.ehDono()) {
            throw new MyFoodException("Usuario nao pode criar uma empresa");
        }

        for (Empresa empresa : dados.getEmpresas().values()) {
            if (empresa.getNome().equals(nome)) {
                if (empresa.getIdDono() != dono) {
                    throw new MyFoodException("Empresa com esse nome ja existe");
                }

                if (empresa.getEndereco().equals(endereco)) {
                    throw new MyFoodException("Proibido cadastrar duas empresas com o mesmo nome e local");
                }
            }
        }

        int id = dados.getProximoIdEmpresa();
        Empresa empresa = new Empresa(id, nome, endereco, tipoCozinha, dono);
        dados.getEmpresas().put(id, empresa);
        dados.setProximoIdEmpresa(id + 1);

        return id;
    }

    public String getEmpresasDoUsuario(int idDono) throws Exception {
        Usuario usuario = dados.getUsuarios().get(idDono);

        if (usuario == null || !usuario.ehDono()) {
            throw new MyFoodException("Usuario nao pode criar uma empresa");
        }

        List<String> lista = new ArrayList<>();

        for (Empresa empresa : dados.getEmpresas().values()) {
            if (empresa.getIdDono() == idDono) {
                lista.add("[" + empresa.getNome() + ", " + empresa.getEndereco() + "]");
            }
        }

        return "{[" + String.join(", ", lista) + "]}";
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws Exception {
        if (textoVazio(nome)) {
            throw new MyFoodException("Nome invalido");
        }

        if (indice < 0) {
            throw new MyFoodException("Indice invalido");
        }

        List<Empresa> lista = new ArrayList<>();

        for (Empresa empresa : dados.getEmpresas().values()) {
            if (empresa.getIdDono() == idDono && empresa.getNome().equals(nome)) {
                lista.add(empresa);
            }
        }

        if (lista.isEmpty()) {
            throw new MyFoodException("Nao existe empresa com esse nome");
        }

        if (indice >= lista.size()) {
            throw new MyFoodException("Indice maior que o esperado");
        }

        return lista.get(indice).getId();
    }

    public String getAtributoEmpresa(int idEmpresa, String atributo) throws Exception {
        Empresa empresa = dados.getEmpresas().get(idEmpresa);

        if (empresa == null) {
            throw new MyFoodException("Empresa nao cadastrada");
        }

        if (textoVazio(atributo)) {
            throw new MyFoodException("Atributo invalido");
        }

        if ("nome".equals(atributo)) return empresa.getNome();
        if ("endereco".equals(atributo)) return empresa.getEndereco();
        if ("tipoCozinha".equals(atributo)) return empresa.getTipoCozinha();

        if ("dono".equals(atributo)) {
            return dados.getUsuarios().get(empresa.getIdDono()).getNome();
        }

        throw new MyFoodException("Atributo invalido");
    }

    private boolean textoVazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}