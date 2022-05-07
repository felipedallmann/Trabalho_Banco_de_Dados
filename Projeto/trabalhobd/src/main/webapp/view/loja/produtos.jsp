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
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>
                        <div>Pesquisa</div>
                        <form class="" action="${pageContext.servletContext.contextPath}/loja/produtos/whiskyPesquisa?"
                            method="GET">
                            <div><input type="text" name="whisky_nome" /></div>
                            <div><input type="hidden" name="loja_nome" value="${loja_nome}" /></div>
                            <div><input type="submit" value="Search"></div>
                        </form>
                    </th>
                </tr>
                <tr>
                    <th class="col-lg-5 h4">Nome do whisky</th>
                    <th class="col-lg-2 h6 ">Preço do whisky</th>
                    <th class="col-lg-2 h6 ">Loja nome</th>
                </tr>
            </thead>

            <c:forEach var="whisky" items="${requestScope.whiskyList}">
                <tbody>
                    <tr var="loja_nome" items="${requestScope.loja_nome}">
                        <td>
                            <a
                                href="${pageContext.servletContext.contextPath}/loja/produtos/whisky?id=${whisky.id}&nome=${loja_nome}">
                                <span class="h4">
                                    <c:out value="${whisky.nome}" /></span>
                            </a>
                        </td>

                        <td>
                            <span class="h4">
                                <c:out value="${whisky.precoSemDesconto}" /></span>
                        </td>

                        <td var="loja_nome" items="${requestScope.loja_nome}">
                            <span class="h4">
                                <c:out value="${loja_nome}" /></span>
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