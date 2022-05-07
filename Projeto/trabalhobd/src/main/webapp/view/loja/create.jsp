<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="/view/include/head.jsp"  %>
    <title>[WebSite App] Loja: cadastro</title>
</head>

<body>
    <div class="container">
        <h2 class="text-center">Inserção de um novo site</h2>

        <form class="form" action="${pageContext.servletContext.contextPath}/loja/create" enctype="form-data"
            method="POST">

            <div class="form-group">
                <label class="control-label" for="usuario-login">Nome do site</label>
                <input id="nome" class="form-control" type="text" name="nome" required autofocus />

                <p class="help-block"></p>
            </div>


            <div class="form-group">
                <label class="control-label" for="usuario-login">URL</label>
                <input id="URL" class="form-control" type="text" name="URL" required autofocus />

                <p class="help-block"></p>
            </div>


            <div class="text-center">
                <button class="btn btn-lg btn-primary" type="submit">Salvar</button>
            </div>
        </form>
    </div>

    <%@include file="/view/include/scripts.jsp"%>
    <script src="${pageContext.servletContext.contextPath}/assets/js/loja.js"></script>
</body>

</html>