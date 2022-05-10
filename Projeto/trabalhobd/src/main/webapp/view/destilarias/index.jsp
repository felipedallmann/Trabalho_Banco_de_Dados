<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="/view/include/head.jsp"  %>
    <title>[WebSite App] Destilarias</title>
</head>
<body>
    <script>

    </script>
    <div>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th class="col-xs-5 h3">Nome</th>
                </tr>
            </thead>
            <c:forEach var="destilarias" items="${requestScope.destilariasList}">
                <tbody>
                    <tr>
                        <td>
                            <a href="
                        ${pageContext.servletContext.contextPath}/whiskys/filtrarDestilaria?destilaria_nome=${destilarias.nome}">
                            <span class="h4">
                                <c:out value="${destilarias.nome}" />
                            </span>
                        </td>
                    </tr>
                </tbody>
            </c:forEach>


    </div>
    <%@include file="/view/include/scripts.jsp"%>
    <script src="${pageContext.servletContext.contextPath}/assets/js/loja.js"></script>
</body>


</html>