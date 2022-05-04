package model;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author olavo
 */
public class PaisDeOrigem {
    @SerializedName("Origem") 
    private String nome;

    public PaisDeOrigem() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "PaisDeOrigem{" + "nome=" + nome + '}';
    }
}
