package dao;

import java.sql.SQLException;
import model.Loja;

public interface LojaDAO extends DAO<Loja> {
    public Loja getByName(String name) throws SQLException;
}
