/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Historico;
import model.Whisky;

/**
 *
 * @author olavo
 */
public class HistoricoDAO implements DAO<Historico> {

    private final Connection connection;
    
    private static final String CREATE_QUERY =
                                "INSERT INTO projetobd.historico(whisky_id, loja_nome, preco_sem_desconto, preco_com_desconto, acessado_em) " +
                                "VALUES(?, ?, ?, ?, ?);";
    
    private static final String READ_QUERY =
                                "SELECT url, nome " +
                                "FROM projetobd.loja " +
                                "WHERE nome = ?;";      
    
    private static final String UPDATE_QUERY =
                                "UPDATE projetobd.loja " +
                                "SET url = ?" +
                                "WHERE nome = ?;";

    private static final String DELETE_QUERY =
                                "DELETE FROM projetobd.loja " +
                                "WHERE nome = ?;";
    
    private static final String ALL_QUERY =
                                "SELECT nome, url " +
                                "FROM projetobd.loja " +
                                "ORDER BY nome;";    

    private static final String GET_BY_NAME_QUERY =
                                "SELECT nome, url" +
                                "FROM projetobd.loja " +
                                "WHERE nome = ?;";
    
    public HistoricoDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Historico historico) throws SQLException {
        if (historico.getPrecoSemDesconto()== null || historico.getPrecoSemDesconto().isBlank()) {
            return;
        }
        if (historico.getAcessadoEm() == null) {
            return;
        }
        
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            System.out.println("Criando novo registro de historico: " + historico);
            statement.setInt(1, historico.getWhiskyId());
            statement.setString(2, historico.getLojaNome());
            statement.setString(3, historico.getPrecoSemDesconto());
            statement.setString(4, historico.getPrecoComDesconto());
            statement.setTimestamp(5, historico.getAcessadoEm());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(HistoricoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("pk_historico")) {
                throw new SQLException("Erro ao inserir historico: nome já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir historico: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir historico.");
            }
        }
    }

    @Override
    public Historico read(String arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Historico arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Historico> all() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
