
package dao;

import java.sql.Connection;

public class PgDAOFactory extends DAOFactory {

    public PgDAOFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public LojaDAO getLojaDAO() {
        return new PgLojaDAO(this.connection);
    }

    @Override
    public PgScriptDAO getScriptDAO() {
        return new PgScriptDAO(this.connection);
    }

    @Override
    public IngredienteDAO getIngredienteDAO() {
        return new IngredienteDAO(this.connection);
    }

    @Override
    public DestilariaDAO getDestilariaDAO() {
        return new DestilariaDAO(this.connection);
    }

    @Override
    public WhiskyDAO getWhiskyDAO() {
        return new WhiskyDAO(this.connection);
    }

    @Override
    public PaisDeOrigemDAO getPaisDeOrigemDAO() {
        return new PaisDeOrigemDAO(this.connection);
    }

    @Override
    public HistoricoDAO getHistoricoDAO() {
        return new HistoricoDAO(this.connection);
    }
}
