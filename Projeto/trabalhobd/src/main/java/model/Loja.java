/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Usuario
 */
public class Loja {
    @SerializedName("Fabricante") private String nome;
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

 
    
    
}
