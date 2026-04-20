package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.MyFoodException;
import br.ufal.ic.myfood.models.DadosSistema;
import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.models.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProdutoService {
    private DadosSistema dados;

    public ProdutoService(DadosSistema dados) {
        this.dados = dados;
    }

    public int criarProduto(int idEmpresa, String nome, double valor, String categoria) throws Exception {
        validarNome(nome);
        validarValor(valor);
        validarCategoria(categoria);

        Empresa empresa = dados.getEmpresas().get(idEmpresa);

        if (empresa == null) {
            throw new MyFoodException("Empresa nao encontrada");
        }

        for (Integer idProduto : empresa.getIdsProdutos()) {
            Produto produto = dados.getProdutos().get(idProduto);
            if (produto.getNome().equals(nome)) {
                throw new MyFoodException("Ja existe um produto com esse nome para essa empresa");
            }
        }

        int id = dados.getProximoIdProduto();
        Produto produto = new Produto(id, nome, valor, categoria, idEmpresa);
        dados.getProdutos().put(id, produto);
        empresa.getIdsProdutos().add(id);
        dados.setProximoIdProduto(id + 1);

        return id;
    }

    public void editarProduto(int idProduto, String nome, double valor, String categoria) throws Exception {
        validarNome(nome);
        validarValor(valor);
        validarCategoria(categoria);

        Produto produto = dados.getProdutos().get(idProduto);

        if (produto == null) {
            throw new MyFoodException("Produto nao cadastrado");
        }

        Empresa empresa = dados.getEmpresas().get(produto.getIdEmpresa());

        for (Integer outroId : empresa.getIdsProdutos()) {
            Produto outroProduto = dados.getProdutos().get(outroId);
            if (outroProduto.getNome().equals(nome) && outroProduto.getId() != idProduto) {
                throw new MyFoodException("Ja existe um produto com esse nome para essa empresa");
            }
        }

        produto.setNome(nome);
        produto.setValor(valor);
        produto.setCategoria(categoria);
    }

    public String getProduto(String nome, int idEmpresa, String atributo) throws Exception {
        Produto produto = buscarProdutoPorNomeNaEmpresa(nome, idEmpresa);

        if (produto == null) {
            throw new MyFoodException("Produto nao encontrado");
        }

        if ("valor".equals(atributo)) return formatarDouble(produto.getValor());
        if ("categoria".equals(atributo)) return produto.getCategoria();

        if ("empresa".equals(atributo)) {
            return dados.getEmpresas().get(produto.getIdEmpresa()).getNome();
        }

        throw new MyFoodException("Atributo nao existe");
    }

    public String listarProdutos(int idEmpresa) throws Exception {
        Empresa empresa = dados.getEmpresas().get(idEmpresa);

        if (empresa == null) {
            throw new MyFoodException("Empresa nao encontrada");
        }

        List<String> nomes = new ArrayList<>();

        for (Integer idProduto : empresa.getIdsProdutos()) {
            nomes.add(dados.getProdutos().get(idProduto).getNome());
        }

        return "{[" + String.join(", ", nomes) + "]}";
    }

    private Produto buscarProdutoPorNomeNaEmpresa(String nome, int idEmpresa) {
        Empresa empresa = dados.getEmpresas().get(idEmpresa);

        if (empresa == null) {
            return null;
        }

        for (Integer idProduto : empresa.getIdsProdutos()) {
            Produto produto = dados.getProdutos().get(idProduto);
            if (produto.getNome().equals(nome)) {
                return produto;
            }
        }

        return null;
    }

    private void validarNome(String nome) throws Exception {
        if (nome == null || nome.trim().isEmpty()) {
            throw new MyFoodException("Nome invalido");
        }
    }

    private void validarValor(double valor) throws Exception {
        if (valor < 0) {
            throw new MyFoodException("Valor invalido");
        }
    }

    private void validarCategoria(String categoria) throws Exception {
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new MyFoodException("Categoria invalido");
        }
    }

    private String formatarDouble(double valor) {
        return String.format(Locale.US, "%.2f", valor);
    }
}