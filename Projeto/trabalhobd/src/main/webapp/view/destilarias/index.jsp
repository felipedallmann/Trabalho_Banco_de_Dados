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
    <tbody>
        <div class="container">

            <table class="table table-striped">
                <thead>
                    <tr>
                        <th class="col-lg-5 h4">Nome</th>
                        <th class="col-lg-4 h4 ">Pais</th>
                    </tr>
                </thead>
                <c:forEach var="destilarias" items="${requestScope.destilariasList}">
                    <tr>
                        <td>
                            <span class="h4">
                                <c:out value="${destilarias.nome}" /></span>
                        </td>
                        <td>
                            <span class="h4">
                                <c:out value="${destilarias.paisOrigemNome}" /></span>
                        </td>

                    </tr>
                    <br />
                </c:forEach>
            </table>

        </div>
    </tbody>
    <%@include file="/view/include/scripts.jsp"%>
    <script src="${pageContext.servletContext.contextPath}/assets/js/loja.js"></script>
</body>

</html>