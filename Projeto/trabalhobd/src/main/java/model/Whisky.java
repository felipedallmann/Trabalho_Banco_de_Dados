package model;

import com.google.gson.annotations.SerializedName;
import java.sql.Date;

public class Whisky {
    private transient int id;
    private transient String nome;
    private transient String idade;
    @SerializedName("Teor alcoólico")
    private String teorAlcolico;
    private transient String destilariaNome;
    private transient String paisOrigemNome;

    public Whisky() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdade() {
        return idade;
    }

    public void setIdade(String idade) {
        this.idade = idade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTeorAlcolico() {
        return teorAlcolico;
    }

    public void setTeorAlcolico(String teorAlcolico) {
        this.teorAlcolico = teorAlcolico;
    }

    public String getestilariaNome() {
        return destilariaNome;
    }

    public void setestilariaNome(String destilariaNome) {
        this.destilariaNome = destilariaNome;
    }

    public String getPaisOrigemNome() {
        return paisOrigemNome;
    }

    public void setPaisOrigemNome(String paisOrigemNome) {
        this.paisOrigemNome = paisOrigemNome;
    }

    @Override
    public String toString() {
        return "Whisky{" + "id=" + id + ", idade=" + idade + ", nome=" + nome + ", teor_alcolico=" + teorAlcolico
                + ", destilaria_nome=" + destilariaNome + ", pais_origem_nome=" + paisOrigemNome + '}';
    }
}
