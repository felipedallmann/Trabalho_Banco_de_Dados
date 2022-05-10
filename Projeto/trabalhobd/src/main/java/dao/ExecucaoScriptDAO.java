package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.ExecucaoScript;
import model.Script;

public class ExecucaoScriptDAO implements DAO<ExecucaoScript> {

    private final Connection connection;

    private static final String CREATE_QUERY = "INSERT INTO projetobd.execucao_scripts(script_loja_nome, script_data_insercao, data_execucao) "
            + "VALUES(?, ?, ?);";

    private static final String READ_QUERY = "SELECT loja_nome, data_insercao, codigo "
            + "FROM projetobd.script "
            + "WHERE script_loja_nome = ? AND script_data_insercao = ? AND data_execucao = ?;";

    // private static final String UPDATE_QUERY = "UPDATE j2ee.website " +
    // "SET url = ?, nome = ?" +
    // "WHERE id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM projetobd.execucao_scripts "
            + "WHERE script_loja_nome = ? AND script_data_insercao = ? AND data_execucao = ?;";

    private static final String ALL_QUERY = "SELECT script_loja_nome, script_data_insercao, data_execucao "
            + "FROM projetobd.execucao_scripts "
            + "WHERE script_loja_nome = ? "
            + "ORDER BY data_execucao;";

    public ExecucaoScriptDAO(Connection connection) {
        this.connection = connection;
    }

    public List<ExecucaoScript> listAll(String lojaNome) throws SQLException {
        var historyList = new ArrayList<ExecucaoScript>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY)) {
            statement.setString(1, lojaNome);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    var execucao = new ExecucaoScript(
                            new Script(
                                    result.getString("script_loja_nome"),
                                    result.getTimestamp("script_data_insercao"),
                                    ""
                            ),
                            result.getTimestamp("data_execucao")
                    );
                    historyList.add(execucao);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PgScriptDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
                throw new SQLException("Erro ao listar usuários.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgScriptDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return historyList;
    }

    @Override
    public void create(ExecucaoScript t) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            Logger.getLogger(ExecucaoScriptDAO.class.getName()).log(Level.INFO, "DAO", t);
            statement.setString(1, t.getScript().getLojaNome());
            statement.setTimestamp(2, t.getScript().getDataInsercao());
            statement.setTimestamp(3, t.getDataExecucao());
            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(ExecucaoScriptDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            if (ex.getMessage().contains("fk_execucao_scripts_script_loja_nome ")) {
                throw new SQLException("Erro ao inserir registro de execução: script alvo não existe");
            } else {
                throw new SQLException("Erro ao inserir registro de execução.");
            }
        }
    }

    @Override
    public ExecucaoScript read(String id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(ExecucaoScript t) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void delete(ExecucaoScript t) throws SQLException {
        try ( PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, t.getScript().getLojaNome());
            statement.setTimestamp(2, t.getScript().getDataInsercao());
            statement.setTimestamp(3, t.getDataExecucao());
            System.out.println(statement);
            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: registro de execução não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgScriptDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: registro de execução não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir registro de execução .");
            }
        }
    }

    @Override
    public List<ExecucaoScript> all() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(String id) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
