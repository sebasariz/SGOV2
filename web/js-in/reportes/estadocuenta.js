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
         "order": [[6, "asc"]],
        data: grid,
        columns: [
            {title: "Codigo"},
            {title: "Nit"},
            {title: "Nombre"},
            {title: "Direccion"},
            {title: "Fecha ultimo pedido"},
            {title: "Fecha ultimo pago"},
            {title: "Saldo", type: 'currency', targets: 0},
            {title: "Acciones", width: "80px"}
        ]
    });
    $(".select").select2();
    $('#datetimepicker1').datetimepicker({defaultDate: new Date()});
    $("#fecha").val(new Date().getTime());
    $('#datetimepicker1').on('dp.change', function (e) {
        $("#fecha").val(e.timeStamp);
    });

    var table = $('#datatable-historial').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
        },
        columns: [
            {title: "Fecha"},
            {title: "Valor", type: 'currency', targets: 0},
            {title: "Acciones", width: "80px"}
        ]
    });

}

function LoadHisotiralPagos(idEmpresa) {
    $("#modal-esperar").modal('show');
    $.ajax({
        type: "POST",
        cache: false,
        url: "LoadHistorialPagosEmpresa.sga",
        data: {idEmpresa: idEmpresa},
        success: function (json) {
            $("#modal-esperar").modal('hide');
            if (json.error) {
                $("#errorLabel").text(json.error);
                $("#modal-error").modal("show");
            } else {
                $("#gestion-modal-historial").modal('show');
                $('#datatable-historial').dataTable().fnClearTable();
                $('#datatable-historial').dataTable().fnAddData(json.historial);
            }
        }
    });
}
function GenerarPago(idEmpresa) {
    $("#idEmpresa").val(idEmpresa);
    $("#gestion-modal-pago").modal("show");
}

$("#gestion-pago").submit(function (e) {
    e.preventDefault();
    var formData = new FormData(this);
    $("#modal-esperar").modal('show');
    $.ajax({
        type: "POST",
        cache: false,
        url: "CreatePagoEmpresa.sga",
        data: formData,
        contentType: false,
        processData: false,
        success: function (json) {
            $("#modal-esperar").modal('hide');
            if (json.error) {
                $("#errorLabel").text(json.error);
                $("#modal-error").modal("show");
            } else {
                $("#gestion-modal-pago").modal('hide');
                $('#datatable').dataTable().fnClearTable();
                $('#datatable').dataTable().fnAddData(json.empresas);
            }
        }
    });
});

var id;
function EliminarPago(idPago) {
    id = idPago;
    $("#remove-modal").modal("show");
}
function confirmarEliminar() {
    $("#modal-esperar").modal('show');
    $.ajax({
        url: "EliminarPago.sga",
        data: {id: id}
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        if (json.error) {
            $("#errorLabel").text(json.error);
            $("#modal-error").modal("show");
        } else {
            $("#remove-modal").modal('hide');
            $('#datatable-historial').dataTable().fnClearTable();
            $('#datatable-historial').dataTable().fnAddData(json.historial);

        }
    });
}