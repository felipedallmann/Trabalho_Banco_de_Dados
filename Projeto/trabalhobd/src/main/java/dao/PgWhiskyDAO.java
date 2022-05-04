package dao;

import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Destilaria;
import model.Historico;
import model.Ingrediente;
import model.PaisDeOrigem;
import model.Script;
import model.Whisky;

/**
 *
 * @author olavo
 */
public class PgWhiskyDAO implements WhiskyDAO {
    private final Connection connection;

    private static final String CREATE_QUERY = "INSERT INTO projetobd.script(loja_nome, data_insercao, codigo) " +
            "VALUES(?, ?, ?);";

    private static final String READ_QUERY = "SELECT loja_nome, data_insercao, codigo " +
            "FROM projetobd.script " +
            "WHERE loja_nome = ? AND data_insercao = ?;";

    private static final String UPDATE_QUERY = "UPDATE j2ee.website " +
            "SET url = ?, nome = ?" +
            "WHERE id = ?;";

    private static final String DELETE_QUERY = "DELETE FROM projetobd.script " +
            "WHERE loja_nome = ? AND data_insercao = ?;";

    private static final String ALL_QUERY = "SELECT loja_nome, data_insercao, codigo " +
            "FROM projetobd.script " +
            "WHERE loja_nome = ? " +
            "ORDER BY data_insercao;";
    
    private static final String GET_PRODUCTS =
                                "SELECT wk.nome, " +
                                "h.preco_sem_desconto " +
                                "FROM projetobd.whisky AS wk, " + 
                                "projetobd.historico AS h, " +
                                "projetobd.loja AS lj " +
                                "WHERE lj.nome = ? AND h.whisky_id = wk.id AND h.loja_nome = lj.nome;";

    public PgWhiskyDAO(Connection connection) {
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

    @Override
    public List<Whisky> listAll(String nome) throws SQLException {
        List<Whisky> whiskyList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(GET_PRODUCTS)){
            statement.setString(1, nome);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Whisky whisky = new Whisky();
                whisky.setNome(result.getString("nome"));
                whisky.setPrecoSemDesconto(result.getString("preco_sem_desconto"));
                System.out.println(result.getString("preco_sem_desconto"));
                whiskyList.add(whisky);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return whiskyList;
    }

}