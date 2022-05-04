
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Destilaria;
import model.PaisDeOrigem;

public class PaisDeOrigemDAO implements DAO<PaisDeOrigem> {
    private final Connection connection;

    private static final String CREATE_QUERY = "INSERT INTO projetobd.pais_origem(nome) " +
            "VALUES(?);";

    private static final String READ_QUERY = "SELECT url, nome " +
            "FROM projetobd.loja " +
            "WHERE nome = ?;";

    private static final String UPDATE_QUERY = "UPDATE projetobd.loja " +
            "SET url = ?" +
            "WHERE nome = ?;";

    private static final String DELETE_QUERY = "DELETE FROM projetobd.loja " +
            "WHERE nome = ?;";

    private static final String ALL_QUERY = "SELECT nome, url " +
            "FROM projetobd.loja " +
            "ORDER BY nome;";

    private static final String GET_BY_NAME_QUERY = "SELECT nome, url" +
            "FROM projetobd.loja " +
            "WHERE nome = ?;";

    public PaisDeOrigemDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(PaisDeOrigem pais) throws SQLException {
        if (pais.getNome() == null || pais.getNome().isBlank()) {
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            System.out.println("Criando novo registro de pais: " + pais);
            statement.setString(1, pais.getNome());
            statement.executeUpdate();
        } catch (SQLException ex) {
            // if (ex.getMessage().contains("pk_destilaria")) {
            // throw new SQLException("Erro ao inserir destilaria: nome já existente.");
            // } else
            if (ex.getMessage().contains("not-null")) {
                Logger.getLogger(WhiskyDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
                throw new SQLException("Erro ao inserir pais: pelo menos um campo está em branco.");
            }
            // else {
            // throw new SQLException("Erro ao inserir destilaria.");
            // }
        }
    }

    @Override
    public PaisDeOrigem read(String arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(PaisDeOrigem arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(String arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<PaisDeOrigem> all() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
