package model;

import java.sql.Timestamp;

/**
 *
 * @author olavo
 */
public class Script {
    private String lojaNome;
    private Timestamp dataInsercao;
    private String codigo;

    public Script() {
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
    
}
