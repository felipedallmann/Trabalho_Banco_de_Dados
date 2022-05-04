<%-- 
    Document   : produtos
    Created on : 14 de fev. de 2022, 20:35:38
    Author     : Usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/view/include/head.jsp"  %>        
        <title>[WebSite App] Produtos</title>
    </head>
    
    <body>
        <script> 
            
        </script>
        <div class="container"> 
            <table  class="table table-hover">
                 <thead>
                     <tr>
                        <th>
                          <div>Pesquisa</div>
                          <div><input/></div>
                        </th>
                    </tr>
                        <tr>
                            <th class="col-lg-5 h4">Nome do whisky</th>
                            <th class="col-lg-2 h6 ">Preço do whisky</th>
                        </tr>
                </thead>
                
               <c:forEach var="whisky" items="${requestScope.whiskyList}">
                 <tbody>
                   <tr>
                        <td>
                            <a href="" >
                           <span class="h4"><c:out value="${whisky.nome}"/></span>
                            </a>
                        </td>
                                
                        <td >
                            <span class="h4"><c:out value="${whisky.precoSemDesconto}"/></span>
                        </td>
                  </tr>
                 </tbody>
                </c:forEach>
                 
            </table>
        </div>      
        <%@include file="/view/include/scripts.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/loja.js"></script>
    </body>


</html>


