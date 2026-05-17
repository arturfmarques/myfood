package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.MyFoodException;
import br.ufal.ic.myfood.models.*;

import java.util.ArrayList;
import java.util.List;

public class EntregaService {
    private DadosSistema dadosDoSistema;

    public EntregaService(DadosSistema dadosDoSistema) {
        this.dadosDoSistema = dadosDoSistema;
    }

    public int obterPedido(int idDoEntregador) throws Exception {
        Usuario usuario = dadosDoSistema.getUsuarios().get(idDoEntregador);

        if (!(usuario instanceof Entregador entregador)) {
            throw new MyFoodException("Usuario nao e um entregador");
        }

        if (entregador.getIdsDasEmpresas().isEmpty()) {
            throw new MyFoodException("Entregador nao estar em nenhuma empresa.");
        }

        for (Pedido pedido : dadosDoSistema.getPedidos().values()) {
            if ("pronto".equals(pedido.getEstado()) &&
                    entregador.getIdsDasEmpresas().contains(pedido.getIdDaEmpresa())) {

                Empresa empresa = dadosDoSistema.getEmpresas().get(pedido.getIdDaEmpresa());
                if (empresa instanceof Farmacia) {
                    return pedido.getNumero();
                }
            }
        }

        for (Pedido pedido : dadosDoSistema.getPedidos().values()) {
            if ("pronto".equals(pedido.getEstado()) &&
                    entregador.getIdsDasEmpresas().contains(pedido.getIdDaEmpresa())) {
                return pedido.getNumero();
            }
        }

        throw new MyFoodException("Nao existe pedido para entrega");
    }

    public int criarEntrega(int numeroDoPedido, int idDoEntregador, String destino) throws Exception {
        Pedido pedido = dadosDoSistema.getPedidos().get(numeroDoPedido);

        if (pedido == null || !"pronto".equals(pedido.getEstado())) {
            throw new MyFoodException("Pedido nao esta pronto para entrega");
        }

        Usuario usuario = dadosDoSistema.getUsuarios().get(idDoEntregador);

        if (!(usuario instanceof Entregador entregador)) {
            throw new MyFoodException("Nao e um entregador valido");
        }

        if (entregador.isEmEntrega()) {
            throw new MyFoodException("Entregador ainda em entrega");
        }

        Usuario cliente = dadosDoSistema.getUsuarios().get(pedido.getIdDoCliente());
        Empresa empresa = dadosDoSistema.getEmpresas().get(pedido.getIdDaEmpresa());

        String destinoFinal = destino;
        if (destinoFinal == null || destinoFinal.trim().isEmpty()) {
            destinoFinal = cliente.getEndereco();
        }

        List<String> nomesDosProdutos = new ArrayList<>();
        for (Integer idDoProduto : pedido.getIdsDosProdutos()) {
            nomesDosProdutos.add(dadosDoSistema.getProdutos().get(idDoProduto).getNome());
        }

        int idDaEntrega = dadosDoSistema.getProximoIdDeEntrega();

        Entrega entrega = new Entrega(
                idDaEntrega,
                cliente.getNome(),
                empresa.getNome(),
                numeroDoPedido,
                idDoEntregador,
                entregador.getNome(),
                destinoFinal,
                nomesDosProdutos
        );

        dadosDoSistema.getEntregas().put(idDaEntrega, entrega);
        dadosDoSistema.setProximoIdDeEntrega(idDaEntrega + 1);

        pedido.setEstado("entregando");
        entregador.setEmEntrega(true);

        return idDaEntrega;
    }

    public String getEntrega(int idDaEntrega, String atributo) throws Exception {
        if (atributo == null || atributo.trim().isEmpty()) {
            throw new MyFoodException("Atributo invalido");
        }

        Entrega entrega = dadosDoSistema.getEntregas().get(idDaEntrega);

        if (entrega == null) {
            throw new MyFoodException("Entrega nao encontrada");
        }

        if ("cliente".equals(atributo)) return entrega.getCliente();
        if ("empresa".equals(atributo)) return entrega.getEmpresa();
        if ("pedido".equals(atributo)) return String.valueOf(entrega.getNumeroDoPedido());
        if ("entregador".equals(atributo)) return entrega.getEntregador();
        if ("destino".equals(atributo)) return entrega.getDestino();
        if ("produtos".equals(atributo)) return "{[" + String.join(", ", entrega.getProdutos()) + "]}";

        throw new MyFoodException("Atributo nao existe");
    }

    public int getIdEntrega(int numeroDoPedido) throws Exception {
        for (Entrega entrega : dadosDoSistema.getEntregas().values()) {
            if (entrega.getNumeroDoPedido() == numeroDoPedido) {
                return entrega.getId();
            }
        }

        throw new MyFoodException("Nao existe entrega com esse id");
    }

    public void entregar(int idDaEntrega) throws Exception {
        Entrega entrega = dadosDoSistema.getEntregas().get(idDaEntrega);

        if (entrega == null) {
            throw new MyFoodException("Nao existe nada para ser entregue com esse id");
        }

        Pedido pedido = dadosDoSistema.getPedidos().get(entrega.getNumeroDoPedido());
        pedido.setEstado("entregue");

        Entregador entregador = (Entregador) dadosDoSistema.getUsuarios().get(entrega.getIdDoEntregador());
        entregador.setEmEntrega(false);
    }
}