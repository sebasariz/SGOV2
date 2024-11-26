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
            {title: "IP"},
            {title: "Nombre"},
            {title: "Fecha"},
            {title: "Actividad"}
        ]
    });
    $(".select").select2();

    $('#datetimepickerInicio').datetimepicker();
    $('#datetimepickerFin').datetimepicker();

    var date = new Date();
    var dateLastMount = new Date();
    dateLastMount.setMonth(dateLastMount.getMonth( ) - 1);
    $('#datetimepickerInicio').data("DateTimePicker").date(dateLastMount);
    $('#datetimepickerFin').data("DateTimePicker").date(date);
}

function loadReporte() {
    var fechaInicio = $("#datetimepickerInicio").data("DateTimePicker").date();
    var fechaFin = $("#datetimepickerFin").data("DateTimePicker").date();
    $("#modal-esperar").modal("show");
    $.ajax({
        type: "POST",
        cache: false,
        data: {
            fechaInicio: fechaInicio + "",
            fechaFin: fechaFin + ""
        },
        url: 'BuscarLogActividades.sga',
        success: function (jsonCompleto) {
            $("#modal-esperar").modal("hide");
            if (jsonCompleto.logs != '') {
                $('#datatable').dataTable().fnAddData(jsonCompleto.logs);
            }
        }
    });
}