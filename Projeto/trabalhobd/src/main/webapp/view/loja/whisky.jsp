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
                            <th class="col-lg-1 h4">Nome</th>
                            <th class="col-lg-3 h4">Idade</th>
                            <th class="col-lg-3 h4">Destilaria</th>
                            <th class="col-lg-2 h4 ">Pais de origem</th>
                            <th class="col-lg-2 h4 ">Teor alcoolico</th>
                            <th class="col-lg-2 h4 ">Loja nome</th>
                        </tr>
                </thead>
                 <tbody  var="whisky" items="${requestScope.whisky}">
                   <tr>
                        <td>
                           <span class="h4"><c:out value="${whisky.nome}"/></span>
                            </a>
                        </td>
                                
                        <td >
                            <span class="h4"><c:out value="${whisky.idade}"/></span>
                        </td>
                        
                        <td >
                            <span class="h4"><c:out value="${whisky.destilariaNome}"/></span>
                        </td>
                        
                        <td >
                            <span class="h4"><c:out value="${whisky.paisOrigemNome}"/></span>
                        </td>
                        
                        <td >
                            <span class="h4"><c:out value="${whisky.teorAlcolico}"/></span>
                        </td>
                        
                        <td var="loja_nome" items="${requestScope.whisky}">
                            <span class="h4"><c:out value="${whisky.teorAlcolico}"/></span>
                        </td>
                  </tr>
                 </tbody>
                 
            </table>
                        <br>
                        <br>
                        <br>
                        <br>
            <table  class="table table-hover">        
                <thead>
                        <tr>
                            <th class="col-md-8 h1">Histórico</th>
                            <th class="col-md-8 h1">Preco</th>
                            <th class="col-md-8 h1">Data</th>
                        </tr>
                </thead>
                <c:forEach var="whiskyItem" items="${requestScope.whiskyList}">
                 <tbody>
                   <tr var="loja_nome" items="${requestScope.loja_nome}">
                        <td>
                                <span class="h4"><c:out value="${whiskyItem.nome}"/></span>
                            </a>
                        </td>
                                
                        <td >
                            <span class="h4"><c:out value="${whiskyItem.precoSemDesconto}"/></span>
                        </td>
                        
                        <td >
                            <span ><c:out value="${whiskyItem.acessadoEm}"/></span>
                        </td>
                  </tr>
                 </tbody>
                </c:forEach>             </table>
        </div>      
        <%@include file="/view/include/scripts.jsp"%>
        <script src="${pageContext.servletContext.contextPath}/assets/js/loja.js"></script>
    </body>


</html>


