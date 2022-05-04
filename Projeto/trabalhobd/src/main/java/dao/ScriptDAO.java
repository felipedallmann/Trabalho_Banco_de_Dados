package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import model.Script;

public interface ScriptDAO extends DAO<Script> {
    public void delete(String lojaNome, Date dataInsercao) throws SQLException;

    public Script read(String lojaNome, Date dataInsercao) throws SQLException;

    public List<Script> all(String lojaNome) throws SQLException;

    public void run(String lojaNome, Timestamp dataInsercao) throws SQLException;
}
