package dao;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import model.Script;
import model.Whisky;

public interface WhiskyDAO extends DAO<Whisky> {
    @Override
    public void create(Whisky whisky) throws SQLException;
    
    @Override
    public void delete(String arg0) throws SQLException;
    
    @Override
    public void update(Whisky arg0) throws SQLException;

    @Override
    public Whisky read(String arg0) throws SQLException;

    public List<Whisky> all() throws SQLException;

    public List<Whisky> listAll(String l) throws SQLException;

}
