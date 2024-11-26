/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var id;
function init(grid, idEmpresa) {

    var table = $('#datatable').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
        },
        buttons: ['copy', 'excel', 'pdf', 'print'],
        data: grid,
        columns: [
            {title: "Documento"},
            {title: "Nombre"},
            {title: "Apellidos"},
            {title: "Correo"},
            {title: "Tipo Usuario"},
            {title: "Acciones"}
        ]
    });
    $(".select").select2();

    if (idEmpresa == idInterfer) {
        $("#special-interer").show();
    } else {
        $("#special-interer").hide();
    }
}

function setVisible() {
    alert("this");
}
function LoadUsuarioJson(id) {

    $(".modal-title").text("Editar");
    $("#gestion").prop("accion", "editar");
    $("#modal-esperar").modal('show');
    $.ajax({
        url: "LoadUsuarioJson.sga",
        data: {id: id}
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        $.each(json, function (key, data) {
            console.log(key + ": " + data)
            $("#" + key).val(data);
            $("#" + key).val(data).change();
            $("#" + key).attr("href", data);

        });
        onEmpresaChange();
        $("#gestion-modal").modal("show");
    });
}



$("#add").on("click", function () {
    $("#gestion-modal").modal("show");
    $(".modal-title").text("Crear");
    $("#gestion").prop("accion", "crear");
});
function EliminarUsuario(idUsuario) {
    id = idUsuario;
    $("#remove-modal").modal("show");
}
function confirmarEliminar() {
    $("#modal-esperar").modal('show');
    $.ajax({
        url: "DeleteUsuario.sga",
        data: {id: id}
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        if (json.error) {
            $("#errorLabel").text(json.error);
            $("#modal-error").modal("show");
        } else {
            $("#remove-modal").modal('hide');
            $('#datatable').dataTable().fnClearTable();
            $('#datatable').dataTable().fnAddData(json.usuarios);

        }
    });
}
$("#gestion").submit(function (e) {
    e.preventDefault();

    var selected = [];
    $('#idEntidad :selected').each(function (i, sel) {
        selected.push($(sel).val());
    });
    $("#idEntidadString").val(JSON.stringify(selected));

    var formData = new FormData(this);
    //$("#modal-esperar").modal('show');
    var action = "";
    if ($("#gestion").prop("accion") == 'crear') {
        action = 'CreateUsuario.sga';
    } else if ($("#gestion").prop("accion") == 'editar') {
        action = 'EditUsuario.sga';
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
                $('#datatable').dataTable().fnAddData(json.usuarios);
            }
        }
    });
});
$("#empresaCliente").hide();
function loadEmpresas(tipoUsuario) {
    if (tipoUsuario != 5) {
        $("#empresaCliente").hide();
    } else {
        $("#empresaCliente").show();
    }

}

function onEmpresaChange() {
    var idEmpresa = $("#empresaCliente").val();
    if (idEmpresa == idInterfer) {
        $("#special-interer").show();
    } else {
        $("#special-interer").hide();
    }
}
