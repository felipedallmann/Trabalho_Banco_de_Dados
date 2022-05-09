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
    <div>
        </table>
        <table class="table table-striped">
            <thead>
                <tr>
                    <th>
                        <div>Pesquisa</div>
                        <form class="" action="${pageContext.servletContext.contextPath}/whiskys/pesquisa?"
                            method="GET">
                            <div><input type="text" name="whisky_nome" /></div>
                            <div><input type="submit" value="Search"></div>
                        </form>
                    </th>
                </tr>
                
                <tr>
                    <th class="col-xs-3 h5 ">Quantidade de produtos</th>
                    <th class="col-xs-4 h5 ">Ultima atualização</th>
                    <th class="col-xs-3 h5 ">Quantidade de acessos</th>
                    <th class="col-xs-3 h5 ">Média de preço</th>

                </tr>

            </thead>
            <tbody>
                <tr>
                    <td var="qtdWhisky" items="${requestScope.qtdWhisky}>
                            <span class=" h4">
                        <c:out value="${qtdWhisky}" /></span>
                    </td>


                    <td var="ultimaAtt" items="${requestScope.UltimaAtt}>
                            <span class=" h4">
                        <c:out value="${ultimaAtt}" /></span>
                    </td>

                    <td var="qtdHistorico" items="${requestScope.qtdHistorico}>
                            <span class=" h4">
                        <c:out value="${qtdHistorico}" /></span>
                    </td>

                    <td var="mediaPreco" items="${requestScope.mediaPreco}>
                            <span class=" h4">
                        <c:out value="${mediaPreco}" /></span>
                    </td>

                </tr>
            </tbody>
            <tbody>
                <tr>

                </tr>
            </tbody>
        </table>

        <table class="table table-hover">
            <thead>
                <tr>
                    <th class="col-xs-5 h3">Nome do whisky</th>
                    <th class="col-xs-5 h5 ">Preço sem desconto</th>
                    <th class="col-xs-5 h5 ">Preço com desconto</th>
                    <th class="col-xs-5 h5 ">Loja nome</th>
                </tr>

            </thead>
            <c:forEach var="whisky" items="${requestScope.whiskyList}">
                <tbody>
                    <tr ">
                        <td>
                            <a href="
                        ${pageContext.servletContext.contextPath}/loja/produtos/whisky?id=${whisky.id}&nome=${whisky.lojaNome}">
                        <span class="h4">
                        <c:out value="${whisky.nome}" /></span>
                        </a>
                        </td>

                        <td>
                            <span class="h4">
                                <c:out value="${whisky.precoSemDesconto}" /></span>
                        </td>

                        <td>
                            <span class="h4">
                                <c:out value="${whisky.precoComDesconto}" /></span>
                        </td>

                        <td>
                            <a
                                href="${pageContext.servletContext.contextPath}/loja/produtos?loja_nome=${whisky.lojaNome}">
                                <span class="h4">
                                    <c:out value="${whisky.lojaNome}" /></span>
                            </a>
                        </td>
                    </tr>
                </tbody>
            </c:forEach>


    </div>
    <%@include file="/view/include/scripts.jsp"%>
    <script src="${pageContext.servletContext.contextPath}/assets/js/loja.js"></script>
</body>


</html>
