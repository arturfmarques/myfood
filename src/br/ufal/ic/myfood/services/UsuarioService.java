package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.MyFoodException;
import br.ufal.ic.myfood.models.Cliente;
import br.ufal.ic.myfood.models.DadosSistema;
import br.ufal.ic.myfood.models.DonoEmpresa;
import br.ufal.ic.myfood.models.Entregador;
import br.ufal.ic.myfood.models.Usuario;

public class UsuarioService {
    private DadosSistema dadosDoSistema;

    public UsuarioService(DadosSistema dadosDoSistema) {
        this.dadosDoSistema = dadosDoSistema;
    }

    public void criarCliente(String nome, String email, String senha, String endereco) throws Exception {
        validarNome(nome);
        validarEmail(email);
        validarSenha(senha);
        validarEndereco(endereco);
        verificarEmailDuplicado(email);

        int id = dadosDoSistema.getProximoIdDeUsuario();
        Cliente cliente = new Cliente(id, nome, email, senha, endereco);
        dadosDoSistema.getUsuarios().put(id, cliente);
        dadosDoSistema.setProximoIdDeUsuario(id + 1);
    }

    public void criarDono(String nome, String email, String senha, String endereco, String cpf) throws Exception {
        validarNome(nome);
        validarEmail(email);
        validarSenha(senha);
        validarEndereco(endereco);
        validarCpf(cpf);
        verificarEmailDuplicado(email);

        int id = dadosDoSistema.getProximoIdDeUsuario();
        DonoEmpresa dono = new DonoEmpresa(id, nome, email, senha, endereco, cpf);
        dadosDoSistema.getUsuarios().put(id, dono);
        dadosDoSistema.setProximoIdDeUsuario(id + 1);
    }

    public void criarEntregador(String nome, String email, String senha, String endereco, String veiculo, String placa) throws Exception {
        validarNome(nome);
        validarEmail(email);
        validarSenha(senha);
        validarEndereco(endereco);
        validarVeiculo(veiculo);
        validarPlaca(placa);
        verificarPlacaDuplicada(placa);
        verificarEmailDuplicado(email);

        int id = dadosDoSistema.getProximoIdDeUsuario();
        Entregador entregador = new Entregador(id, nome, email, senha, endereco, veiculo, placa);
        dadosDoSistema.getUsuarios().put(id, entregador);
        dadosDoSistema.setProximoIdDeUsuario(id + 1);
    }

    public int login(String email, String senha) throws Exception {
        for (Usuario usuario : dadosDoSistema.getUsuarios().values()) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                return usuario.getId();
            }
        }

        throw new MyFoodException("Login ou senha invalidos");
    }

    public String getAtributoUsuario(int id, String atributo) throws Exception {
        Usuario usuario = dadosDoSistema.getUsuarios().get(id);

        if (usuario == null) {
            throw new MyFoodException("Usuario nao cadastrado.");
        }

        if ("nome".equals(atributo)) return usuario.getNome();
        if ("email".equals(atributo)) return usuario.getEmail();
        if ("senha".equals(atributo)) return usuario.getSenha();
        if ("endereco".equals(atributo)) return usuario.getEndereco();

        if ("cpf".equals(atributo) && usuario instanceof DonoEmpresa donoEmpresa) {
            return donoEmpresa.getCpf();
        }

        if ("veiculo".equals(atributo) && usuario instanceof Entregador entregador) {
            return entregador.getVeiculo();
        }

        if ("placa".equals(atributo) && usuario instanceof Entregador entregador) {
            return entregador.getPlaca();
        }

        return "";
    }

    private void verificarEmailDuplicado(String email) throws Exception {
        for (Usuario usuario : dadosDoSistema.getUsuarios().values()) {
            if (usuario.getEmail().equals(email)) {
                throw new MyFoodException("Conta com esse email ja existe");
            }
        }
    }

    private void verificarPlacaDuplicada(String placa) throws Exception {
        for (Usuario usuario : dadosDoSistema.getUsuarios().values()) {
            if (usuario instanceof Entregador entregador) {
                if (entregador.getPlaca().equals(placa)) {
                    throw new MyFoodException("Placa invalido");
                }
            }
        }
    }

    private void validarNome(String nome) throws Exception {
        if (textoVazio(nome)) {
            throw new MyFoodException("Nome invalido");
        }
    }

    private void validarEmail(String email) throws Exception {
        if (textoVazio(email) || !email.contains("@")) {
            throw new MyFoodException("Email invalido");
        }
    }

    private void validarSenha(String senha) throws Exception {
        if (textoVazio(senha)) {
            throw new MyFoodException("Senha invalido");
        }
    }

    private void validarEndereco(String endereco) throws Exception {
        if (textoVazio(endereco)) {
            throw new MyFoodException("Endereco invalido");
        }
    }

    private void validarCpf(String cpf) throws Exception {
        if (textoVazio(cpf) || cpf.length() != 14) {
            throw new MyFoodException("CPF invalido");
        }
    }

    private void validarVeiculo(String veiculo) throws Exception {
        if (textoVazio(veiculo)) {
            throw new MyFoodException("Veiculo invalido");
        }
    }

    private void validarPlaca(String placa) throws Exception {
        if (textoVazio(placa)) {
            throw new MyFoodException("Placa invalido");
        }
    }

    private boolean textoVazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}