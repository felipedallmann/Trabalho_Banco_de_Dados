package dao;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Destilaria;
import model.DestilariaUtilizaIngrediente;
import model.ExecucaoScript;
import model.Historico;
import model.Ingrediente;
import model.PaisDeOrigem;
import model.Script;
import model.Whisky;

public class PgScriptDAO implements ScriptDAO {

    private final Connection connection;

    private static final String CREATE_QUERY = "INSERT INTO projetobd.script(loja_nome, data_insercao, codigo) "
            + "VALUES(?, ?, ?);";

    private static final String READ_QUERY = "SELECT loja_nome, data_insercao, codigo "
            + "FROM projetobd.script "
            + "WHERE loja_nome = ? AND data_insercao = ?;";

    // private static final String UPDATE_QUERY = "UPDATE j2ee.website " +
    // "SET url = ?, nome = ?" +
    // "WHERE id = ?;";
    private static final String DELETE_QUERY = "DELETE FROM projetobd.script "
            + "WHERE loja_nome = ? AND data_insercao = ?;";

    private static final String ALL_QUERY = "SELECT loja_nome, data_insercao, codigo "
            + "FROM projetobd.script "
            + "WHERE loja_nome = ? "
            + "ORDER BY data_insercao;";

    public PgScriptDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Script script) throws SQLException {
        try ( PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
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

        try ( PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setString(1, lojaNome);
            try ( ResultSet result = statement.executeQuery()) {
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
    public Script read(String lojaNome, Timestamp dataInsercao) throws SQLException {
        Script script = new Script();

        try ( PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setString(1, lojaNome);
            statement.setTimestamp(2, dataInsercao);
            try ( ResultSet result = statement.executeQuery()) {
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void delete(String lojaNome, Timestamp dataInsercao) throws SQLException {
        try ( PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, lojaNome);
            statement.setTimestamp(2, dataInsercao);
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Script> all(String lojaNome) throws SQLException {
        List<Script> scriptList = new ArrayList<>();

        try ( PreparedStatement statement = connection.prepareStatement(ALL_QUERY)) {
            statement.setString(1, lojaNome);
            System.out.println(statement);
            try ( ResultSet result = statement.executeQuery()) {
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
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public static void deleteDirectory(String filePath)
        throws IOException {

        Path path = Paths.get(filePath);

        Files.walkFileTree(path,
            new SimpleFileVisitor<>() {

                // delete directories or folders
                @Override
                public FileVisitResult postVisitDirectory(Path dir,
                                                          IOException exc)
                                                          throws IOException {
                    Files.delete(dir);
                    System.out.printf("Directory is deleted : %s%n", dir);
                    return FileVisitResult.CONTINUE;
                }

                // delete files
                @Override
                public FileVisitResult visitFile(Path file,
                                                 BasicFileAttributes attrs)
                                                 throws IOException {
                    Files.delete(file);
                    System.out.printf("File is deleted : %s%n", file);
                    return FileVisitResult.CONTINUE;
                }
            }
        );

    }

    @Override
    public void run(Script script) throws SQLException {
        try ( var out = new PrintWriter("web_scraping.py")) {
            out.println(script.getCodigo());
            Logger.getLogger(PgScriptDAO.class.getName()).log(Level.SEVERE, "Execuntado script!");
            var processBuilder = new ProcessBuilder("python", "./web_scraping.py");
            var process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(PgScriptDAO.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        File outputDir = new File("./saidas");
        File outputScript = new File("web_scraping.py");
        File[] directoryListing = outputDir.listFiles();
        try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
            // Registra a execução
            try {
                var registro = new ExecucaoScript(
                        script,
                        new Timestamp(System.currentTimeMillis())
                );
                var dao = daoFactory.getExecucaoScriptDAO();
                dao.create(registro);
            } catch (SQLException ex) {
                Logger.getLogger(WhiskyDAO.class.getName()).log(Level.SEVERE,
                        "DAO: Erro ao criar registro de execução do script!");
            }

            for (File file : directoryListing) {
                System.out.println(file);

                var content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);

                var gson = new Gson();

                // Lê o ingrediente
                Ingrediente ingrediente = gson.fromJson(content, Ingrediente.class);
                System.out.println(ingrediente.toString());
                // Registra o ingrediente
                try {
                    IngredienteDAO dao = daoFactory.getIngredienteDAO();
                    dao.create(ingrediente);
                } catch (SQLException ex) {
                    Logger.getLogger(IngredienteDAO.class.getName()).log(Level.SEVERE,
                            "DAO: Erro ao criar registro de destilaria");
                }

                // Lê o pais de origem
                PaisDeOrigem pais = gson.fromJson(content, PaisDeOrigem.class);
                System.out.println(pais.toString());
                // Registra o pais de origem
                try {
                    PaisDeOrigemDAO dao = daoFactory.getPaisDeOrigemDAO();
                    dao.create(pais);
                } catch (SQLException ex) {
                    Logger.getLogger(IngredienteDAO.class.getName()).log(Level.SEVERE,
                            "DAO: Erro ao criar registro de destilaria");
                }

                // Lê a destilaria
                Destilaria destilaria = gson.fromJson(content, Destilaria.class);
                System.out.println(destilaria.toString());
                // Registra a destilaria
                try {
                    DestilariaDAO dao = daoFactory.getDestilariaDAO();
                    dao.create(destilaria);
                } catch (SQLException ex) {
                    Logger.getLogger(DestilariaDAO.class.getName()).log(Level.SEVERE,
                            "DAO: Erro ao criar registro de destilaria");
                }

                // Lê a relação destilaria x ingrediente
                var dui = new DestilariaUtilizaIngrediente(destilaria, ingrediente);
                System.out.println(dui.toString());
                // Registra a relação destilaria x ingrediente
                try {
                    var dao = daoFactory.getDestilariaUtilizaIngredienteDAO();
                    dao.create(dui);
                } catch (SQLException ex) {
                    Logger.getLogger(IngredienteDAO.class.getName()).log(Level.SEVERE,
                            "DAO: Erro ao criar a relação destilaria x ingrediente");
                }

                // Lê o whisky
                Whisky whisky = gson.fromJson(content, Whisky.class);
                // Registra o whisky
                try {
                    whisky.setPaisOrigemNome(pais.getNome());
                    whisky.setDestilariaNome(destilaria.getNome());
                    PgWhiskyDAO dao = daoFactory.getWhiskyDAO();
                    dao.create(whisky);
                } catch (SQLException ex) {
                    Logger.getLogger(WhiskyDAO.class.getName()).log(Level.SEVERE,
                            "DAO: Erro ao criar registro de whisky");
                }

                // Lê o histórico
                Historico historico = gson.fromJson(content, Historico.class);
                // Registra o histórico
                try {
                    historico.setLojaNome(script.getLojaNome());
                    historico.setWhiskyId(whisky.getId());
                    historico.setAcessadoEm(new Timestamp(System.currentTimeMillis()));
                    PgHistoricoDAO dao = daoFactory.getHistoricoDAO();
                    dao.create(historico);
                } catch (SQLException ex) {
                    Logger.getLogger(IngredienteDAO.class.getName()).log(Level.SEVERE,
                            "DAO: Erro ao criar registro de histórico");
                }
            }
        } catch (ClassNotFoundException | IOException ex) {
            Logger.getLogger(PgScriptDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            throw new RuntimeException("Erro ao executar script!");
        } finally {
            try {
                deleteDirectory("./saidas");
            } catch (IOException ex) {
                Logger.getLogger(PgScriptDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            outputDir.delete();
            outputScript.delete();
        }
    }
}
