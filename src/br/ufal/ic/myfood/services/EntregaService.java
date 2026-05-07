package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.MyFoodException;
import br.ufal.ic.myfood.models.*;

import java.util.ArrayList;
import java.util.List;

public class EntregaService {
    private DadosSistema dados;

    public EntregaService(DadosSistema dados) {
        this.dados = dados;
    }

    public int obterPedido(int entregadorId) throws Exception {
        Usuario usuario = dados.getUsuarios().get(entregadorId);

        if (!(usuario instanceof Entregador)) {
            throw new MyFoodException("Usuario nao e um entregador");
        }

        Entregador entregador = (Entregador) usuario;

        if (entregador.getIdsEmpresas().isEmpty()) {
            throw new MyFoodException("Entregador nao estar em nenhuma empresa.");
        }

        for (Pedido pedido : dados.getPedidos().values()) {
            if ("pronto".equals(pedido.getEstado()) &&
                    entregador.getIdsEmpresas().contains(pedido.getIdEmpresa())) {

                Empresa empresa = dados.getEmpresas().get(pedido.getIdEmpresa());
                if (empresa instanceof Farmacia) {
                    return pedido.getNumero();
                }
            }
        }

        for (Pedido pedido : dados.getPedidos().values()) {
            if ("pronto".equals(pedido.getEstado()) &&
                    entregador.getIdsEmpresas().contains(pedido.getIdEmpresa())) {
                return pedido.getNumero();
            }
        }

        throw new MyFoodException("Nao existe pedido para entrega");
    }

    public int criarEntrega(int pedidoId, int entregadorId, String destino) throws Exception {
        Pedido pedido = dados.getPedidos().get(pedidoId);

        if (pedido == null || !"pronto".equals(pedido.getEstado())) {
            throw new MyFoodException("Pedido nao esta pronto para entrega");
        }

        Usuario usuario = dados.getUsuarios().get(entregadorId);

        if (!(usuario instanceof Entregador)) {
            throw new MyFoodException("Nao e um entregador valido");
        }

        Entregador entregador = (Entregador) usuario;

        if (entregador.isEmEntrega()) {
            throw new MyFoodException("Entregador ainda em entrega");
        }

        Usuario cliente = dados.getUsuarios().get(pedido.getIdCliente());
        Empresa empresa = dados.getEmpresas().get(pedido.getIdEmpresa());

        String destinoFinal = destino;
        if (destinoFinal == null || destinoFinal.trim().isEmpty()) {
            destinoFinal = cliente.getEndereco();
        }

        List<String> produtos = new ArrayList<>();
        for (Integer idProduto : pedido.getIdsProdutos()) {
            produtos.add(dados.getProdutos().get(idProduto).getNome());
        }

        int idEntrega = dados.getProximoIdEntrega();

        Entrega entrega = new Entrega(
                idEntrega,
                cliente.getNome(),
                empresa.getNome(),
                pedidoId,
                entregadorId,
                entregador.getNome(),
                destinoFinal,
                produtos
        );

        dados.getEntregas().put(idEntrega, entrega);
        dados.setProximoIdEntrega(idEntrega + 1);

        pedido.setEstado("entregando");
        entregador.setEmEntrega(true);

        return idEntrega;
    }

    public String getEntrega(int idEntrega, String atributo) throws Exception {
        if (atributo == null || atributo.trim().isEmpty()) {
            throw new MyFoodException("Atributo invalido");
        }

        Entrega entrega = dados.getEntregas().get(idEntrega);

        if (entrega == null) {
            throw new MyFoodException("Entrega nao encontrada");
        }

        if ("cliente".equals(atributo)) return entrega.getCliente();
        if ("empresa".equals(atributo)) return entrega.getEmpresa();
        if ("pedido".equals(atributo)) return String.valueOf(entrega.getPedido());
        if ("entregador".equals(atributo)) return entrega.getEntregador();
        if ("destino".equals(atributo)) return entrega.getDestino();
        if ("produtos".equals(atributo)) return "{[" + String.join(", ", entrega.getProdutos()) + "]}";

        throw new MyFoodException("Atributo nao existe");
    }

    public int getIdEntrega(int pedidoId) throws Exception {
        for (Entrega entrega : dados.getEntregas().values()) {
            if (entrega.getPedido() == pedidoId) {
                return entrega.getId();
            }
        }

        throw new MyFoodException("Nao existe entrega com esse id");
    }

    public void entregar(int idEntrega) throws Exception {
        Entrega entrega = dados.getEntregas().get(idEntrega);

        if (entrega == null) {
            throw new MyFoodException("Nao existe nada para ser entregue com esse id");
        }

        Pedido pedido = dados.getPedidos().get(entrega.getPedido());
        pedido.setEstado("entregue");

        Entregador entregador = (Entregador) dados.getUsuarios().get(entrega.getIdEntregador());
        entregador.setEmEntrega(false);
    }
}