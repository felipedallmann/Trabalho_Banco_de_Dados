package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Script;

/**
 *
 * @author olavo
 */
public class ScriptDAO implements DAO<Script> {
    private final Connection connection;
    
    private static final String CREATE_QUERY =
                                "INSERT INTO projetobd.script(loja_nome, data_insercao, codigo) " +
                                "VALUES(?, ?, ?);";
    
    private static final String READ_QUERY =
                                "SELECT url, nome " +
                                "FROM j2ee.website " +
                                "WHERE id = ?;";      
    
    private static final String UPDATE_QUERY =
                                "UPDATE j2ee.website " +
                                "SET url = ?, nome = ?" +
                                "WHERE id = ?;";


    private static final String DELETE_QUERY =
                                "DELETE FROM j2ee.website " +
                                "WHERE id = ?;";
    
    private static final String ALL_QUERY =
                                "SELECT id, nome, url " +
                                "FROM j2ee.website " +
                                "ORDER BY id;";    

    private static final String GET_BY_NAME_QUERY =
                                "SELECT nome, url" +
                                "FROM j2ee.website " +
                                "WHERE nome = ?;";
    
    public ScriptDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Script script) throws SQLException {
       try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, script.getLojaNome());
            statement.setDate(2, script.getDataInsercao());
            statement.setString(3, script.getCodigo());
            System.out.println(statement);

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Script.class.getName()).log(Level.SEVERE, "DAO", ex);
            if (ex.getMessage().contains("pk_loja_vende_whisky")) {
                throw new SQLException("Erro ao inserir script: id já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir script: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir script.");
            }
        }
    }    

    @Override
    public Script read(String lojaNome) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Script arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String lojaNome) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Script> all() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
