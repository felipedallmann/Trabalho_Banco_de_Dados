/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.DAO;
import dao.DAOFactory;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.WebSite;

/**
 *
 * @author Usuario
 */
@WebServlet( name = "WebSiteController",
        urlPatterns = {
            "/website",
            "/website/create",
            "/website/read",
            "/website/delete",
           
        })
public class WebSiteController extends HttpServlet {



    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO<WebSite> dao;
        WebSite webSite;
        RequestDispatcher dispatcher;
        
         switch (request.getServletPath()) {
            case "/website": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getWebSiteDAO();

                    List<WebSite> webSiteList = dao.all();
                    request.setAttribute("webSiteList", webSiteList);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                dispatcher = request.getRequestDispatcher("/view/website/index.jsp");
                dispatcher.forward(request, response);
                break;
            }
            case "/website/create": {
                dispatcher = request.getRequestDispatcher("/view/website/create.jsp");
                dispatcher.forward(request, response);
                response.sendRedirect(request.getContextPath() + "/website");
                break;
            }
            case "/website/delete": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getWebSiteDAO();
                    dao.delete(Integer.parseInt(request.getParameter("id")));
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                response.sendRedirect(request.getContextPath() + "/website");
                break;
            }  
            
            case "/website/read": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getWebSiteDAO();

                    webSite = dao.read(Integer.parseInt(request.getParameter("id")));

                    Gson gson = new GsonBuilder().create();
                    String json = gson.toJson(webSite);

                    response.getOutputStream().print(json);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/website");
                }
                break;
            }
         }
        
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            DAO<WebSite> dao;
            WebSite webSite = new WebSite();
            HttpSession session = request.getSession();

        String servletPath = request.getServletPath();        
        
        switch (request.getServletPath()) {

            case "/website/create":{
                // Se fosse um form simples, usaria request.getParameter()
                String nome = request.getParameter("nome");
                String URL = request.getParameter("URL");

                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getWebSiteDAO();
                    webSite.setNome(nome);
                    webSite.setURL(URL);
                    
                    
                    dao = daoFactory.getWebSiteDAO();

                    if (servletPath.equals("/website/create")) {
                        dao.create(webSite);
                    }                     
                    } catch (ClassNotFoundException ex) {
                    Logger.getLogger(WebSiteController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(WebSiteController.class.getName()).log(Level.SEVERE, null, ex);
                }
                response.sendRedirect(request.getContextPath() + "/website");

                break;
            }
            
            case "/website/delete": {
                String[] webSites = request.getParameterValues("delete");

                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getWebSiteDAO();

                    try {
                        daoFactory.beginTransaction();

                        for (String webSiteId : webSites) {
                            dao.delete(Integer.parseInt(webSiteId));
                        }

                        daoFactory.commitTransaction();
                        daoFactory.endTransaction();
                    } catch (SQLException ex) {
                        session.setAttribute("error", ex.getMessage());
                        daoFactory.rollbackTransaction();
                    }
                } catch (ClassNotFoundException | IOException ex) {
                    Logger.getLogger(WebSiteController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    session.setAttribute("error", ex.getMessage());
                } catch (SQLException ex) {
                    Logger.getLogger(WebSiteController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    session.setAttribute("rollbackError", ex.getMessage());
                }

                response.sendRedirect(request.getContextPath() + "/website");
                break;
            }
            
            
        }   
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
