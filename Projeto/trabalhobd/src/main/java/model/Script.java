package model;

import java.sql.Timestamp;

public class Script {
    private String lojaNome;
    private Timestamp dataInsercao;
    private String codigo;

    public Script() {
    }

    public Script(String lojaNome, Timestamp dataInsercao, String codigo) {
        this.lojaNome = lojaNome;
        this.dataInsercao = dataInsercao;
        this.codigo = codigo;
    }

    public String getLojaNome() {
        return lojaNome;
    }

    public void setLojaNome(String lojaNome) {
        this.lojaNome = lojaNome;
    }

    public Timestamp getDataInsercao() {
        return dataInsercao;
    }

    public void setDataInsercao(Timestamp dataInsercao) {
        this.dataInsercao = dataInsercao;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "Script [codigo=" + codigo + ", dataInsercao=" + dataInsercao + ", lojaNome=" + lojaNome + "]";
    }

}
