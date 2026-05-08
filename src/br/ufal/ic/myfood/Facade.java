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
    private EntregaService entregaService;

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
        this.entregaService = new EntregaService(dados);
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

    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws Exception {
        usuarioService.criarEntregador(nome, email, senha, endereco, veiculo, placa);
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

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws Exception {
        return empresaService.criarEmpresa(tipoEmpresa, dono, nome, endereco, abre, fecha, tipoMercado);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios) throws Exception {
        return empresaService.criarEmpresa(tipoEmpresa, dono, nome, endereco, aberto24Horas, numeroFuncionarios);
    }

    public void alterarFuncionamento(int mercado, String abre, String fecha) throws Exception {
        empresaService.alterarFuncionamento(mercado, abre, fecha);
    }

    public void cadastrarEntregador(int empresa, int entregador) throws Exception {
        empresaService.cadastrarEntregador(empresa, entregador);
    }

    public String getEntregadores(int empresa) throws Exception {
        return empresaService.getEntregadores(empresa);
    }

    public String getEmpresas(int entregador) throws Exception {
        return empresaService.getEmpresas(entregador);
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

    public void liberarPedido(int numero) throws Exception {
        pedidoService.liberarPedido(numero);
    }

    public void removerProduto(int pedido, String produto) throws Exception {
        pedidoService.removerProduto(pedido, produto);
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) throws Exception {
        return pedidoService.getNumeroPedido(cliente, empresa, indice);
    }

    public int obterPedido(int entregador) throws Exception {
        return entregaService.obterPedido(entregador);
    }

    public int criarEntrega(int pedido, int entregador, String destino) throws Exception {
        return entregaService.criarEntrega(pedido, entregador, destino);
    }

    public String getEntrega(int id, String atributo) throws Exception {
        return entregaService.getEntrega(id, atributo);
    }

    public int getIdEntrega(int pedido) throws Exception {
        return entregaService.getIdEntrega(pedido);
    }

    public void entregar(int entrega) throws Exception {
        entregaService.entregar(entrega);
    }
}