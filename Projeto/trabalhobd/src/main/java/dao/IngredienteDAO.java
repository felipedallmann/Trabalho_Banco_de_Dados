package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Ingrediente;

public class IngredienteDAO implements DAO<Ingrediente> {

    private final Connection connection;

    private static final String CREATE_QUERY = "INSERT INTO projetobd.ingrediente(nome) " +
            "VALUES(?);";

    // private static final String READ_QUERY =
    // "SELECT url, nome " +
    // "FROM projetobd.loja " +
    // "WHERE nome = ?;";

    // private static final String UPDATE_QUERY =
    // "UPDATE projetobd.loja " +
    // "SET url = ?" +
    // "WHERE nome = ?;";

    // private static final String DELETE_QUERY =
    // "DELETE FROM projetobd.loja " +
    // "WHERE nome = ?;";

    // private static final String ALL_QUERY =
    // "SELECT nome, url " +
    // "FROM projetobd.loja " +
    // "ORDER BY nome;";

    // private static final String GET_BY_NAME_QUERY =
    // "SELECT nome, url" +
    // "FROM projetobd.loja " +
    // "WHERE nome = ?;";

    public IngredienteDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Ingrediente ingrediente) throws SQLException {
        if (ingrediente.getNome() == null || ingrediente.getNome().isBlank()) {
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            System.out.println("Criando novo registro de ingrediente: " + ingrediente);
            statement.setString(1, ingrediente.getNome());
            statement.executeUpdate();
        } catch (SQLException ex) {

            // if (ex.getMessage().contains("pk_ingrediente")) {
            // throw new SQLException("Erro ao inserir ingrediente: nome já existente.");
            // } else
            if (ex.getMessage().contains("not-null")) {
                Logger.getLogger(WhiskyDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
                throw new SQLException("Erro ao inserir ingrediente: pelo menos um campo está em branco.");
            }
            // else {
            // Logger.getLogger(WhiskyDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            // throw new SQLException("Erro ao inserir ingrediente.");
            // }
        }
    }

    @Override
    public Ingrediente read(String arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Ingrediente arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Ingrediente> all() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
