/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var id;
function init(grid, tipousuario) {
    var table = $('#datatable').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
        },
        data: grid,
        columns: [
            {title: "Nombre"},
            {title: "Descripción"},
            {title: "Empresa"},
            {title: "Acciones"}
        ]
    });
    $(".select").select2();
    loadEmpresas(tipousuario);
}

function loadEmpresas(tipoUsuario) {
    if (tipoUsuario != 1) {
        $("#empresa").hide();
    } else {
        $("#empresa").show();
    }

}
function LoadServicioRiesgoInternoJson(id) {

    $(".modal-title").text("Editar");
    $("#gestion").prop("accion", "editar");
    $("#modal-esperar").modal("show");
    $.ajax({
        url: "LoadServicioRiesgosInternosJson.sga",
        data: {id: id}
    }).done(function (json) {
        $("#modal-esperar").modal("hide");
        $.each(json, function (key, data) {
            console.log(key + ": " + data)
            $("#" + key).val(data);
//            $("#" + key).text(data);
            $("#" + key).val(data).change();
            $("#" + key).attr("href", data);
            $("#" + key).attr("src", data);
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
function EliminarServicioRiesgoInterno(idServicioRiesgoInterno) {
    id = idServicioRiesgoInterno;
    $("#remove-modal").modal("show");
}
function confirmarEliminar() {
    $("#modal-esperar").modal("show");
    $.ajax({
        url: "DeleteServicioRiesgosInternos.sga",
        data: {id: id}
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        if (json.error) {
            $("#errorLabel").text(json.error);
            $("#modal-error").modal("show");
        } else {
            $("#remove-modal").modal('hide');
            $('#datatable').dataTable().fnClearTable();
            if (json.serviciosRI != '') {
                $('#datatable').dataTable().fnAddData(json.serviciosRI);
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
        action = 'CreateServicioRiesgosInternos.sga';
    } else if ($("#gestion").prop("accion") == 'editar') {
        action = 'EditServicioRiesgosInternos.sga';
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
                if (json.serviciosRI != '') {
                    $('#datatable').dataTable().fnAddData(json.serviciosRI);
                }
            }
        }
    });
});
     