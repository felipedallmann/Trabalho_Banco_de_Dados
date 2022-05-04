
package model;

import com.google.gson.annotations.SerializedName;

public class Destilaria {
    @SerializedName("Fabricante")
    private String nome;
    private transient String paisOrigemNome;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Destilaria{" + "nome=" + nome + '}';
    }

    public String getPaisOrigemNome() {
        return paisOrigemNome;
    }

    public void setPaisOrigemNome(String paisOrigemNome) {
        this.paisOrigemNome = paisOrigemNome;
    }
}
