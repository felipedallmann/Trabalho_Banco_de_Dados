/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;

/**
 *
 * @author dskaster
 */
public class PgDAOFactory extends DAOFactory {

    public PgDAOFactory(Connection connection) {
        this.connection = connection;
    }

    @Override
    public LojaDAO getLojaDAO() {
        return new PgLojaDAO(this.connection);
    }

    @Override
    public ScriptDAO getScriptDAO() {
        return new ScriptDAO(this.connection);
    }
}
