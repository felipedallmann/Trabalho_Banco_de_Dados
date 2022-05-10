<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <%@include file="/view/include/head.jsp"  %>
    <title>[WebSite App] Sites</title>
</head>

<body>
    <tbody>
        <div class="container">
            <div class="text-center div_inserir_excluir">
                <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/loja/create">
                    Inserir novo site
                </a>

                <a class="btn btn-lg btn-primary" href="${pageContext.servletContext.contextPath}/whiskys">
                    Mostrar todos produtos
                </a>

                <button class="btn btn-lg btn-warning" data-toggle="modal" data-target=".modal_excluir_websites">
                    Excluir múltiplos sites
                </button>
            </div>
            <form class="form_excluir_websites" action="${pageContext.servletContext.contextPath}/loja/delete"
                method="POST">
                <table class="table table-striped">
                    <thead>
                        <tr>
                            <th class="col-lg-5 h4">Nome</th>
                            <th class="col-lg-4 h4 ">URL</th>
                            <th class="col-lg-1 h4 ">Histórico de execução</th>
                            <th class="col-lg-1 h4 ">Scripts</th>
                            <th class="col-lg-1 h4 ">Excluir</th>
                        </tr>
                    </thead>
                    <c:forEach var="loja" items="${requestScope.lojaList}">
                        <tr>
                            <td>
                                <a
                                    href="${pageContext.servletContext.contextPath}/loja/produtos?loja_nome=${loja.nome}">
                                    <span class="h4">
                                        <c:out value="${loja.nome}" /></span>
                                </a>
                            </td>
                            <td>
                                <a href="${loja.URL}">
                                    <span class="h4">
                                        <c:out value="${loja.URL}" /></span>
                                </a>
                            </td>
                            <td class="text-center">
                                <a class="btn btn-default"
                                    href="${pageContext.servletContext.contextPath}/script/history?lojaNome=${loja.nome}"
                                    data-original-title="Script">
                                    <i class="fa fa-history"></i>
                                </a>
                            </td>
                            <td class="text-center">
                                <a class="btn btn-default"
                                    href="${pageContext.servletContext.contextPath}/script?lojaNome=${loja.nome}"
                                    data-original-title="Script">
                                    <i class="fa fa-file"></i>
                                </a>
                            </td>
                            <td class="text-center">
                                <a class="btn btn-default link_excluir_website" href="#"
                                    data-href="${pageContext.servletContext.contextPath}/loja/delete?nome=${loja.nome}"
                                    data-toggle="tooltip" data-original-title="Excluir">
                                    <i class="fa fa-trash"></i>
                                </a>
                            </td>
                            <td class="text-center">
                                <input class="checkbox-inline" type="checkbox" name="delete" value="${loja.nome}" />
                            </td>
                        </tr>
                        <br />
                    </c:forEach>
                </table>

                <div class="modal fade modal_excluir_websites">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <h4 class="modal-title">Confirmação</h4>
                                <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                            </div>
                            <div class="modal-body">
                                <p>Tem certeza de que deseja excluir os sites selecionados?</p>
                            </div>
                            <div class="modal-footer">
                                <button class="btn btn-danger button_confirmacao_excluir_websites"
                                    type="button">Sim</button>
                                <button class="btn btn-primary" type="button" data-dismiss="modal">Não</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>

            <div class="modal fade modal_excluir_website">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Confirmação</h4>
                            <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                        </div>
                        <div class="modal-body">
                            <p>Tem certeza de que deseja excluir este usuário?</p>
                        </div>
                        <div class="modal-footer">
                            <a class="btn btn-danger link_confirmacao_excluir_website">Sim</a>
                            <button class="btn btn-primary" type="button" data-dismiss="modal">Não</button>
                        </div>
                    </div>
                </div>
            </div>



            <div class="modal modal-visualizar-website">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">Detalhes</h4>
                            <button class="close" type="button" data-dismiss="modal"><span>&times;</span></button>
                        </div>
                        <div class="modal-body">
                            <div class="container-fluid">
                                <div class="row">
                                    <div class="col-md-8">
                                        <p class="p_nome"></p>
                                        <p class="p_url"></p>
                                        <a href="">Exibir produtos</a>
                                    </div>

                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-primary" type="button" data-dismiss="modal">Fechar</button>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </tbody>
    <%@include file="/view/include/scripts.jsp"%>
    <script src="${pageContext.servletContext.contextPath}/assets/js/loja.js"></script>
</body>

</html>