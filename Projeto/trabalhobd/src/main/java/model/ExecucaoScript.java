package model;

import java.sql.Timestamp;

public class ExecucaoScript {
    private Script script;
    private Timestamp dataExecucao;

    public ExecucaoScript(Script script, Timestamp dataExecucao) {
        this.script = script;
        this.dataExecucao = dataExecucao;
    }

    @Override
    public String toString() {
        return "ExecucaoScript{" + "script=" + script + ", dataExecucao=" + dataExecucao + '}';
    }

    public Script getScript() {
        return script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public Timestamp getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(Timestamp dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

}
