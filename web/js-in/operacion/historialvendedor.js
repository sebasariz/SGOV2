/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var id;
function init() {
    table = $('#datatable').DataTable(optionsTable);
    $(".select").select2();
    $('#datetimepickerInicio').datetimepicker();
    $('#datetimepickerFin').datetimepicker();
    var date = new Date();
    var dateLastMount = new Date();
    dateLastMount.setMonth(dateLastMount.getMonth( ) - 1);
    $('#datetimepickerInicio').data("DateTimePicker").date(dateLastMount);
    $('#datetimepickerFin').data("DateTimePicker").date(date);
}
function search() {
    var fechaInicio = $("#datetimepickerInicio").data("DateTimePicker").date();
    var fechaFin = $("#datetimepickerFin").data("DateTimePicker").date();
    var idVendedor = $("#idvendedor").val();
    $("#modal-esperar").modal("show");
    $.ajax({
        url: "LoadHistorialVendedorJson.sga",
        data: {
            idVendedor: idVendedor,
            fechaInicio: fechaInicio + "",
            fechaFin: fechaFin + ""}
    }).done(function (json) {
        $("#modal-esperar").modal("hide");
        $('#datatable').dataTable().fnClearTable();
        if (json.historial != '') {
            $('#datatable').dataTable().fnAddData(json.historial);
        }
    });
}


function EntregarPedidoJson(id) {
    $(".modal-title").text("Entrega");
    $("#gestion").prop("accion", "editar");
    $("#modal-esperar").modal('show');
    $.ajax({
        url: "LoadPedidoJson.sga",
        data: {id: id}
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        $("#items_empacados_container").empty();
        $.each(json, function (key, data) {
//            console.log(key + ": " + data)
            $("#" + key + "emp").val(data);
            $("#" + key + "emp").val(data).change();
            $("#" + key + "emp").attr("href", data);
        });

        var empresa = json.empresaCliente;
        $("#codigoEmrpesa").text(empresa.codigo);
        $("#nitEmrpesa").text(empresa.nit);
        $("#nombreEmrpesa").text(empresa.nombre);
        $("#telefonoEmrpesa").text(empresa.telefono);
        $("#direccionEmrpesa").text(empresa.direccion);
        $("#numeroGuia").val(json.numeroGuia);

        var jsonString = json.arrayProductosSolicitadosString;
        var array = JSON.parse(jsonString);
        //cargamos los items
        $.get("pages_in/item_entrega.jsp", function (itemHtml) {
            for (var i = 0; i < array.length; i++) {
                var inicial = itemHtml;
                var jsonIn = array[i];
                inicial = inicial.replace("$itemtext", jsonIn.nombre);
                inicial = inicial.replace("$itemref", jsonIn.referencia);
                inicial = inicial.replace("$cantidad", jsonIn.cantidad);
                inicial = inicial.replace("$item", jsonIn.itemid);
                if (jsonIn.empacados) {
                    inicial = inicial.replace("$empacados", jsonIn.empacados);
                } else {
                    inicial = inicial.replace("$empacados", 0);
                }
                inicial = inicial.replace("$total", jsonIn.valorVenta);

                $("#items_empacados_container").append(inicial);

            }
        });
        $("#gestion-modal_empacados").modal("show");
    });
}
