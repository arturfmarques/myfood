package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.MyFoodException;
import br.ufal.ic.myfood.models.DadosSistema;
import br.ufal.ic.myfood.models.Empresa;
import br.ufal.ic.myfood.models.Produto;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProdutoService {
    private DadosSistema dadosDoSistema;

    public ProdutoService(DadosSistema dadosDoSistema) {
        this.dadosDoSistema = dadosDoSistema;
    }

    public int criarProduto(int idDaEmpresa, String nome, double valor, String categoria) throws Exception {
        validarNome(nome);
        validarValor(valor);
        validarCategoria(categoria);

        Empresa empresa = dadosDoSistema.getEmpresas().get(idDaEmpresa);

        if (empresa == null) {
            throw new MyFoodException("Empresa nao encontrada");
        }

        for (Integer idDoProduto : empresa.getIdsDosProdutos()) {
            Produto produto = dadosDoSistema.getProdutos().get(idDoProduto);
            if (produto.getNome().equals(nome)) {
                throw new MyFoodException("Ja existe um produto com esse nome para essa empresa");
            }
        }

        int id = dadosDoSistema.getProximoIdDeProduto();
        Produto produto = new Produto(id, nome, valor, categoria, idDaEmpresa);
        dadosDoSistema.getProdutos().put(id, produto);
        empresa.getIdsDosProdutos().add(id);
        dadosDoSistema.setProximoIdDeProduto(id + 1);

        return id;
    }

    public void editarProduto(int idDoProduto, String nome, double valor, String categoria) throws Exception {
        validarNome(nome);
        validarValor(valor);
        validarCategoria(categoria);

        Produto produto = dadosDoSistema.getProdutos().get(idDoProduto);

        if (produto == null) {
            throw new MyFoodException("Produto nao cadastrado");
        }

        Empresa empresa = dadosDoSistema.getEmpresas().get(produto.getIdDaEmpresa());

        for (Integer outroIdDeProduto : empresa.getIdsDosProdutos()) {
            Produto outroProduto = dadosDoSistema.getProdutos().get(outroIdDeProduto);
            if (outroProduto.getNome().equals(nome) && outroProduto.getId() != idDoProduto) {
                throw new MyFoodException("Ja existe um produto com esse nome para essa empresa");
            }
        }

        produto.setNome(nome);
        produto.setValor(valor);
        produto.setCategoria(categoria);
    }

    public String getProduto(String nome, int idDaEmpresa, String atributo) throws Exception {
        Produto produto = buscarProdutoPorNomeNaEmpresa(nome, idDaEmpresa);

        if (produto == null) {
            throw new MyFoodException("Produto nao encontrado");
        }

        if ("valor".equals(atributo)) return formatarDouble(produto.getValor());
        if ("categoria".equals(atributo)) return produto.getCategoria();

        if ("empresa".equals(atributo)) {
            return dadosDoSistema.getEmpresas().get(produto.getIdDaEmpresa()).getNome();
        }

        throw new MyFoodException("Atributo nao existe");
    }

    public String listarProdutos(int idDaEmpresa) throws Exception {
        Empresa empresa = dadosDoSistema.getEmpresas().get(idDaEmpresa);

        if (empresa == null) {
            throw new MyFoodException("Empresa nao encontrada");
        }

        List<String> nomesDosProdutos = new ArrayList<>();

        for (Integer idDoProduto : empresa.getIdsDosProdutos()) {
            nomesDosProdutos.add(dadosDoSistema.getProdutos().get(idDoProduto).getNome());
        }

        return "{[" + String.join(", ", nomesDosProdutos) + "]}";
    }

    private Produto buscarProdutoPorNomeNaEmpresa(String nome, int idDaEmpresa) {
        Empresa empresa = dadosDoSistema.getEmpresas().get(idDaEmpresa);

        if (empresa == null) {
            return null;
        }

        for (Integer idDoProduto : empresa.getIdsDosProdutos()) {
            Produto produto = dadosDoSistema.getProdutos().get(idDoProduto);
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