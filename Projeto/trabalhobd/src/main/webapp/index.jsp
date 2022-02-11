<%-- 
    Document   : index
    Created on : 30 de nov de 2021, 10:38:38
    Author     : dskaster
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <title>[WebSite App] Tela inicial</title>
    </head>
    <body>
        <div class="container">
            
                <p class="help-block">
                    <a href="${pageContext.servletContext.contextPath}/website/create">
                        <h2 class="form-signin-heading">Cadastrar site</h2>
                    </a>
                </p>
                
                 <p class="help-block">
                    <a href="${pageContext.servletContext.contextPath}/website">
                        <h2 class="form-signin-heading">Mostrar sites cadastrados</h2>
                    </a>
                </p>

                    
        </div>
                     
    </body>
</html>
