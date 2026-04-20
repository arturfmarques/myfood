package br.ufal.ic.myfood;

import br.ufal.ic.myfood.models.DadosSistema;
import br.ufal.ic.myfood.services.*;

public class Facade {
    private DadosSistema dados;
    private PersistenciaService persistenciaService;
    private UsuarioService usuarioService;
    private EmpresaService empresaService;
    private ProdutoService produtoService;
    private PedidoService pedidoService;

    public Facade() {
        this.persistenciaService = new PersistenciaService();
        this.dados = persistenciaService.carregar();
        inicializarServicos();
    }

    private void inicializarServicos() {
        this.usuarioService = new UsuarioService(dados);
        this.empresaService = new EmpresaService(dados);
        this.produtoService = new ProdutoService(dados);
        this.pedidoService = new PedidoService(dados);
    }

    public void zerarSistema() {
        this.dados = new DadosSistema();
        persistenciaService.apagar();
        inicializarServicos();
    }

    public void encerrarSistema() {
        persistenciaService.salvar(dados);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws Exception {
        usuarioService.criarCliente(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws Exception {
        usuarioService.criarDono(nome, email, senha, endereco, cpf);
    }

    public int login(String email, String senha) throws Exception {
        return usuarioService.login(email, senha);
    }

    public String getAtributoUsuario(int id, String atributo) throws Exception {
        return usuarioService.getAtributoUsuario(id, atributo);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws Exception {
        return empresaService.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoCozinha);
    }

    public String getEmpresasDoUsuario(int idDono) throws Exception {
        return empresaService.getEmpresasDoUsuario(idDono);
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws Exception {
        return empresaService.getIdEmpresa(idDono, nome, indice);
    }

    public String getAtributoEmpresa(int empresa, String atributo) throws Exception {
        return empresaService.getAtributoEmpresa(empresa, atributo);
    }

    public int criarProduto(int empresa, String nome, double valor, String categoria) throws Exception {
        return produtoService.criarProduto(empresa, nome, valor, categoria);
    }

    public void editarProduto(int produto, String nome, double valor, String categoria) throws Exception {
        produtoService.editarProduto(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresa, String atributo) throws Exception {
        return produtoService.getProduto(nome, empresa, atributo);
    }

    public String listarProdutos(int empresa) throws Exception {
        return produtoService.listarProdutos(empresa);
    }

    public int criarPedido(int cliente, int empresa) throws Exception {
        return pedidoService.criarPedido(cliente, empresa);
    }

    public void adicionarProduto(int numero, int produto) throws Exception {
        pedidoService.adicionarProduto(numero, produto);
    }

    public String getPedidos(int pedido, String atributo) throws Exception {
        return pedidoService.getPedidos(pedido, atributo);
    }

    public void fecharPedido(int numero) throws Exception {
        pedidoService.fecharPedido(numero);
    }

    public void removerProduto(int pedido, String produto) throws Exception {
        pedidoService.removerProduto(pedido, produto);
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) throws Exception {
        return pedidoService.getNumeroPedido(cliente, empresa, indice);
    }
}