package controller;

import java.io.IOException;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dao.DAO;
import dao.DAOFactory;
import dao.PgWhiskyDAO;
import model.Loja;
import model.Whisky;

@WebServlet(name = "LojaController", urlPatterns = {
        "/loja",
        "/loja/create",
        "/loja/read",
        "/loja/produtos",
        "/loja/produtos/whisky",
        "/loja/produtos/whiskyPesquisa",
        "/loja/delete",

})
public class LojaController extends HttpServlet {

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
        DAO<Loja> dao;
        PgWhiskyDAO whiskydao;
        Loja loja;
        RequestDispatcher dispatcher;

        switch (request.getServletPath()) {
            case "/loja": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getLojaDAO();

                    List<Loja> lojaList = dao.all();
                    request.setAttribute("lojaList", lojaList);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                dispatcher = request.getRequestDispatcher("/view/loja/index.jsp");
                dispatcher.forward(request, response);
                break;
            }
            case "/loja/create": {
                dispatcher = request.getRequestDispatcher("/view/loja/create.jsp");
                dispatcher.forward(request, response);
                response.sendRedirect(request.getContextPath() + "/loja");
                break;
            }
            case "/loja/delete": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getLojaDAO();
                    dao.delete(request.getParameter("nome"));
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                response.sendRedirect(request.getContextPath() + "/loja");
                break;
            }
            case "/loja/produtos": {
                System.out.println(request.getParameter("loja_nome"));
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    whiskydao = daoFactory.getWhiskyDAO();
                    String nome = request.getParameter("loja_nome");

                    List<Whisky> whiskyList = whiskydao.listAll(nome);
                    request.setAttribute("whiskyList", whiskyList);
                    request.setAttribute("loja_nome", nome);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                dispatcher = request.getRequestDispatcher("/view/loja/produtos.jsp");
                dispatcher.forward(request, response);
                break;
            }
            case "/loja/produtos/whisky": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    whiskydao = daoFactory.getWhiskyDAO();
                    String id = request.getParameter("id");
                    String nome = request.getParameter("nome");
                    
                    // OBTENDO WHISKY ESPECIFICO
                    Whisky whisky = whiskydao.read(id);
                    // OBTENDO HISTÓRICO DE UM WHISKY ESPECIFICO
                    List<Whisky> whiskyList = whiskydao.listAllHistorico(nome, id);
                    Double maiorPreco = whiskydao.getMaiorPreco(id);
                    Double menorPreco = whiskydao.getMenorPreco(id);
                    request.setAttribute("whisky", whisky);
                    request.setAttribute("whiskyList", whiskyList);
                    request.setAttribute("maiorPreco", maiorPreco);
                    request.setAttribute("menorPreco", menorPreco);

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                dispatcher = request.getRequestDispatcher("/view/loja/whisky.jsp");
                dispatcher.forward(request, response);
                break;
            }
            case "/loja/produtos/whiskyPesquisa": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    whiskydao = daoFactory.getWhiskyDAO();
                    String whisky_nome = request.getParameter("whisky_nome");
                    String loja_nome = request.getParameter("loja_nome");

                    List<Whisky> whiskyList = whiskydao.listSearch(whisky_nome, loja_nome);
                    request.setAttribute("whiskyList", whiskyList);
                    request.setAttribute("loja_nome", loja_nome);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                dispatcher = request.getRequestDispatcher("/view/loja/produtos.jsp");
                dispatcher.forward(request, response);
                break;
            }
            case "/loja/read": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getLojaDAO();

                    loja = dao.read(request.getParameter("nome"));

                    Gson gson = new GsonBuilder().create();
                    String json = gson.toJson(loja);

                    response.getOutputStream().print(json);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/loja");
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
        DAO<Loja> dao;
        Loja loja = new Loja();
        HttpSession session = request.getSession();

        String servletPath = request.getServletPath();

        switch (request.getServletPath()) {

            case "/loja/create": {
                // Se fosse um form simples, usaria request.getParameter()
                String nome = request.getParameter("nome");
                String URL = request.getParameter("URL");

                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getLojaDAO();
                    loja.setNome(nome);
                    loja.setURL(URL);

                    if (servletPath.equals("/loja/create")) {
                        dao.create(loja);
                    }
                } catch (ClassNotFoundException | SQLException ex) {
                    Logger.getLogger(LojaController.class.getName()).log(Level.SEVERE, null, ex);
                }
                response.sendRedirect(request.getContextPath() + "/loja");

                break;
            }

            case "/loja/delete": {
                String[] lojas = request.getParameterValues("delete");

                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getLojaDAO();

                    try {
                        daoFactory.beginTransaction();

                        for (String nome : lojas) {
                            dao.delete(nome);
                        }

                        daoFactory.commitTransaction();
                        daoFactory.endTransaction();
                    } catch (SQLException ex) {
                        session.setAttribute("error", ex.getMessage());
                        daoFactory.rollbackTransaction();
                    }
                } catch (ClassNotFoundException | IOException ex) {
                    Logger.getLogger(LojaController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    session.setAttribute("error", ex.getMessage());
                } catch (SQLException ex) {
                    Logger.getLogger(LojaController.class.getName()).log(Level.SEVERE, "Controller", ex);
                    session.setAttribute("rollbackError", ex.getMessage());
                }

                response.sendRedirect(request.getContextPath() + "/loja");
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
