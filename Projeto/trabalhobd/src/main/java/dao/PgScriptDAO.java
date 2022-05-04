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
public class PgScriptDAO implements ScriptDAO {
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

    public PgScriptDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Script script) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, script.getLojaNome());
            statement.setTimestamp(2, script.getDataInsercao());
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
        Script script = new Script();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setString(1, lojaNome);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    script.setLojaNome(result.getString("lojaNome"));
                    script.setDataInsercao(result.getTimestamp("dataInsercao"));
                    script.setCodigo(result.getString("codigo"));
                } else {
                    throw new SQLException("Erro ao visualizar: site não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgScriptDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao visualizar: script não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar script.");
            }
        }
        return script;
    }

    @Override
    public Script read(String lojaNome, Date dataInsercao) throws SQLException {
        Script script = new Script();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setString(1, lojaNome);
            statement.setDate(2, dataInsercao);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    script.setLojaNome(result.getString("loja_nome"));
                    script.setDataInsercao(result.getTimestamp("data_insercao"));
                    script.setCodigo(result.getString("codigo"));
                } else {
                    throw new SQLException("Erro ao visualizar: site não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgScriptDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao visualizar: script não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar script.");
            }
        }
        return script;
    }

    @Override
    public void update(Script arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String lojaNome, Date dataInsercao) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, lojaNome);
            statement.setDate(2, dataInsercao);
            System.out.println(statement);
            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: script não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgScriptDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: script não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir script.");
            }
        }
    }

    @Override
    public void delete(String arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public List<Script> all(String lojaNome) throws SQLException {
        List<Script> scriptList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY)) {
            statement.setString(1, lojaNome);
            System.out.println(statement);
            try (ResultSet result = statement.executeQuery()) {
                while (result.next()) {
                    Script script = new Script();
                    script.setLojaNome(result.getString("loja_nome"));
                    script.setDataInsercao(result.getTimestamp("data_insercao"));
                    script.setCodigo(result.getString("codigo"));

                    scriptList.add(script);
                }
            } catch (SQLException ex) {
                Logger.getLogger(PgScriptDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
                throw new SQLException("Erro ao listar usuários.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgScriptDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return scriptList;
    }

    @Override
    public List<Script> all() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from
                                                                       // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void run(String lojaNome, Timestamp dataInsercao) throws SQLException {
        File currentDir = new File("./saidas");
        File[] directoryListing = currentDir.listFiles();
        for (File file : directoryListing) {
            System.out.println(file);

            try {
                String content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

                Gson gson = new Gson();
                // Lê o whisky
                Whisky whisky = gson.fromJson(content, Whisky.class);
                System.out.println(whisky.toString());

                // Lê o ingrediente
                Ingrediente ingrediente = gson.fromJson(content, Ingrediente.class);
                System.out.println(ingrediente.toString());

                // Lê o pais de origem
                PaisDeOrigem pais = gson.fromJson(content, PaisDeOrigem.class);
                System.out.println(pais.toString());

                // Lê a destilaria
                Destilaria destilaria = gson.fromJson(content, Destilaria.class);
                System.out.println(destilaria.toString());

                // Lê o histórico
                Historico historico = gson.fromJson(content, Historico.class);
                System.out.println(historico.toString());

                // Registra o ingrediente
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    IngredienteDAO dao = daoFactory.getIngredienteDAO();
                    dao.create(ingrediente);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    Logger.getLogger(IngredienteDAO.class.getName()).log(Level.SEVERE,
                            "DAO: Erro ao criar registro de destilaria");
                }

                // Registra o pais de origem
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    PaisDeOrigemDAO dao = daoFactory.getPaisDeOrigemDAO();
                    dao.create(pais);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    Logger.getLogger(IngredienteDAO.class.getName()).log(Level.SEVERE,
                            "DAO: Erro ao criar registro de destilaria");
                }

                // Registra a destilaria
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    DestilariaDAO dao = daoFactory.getDestilariaDAO();
                    dao.create(destilaria);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    Logger.getLogger(DestilariaDAO.class.getName()).log(Level.SEVERE,
                            "DAO: Erro ao criar registro de destilaria");
                }

                // Registra o whisky
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    WhiskyDAO dao = daoFactory.getWhiskyDAO();
                    dao.create(whisky);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    Logger.getLogger(WhiskyDAO.class.getName()).log(Level.SEVERE,
                            "DAO: Erro ao criar registro de whisky");
                }

                // Registra o histórico
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    historico.setLojaNome(destilaria.getNome());
                    historico.setWhiskyId(whisky.getId());
                    historico.setAcessadoEm(new Timestamp(System.currentTimeMillis()));

                    HistoricoDAO dao = daoFactory.getHistoricoDAO();
                    dao.create(historico);

                    // dao.create(ingrediente);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    Logger.getLogger(IngredienteDAO.class.getName()).log(Level.SEVERE,
                            "DAO: Erro ao criar registro de destilaria");
                }
            } catch (IOException ex) {
                Logger.getLogger(PgScriptDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
                throw new SQLException("Erro ao abrir arquivo: " + file);
            }
        }
    }
}
