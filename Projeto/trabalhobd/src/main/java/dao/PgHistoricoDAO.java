package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Historico;
import org.postgresql.util.PSQLException;

public class PgHistoricoDAO implements DAO<Historico> {

    private final Connection connection;

    private static final String CREATE_QUERY = "INSERT INTO projetobd.historico(whisky_id, loja_nome, preco_sem_desconto, preco_com_desconto, acessado_em) "
            + "VALUES(?, ?, ?, ?, ?);";

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

    private static final String GET_QTD = "SELECT count(DISTINCT acessado_em) qtd FROM projetobd.historico;";

    private static final String GET_ULTIMA_ATT = "SELECT MAX(acessado_em) ultima_att FROM projetobd.historico;";

    private static final String GET_MEDIA_PRECO = "SELECT avg(CAST(h.preco_sem_desconto AS float )) as mediaPreco FROM projetobd.whisky AS wk, "
            +
            "projetobd.historico AS h " +
            "WHERE h.whisky_id = wk.id AND h.acessado_em = (select max(acessado_em) FROM projetobd.historico as hi WHERE hi.whisky_id = wk.id)";

    public PgHistoricoDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Historico historico) throws SQLException {
        if (historico.getPrecoSemDesconto() == null || historico.getPrecoSemDesconto().isBlank()) {
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
            // Logger.getLogger(PgHistoricoDAO.class.getName()).log(Level.SEVERE, "DAO",
            // ex);

            if (ex.getMessage().contains("pk_historico")) {
                throw new SQLException("Erro ao inserir historico: nome já existente.");
            } else if (ex.getMessage().contains("fk_historico_whisky")) {
                throw new SQLException("Erro ao inserir historico: wisky não existe.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir historico: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir historico.");
            }
        }
    }

    @Override
    public Historico read(String arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Historico arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(String arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Historico> all() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getQtd() throws SQLException {
        int qtd;
        try (PreparedStatement statement = connection.prepareStatement(GET_QTD)) {
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    qtd = Integer.parseInt(result.getString("qtd"));
                } else {
                    throw new SQLException("Erro ao visualizar: site não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return qtd;
    }

    public Timestamp getUltimaAtt() throws SQLException {
        Timestamp ultima_att;
        try (PreparedStatement statement = connection.prepareStatement(GET_ULTIMA_ATT)) {
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    ultima_att = Timestamp.valueOf(result.getString("ultima_att"));
                } else {
                    throw new SQLException("Erro ao visualizar: site não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return ultima_att;
    }

    public Double mediaPreco() throws SQLException {
        Double mediaPreco;
        try (PreparedStatement statement = connection.prepareStatement(GET_MEDIA_PRECO)) {
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    mediaPreco = result.getDouble("mediaPreco");
                } else {
                    throw new SQLException("Erro ao visualizar: site não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return mediaPreco;
    }
}
