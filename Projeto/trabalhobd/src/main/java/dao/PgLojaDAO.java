package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Loja;

public class PgLojaDAO implements LojaDAO {

    private final Connection connection;

    private static final String CREATE_QUERY = "INSERT INTO projetobd.loja(nome, url) " +
            "VALUES(?, ?);";

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

    public PgLojaDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Loja getByName(String name) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_BY_NAME_QUERY)) {
            statement.setString(1, name);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    Loja webSite = new Loja();
                    webSite.setNome(result.getString("nome"));
                    webSite.setURL(result.getString("URL"));
                    return webSite;

                } else {

                    return null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao obter site.");
        }
    }

    @Override
    public void create(Loja webSite) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, webSite.getNome());
            statement.setString(2, webSite.getURL());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

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
    public Loja read(String nome) throws SQLException {
        Loja webSite = new Loja();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setString(1, nome);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    webSite.setNome(result.getString("nome"));
                    webSite.setURL(result.getString("URL"));
                } else {
                    throw new SQLException("Erro ao visualizar: site não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao visualizar: usuário não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar usuário.");
            }
        }

        return webSite;
    }

    @Override
    public void update(Loja webSite) throws SQLException {
        String query;

        query = UPDATE_QUERY;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, webSite.getNome());
            statement.setString(2, webSite.getURL());

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: usuário não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: site não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar site: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao editar site.");
            }
        }
    }

    @Override
    public void delete(String nome) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, nome);
            System.out.println(statement);
            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: site não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: usuário não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir usuário.");
            }
        }
    }

    @Override
    public List<Loja> all() throws SQLException {

        List<Loja> webSiteList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
                ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Loja webSite = new Loja();
                webSite.setNome(result.getString("nome"));
                webSite.setURL(result.getString("url"));

                webSiteList.add(webSite);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return webSiteList;
    }

}
