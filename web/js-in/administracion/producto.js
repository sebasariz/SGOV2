/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var id;
function init(grid) {
    var table = $('#datatable').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
        },
        data: grid,
        columns: [
            {title: "Nombre"},
            {title: "Referencia"},
            {title: "Cantidad"},
            {title: "Vlr/unidad"}, 
            {title: "Acciones"}
        ]
    });
    $(".select").select2();
}
function LoadProductoJson(id) {

    $(".modal-title").text("Editar");
    $("#gestion").prop("accion", "editar");
    $("#modal-esperar").modal('show');
    $.ajax({
        url: "LoadProductoJson.sga",
        data: {id: id}
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        $.each(json, function (key, data) {
            console.log(key + ": " + data)
            $("#" + key).val(data);
            $("#" + key).val(data).change();
            $("#" + key).attr("href", data);
        });
        $("#gestion-modal").modal("show");
    });
}



$("#add").on("click", function () {
    $("#gestion-modal").modal("show");
    $(".modal-title").text("Crear");
    $("#gestion").prop("accion", "crear");
});
$("#cargar").on("click", function () {
    $("#gestion-modal-load").modal("show");
});
function EliminarProducto(idProducto) {
    id = idProducto;
    $("#remove-modal").modal("show");
}
function confirmarEliminar() {
    $("#modal-esperar").modal('show');
    $.ajax({
        url: "DeleteProducto.sga",
        data: {id: id}
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        if (json.error) {
            $("#errorLabel").text(json.error);
            $("#modal-error").modal("show");
        } else {
            $("#remove-modal").modal('hide');
            $('#datatable').dataTable().fnClearTable();
            if (json.productos != '') {
                $('#datatable').dataTable().fnAddData(json.productos);
            }

        }
    });
}
$("#gestion").submit(function (e) {
    e.preventDefault();
    var formData = new FormData(this);
    //$("#modal-esperar").modal('show');
    var action = "";
    if ($("#gestion").prop("accion") == 'crear') {
        action = 'CreateProducto.sga';
    } else if ($("#gestion").prop("accion") == 'editar') {
        action = 'EditProducto.sga';
    }
    $("#modal-esperar").modal('show');
    $.ajax({
        type: "POST",
        cache: false,
        url: action,
        data: formData,
        contentType: false,
        processData: false,
        success: function (json) {
            $("#modal-esperar").modal('hide');
            if (json.error) {
                $("#errorLabel").text(json.error);
                $("#modal-error").modal("show");
            } else {
                $("#gestion-modal").modal('hide');
                $('#datatable').dataTable().fnClearTable();
                if (json.productos != '') {
                    $('#datatable').dataTable().fnAddData(json.productos);
                }
            }
        }
    });
});

$("#load-masiva").submit(function (e) {
    e.preventDefault();
    var formData = new FormData(this);
    $("#modal-esperar").modal('show');
    $.ajax({
        type: "POST",
        cache: false,
        url: "MasivaProductos.sga",
        data: formData,
        contentType: false,
        processData: false,
        success: function (json) {
            $("#modal-esperar").modal('hide');
            if (json.error) {
                $("#errorLabel").text(json.error);
                $("#modal-error").modal("show");
            } else {
                $("#gestion-modal-load").modal('hide');
                $('#datatable').dataTable().fnClearTable();
                if (json.productos != '') {
                    $('#datatable').dataTable().fnAddData(json.productos);
                }
            }
        }
    });
});