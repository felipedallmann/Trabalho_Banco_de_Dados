package model;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author olavo
 */
public class Ingrediente {
    @SerializedName("Ingredientes")     
    private String nome;

    public Ingrediente() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Ingrediente{" + "nome=" + nome + '}';
    }
}
