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
            {title: "Fecha ultima conexion"},
            {title: "tipo"},
            {title: "DeviceIOT"},
            {title: "Acciones"}
        ]
        , 'rowCallback': function (row, data, index) {
            var dateUltimaFecha = moment(data[1], "dd-MM-yyyy hh:mm a").toDate().getTime();
            var OneDay = new Date().getTime() - (1 * 24 * 60 * 60 * 1000);
            var halfDay = new Date().getTime() - (1 * 12 * 60 * 60 * 1000);  
            if (OneDay > dateUltimaFecha) {
                //rojo
                $(row).find('td:eq(1)').css('color', 'white').css('background', '#C0B962').css('vertical-align', 'middle');
            } else if ( halfDay > dateUltimaFecha) {
                //amarillo
                $(row).find('td:eq(1)').css('color', 'white').css('background', '#C06862').css('vertical-align', 'middle');
            } else {
                //verde
                $(row).find('td:eq(1)').css('color', 'white').css('background', '#1d9432').css('vertical-align', 'middle');
            }
        }
    });
    $(".select").select2();
}
function LoadDeviceIOTJson(id) {

    $(".modal-title").text("Editar");
    $("#gestion").prop("accion", "editar");
    $("#modal-esperar").modal("show");
    $.ajax({
        url: "LoadDeviceIOTJson.sga",
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
function EliminarDeviceIOT(idDeviceIOT) {
    id = idDeviceIOT;
    $("#remove-modal").modal("show");
}
function confirmarEliminar() {
    $("#modal-esperar").modal("show");
    $.ajax({
        url: "DeleteDeviceIOT.sga",
        data: {id: id}
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        if (json.error) {
            $("#errorLabel").text(json.error);
            $("#modal-error").modal("show");
        } else {
            $("#remove-modal").modal('hide');
            $('#datatable').dataTable().fnClearTable();
            if (json.dispositivos != '') {
                $('#datatable').dataTable().fnAddData(json.dispositivos);
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
        action = 'CreateDeviceIOT.sga';
    } else if ($("#gestion").prop("accion") == 'editar') {
        action = 'EditDeviceIOT.sga';
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
                if (json.dispositivos != '') {
                    $('#datatable').dataTable().fnAddData(json.dispositivos);
                }
            }
        }
    });
});

     