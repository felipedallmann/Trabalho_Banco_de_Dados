/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */


function deleteWebSite(e) {
    e.preventDefault();
    $('.link_confirmacao_excluir_website').attr('href', $(this).data('href'));
    $('.modal_excluir_website').modal();
}

function deleteWebSites(e) {
    e.preventDefault();
    $('.form_excluir_websites').submit();
}

function readWebSite(e) {
    e.preventDefault();
    $.get($(this).data('href'), function (data) {
        var webSite = JSON.parse(data);
        var $modal = $('.modal-visualizar-website');

        $modal.find(".p_nome").html('<strong>Nome: </strong>' + webSite.nome);
        $modal.find('.p_url').html('<strong>URL: </strong>' + webSite.URL);
        $modal.modal();
    });
}

$(document).ready(function () {
    $(document).on('click', '.link_excluir_website', deleteWebSite);
    $(document).on('click', '.link_visualizar_website', readWebSite);
    $(document).on('click', '.button_confirmacao_excluir_websites', deleteWebSites);
//    $("*[data-toggle='tooltip']").tooltip({
//        'container': 'body'
//    });
});