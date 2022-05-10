package dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import model.Script;

public interface ScriptDAO extends DAO<Script> {
    public void delete(String lojaNome, Timestamp dataInsercao) throws SQLException;

    public Script read(String lojaNome, Timestamp dataInsercao) throws SQLException;

    public List<Script> all(String lojaNome) throws SQLException;

    public void run(Script script) throws SQLException;
}
