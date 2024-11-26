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
            {title: "Nit"},
            {title: "Nombre"},
            {title: "Direccion"},
            {title: "Contacto"},
            {title: "Telefono"},
            {title: "Tipo"},
            {title: "Acciones"}
        ]
    });
    $(".select").select2();
}
function LoadEmpresaJson(id) {

    $(".modal-title").text("Editar");
    $("#gestion").prop("accion", "editar");
    $("#modal-esperar").modal("show");
    $.ajax({
        url: "LoadEmpresaJson.sga",
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
function EliminarEmpresa(idEmpresa) {
    id = idEmpresa;
    $("#remove-modal").modal("show");
}
function confirmarEliminar() {
    $("#modal-esperar").modal("show");
    $.ajax({
        url: "DeleteEmpresa.sga",
        data: {id: id}
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        if (json.error) {
            $("#errorLabel").text(json.error);
            $("#modal-error").modal("show");
        } else {
            $("#remove-modal").modal('hide');
            $('#datatable').dataTable().fnClearTable();
            if (json.empresas != '') {
                $('#datatable').dataTable().fnAddData(json.empresas);
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
        action = 'CreateEmpresa.sga';
    } else if ($("#gestion").prop("accion") == 'editar') {
        action = 'EditEmpresa.sga';
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
                if (json.empresas != '') {
                    $('#datatable').dataTable().fnAddData(json.empresas);
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
        url: "MasivaCliente.sga",
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
            }
        }
    });
});

function LoadAprobarEmpresa(idEmpresa) {
    //show modal
    id=idEmpresa;
    $("#aprobar-modal").modal("show");
}

function AprobarEmpresa() {
    //enviamos la aprobacion y cargamos la lista
    $("#modal-esperar").modal('show');
    $.ajax({
        type: "POST",
        cache: false,
        url: "AprobarCliente.sga",
        data: {id: id}, 
        success: function (json) {
            $("#modal-esperar").modal('hide');
            if (json.error) {
                $("#errorLabel").text(json.error);
                $("#modal-error").modal("show");
            } else {
                $("#gestion-modal-load").modal('hide');
                $('#datatable').dataTable().fnClearTable();
                if (json.empresas != '') {
                    $('#datatable').dataTable().fnAddData(json.empresas);
                }
            }
        }
    });
}


function loadTipoEmpresa(tipo){
    if(tipo==1){
        $(".css").show();
    }else{
        $(".css").hide();
    }
}
loadTipoEmpresa($("#tipoEmpresa").val());

