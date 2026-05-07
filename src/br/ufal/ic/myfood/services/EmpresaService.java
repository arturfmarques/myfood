package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.exceptions.MyFoodException;
import br.ufal.ic.myfood.models.*;

import java.util.ArrayList;
import java.util.List;

public class EmpresaService {
    private DadosSistema dados;

    public EmpresaService(DadosSistema dados) {
        this.dados = dados;
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String tipoCozinha) throws Exception {
        validarTipoEmpresa(tipoEmpresa, "restaurante");
        validarNome(nome);
        validarEndereco(endereco);
        validarDono(dono);
        validarDuplicidade(dono, nome, endereco);

        int id = dados.getProximoIdEmpresa();
        Restaurante restaurante = new Restaurante(id, nome, endereco, dono, tipoCozinha);
        dados.getEmpresas().put(id, restaurante);
        dados.setProximoIdEmpresa(id + 1);
        return id;
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, String abre, String fecha, String tipoMercado) throws Exception {
        validarTipoEmpresa(tipoEmpresa, "mercado");
        validarNome(nome);
        validarEndereco(endereco);
        validarDono(dono);
        validarHorarioCadastro(abre, fecha);
        validarTipoMercado(tipoMercado);
        validarDuplicidade(dono, nome, endereco);

        int id = dados.getProximoIdEmpresa();
        Mercado mercado = new Mercado(id, nome, endereco, dono, abre, fecha, tipoMercado);
        dados.getEmpresas().put(id, mercado);
        dados.setProximoIdEmpresa(id + 1);
        return id;
    }

    public int criarEmpresa(String tipoEmpresa, int dono, String nome, String endereco, boolean aberto24Horas, int numeroFuncionarios) throws Exception {
        validarTipoEmpresa(tipoEmpresa, "farmacia");
        validarNome(nome);
        validarEndereco(endereco);
        validarDono(dono);
        validarDuplicidade(dono, nome, endereco);

        int id = dados.getProximoIdEmpresa();
        Farmacia farmacia = new Farmacia(id, nome, endereco, dono, aberto24Horas, numeroFuncionarios);
        dados.getEmpresas().put(id, farmacia);
        dados.setProximoIdEmpresa(id + 1);
        return id;
    }

    public void alterarFuncionamento(int mercadoId, String abre, String fecha) throws Exception {
        Empresa empresa = dados.getEmpresas().get(mercadoId);

        if (!(empresa instanceof Mercado)) {
            throw new MyFoodException("Nao e um mercado valido");
        }

        validarHorarioAlteracao(abre, fecha);

        Mercado mercado = (Mercado) empresa;
        mercado.setAbre(abre);
        mercado.setFecha(fecha);
    }

    public String getEmpresasDoUsuario(int idDono) throws Exception {
        Usuario usuario = dados.getUsuarios().get(idDono);

        if (usuario == null || !usuario.ehDono()) {
            throw new MyFoodException("Usuario nao pode criar uma empresa");
        }

        List<String> lista = new ArrayList<>();

        for (Empresa empresa : dados.getEmpresas().values()) {
            if (empresa.getIdDono() == idDono) {
                lista.add("[" + empresa.getNome() + ", " + empresa.getEndereco() + "]");
            }
        }

        return "{[" + String.join(", ", lista) + "]}";
    }

    public int getIdEmpresa(int idDono, String nome, int indice) throws Exception {
        if (textoVazio(nome)) {
            throw new MyFoodException("Nome invalido");
        }

        if (indice < 0) {
            throw new MyFoodException("Indice invalido");
        }

        List<Empresa> lista = new ArrayList<>();

        for (Empresa empresa : dados.getEmpresas().values()) {
            if (empresa.getIdDono() == idDono && empresa.getNome().equals(nome)) {
                lista.add(empresa);
            }
        }

        if (lista.isEmpty()) {
            throw new MyFoodException("Nao existe empresa com esse nome");
        }

        if (indice >= lista.size()) {
            throw new MyFoodException("Indice maior que o esperado");
        }

        return lista.get(indice).getId();
    }

    public String getAtributoEmpresa(int idEmpresa, String atributo) throws Exception {
        Empresa empresa = dados.getEmpresas().get(idEmpresa);

        if (empresa == null) {
            throw new MyFoodException("Empresa nao cadastrada");
        }

        if (atributo == null || atributo.trim().isEmpty()) {
            throw new MyFoodException("Atributo invalido");
        }

        if ("nome".equals(atributo)) return empresa.getNome();
        if ("endereco".equals(atributo)) return empresa.getEndereco();
        if ("dono".equals(atributo)) return dados.getUsuarios().get(empresa.getIdDono()).getNome();

        if (empresa instanceof Restaurante) {
            Restaurante restaurante = (Restaurante) empresa;
            if ("tipoCozinha".equals(atributo)) return restaurante.getTipoCozinha();
        }

        if (empresa instanceof Mercado) {
            Mercado mercado = (Mercado) empresa;
            if ("abre".equals(atributo)) return mercado.getAbre();
            if ("fecha".equals(atributo)) return mercado.getFecha();
            if ("tipoMercado".equals(atributo)) return mercado.getTipoMercado();
        }

        if (empresa instanceof Farmacia) {
            Farmacia farmacia = (Farmacia) empresa;
            if ("aberto24Horas".equals(atributo)) return String.valueOf(farmacia.isAberto24Horas());
            if ("numeroFuncionarios".equals(atributo)) return String.valueOf(farmacia.getNumeroFuncionarios());
        }

        throw new MyFoodException("Atributo invalido");
    }

    private void validarTipoEmpresa(String tipoEmpresa, String esperado) throws Exception {
        if (textoVazio(tipoEmpresa) || !tipoEmpresa.equals(esperado)) {
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

    private void validarDono(int dono) throws Exception {
        Usuario usuario = dados.getUsuarios().get(dono);

        if (usuario == null || !usuario.ehDono()) {
            throw new MyFoodException("Usuario nao pode criar uma empresa");
        }
    }

    private void validarDuplicidade(int dono, String nome, String endereco) throws Exception {
        for (Empresa empresa : dados.getEmpresas().values()) {
            if (empresa.getNome().equals(nome)) {
                if (empresa.getIdDono() != dono) {
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

        boolean valido =
                tipoMercado.equals("supermercado") ||
                        tipoMercado.equals("minimercado") ||
                        tipoMercado.equals("atacadista");

        if (!valido) {
            throw new MyFoodException("Tipo de mercado invalido");
        }
    }

    private void validarHorarioCadastro(String abre, String fecha) throws Exception {
        if (abre == null || fecha == null) {
            throw new MyFoodException("Horario invalido");
        }

        if (!formatoHoraValido(abre) || !formatoHoraValido(fecha)) {
            throw new MyFoodException("Formato de hora invalido");
        }

        if (!intervaloHoraValido(abre, fecha)) {
            throw new MyFoodException("Horario invalido");
        }
    }

    private void validarHorarioAlteracao(String abre, String fecha) throws Exception {
        if (abre == null || fecha == null || textoVazio(abre) || textoVazio(fecha)) {
            throw new MyFoodException("Horario invalido");
        }

        if (!formatoHoraValido(abre) || !formatoHoraValido(fecha)) {
            throw new MyFoodException("Formato de hora invalido");
        }

        if (!intervaloHoraValido(abre, fecha)) {
            throw new MyFoodException("Horario invalido");
        }
    }

    private boolean formatoHoraValido(String hora) {
        return hora.matches("\\d{2}:\\d{2}");
    }

    private boolean intervaloHoraValido(String abre, String fecha) {
        int horaAbre = Integer.parseInt(abre.substring(0, 2));
        int minutoAbre = Integer.parseInt(abre.substring(3, 5));

        int horaFecha = Integer.parseInt(fecha.substring(0, 2));
        int minutoFecha = Integer.parseInt(fecha.substring(3, 5));

        if (horaAbre < 0 || horaAbre > 23) return false;
        if (minutoAbre < 0 || minutoAbre > 59) return false;
        if (horaFecha < 0 || horaFecha > 23) return false;
        if (minutoFecha < 0 || minutoFecha > 59) return false;

        int totalAbre = horaAbre * 60 + minutoAbre;
        int totalFecha = horaFecha * 60 + minutoFecha;

        return totalFecha > totalAbre;
    }

    private boolean textoVazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }
}