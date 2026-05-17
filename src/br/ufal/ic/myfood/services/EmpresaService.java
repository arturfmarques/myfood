package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.MyFoodException;
import br.ufal.ic.myfood.models.*;

import java.util.ArrayList;
import java.util.List;

public class EmpresaService {
    private DadosSistema dadosDoSistema;

    public EmpresaService(DadosSistema dadosDoSistema) {
        this.dadosDoSistema = dadosDoSistema;
    }

    public int criarEmpresa(String tipoEmpresa, int idDoDono, String nome, String endereco, String tipoCozinha) throws Exception {
        validarTipoEmpresa(tipoEmpresa, "restaurante");
        validarNome(nome);
        validarEndereco(endereco);
        validarDono(idDoDono);
        validarDuplicidade(idDoDono, nome, endereco);

        int id = dadosDoSistema.getProximoIdDeEmpresa();
        Restaurante restaurante = new Restaurante(id, nome, endereco, idDoDono, tipoCozinha);
        dadosDoSistema.getEmpresas().put(id, restaurante);
        dadosDoSistema.setProximoIdDeEmpresa(id + 1);
        return id;
    }

    public int criarEmpresa(String tipoEmpresa, int idDoDono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws Exception {
        validarTipoEmpresa(tipoEmpresa, "mercado");
        validarNome(nome);
        validarEndereco(endereco);
        validarDono(idDoDono);
        validarHorarioCadastro(abre, fecha);
        validarTipoMercado(tipoMercado);
        validarDuplicidade(idDoDono, nome, endereco);

        int id = dadosDoSistema.getProximoIdDeEmpresa();
        Mercado mercado = new Mercado(id, nome, endereco, idDoDono, abre, fecha, tipoMercado);
        dadosDoSistema.getEmpresas().put(id, mercado);
        dadosDoSistema.setProximoIdDeEmpresa(id + 1);
        return id;
    }

    public int criarEmpresa(String tipoEmpresa, int idDoDono, String nome, String endereco, boolean aberto24Horas, int numeroDeFuncionarios) throws Exception {
        validarTipoEmpresa(tipoEmpresa, "farmacia");
        validarNome(nome);
        validarEndereco(endereco);
        validarDono(idDoDono);
        validarDuplicidade(idDoDono, nome, endereco);

        int id = dadosDoSistema.getProximoIdDeEmpresa();
        Farmacia farmacia = new Farmacia(id, nome, endereco, idDoDono, aberto24Horas, numeroDeFuncionarios);
        dadosDoSistema.getEmpresas().put(id, farmacia);
        dadosDoSistema.setProximoIdDeEmpresa(id + 1);
        return id;
    }

    public void alterarFuncionamento(int idDoMercado, String abre, String fecha) throws Exception {
        Empresa empresa = dadosDoSistema.getEmpresas().get(idDoMercado);

        if (!(empresa instanceof Mercado mercado)) {
            throw new MyFoodException("Nao e um mercado valido");
        }

        validarHorarioAlteracao(abre, fecha);

        mercado.setAbre(abre);
        mercado.setFecha(fecha);
    }

    public void cadastrarEntregador(int idDaEmpresa, int idDoEntregador) throws Exception {
        Empresa empresa = dadosDoSistema.getEmpresas().get(idDaEmpresa);
        Usuario usuario = dadosDoSistema.getUsuarios().get(idDoEntregador);

        if (!(usuario instanceof Entregador entregador)) {
            throw new MyFoodException("Usuario nao e um entregador");
        }

        if (!empresa.getIdsDosEntregadores().contains(idDoEntregador)) {
            empresa.getIdsDosEntregadores().add(idDoEntregador);
        }

        if (!entregador.getIdsDasEmpresas().contains(idDaEmpresa)) {
            entregador.getIdsDasEmpresas().add(idDaEmpresa);
        }
    }

    public String getEntregadores(int idDaEmpresa) {
        Empresa empresa = dadosDoSistema.getEmpresas().get(idDaEmpresa);

        List<String> emailsDosEntregadores = new ArrayList<>();

        for (Integer idDoEntregador : empresa.getIdsDosEntregadores()) {
            Entregador entregador = (Entregador) dadosDoSistema.getUsuarios().get(idDoEntregador);
            emailsDosEntregadores.add(entregador.getEmail());
        }

        return "{[" + String.join(", ", emailsDosEntregadores) + "]}";
    }

    public String getEmpresas(int idDoEntregador) throws Exception {
        Usuario usuario = dadosDoSistema.getUsuarios().get(idDoEntregador);

        if (!(usuario instanceof Entregador entregador)) {
            throw new MyFoodException("Usuario nao e um entregador");
        }

        List<String> empresasDoEntregador = new ArrayList<>();

        for (Integer idDaEmpresa : entregador.getIdsDasEmpresas()) {
            Empresa empresa = dadosDoSistema.getEmpresas().get(idDaEmpresa);
            empresasDoEntregador.add("[" + empresa.getNome() + ", " + empresa.getEndereco() + "]");
        }

        return "{[" + String.join(", ", empresasDoEntregador) + "]}";
    }

    public int quantidadeEmpresasDoEntregador(int idDoEntregador) throws Exception {
        Usuario usuario = dadosDoSistema.getUsuarios().get(idDoEntregador);

        if (!(usuario instanceof Entregador entregador)) {
            throw new MyFoodException("Usuario nao e um entregador");
        }

        return entregador.getIdsDasEmpresas().size();
    }

    public String getEmpresasDoUsuario(int idDoDono) throws Exception {
        Usuario usuario = dadosDoSistema.getUsuarios().get(idDoDono);

        if (usuario == null || !usuario.ehDono()) {
            throw new MyFoodException("Usuario nao pode criar uma empresa");
        }

        List<String> empresasDoDono = new ArrayList<>();

        for (Empresa empresa : dadosDoSistema.getEmpresas().values()) {
            if (empresa.getIdDoDono() == idDoDono) {
                empresasDoDono.add("[" + empresa.getNome() + ", " + empresa.getEndereco() + "]");
            }
        }

        return "{[" + String.join(", ", empresasDoDono) + "]}";
    }

    public int getIdEmpresa(int idDoDono, String nome, int indice) throws Exception {
        if (textoVazio(nome)) {
            throw new MyFoodException("Nome invalido");
        }

        if (indice < 0) {
            throw new MyFoodException("Indice invalido");
        }

        List<Empresa> empresasEncontradas = new ArrayList<>();

        for (Empresa empresa : dadosDoSistema.getEmpresas().values()) {
            if (empresa.getIdDoDono() == idDoDono && empresa.getNome().equals(nome)) {
                empresasEncontradas.add(empresa);
            }
        }

        if (empresasEncontradas.isEmpty()) {
            throw new MyFoodException("Nao existe empresa com esse nome");
        }

        if (indice >= empresasEncontradas.size()) {
            throw new MyFoodException("Indice maior que o esperado");
        }

        return empresasEncontradas.get(indice).getId();
    }

    public String getAtributoEmpresa(int idDaEmpresa, String atributo) throws Exception {
        Empresa empresa = dadosDoSistema.getEmpresas().get(idDaEmpresa);

        if (empresa == null) {
            throw new MyFoodException("Empresa nao cadastrada");
        }

        if (atributo == null || atributo.trim().isEmpty()) {
            throw new MyFoodException("Atributo invalido");
        }

        if ("nome".equals(atributo)) return empresa.getNome();
        if ("endereco".equals(atributo)) return empresa.getEndereco();
        if ("dono".equals(atributo)) return dadosDoSistema.getUsuarios().get(empresa.getIdDoDono()).getNome();

        if (empresa instanceof Restaurante restaurante) {
            if ("tipoCozinha".equals(atributo)) return restaurante.getTipoCozinha();
        }

        if (empresa instanceof Mercado mercado) {
            if ("abre".equals(atributo)) return mercado.getAbre();
            if ("fecha".equals(atributo)) return mercado.getFecha();
            if ("tipoMercado".equals(atributo)) return mercado.getTipoMercado();
        }

        if (empresa instanceof Farmacia farmacia) {
            if ("aberto24Horas".equals(atributo)) return String.valueOf(farmacia.isAberto24Horas());
            if ("numeroFuncionarios".equals(atributo)) return String.valueOf(farmacia.getNumeroDeFuncionarios());
        }

        throw new MyFoodException("Atributo invalido");
    }

    private void validarTipoEmpresa(String tipoEmpresa, String tipoEsperado) throws Exception {
        if (textoVazio(tipoEmpresa) || !tipoEmpresa.equals(tipoEsperado)) {
            throw new MyFoodException("Tipo de empresa invalido");
        }
    }

    private void validarNome(String nome) throws Exception {
        if (textoVazio(nome)) {
            throw new MyFoodException("Nome invalido");
        }
    }

    private void validarEndereco(String endereco) throws Exception {
        if (textoVazio(endereco)) {
            throw new MyFoodException("Endereco da empresa invalido");
        }
    }

    private void validarDono(int idDoDono) throws Exception {
        Usuario usuario = dadosDoSistema.getUsuarios().get(idDoDono);

        if (usuario == null || !usuario.ehDono()) {
            throw new MyFoodException("Usuario nao pode criar uma empresa");
        }
    }

    private void validarDuplicidade(int idDoDono, String nome, String endereco) throws Exception {
        for (Empresa empresa : dadosDoSistema.getEmpresas().values()) {
            if (empresa.getNome().equals(nome)) {
                if (empresa.getIdDoDono() != idDoDono) {
                    throw new MyFoodException("Empresa com esse nome ja existe");
                }

                if (empresa.getEndereco().equals(endereco)) {
                    throw new MyFoodException("Proibido cadastrar duas empresas com o mesmo nome e local");
                }
            }
        }
    }

    private void validarTipoMercado(String tipoMercado) throws Exception {
        if (textoVazio(tipoMercado)) {
            throw new MyFoodException("Tipo de mercado invalido");
        }

        boolean tipoValido =
                tipoMercado.equals("supermercado") ||
                        tipoMercado.equals("minimercado") ||
                        tipoMercado.equals("atacadista");

        if (!tipoValido) {
            throw new MyFoodException("Tipo de mercado invalido");
        }
    }

    private void validarHorarioCadastro(String abre, String fecha) throws Exception {
        if (abre == null || fecha == null) {
            throw new MyFoodException("Horario invalido");
        }

        if (!formatoDeHoraValido(abre) || !formatoDeHoraValido(fecha)) {
            throw new MyFoodException("Formato de hora invalido");
        }

        if (!intervaloDeHoraValido(abre, fecha)) {
            throw new MyFoodException("Horario invalido");
        }
    }

    private void validarHorarioAlteracao(String abre, String fecha) throws Exception {
        if (abre == null || fecha == null || textoVazio(abre) || textoVazio(fecha)) {
            throw new MyFoodException("Horario invalido");
        }

        if (!formatoDeHoraValido(abre) || !formatoDeHoraValido(fecha)) {
            throw new MyFoodException("Formato de hora invalido");
        }

        if (!intervaloDeHoraValido(abre, fecha)) {
            throw new MyFoodException("Horario invalido");
        }
    }

    private boolean formatoDeHoraValido(String hora) {
        return hora.matches("\\d{2}:\\d{2}");
    }

    private boolean intervaloDeHoraValido(String abre, String fecha) {
        int horaDeAbertura = Integer.parseInt(abre.substring(0, 2));
        int minutoDeAbertura = Integer.parseInt(abre.substring(3, 5));

        int horaDeFechamento = Integer.parseInt(fecha.substring(0, 2));
        int minutoDeFechamento = Integer.parseInt(fecha.substring(3, 5));

        if (horaDeAbertura < 0 || horaDeAbertura > 23) return false;
        if (minutoDeAbertura < 0 || minutoDeAbertura > 59) return false;
        if (horaDeFechamento < 0 || horaDeFechamento > 23) return false;
        if (minutoDeFechamento < 0 || minutoDeFechamento > 59) return false;

        int totalDeAbertura = horaDeAbertura * 60 + minutoDeAbertura;
        int totalDeFechamento = horaDeFechamento * 60 + minutoDeFechamento;

        return totalDeFechamento > totalDeAbertura;
    }

    private boolean textoVazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}