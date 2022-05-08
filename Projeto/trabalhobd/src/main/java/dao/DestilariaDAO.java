
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Destilaria;


public class DestilariaDAO implements DAO<Destilaria> {

    private final Connection connection;

    private static final String CREATE_QUERY = "INSERT INTO projetobd.destilaria(nome, pais_origem_nome) " +
            "VALUES(?, ?);";

    // private static final String READ_QUERY = "SELECT url, nome " +
    // "FROM projetobd.loja " +
    // "WHERE nome = ?;";

    // private static final String UPDATE_QUERY = "UPDATE projetobd.loja " +
    // "SET url = ?" +
    // "WHERE nome = ?;";

    // private static final String DELETE_QUERY = "DELETE FROM projetobd.loja " +
    // "WHERE nome = ?;";

    private static final String ALL_QUERY = "SELECT nome, pais_origem_nome " +
            "FROM projetobd.destilaria; ";

    // private static final String GET_BY_NAME_QUERY = "SELECT nome, url" +
    // "FROM projetobd.loja " +
    // "WHERE nome = ?;";

    public DestilariaDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Destilaria destilaria) throws SQLException {
        if (destilaria.getNome() == null || destilaria.getNome().isBlank()) {
            return;
        }

        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            System.out.println("Criando novo registro de destilaria: " + destilaria);
            statement.setString(1, destilaria.getNome());
            statement.setString(2, destilaria.getPaisOrigemNome());
            statement.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getMessage().contains("pk_destilaria")) {
                Logger.getLogger(WhiskyDAO.class.getName()).log(Level.WARNING,
                        "Destilaria " + destilaria.getNome() + " já existe!");
                // throw new SQLException("Erro ao inserir destilaria: nome já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                Logger.getLogger(WhiskyDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
                throw new SQLException("Erro ao inserir destilaria: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir destilaria.");
            }
        }
    }

    @Override
    public void update(Destilaria arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Destilaria read(String arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Destilaria> all() throws SQLException {
        List<Destilaria> destilariaList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
                ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Destilaria destilaria = new Destilaria();
                destilaria.setNome(result.getString("nome"));
                destilaria.setPaisOrigemNome(result.getString("pais_origem_nome"));

                destilariaList.add(destilaria);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar destilarias.");
        }

        return destilariaList;
    }
}
