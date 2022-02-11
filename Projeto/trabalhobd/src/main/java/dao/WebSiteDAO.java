/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import java.sql.SQLException;
import model.WebSite;

/**
 *
 * @author dskaster
 */
public interface WebSiteDAO extends DAO<WebSite> {

    public WebSite getByName(String name) throws SQLException;
    
}
