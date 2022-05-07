package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.DAO;
import dao.DAOFactory;
import dao.ScriptDAO;
import model.Script;

@WebServlet(name = "scriptController", urlPatterns = {
        "/script",
        "/script/create",
        "/script/read",
        "/script/delete",
        "/script/download",
        "/script/run",
})
public class ScriptController extends HttpServlet {
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ScriptDAO dao;
        Script script;
        RequestDispatcher dispatcher;

        switch (request.getServletPath()) {
            case "/script": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    String lojaNome = request.getParameter("lojaNome");
                    dao = daoFactory.getScriptDAO();

                    List<Script> scriptList = dao.all(lojaNome);
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
                    String lojaNome = request.getParameter("lojaNome");
                    Date dataInsercao = Date.valueOf(request.getParameter("dataInsercao"));
                    dao.delete(lojaNome, dataInsercao);
                    response.sendRedirect(request.getContextPath() + "/script?=" + lojaNome);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/script");
                }
                break;
            }
            case "/script/read": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getScriptDAO();

                    script = dao.read(request.getParameter("lojaNome"));

                    Gson gson = new GsonBuilder().create();
                    String json = gson.toJson(script);

                    response.getOutputStream().print(json);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/script");
                }
                break;
            }
            case "/script/download": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    String lojaNome = request.getParameter("lojaNome");
                    Date dataInsercao = Date.valueOf(request.getParameter("dataInsercao"));

                    dao = daoFactory.getScriptDAO();
                    script = dao.read(lojaNome, dataInsercao);

                    try (PrintWriter out = response.getWriter()) {
                        // set the content type
                        response.setContentType("APPLICATION/OCTET-STREAM");
                        // force to download dialog
                        response.setHeader("Content-Disposition",
                                "attachment; filename=\"" + lojaNome + "_" + dataInsercao + ".py\"");

                        out.write(script.getCodigo());
                        out.close();
                    }
                    response.sendRedirect(request.getContextPath() + "/script?=" + lojaNome);

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/script");
                }
                break;
            }
            case "/script/run": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getScriptDAO();
                    String lojaNome = request.getParameter("lojaNome");
                    System.out.println(request.getParameter("lojaNome"));
                    // Timestamp dataInsercao =
                    // Timestamp.valueOf(request.getParameter("dataInsercao"));
                    // System.out.println(request.getParameter("dataInsercao"));
                    // TODO
                    // dao.run(lojaNome, dataInsercao);
                    dao.run(lojaNome, new Timestamp(System.currentTimeMillis()));
                    response.sendRedirect(request.getContextPath() + "/script?=" + lojaNome);
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
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DAO<Script> dao;
        Script script = new Script();
        String servletPath = request.getServletPath();

        switch (servletPath) {
            case "/script/create": {
                // Se fosse um form simples, usaria request.getParameter()
                String lojaNome = request.getParameter("lojaNome");
                String codigo = request.getParameter("codigo");

                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getScriptDAO();
                    script.setLojaNome(lojaNome);
                    script.setCodigo(codigo);
                    script.setDataInsercao(new Timestamp(System.currentTimeMillis()));
                    dao.create(script);
                    response.sendRedirect(request.getContextPath() + "/script?=" + lojaNome);
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(ScriptController.class.getName()).log(Level.SEVERE, null, ex);
                    response.sendRedirect(request.getContextPath() + "/script");
                }

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
