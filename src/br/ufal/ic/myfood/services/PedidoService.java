package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.MyFoodException;
import br.ufal.ic.myfood.models.DadosSistema;
import br.ufal.ic.myfood.models.Pedido;
import br.ufal.ic.myfood.models.Produto;
import br.ufal.ic.myfood.models.Usuario;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PedidoService {
    private DadosSistema dadosDoSistema;

    public PedidoService(DadosSistema dadosDoSistema) {
        this.dadosDoSistema = dadosDoSistema;
    }

    public int criarPedido(int idDoCliente, int idDaEmpresa) throws Exception {
        Usuario usuario = dadosDoSistema.getUsuarios().get(idDoCliente);

        if (usuario == null || usuario.ehDono() || usuario.ehEntregador()) {
            throw new MyFoodException("Dono de empresa nao pode fazer um pedido");
        }

        for (Pedido pedido : dadosDoSistema.getPedidos().values()) {
            if (pedido.getIdDoCliente() == idDoCliente &&
                    pedido.getIdDaEmpresa() == idDaEmpresa &&
                    "aberto".equals(pedido.getEstado())) {
                throw new MyFoodException("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
            }
        }

        int numero = dadosDoSistema.getProximoNumeroDePedido();
        Pedido pedido = new Pedido(numero, idDoCliente, idDaEmpresa);
        dadosDoSistema.getPedidos().put(numero, pedido);
        dadosDoSistema.setProximoNumeroDePedido(numero + 1);

        return numero;
    }

    public void adicionarProduto(int numeroDoPedido, int idDoProduto) throws Exception {
        Pedido pedido = dadosDoSistema.getPedidos().get(numeroDoPedido);

        if (pedido == null) {
            throw new MyFoodException("Nao existe pedido em aberto");
        }

        if (!"aberto".equals(pedido.getEstado())) {
            throw new MyFoodException("Nao e possivel adcionar produtos a um pedido fechado");
        }

        Produto produto = dadosDoSistema.getProdutos().get(idDoProduto);

        if (produto == null || produto.getIdDaEmpresa() != pedido.getIdDaEmpresa()) {
            throw new MyFoodException("O produto nao pertence a essa empresa");
        }

        pedido.getIdsDosProdutos().add(idDoProduto);
    }

    public String getPedidos(int numeroDoPedido, String atributo) throws Exception {
        if (atributo == null || atributo.trim().isEmpty()) {
            throw new MyFoodException("Atributo invalido");
        }

        Pedido pedido = dadosDoSistema.getPedidos().get(numeroDoPedido);

        if (pedido == null) {
            throw new MyFoodException("Pedido nao encontrado");
        }

        if ("cliente".equals(atributo)) {
            return dadosDoSistema.getUsuarios().get(pedido.getIdDoCliente()).getNome();
        }

        if ("empresa".equals(atributo)) {
            return dadosDoSistema.getEmpresas().get(pedido.getIdDaEmpresa()).getNome();
        }

        if ("estado".equals(atributo)) {
            return pedido.getEstado();
        }

        if ("produtos".equals(atributo)) {
            List<String> nomesDosProdutos = new ArrayList<>();

            for (Integer idDoProduto : pedido.getIdsDosProdutos()) {
                nomesDosProdutos.add(dadosDoSistema.getProdutos().get(idDoProduto).getNome());
            }

            return "{[" + String.join(", ", nomesDosProdutos) + "]}";
        }

        if ("valor".equals(atributo)) {
            double valorTotal = 0.0;

            for (Integer idDoProduto : pedido.getIdsDosProdutos()) {
                valorTotal += dadosDoSistema.getProdutos().get(idDoProduto).getValor();
            }

            return String.format(Locale.US, "%.2f", valorTotal);
        }

        throw new MyFoodException("Atributo nao existe");
    }

    public void fecharPedido(int numeroDoPedido) throws Exception {
        Pedido pedido = dadosDoSistema.getPedidos().get(numeroDoPedido);

        if (pedido == null) {
            throw new MyFoodException("Pedido nao encontrado");
        }

        pedido.setEstado("preparando");
    }

    public void liberarPedido(int numeroDoPedido) throws Exception {
        Pedido pedido = dadosDoSistema.getPedidos().get(numeroDoPedido);

        if (pedido == null) {
            throw new MyFoodException("Pedido nao encontrado");
        }

        if ("pronto".equals(pedido.getEstado())) {
            throw new MyFoodException("Pedido ja liberado");
        }

        if (!"preparando".equals(pedido.getEstado())) {
            throw new MyFoodException("Nao e possivel liberar um produto que nao esta sendo preparado");
        }

        pedido.setEstado("pronto");
    }

    public void removerProduto(int numeroDoPedido, String nomeDoProduto) throws Exception {
        if (nomeDoProduto == null || nomeDoProduto.trim().isEmpty()) {
            throw new MyFoodException("Produto invalido");
        }

        Pedido pedido = dadosDoSistema.getPedidos().get(numeroDoPedido);

        if (pedido == null) {
            throw new MyFoodException("Pedido nao encontrado");
        }

        if (!"aberto".equals(pedido.getEstado())) {
            throw new MyFoodException("Nao e possivel remover produtos de um pedido fechado");
        }

        for (int indice = 0; indice < pedido.getIdsDosProdutos().size(); indice++) {
            int idDoProduto = pedido.getIdsDosProdutos().get(indice);
            Produto produto = dadosDoSistema.getProdutos().get(idDoProduto);

            if (produto.getNome().equals(nomeDoProduto)) {
                pedido.getIdsDosProdutos().remove(indice);
                return;
            }
        }

        throw new MyFoodException("Produto nao encontrado");
    }

    public int getNumeroPedido(int idDoCliente, int idDaEmpresa, int indice) {
        List<Pedido> pedidosEncontrados = new ArrayList<>();

        for (Pedido pedido : dadosDoSistema.getPedidos().values()) {
            if (pedido.getIdDoCliente() == idDoCliente && pedido.getIdDaEmpresa() == idDaEmpresa) {
                pedidosEncontrados.add(pedido);
            }
        }

        return pedidosEncontrados.get(indice).getNumero();
    }
}