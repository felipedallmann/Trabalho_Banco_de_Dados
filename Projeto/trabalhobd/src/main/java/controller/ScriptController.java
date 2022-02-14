package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.DAO;
import dao.DAOFactory;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
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
import model.Script;

/**
 *
 * @author Usuario
 */
@WebServlet( name = "scriptController",
        urlPatterns = {
            "/script",
            "/script/create",
            "/script/read",
            "/script/delete",
           
        })
public class ScriptController extends HttpServlet {
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
        DAO<Script> dao;
        Script script;
        RequestDispatcher dispatcher;
        
        switch (request.getServletPath()) {
            case "/script": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getScriptDAO();

                    List<Script> scriptList = dao.all();
                    request.setAttribute("scriptList", scriptList);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                dispatcher = request.getRequestDispatcher("/view/script/index.jsp");
                dispatcher.forward(request, response);
                break;
            }
            case "/script/create": {
                dispatcher = request.getRequestDispatcher("/view/script/create.jsp");
                dispatcher.forward(request, response);
                response.sendRedirect(request.getContextPath() + "/script");
                break;
            }
            case "/script/delete": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getScriptDAO();
                    dao.delete(request.getParameter("nome"));
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                response.sendRedirect(request.getContextPath() + "/script");
                break;
            }  
            
            case "/script/read": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getScriptDAO();

                    script = dao.read(request.getParameter("nome"));

                    Gson gson = new GsonBuilder().create();
                    String json = gson.toJson(script);

                    response.getOutputStream().print(json);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/script");
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
        DAO<Script> dao;
        Script script = new Script();
        String servletPath = request.getServletPath();        
        
        switch (servletPath) {
            case "/script/create":{
                // Se fosse um form simples, usaria request.getParameter()
                String lojaNome = request.getParameter("lojaNome");
                String codigo = request.getParameter("codigo");

                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getScriptDAO();
                    script.setLojaNome(lojaNome);
                    script.setCodigo(codigo);
                    script.setDataInsercao(new Date(System.currentTimeMillis()));
                    dao.create(script);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(ScriptController.class.getName()).log(Level.SEVERE, null, ex);
                }
                response.sendRedirect(request.getContextPath() + "/script");

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
