
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.DestilariaUtilizaIngrediente;


public class DestilariaUtilizaIngredienteDAO implements DAO<DestilariaUtilizaIngrediente> {

    private final Connection connection;

    private static final String CREATE_QUERY = "INSERT INTO projetobd.destilaria_utiliza_ingrediente(destilaria_nome, ingrediente_nome) " +
            "VALUES(?, ?);";

    // private static final String READ_QUERY = "SELECT url, nome " +
    // "FROM projetobd.loja " +
    // "WHERE nome = ?;";

    // private static final String UPDATE_QUERY = "UPDATE projetobd.loja " +
    // "SET url = ?" +
    // "WHERE nome = ?;";

    // private static final String DELETE_QUERY = "DELETE FROM projetobd.loja " +
    // "WHERE nome = ?;";

    //private static final String ALL_QUERY = "SELECT nome, pais_origem_nome " +
    //        "FROM projetobd.destilaria; ";

    // private static final String GET_BY_NAME_QUERY = "SELECT nome, url" +
    // "FROM projetobd.loja " +
    // "WHERE nome = ?;";

    public DestilariaUtilizaIngredienteDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(DestilariaUtilizaIngrediente t) throws SQLException {
        if(t.getDestilaria().getNome() == null || 
            t.getIngrediente().getNome() == null){
            Logger.getLogger(DestilariaUtilizaIngredienteDAO.class.getName()).log(Level.WARNING,
                    "Relação destilaria x ingredinete não criada já que possui nome nulo!");
            return;
        }
        
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            System.out.println("Criando nova relação de destilaria utiliza ingrediente: " + t);
            statement.setString(1, t.getDestilaria().getNome());
            statement.setString(2, t.getIngrediente().getNome());
            statement.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getMessage().contains("pk_destilaria_utiliza_ingrediente")) {
                Logger.getLogger(DestilariaUtilizaIngredienteDAO.class.getName()).log(Level.WARNING, "Relação Destilaria: {0}, Ingrediente: {1} já existe!", new Object[]{t.getDestilaria().getNome(), t.getIngrediente().getNome()});
                // throw new SQLException("Erro ao inserir destilaria: nome já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                Logger.getLogger(WhiskyDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
                throw new SQLException("Erro ao inserir relação destilaria utiliza ingrediente: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir relação destilaria utiliza ingrediente.");
            }
        }
    }

    @Override
    public void delete(String arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(DestilariaUtilizaIngrediente t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public DestilariaUtilizaIngrediente read(String id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<DestilariaUtilizaIngrediente> all() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
