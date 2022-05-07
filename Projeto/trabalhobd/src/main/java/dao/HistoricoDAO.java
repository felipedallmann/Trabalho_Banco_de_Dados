package dao;

import java.sql.SQLException;
import java.util.List;
import model.Historico;

public interface HistoricoDAO extends DAO<Historico> {
    @Override
    public void create(Historico historico) throws SQLException;

    @Override
    public void delete(String arg0) throws SQLException;

    @Override
    public void update(Historico arg0) throws SQLException;

    @Override
    public Historico read(String arg0) throws SQLException;

    public List<Historico> all() throws SQLException;

}
