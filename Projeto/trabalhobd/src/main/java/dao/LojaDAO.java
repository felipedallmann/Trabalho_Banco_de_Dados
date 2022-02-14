/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.sql.SQLException;
import model.Loja;

/**
 *
 * @author dskaster
 */
public interface LojaDAO extends DAO<Loja> {

    public Loja getByName(String name) throws SQLException;
    
}
