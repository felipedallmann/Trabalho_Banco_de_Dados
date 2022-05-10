<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/view/include/head.jsp"  %>        
        <title>[WebSite App] Scripts</title>
    </head>
    <body>
        <tbody>
        <div class="container">
             <div class="text-center div_inserir_excluir">
            <h1 class="h1">Histórico de execução para a loja ${pageContext.request.getParameter("lojaNome")}</h1>
               
            </div>
            <form class="form_excluir_websites" action="${pageContext.servletContext.contextPath}/script/delete" method="POST">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th class="col-lg-4 h4 ">Data execução</th>
                            <th class="col-lg-1 h4 ">Download</th>
                            <th class="col-lg-1 h4 ">Executar</th>
                            <th class="col-lg-1 h4 ">Excluir</th>
                        </tr>
                    </thead>    
                    <c:forEach var="history" items="${requestScope.historyList}">
                            <tr>
                                <td >
                                    <span class="h4"><c:out value="${history.dataExecucao}"/>    </span>
                                </td>
                                <td class="text-center">
                                    <a class="btn btn-default"
                                       href="${pageContext.servletContext.contextPath}/script/download?lojaNome=${history.script.lojaNome}&dataInsercao=${history.script.dataInsercao}"
                                       data-original-title="Script">
                                        <i class="fa fa-download"></i>
                                    </a>
                                </td>
                                <td class="text-center">
                                    <a class="btn btn-default"
                                       href="${pageContext.servletContext.contextPath}/script/run?lojaNome=${history.script.lojaNome}&dataInsercao=${history.script.dataInsercao}"
                                       data-original-title="Script">
                                        <i class="fa fa-play"></i>
                                    </a>
                                </td>
                                <td class="text-center">
                                    <a class="btn btn-default link_excluir_website"
                                       href="${pageContext.servletContext.contextPath}/script/history/delete?lojaNome=${history.script.lojaNome}&dataInsercao=${history.script.dataInsercao}&dataExecucao=${history.dataExecucao}"
                                       data-toggle="tooltip"
                                       data-original-title="Excluir">
                                        <i class="fa fa-trash"></i>
                                    </a>
                                </td>
                            </tr>
                            <br/>
                        </c:forEach>
                    </table>
                
            </form>
                            
     
                                
        </div>      
        </tbody>
        <%@include file="/view/include/scripts.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/website.js"></script>
</body>
</html>
