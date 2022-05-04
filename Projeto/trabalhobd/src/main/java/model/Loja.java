package model;

import com.google.gson.annotations.SerializedName;

public class Loja {
    @SerializedName("Fabricante")
    private String nome;
    private String URL;
    private transient int itensVendidos;
    private transient int precoMedio;

    public int getItensVendidos() {
        return itensVendidos;
    }

    public void setItensVendidos(int itensVendidos) {
        this.itensVendidos = itensVendidos;
    }

    public int getPrecoMedio() {
        return precoMedio;
    }

    public void setPrecoMedio(int precoMedio) {
        this.precoMedio = precoMedio;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    @Override
    public String toString() {
        return "Loja{" + "nome=" + nome + ", URL=" + URL + ", itensVendidos=" + itensVendidos + ", precoMedio="
                + precoMedio + '}';
    }
}
