package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.constraints.Null;

import model.Whisky;

public class WhiskyDAO implements DAO<Whisky> {

    private final Connection connection;

    private static final String CREATE_QUERY = "INSERT INTO projetobd.whisky(nome, idade, teor_alcolico, destilaria_nome, pais_origem_nome) "
            +
            "VALUES(?, ?, ?, ?, ?) RETURNING id;";

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

    public WhiskyDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Whisky whisky) throws SQLException {
        if (whisky.getNome() == null || whisky.getNome().isBlank()) {
            Logger.getLogger(WhiskyDAO.class.getName()).log(Level.WARNING,
                    "Whisky não criado já que possui nome nulo!");
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            System.out.println("Criando novo registro de whisky: " + whisky);
            statement.setString(1, whisky.getNome());
            statement.setString(2, whisky.getIdade());
            statement.setString(3, whisky.getTeorAlcolico());
            statement.setString(4, whisky.getestilariaNome());
            statement.setString(5, whisky.getPaisOrigemNome());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            rs.next();
            whisky.setId(rs.getInt("id"));
        } catch (SQLException ex) {
            Logger.getLogger(WhiskyDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("uq_website_nome")) {
                throw new SQLException("Erro ao inserir site: nome já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir site: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir site.");
            }
        }
    }

    @Override
    public Whisky read(String arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Whisky arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(String arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Whisky> all() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
