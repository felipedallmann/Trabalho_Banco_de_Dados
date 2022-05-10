package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import model.Whisky;

public class PgWhiskyDAO implements WhiskyDAO {

    private final Connection connection;

    private static final String CREATE_QUERY = ""
            + "INSERT INTO projetobd.whisky(nome, idade, teor_alcolico, destilaria_nome, pais_origem_nome) "
            + "VALUES(?, ?, ?, ?, ?)"
            + " ON CONFLICT (nome)"
            + " DO"
            + " UPDATE SET"
            + " idade = EXCLUDED.idade, "
            + " teor_alcolico = EXCLUDED.teor_alcolico,"
            + " pais_origem_nome = EXCLUDED.pais_origem_nome,"
            + " destilaria_nome = EXCLUDED.destilaria_nome "
            + " RETURNING id;";

    private static final String READ_QUERY = "SELECT * "
            + "FROM projetobd.whisky "
            + "WHERE id = ?;";

    // private static final String UPDATE_QUERY = "UPDATE j2ee.website " +
    // "SET url = ?, nome = ?" +
    // "WHERE id = ?;";
    // private static final String DELETE_QUERY = "DELETE FROM projetobd.script " +
    // "WHERE loja_nome = ? AND data_insercao = ?;";
    // private static final String ALL_QUERY = "SELECT loja_nome, data_insercao,
    // codigo " +
    // "FROM projetobd.script " +
    // "WHERE loja_nome = ? " +
    // "ORDER BY data_insercao;";
    
    private static final String GET_QTD = "SELECT count(*) qtd FROM projetobd.whisky;";

    private static final String ALL_QUERY = "SELECT DISTINCT wk.nome, "
            + "h.preco_sem_desconto, "
            + "h.preco_com_desconto, "
            + "h.loja_nome, "
            + "wk.id "
            + "FROM projetobd.whisky AS wk, "
            + "projetobd.historico AS h, "
            + "projetobd.loja AS lj "
            + "WHERE wk.id = h.whisky_id AND h.acessado_em = (select max(acessado_em) FROM projetobd.historico as hi WHERE hi.whisky_id = wk.id)";

    private static final String GET_PRODUCTS = "SELECT DISTINCT wk.nome, "
            + "h.preco_sem_desconto, "
            + "h.preco_com_desconto, "
            + "wk.id "
            + "FROM projetobd.whisky AS wk, "
            + "projetobd.historico AS h, "
            + "projetobd.loja AS lj "
            + "WHERE lj.nome = ? AND h.whisky_id = wk.id AND h.loja_nome = lj.nome AND h.acessado_em = (select max(acessado_em) FROM projetobd.historico as h WHERE h.whisky_id = wk.id)";

    private static final String GET_PRODUCTS_SEARCH = "SELECT DISTINCT wk.nome, "
            + "h.preco_sem_desconto, "
            + "h.preco_com_desconto, "
            + "wk.id "
            + "FROM projetobd.whisky AS wk, "
            + "projetobd.historico AS h, "
            + "projetobd.loja AS lj "
            + "WHERE lj.nome = ? AND LOWER(wk.nome) LIKE LOWER(?) AND h.whisky_id = wk.id AND h.loja_nome = lj.nome AND h.acessado_em = (select max(acessado_em) FROM projetobd.historico as h WHERE h.whisky_id = wk.id)";

    private static final String GET_PRODUCTS_SEARCH_ALL = "SELECT DISTINCT wk.nome, "
            + "h.preco_sem_desconto, "
            + "h.preco_com_desconto, "
            + "lj.nome as nome_loja, "
            + "wk.id "
            + "FROM projetobd.whisky AS wk, "
            + "projetobd.historico AS h, "
            + "projetobd.loja AS lj "
            + "WHERE LOWER(wk.nome) LIKE LOWER(?) AND h.whisky_id = wk.id AND h.loja_nome = lj.nome AND h.acessado_em = (select max(acessado_em) FROM projetobd.historico as h WHERE h.whisky_id = wk.id)";
    
    private static final String GET_PRODUCTS_SEARCH_DESTILARIA = "SELECT wk.id, "
            + "wk.nome, "
            + "wk.destilaria_nome, "
            + "h.loja_nome, "
            + "h.preco_sem_desconto, "
            + "h.preco_com_desconto "
            + "FROM projetobd.whisky AS wk, "
            + "projetobd.historico AS h, "
            + "projetobd.destilaria AS d "
            + "WHERE h.whisky_id = wk.id "
            + "AND d.nome = ? "
            + "AND wk.destilaria_nome = d.nome "
            + "AND h.acessado_em = (select max(acessado_em) FROM projetobd.historico as h WHERE h.whisky_id = wk.id) ";
    
    
    private static final String GET_HISTORY = "SELECT wk.nome, "
            + "h.preco_sem_desconto, "
            + "h.preco_com_desconto, "
            + "h.acessado_em, "
            + "wk.id "
            + "FROM projetobd.whisky AS wk, "
            + "projetobd.historico AS h, "
            + "projetobd.loja AS lj "
            + "WHERE lj.nome = ? AND h.whisky_id = ? AND wk.id = ? AND h.loja_nome = lj.nome;";

    private static final String GET_MAIOR_PRECO = "SELECT MAX(value) maiorPreco FROM (SELECT MAX(CAST(preco_com_desconto as float)) AS value FROM projetobd.historico WHERE whisky_id = ?  UNION SELECT MAX(CAST(preco_sem_desconto as float)) AS value FROM projetobd.historico WHERE whisky_id = ? ) AS ma";
    private static final String GET_MENOR_PRECO = "SELECT MIN(value) menorPreco FROM (SELECT MIN(NULLIF(CAST(preco_com_desconto as float), 0)) AS value FROM projetobd.historico WHERE whisky_id = ?  UNION SELECT MIN(NULLIF(CAST(preco_sem_desconto as float), 0)) AS value FROM projetobd.historico WHERE whisky_id = ? ) AS mi";
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
        if (whisky.getestilariaNome() != null && whisky.getestilariaNome().isBlank()) {
            whisky.setDestilariaNome(null);
        }
        if (whisky.getPaisOrigemNome() != null && whisky.getPaisOrigemNome().isBlank()) {
            whisky.setPaisOrigemNome(null);
        }

        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            System.out.println("Criando novo registro de whisky: " + whisky);
            statement.setString(1, whisky.getNome());
            statement.setString(2, whisky.getIdade());
            statement.setString(3, whisky.getTeorAlcolico());
            statement.setString(4, whisky.getestilariaNome());
            statement.setString(5, whisky.getPaisOrigemNome());
            boolean status = statement.execute();
            if (status) {
                try ( ResultSet rs = statement.getResultSet()) {
                    if (rs.next()) {
                        int id = rs.getInt("id");
                        Logger.getLogger(WhiskyDAO.class.getName()).log(Level.INFO, "Id do whisky atualizado para {0}", id);
                        whisky.setId(id);
                    }
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgWhiskyDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("uq_website_nome")) {
                throw new SQLException("Erro ao inserir whisky: nome já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir whisky: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir whisky.");
            }
        }
    }

    @Override
    public Whisky read(String id) throws SQLException {
        Whisky whisky = new Whisky();

        try ( PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setInt(1, Integer.parseInt(id));
            try ( ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    whisky.setNome(result.getString("nome"));
                    whisky.setDestilariaNome(result.getString("destilaria_nome"));
                    whisky.setIdade(result.getString("idade"));
                    whisky.setPaisOrigemNome(result.getString("pais_origem_nome"));
                    whisky.setTeorAlcolico(result.getString("teor_alcolico"));
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

        return whisky;
    }

    public int getQtd() throws SQLException {
        int qtd;
        try ( PreparedStatement statement = connection.prepareStatement(GET_QTD)) {
            try ( ResultSet result = statement.executeQuery()) {
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

    @Override
    public List<Whisky> all() throws SQLException {
        var whiskyList = new ArrayList<Whisky>();
        try ( PreparedStatement statement = connection.prepareStatement(ALL_QUERY)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Whisky whisky = new Whisky();
                whisky.setNome(result.getString("nome"));
                whisky.setPrecoSemDesconto(result.getString("preco_sem_desconto"));
                whisky.setPrecoComDesconto(result.getString("preco_com_desconto"));
                whisky.setLojaNome(result.getString("loja_nome"));
                whisky.setId(Integer.parseInt(result.getString("id")));
                whiskyList.add(whisky);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar whiskys.");
        }

        return whiskyList;
    }

    @Override
    public List<Whisky> listAll(String nome) throws SQLException {
        List<Whisky> whiskyList = new ArrayList<>();

        try ( PreparedStatement statement = connection.prepareStatement(GET_PRODUCTS)) {
            statement.setString(1, nome);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Whisky whisky = new Whisky();
                whisky.setNome(result.getString("nome"));
                whisky.setPrecoSemDesconto(result.getString("preco_sem_desconto"));
                whisky.setPrecoComDesconto(result.getString("preco_com_desconto"));
                whisky.setId(Integer.parseInt(result.getString("id")));
                System.out.println(result.getString("preco_sem_desconto"));
                whiskyList.add(whisky);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return whiskyList;
    }

    public List<Whisky> listAllHistorico(String nome, String id) throws SQLException {
        List<Whisky> whiskyList = new ArrayList<>();
        try ( PreparedStatement statement = connection.prepareStatement(GET_HISTORY)) {
            statement.setString(1, nome);
            statement.setInt(2, Integer.parseInt(id));
            statement.setInt(3, Integer.parseInt(id));
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Whisky whisky = new Whisky();
                whisky.setNome(result.getString("nome"));
                whisky.setPrecoSemDesconto(result.getString("preco_sem_desconto"));
                whisky.setPrecoComDesconto(result.getString("preco_com_desconto"));
                whisky.setId(Integer.parseInt(result.getString("id")));
                Timestamp timestamp = Timestamp.valueOf(result.getString("acessado_em"));
                whisky.setAcessadoEm(timestamp);
                whiskyList.add(whisky);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }
        // retornar uma lista com os dados do whisky e as datas

        return whiskyList;
    }

    public List<Whisky> listSearch(String whisky_nome, String loja_nome) throws SQLException {
        List<Whisky> whiskyList = new ArrayList<>();

        try ( PreparedStatement statement = connection.prepareStatement(GET_PRODUCTS_SEARCH)) {
            statement.setString(1, loja_nome);
            statement.setString(2, "%" + whisky_nome + "%");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Whisky whisky = new Whisky();
                whisky.setNome(result.getString("nome"));
                whisky.setPrecoSemDesconto(result.getString("preco_sem_desconto"));
                whisky.setPrecoComDesconto(result.getString("preco_com_desconto"));
                whisky.setId(Integer.parseInt(result.getString("id")));
                System.out.println(result.getString("preco_sem_desconto"));
                whiskyList.add(whisky);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return whiskyList;
    }
    
    public List<Whisky> listSearchAll(String whisky_nome) throws SQLException {
        List<Whisky> whiskyList = new ArrayList<>();

        try ( PreparedStatement statement = connection.prepareStatement(GET_PRODUCTS_SEARCH_ALL)) {
            String whisky_nome_espaco = whisky_nome.replace(' ', '%');
            statement.setString(1, "%" + whisky_nome_espaco + "%");
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Whisky whisky = new Whisky();
                whisky.setNome(result.getString("nome"));
                whisky.setPrecoSemDesconto(result.getString("preco_sem_desconto"));
                whisky.setPrecoComDesconto(result.getString("preco_com_desconto"));
                whisky.setId(Integer.parseInt(result.getString("id")));
                whisky.setLojaNome(result.getString("nome_loja"));
                System.out.println(result.getString("preco_sem_desconto"));
                whiskyList.add(whisky);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return whiskyList;
    }


    public List<Whisky> listSearchDestilaria(String destilaria_nome) throws SQLException {
        List<Whisky> whiskyList = new ArrayList<>();

        try ( PreparedStatement statement = connection.prepareStatement(GET_PRODUCTS_SEARCH_DESTILARIA)) {
            statement.setString(1, destilaria_nome);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Whisky whisky = new Whisky();
                whisky.setNome(result.getString("nome"));
                whisky.setPrecoSemDesconto(result.getString("preco_sem_desconto"));
                whisky.setPrecoComDesconto(result.getString("preco_com_desconto"));
                whisky.setId(Integer.parseInt(result.getString("id")));
                whisky.setLojaNome(result.getString("loja_nome"));
                whiskyList.add(whisky);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return whiskyList;
    }

    public Double getMaiorPreco(String id) throws SQLException {
        Double maiorPreco;
        try ( PreparedStatement statement = connection.prepareStatement(GET_MAIOR_PRECO)) {
            statement.setInt(1, Integer.parseInt(id));
            statement.setInt(2, Integer.parseInt(id));
            try ( ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    maiorPreco = result.getDouble("maiorPreco");
                } else {
                    throw new SQLException("Erro ao visualizar: site não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return maiorPreco;
    }

    public Double getMenorPreco(String id) throws SQLException {
        Double menorPreco;
        try ( PreparedStatement statement = connection.prepareStatement(GET_MENOR_PRECO)) {
            statement.setInt(1, Integer.parseInt(id));
            statement.setInt(2, Integer.parseInt(id));
            try ( ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    menorPreco = result.getDouble("menorPreco");
                } else {
                    throw new SQLException("Erro ao visualizar: site não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgLojaDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return menorPreco;
    }

    @Override
    public void delete(String arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void update(Whisky arg0) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
}