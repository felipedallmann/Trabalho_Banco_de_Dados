package model;

import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;

public class Historico {
    private transient int whiskyId;
    private transient String lojaNome;
    private transient Timestamp acessadoEm;
    @SerializedName("Preço sem oferta")
    private String precoSemDesconto;
    @SerializedName("Preço com oferta")
    private String precoComDesconto;

    public Historico() {
    }

    public int getWhiskyId() {
        return whiskyId;
    }

    public void setWhiskyId(int whiskyId) {
        this.whiskyId = whiskyId;
    }

    public String getLojaNome() {
        return lojaNome;
    }

    public void setLojaNome(String lojaNome) {
        this.lojaNome = lojaNome;
    }

    public Timestamp getAcessadoEm() {
        return acessadoEm;
    }

    public void setAcessadoEm(Timestamp acessadoEm) {
        this.acessadoEm = acessadoEm;
    }

    public String getPrecoSemDesconto() {
        return precoSemDesconto;
    }

    public void setPrecoSemDesconto(String precoSemDesconto) {
        this.precoSemDesconto = precoSemDesconto;
    }

    public String getPrecoComDesconto() {
        return precoComDesconto;
    }

    public void setPrecoComDesconto(String precoComDesconto) {
        this.precoComDesconto = precoComDesconto;
    }

    @Override
    public String toString() {
        return "Historico{" + "whiskyId=" + whiskyId + ", lojaNome=" + lojaNome + ", acessadoEm=" + acessadoEm
                + ", precoSemDesconto=" + precoSemDesconto + ", precoComDesconto=" + precoComDesconto + '}';
    }
}
