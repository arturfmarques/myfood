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
    private DadosSistema dados;

    public PedidoService(DadosSistema dados) {
        this.dados = dados;
    }

    public int criarPedido(int idCliente, int idEmpresa) throws Exception {
        Usuario usuario = dados.getUsuarios().get(idCliente);

        if (usuario == null || usuario.ehDono() || usuario.ehEntregador()) {
            throw new MyFoodException("Dono de empresa nao pode fazer um pedido");
        }

        for (Pedido pedido : dados.getPedidos().values()) {
            if (pedido.getIdCliente() == idCliente &&
                    pedido.getIdEmpresa() == idEmpresa &&
                    "aberto".equals(pedido.getEstado())) {
                throw new MyFoodException("Nao e permitido ter dois pedidos em aberto para a mesma empresa");
            }
        }

        int numero = dados.getProximoNumeroPedido();
        Pedido pedido = new Pedido(numero, idCliente, idEmpresa);
        dados.getPedidos().put(numero, pedido);
        dados.setProximoNumeroPedido(numero + 1);

        return numero;
    }

    public void adicionarProduto(int numeroPedido, int idProduto) throws Exception {
        Pedido pedido = dados.getPedidos().get(numeroPedido);

        if (pedido == null) {
            throw new MyFoodException("Nao existe pedido em aberto");
        }

        if (!"aberto".equals(pedido.getEstado())) {
            throw new MyFoodException("Nao e possivel adcionar produtos a um pedido fechado");
        }

        Produto produto = dados.getProdutos().get(idProduto);

        if (produto == null || produto.getIdEmpresa() != pedido.getIdEmpresa()) {
            throw new MyFoodException("O produto nao pertence a essa empresa");
        }

        pedido.getIdsProdutos().add(idProduto);
    }

    public String getPedidos(int numeroPedido, String atributo) throws Exception {
        if (atributo == null || atributo.trim().isEmpty()) {
            throw new MyFoodException("Atributo invalido");
        }

        Pedido pedido = dados.getPedidos().get(numeroPedido);

        if (pedido == null) {
            throw new MyFoodException("Pedido nao encontrado");
        }

        if ("cliente".equals(atributo)) {
            return dados.getUsuarios().get(pedido.getIdCliente()).getNome();
        }

        if ("empresa".equals(atributo)) {
            return dados.getEmpresas().get(pedido.getIdEmpresa()).getNome();
        }

        if ("estado".equals(atributo)) {
            return pedido.getEstado();
        }

        if ("produtos".equals(atributo)) {
            List<String> nomes = new ArrayList<>();

            for (Integer idProduto : pedido.getIdsProdutos()) {
                nomes.add(dados.getProdutos().get(idProduto).getNome());
            }

            return "{[" + String.join(", ", nomes) + "]}";
        }

        if ("valor".equals(atributo)) {
            double total = 0.0;

            for (Integer idProduto : pedido.getIdsProdutos()) {
                total += dados.getProdutos().get(idProduto).getValor();
            }

            return String.format(Locale.US, "%.2f", total);
        }

        throw new MyFoodException("Atributo nao existe");
    }

    public void fecharPedido(int numeroPedido) throws Exception {
        Pedido pedido = dados.getPedidos().get(numeroPedido);

        if (pedido == null) {
            throw new MyFoodException("Pedido nao encontrado");
        }

        pedido.setEstado("preparando");
    }

    public void liberarPedido(int numeroPedido) throws Exception {
        Pedido pedido = dados.getPedidos().get(numeroPedido);

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

    public void removerProduto(int numeroPedido, String nomeProduto) throws Exception {
        if (nomeProduto == null || nomeProduto.trim().isEmpty()) {
            throw new MyFoodException("Produto invalido");
        }

        Pedido pedido = dados.getPedidos().get(numeroPedido);

        if (pedido == null) {
            throw new MyFoodException("Pedido nao encontrado");
        }

        if (!"aberto".equals(pedido.getEstado())) {
            throw new MyFoodException("Nao e possivel remover produtos de um pedido fechado");
        }

        for (int i = 0; i < pedido.getIdsProdutos().size(); i++) {
            int idProduto = pedido.getIdsProdutos().get(i);
            Produto produto = dados.getProdutos().get(idProduto);

            if (produto.getNome().equals(nomeProduto)) {
                pedido.getIdsProdutos().remove(i);
                return;
            }
        }

        throw new MyFoodException("Produto nao encontrado");
    }

    public int getNumeroPedido(int idCliente, int idEmpresa, int indice) {
        List<Pedido> lista = new ArrayList<>();

        for (Pedido pedido : dados.getPedidos().values()) {
            if (pedido.getIdCliente() == idCliente && pedido.getIdEmpresa() == idEmpresa) {
                lista.add(pedido);
            }
        }

        return lista.get(indice).getNumero();
    }
}