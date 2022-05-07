package controller;

import dao.DAOFactory;
import dao.PgHistoricoDAO;
import dao.PgWhiskyDAO;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Whisky;

@WebServlet(name = "WhiskyController", urlPatterns = {
        "/whiskys"

})
public class WhiskyController extends HttpServlet {

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
        RequestDispatcher dispatcher;
        PgWhiskyDAO whiskydao;
        PgHistoricoDAO historicodao;

        switch (request.getServletPath()) {
            case "/whiskys": {
                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    whiskydao = daoFactory.getWhiskyDAO();
                    historicodao = daoFactory.getHistoricoDAO();
                    List<Whisky> whiskyList = whiskydao.all();
                    int qtdWhiskys = whiskydao.getQtd();
                    int qtdHistorico = historicodao.getQtd();
                    Double mediaPreco = historicodao.mediaPreco();
                    Timestamp timestamp = historicodao.getUltimaAtt();
                    request.setAttribute("whiskyList", whiskyList);
                    request.setAttribute("qtdWhisky", qtdWhiskys);
                    request.setAttribute("qtdHistorico", qtdHistorico);
                    request.setAttribute("ultimaAtt", timestamp);
                    request.setAttribute("mediaPreco", mediaPreco);

                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }

                dispatcher = request.getRequestDispatcher("/view/whiskys/index.jsp");
                dispatcher.forward(request, response);
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

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
