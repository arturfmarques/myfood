package br.ufal.ic.myfood;

import br.ufal.ic.myfood.models.DadosSistema;
import br.ufal.ic.myfood.services.*;

public class Facade {
    private DadosSistema dadosDoSistema;
    private PersistenciaService servicoDePersistencia;
    private UsuarioService servicoDeUsuario;
    private EmpresaService servicoDeEmpresa;
    private ProdutoService servicoDeProduto;
    private PedidoService servicoDePedido;
    private EntregaService servicoDeEntrega;

    public Facade() {
        this.servicoDePersistencia = new PersistenciaService();
        this.dadosDoSistema = servicoDePersistencia.carregar();
        inicializarServicos();
    }

    private void inicializarServicos() {
        this.servicoDeUsuario = new UsuarioService(dadosDoSistema);
        this.servicoDeEmpresa = new EmpresaService(dadosDoSistema);
        this.servicoDeProduto = new ProdutoService(dadosDoSistema);
        this.servicoDePedido = new PedidoService(dadosDoSistema);
        this.servicoDeEntrega = new EntregaService(dadosDoSistema);
    }

    public void zerarSistema() {
        this.dadosDoSistema = new DadosSistema();
        servicoDePersistencia.apagar();
        inicializarServicos();
    }

    public void encerrarSistema() {
        servicoDePersistencia.salvar(dadosDoSistema);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco) throws Exception {
        servicoDeUsuario.criarCliente(nome, email, senha, endereco);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String cpf) throws Exception {
        servicoDeUsuario.criarDono(nome, email, senha, endereco, cpf);
    }

    public void criarUsuario(String nome, String email, String senha, String endereco, String veiculo, String placa) throws Exception {
        servicoDeUsuario.criarEntregador(nome, email, senha, endereco, veiculo, placa);
    }

    public int login(String email, String senha) throws Exception {
        return servicoDeUsuario.login(email, senha);
    }

    public String getAtributoUsuario(int id, String atributo) throws Exception {
        return servicoDeUsuario.getAtributoUsuario(id, atributo);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws Exception {
        return servicoDeEmpresa.criarEmpresa(tipoEmpresa, dono, nome, endereco, tipoCozinha);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws Exception {
        return servicoDeEmpresa.criarEmpresa(tipoEmpresa, dono, nome, endereco, abre, fecha, tipoMercado);
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios) throws Exception {
        return servicoDeEmpresa.criarEmpresa(tipoEmpresa, dono, nome, endereco, aberto24Horas, numeroFuncionarios);
    }

    public void alterarFuncionamento(int mercado, String abre, String fecha) throws Exception {
        servicoDeEmpresa.alterarFuncionamento(mercado, abre, fecha);
    }

    public void cadastrarEntregador(int empresa, int entregador) throws Exception {
        servicoDeEmpresa.cadastrarEntregador(empresa, entregador);
    }

    public String getEntregadores(int empresa) throws Exception {
        return servicoDeEmpresa.getEntregadores(empresa);
    }

    public String getEmpresas(int entregador) throws Exception {
        return servicoDeEmpresa.getEmpresas(entregador);
    }

    public String getEmpresasDoUsuario(int idDoDono) throws Exception {
        return servicoDeEmpresa.getEmpresasDoUsuario(idDoDono);
    }

    public int getIdEmpresa(int idDoDono, String nome, int indice) throws Exception {
        return servicoDeEmpresa.getIdEmpresa(idDoDono, nome, indice);
    }

    public String getAtributoEmpresa(int empresa, String atributo) throws Exception {
        return servicoDeEmpresa.getAtributoEmpresa(empresa, atributo);
    }

    public int criarProduto(int empresa, String nome, double valor, String categoria) throws Exception {
        return servicoDeProduto.criarProduto(empresa, nome, valor, categoria);
    }

    public void editarProduto(int produto, String nome, double valor, String categoria) throws Exception {
        servicoDeProduto.editarProduto(produto, nome, valor, categoria);
    }

    public String getProduto(String nome, int empresa, String atributo) throws Exception {
        return servicoDeProduto.getProduto(nome, empresa, atributo);
    }

    public String listarProdutos(int empresa) throws Exception {
        return servicoDeProduto.listarProdutos(empresa);
    }

    public int criarPedido(int cliente, int empresa) throws Exception {
        return servicoDePedido.criarPedido(cliente, empresa);
    }

    public void adicionarProduto(int numero, int produto) throws Exception {
        servicoDePedido.adicionarProduto(numero, produto);
    }

    public String getPedidos(int pedido, String atributo) throws Exception {
        return servicoDePedido.getPedidos(pedido, atributo);
    }

    public void fecharPedido(int numero) throws Exception {
        servicoDePedido.fecharPedido(numero);
    }

    public void liberarPedido(int numero) throws Exception {
        servicoDePedido.liberarPedido(numero);
    }

    public void removerProduto(int pedido, String produto) throws Exception {
        servicoDePedido.removerProduto(pedido, produto);
    }

    public int getNumeroPedido(int cliente, int empresa, int indice) throws Exception {
        return servicoDePedido.getNumeroPedido(cliente, empresa, indice);
    }

    public int obterPedido(int entregador) throws Exception {
        return servicoDeEntrega.obterPedido(entregador);
    }

    public int criarEntrega(int pedido, int entregador, String destino) throws Exception {
        return servicoDeEntrega.criarEntrega(pedido, entregador, destino);
    }

    public String getEntrega(int id, String atributo) throws Exception {
        return servicoDeEntrega.getEntrega(id, atributo);
    }

    public int getIdEntrega(int pedido) throws Exception {
        return servicoDeEntrega.getIdEntrega(pedido);
    }

    public void entregar(int entrega) throws Exception {
        servicoDeEntrega.entregar(entrega);
    }
}