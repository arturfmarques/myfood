package br.ufal.ic.myfood.services;

import br.ufal.ic.myfood.models.DadosSistema;

import java.io.*;

public class PersistenciaService {
    private static final String PASTA = "dados";
    private static final String ARQUIVO = PASTA + File.separator + "myfood.dat";

    public DadosSistema carregar() {
        File arquivo = new File(ARQUIVO);

        if (!arquivo.exists()) {
            return new DadosSistema();
        }

        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (DadosSistema) entrada.readObject();
        } catch (Exception e) {
            return new DadosSistema();
        }
    }

    public void salvar(DadosSistema dados) {
        try {
            File pasta = new File(PASTA);
            if (!pasta.exists()) {
                pasta.mkdirs();
            }

            try (ObjectOutputStream saida = new ObjectOutputStream(new FileOutputStream(ARQUIVO))) {
                saida.writeObject(dados);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void apagar() {
        File arquivo = new File(ARQUIVO);
        if (arquivo.exists()) {
            arquivo.delete();
        }
    }
}