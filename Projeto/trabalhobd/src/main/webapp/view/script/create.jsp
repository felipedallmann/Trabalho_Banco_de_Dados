<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/view/include/head.jsp"  %>
        <title>[WebSite App] Script: cadastro</title>
    </head>
    <body>
       <div class="container">
            <h2 class="text-center">Novo script para a loja </h2>

            <form
                class="form"
                action="${pageContext.servletContext.contextPath}/script/create"
                method = "POST"
                enctype = "multipart/form-data">

                <div class="form-group">
                    <input value="${pageContext.request.getParameter("lojaNome")}" id="URL" class="form-control" type="hidden" name="lojaNome" required />
                    <p class="help-block"></p>
                </div>
                
                <div class="form-group">
                    <label class="control-label" for="codigo">Codigo: </label>
                    <input type = "file" name = "codigo" size = "50" />
                    <p class="help-block"></p>
                </div>


                <div class="text-center">
                    <button class="btn btn-lg btn-primary" type="submit">Adicionar</button>
                </div>
            </form>
        </div>
                
        <%@include file="/view/include/scripts.jsp"%>
    </body>
</html>
