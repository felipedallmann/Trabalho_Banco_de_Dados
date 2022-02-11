/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.WebSite;

/**
 *
 * @author dskaster
 */
public class PgWebSiteDAO implements WebSiteDAO {

    private final Connection connection;
    
    private static final String CREATE_QUERY =
                                "INSERT INTO j2ee.website(nome, url) " +
                                "VALUES(?, ?);";
    
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
    
    public PgWebSiteDAO(Connection connection) {
        this.connection = connection;
    }


    @Override
    public WebSite getByName(String login) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(GET_BY_NAME_QUERY)) {
            statement.setString(1, login);

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    WebSite webSite = new WebSite();
                    webSite.setId(result.getInt("id"));
                    webSite.setNome(result.getString("nome"));
                    webSite.setURL(result.getString("URL"));
                    return webSite;

                } else {

                    return null;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgWebSiteDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
            throw new SQLException("Erro ao obter site.");
        }
    }

    @Override
    public void create(WebSite webSite) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, webSite.getNome());
            statement.setString(2, webSite.getURL());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgWebSiteDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

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
    public WebSite read(Integer id) throws SQLException {
        WebSite webSite = new WebSite();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, id);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    webSite.setId(id);
                    webSite.setNome(result.getString("nome"));
                    webSite.setURL(result.getString("URL"));
                } else {
                    throw new SQLException("Erro ao visualizar: site não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgWebSiteDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
            if (ex.getMessage().equals("Erro ao visualizar: usuário não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar usuário.");
            }
        }

        return webSite;
    }

    @Override
    public void update(WebSite webSite) throws SQLException {
        String query;

        query = UPDATE_QUERY;


        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, webSite.getNome());
            statement.setString(2, webSite.getURL());

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: usuário não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgWebSiteDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

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
    public void delete(Integer id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setInt(1, id);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: site não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgWebSiteDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: usuário não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir usuário.");
            }
        }
    }

    @Override
    public List<WebSite> all() throws SQLException {
        
        List<WebSite> webSiteList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
             ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                WebSite webSite = new WebSite();
                webSite.setId(result.getInt("id"));
                webSite.setNome(result.getString("nome"));
                webSite.setURL(result.getString("url"));

                webSiteList.add(webSite);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgWebSiteDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return webSiteList;
    }
    
}
