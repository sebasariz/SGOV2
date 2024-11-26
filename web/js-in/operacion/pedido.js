/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var idPedido;
var table;
var itemsGlobal;
var find = true;

function init(grid) {
    table = $('#datatable').DataTable(optionsTable);
    $(".select").select2();
    if (grid != '') {
        $('#datatable').dataTable().fnAddData(grid);
    }

}
/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function LoadPedidoJson(id) {
    idPedido = id;
    itemsGlobal = $("#items");
    $("#modal-esperar").modal('show');
    $.ajax({
        url: "LoadPedidoJson.sga",
        data: {id: id}
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        $("#items").empty();
//        console.log("json: " + json); 
        $("#idPedidoEdit").val(json.idPedido);
        $("#nota").val(json.nota);
        var empresa = json.empresaCliente;
        $("#idEmpresaCliente").val(json.empresaCliente.idEmpresa).change();
        $("#codigoEmrpesaEdit").text(empresa.codigo);
        $("#numeroGuia").val(json.numeroGuia);
        $("#nitEmrpesaEdit").text(empresa.nit);
        $("#nombreEmrpesaEdit").text(empresa.nombre);
        $("#telefonoEmrpesaEdit").text(empresa.telefono);
        $("#direccionEmrpesaEdit").text(empresa.direccion);
        $("#notaEmrpesaEdit").text(empresa.nota);
        $.each(json, function (key, data) {
            console.log(key + ": " + data)
        });
        $("#itemsCrear").empty();
        $("#items").empty();
        var jsonString = json.arrayProductosSolicitadosString;
        var array = JSON.parse(jsonString);
        var subtotal = 0;
        $.get("pages_in/item.jsp", function (itemHtmlORG) {
            for (var i = 0; i < array.length; i++) {
                var itemHtml = itemHtmlORG;
                var json = array[i];
                //cargamos los items 
                itemHtml = itemHtml.replace("$itemtext", json.nombre);
                itemHtml = itemHtml.replace("$itemref", json.referencia);
                itemHtml = itemHtml.replace("$cantidad", json.cantidad);
                itemHtml = itemHtml.replace("$item", json.itemid);
                if (json.empacados) {
                    itemHtml = itemHtml.replace("$empacados", json.empacados);
                } else {
                    itemHtml = itemHtml.replace("$empacados", 0);
                }
                itemHtml = itemHtml.replace("$total", json.valorVenta);
                subtotal = subtotal + parseInt(json.valorVenta);
                $("#items").append(itemHtml);

            }
            $("#subtotal").val(subtotal);
            formatCurrency($("#subtotal"));
            var iva = subtotal * 0.19;
            $("#iva").val(iva);
            formatCurrency($("#iva"));
            var total = subtotal + iva;
            $("#total").val(total);
            formatCurrency($("#total"));
        });

        $("#gestion-modal").modal("show");
    });
}
$("#add").on("click", function () {
    $("#gestion-crear-modal").modal("show");
    itemsGlobal = $("#items-crear");
    $("#totalCrear").val(0);
    $("#subtotalCrear").val(0);
    $("#ivaCrear").val(0);
    $("#itemsCrear").empty();
    $("#items").empty();
    $("#numeroGuia").empty();
    $("#notaGuia").empty();
    $("#transportadora").empty();
    $("#nota-crear").val('');
});
function EliminarPedido(id) {
    idPedido = id;
    $("#remove-modal").modal("show");
}
function confirmarEliminar() {
    $("#modal-esperar").modal('show');
    $.ajax({
        url: "DeletePedido.sga",
        data: {id: idPedido}
    }).done(function (json) {
        $("#modal-esperar").modal('hide');
        if (json.error) {
            $("#errorLabel").text(json.error);
            $("#modal-error").modal("show");
        } else {
            $("#remove-modal").modal('hide');
            $('#datatable').dataTable().fnClearTable();
            if (json.pedidos != '') {
                $('#datatable').dataTable().fnAddData(json.pedidos);
            }

        }
    });
}
$("#gestion").submit(function (e) {
    e.preventDefault();
    var items = $(".item");
    var json = [];
    $.each(items, function (key, data) {
        var itemid = $(data).find(".itemid").val();
        var cantidad = $(data).find(".cantidad").val();
        var itemname = $(data).find(".item").val();
        var itemref = $(data).find(".itemref").val();
        var total = $(data).find(".total").val();
        var myObjectData = {
            itemid: itemid,
            cantidad: cantidad,
            nombre: itemname,
            empacados: 0,
            valorVenta: total,
            referencia: itemref
        };
        if (itemid && cantidad) {
            json.push(myObjectData);
        }
    });
    $("#arrayProductosSolicitadosString").val(JSON.stringify(json));
//    return;
    var formData = new FormData(this);
    $("#modal-esperar").modal('show');
    $.ajax({
        type: "POST",
        cache: false,
        url: "EditPedido.sga",
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
                if (json.pedidos) {
                    itemsGlobal.empty();
                    $('#datatable').dataTable().fnClearTable();
                    if (json.pedidos != '') {
                        $('#datatable').dataTable().fnAddData(json.pedidos);
                    }
                }
            }
        }
    });
});
$("#gestion-crear").submit(function (e) {
    e.preventDefault();

    var items = $(".item");
    var json = [];
    $.each(items, function (key, data) {
        var itemid = $(data).find(".itemid").val();
        var cantidad = $(data).find(".cantidad").val();
        var itemname = $(data).find(".item").val();
        var itemref = $(data).find(".itemref").val();
        var total = $(data).find(".total").val();
        var myObjectData = {
            itemid: itemid,
            cantidad: cantidad,
            nombre: itemname,
            empacados: 0,
            valorVenta: total,
            referencia: itemref
        };
        if (itemid && cantidad) {
            json.push(myObjectData);
        }
    });
    $("#arrayProductosSolicitadosStringCrear").val(JSON.stringify(json));
    var formData = new FormData(this);
    $("#modal-esperar").modal('show');
    $.ajax({
        type: "POST",
        cache: false,
        url: "CreatePedido.sga",
        data: formData,
        contentType: false,
        processData: false,
        success: function (json) {
            $("#modal-esperar").modal('hide');
            if (json.error) {
                $("#errorLabel").text(json.error);
                $("#modal-error").modal("show");
            } else {
                $("#gestion-crear-modal").modal('hide');
                if (json.pedidos) {
                    itemsGlobal.empty();
                    $('#datatable').dataTable().fnClearTable();
                    if (json.pedidos != '') {
                        $('#datatable').dataTable().fnAddData(json.pedidos);
                    }
                }
            }
        }
    });
});
$("#gestion_empacados").submit(function (e) {
    e.preventDefault();
    var btn = $(this).find("button[type=submit]:focus");
    if (btn.attr("value") == "guardar") {
        empacar(this);
    }
    if (btn.attr("value") == "despachar") {
        despachar(this);
    }
});
function addItem() {
    var itemText = $("#idProducto").select2('data')[0].text;
    var ref = itemText.split("Ref: ")[1].split(" - Cant:")[0];
    var item = $("#idProducto").val();
    var valor = $("#valor").val();
    if (item == 0) {
        $("#errorLabel").text("Seleccione un item");
        $("#modal-error").modal("show");
        return;
    }
    var cantidad = $("#cantidad").val();
    if (!isFinite(cantidad)) {
        $("#errorLabel").text("Ingrese una cantidad valida");
        $("#modal-error").modal("show");
        return;
    }
    $.get("pages_in/item.jsp", function (itemHtml) {
        itemHtml = itemHtml.replace("$itemtext", itemText.split(" (Ref")[0]);
        itemHtml = itemHtml.replace("$cantidad", cantidad);
        itemHtml = itemHtml.replace("$item", item);
        itemHtml = itemHtml.replace("$total", valor);
        itemHtml = itemHtml.replace("$itemref", ref);

        itemsGlobal.append(itemHtml);
    });

    var subtotal = $("#subtotal").val().replace(/[^0-9.-]+/g, "");
    subtotal = parseInt(subtotal) + parseInt(valor);
    $("#subtotal").val(subtotal);
    formatCurrency($("#subtotal"));
    var iva = subtotal * 0.19;
    $("#iva").val(iva);
    formatCurrency($("#iva"));
    var total = iva + subtotal;
    $("#total").val(total);
    formatCurrency($("#total"));



    $("#idProducto").val(0).change();
    $("#cantidad").empty();
}
function addItemCreate() {
    var itemText = $("#idProductoCrear").select2('data')[0].text;
    var ref = itemText.split("Ref: ")[1].split(" - Cant:")[0];
    var item = $("#idProductoCrear").val();
    var valor = $("#valorCrear").val();
    if (item == 0) {
        $("#errorLabel").text("Seleccione un item");
        $("#modal-error").modal("show");
        return;
    }
    var cantidad = $("#cantidadCrear").val();
    if (!isFinite(cantidad)) {
        $("#errorLabel").text("Ingrese una cantidad valida");
        $("#modal-error").modal("show");
        return;
    }
    $.get("pages_in/item.jsp", function (itemHtml) {
        itemHtml = itemHtml.replace("$itemtext", itemText.split(" (Ref")[0]);
        itemHtml = itemHtml.replace("$cantidad", cantidad);
        itemHtml = itemHtml.replace("$item", item);
        itemHtml = itemHtml.replace("$total", valor);
        itemHtml = itemHtml.replace("$itemref", ref);
        $("#items-crear").append(itemHtml);
    });
    var subtotal = $("#subtotalCrear").val().replace(/[^0-9.-]+/g, "");
    subtotal = parseInt(subtotal) + parseInt(valor);
    $("#subtotalCrear").val(subtotal);
    formatCurrency($("#subtotalCrear"));
    var iva = subtotal * 0.19;
    $("#ivaCrear").val(iva);
    formatCurrency($("#ivaCrear"));
    var total = iva + subtotal;
    $("#totalCrear").val(total);
    formatCurrency($("#totalCrear"));

    $("#idProductoCrear").val(0).change();
    $("#cantidadCrear").val(0);
    $("#valorCrear").val(0);
}
function calculateValor() {
    var cantidad = $("#cantidad").val();
    var valorUnidad = $("#idProducto").select2().find(":selected").data("valor");
    var total = cantidad * valorUnidad;
    $("#valor").val(total);
}
function calculateValorCrear() {
    var valorUnidad = $("#idProductoCrear").select2().find(":selected").data("valor");
    var cantidad = $("#cantidadCrear").val();
    var total = cantidad * valorUnidad;
    $("#valorCrear").val(total);
}
//$(".empacados").add("onchange", function () {
//    alert("1");
//});
function EntregarPedidoJson(id) {
    idPedido = id;
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
        $("#transportadora").val(json.transportadora);
        $("#notaGuia").val(json.notaGuia);
        $("#idTercero").text(json.idTercero);
        var date = new Date(json.fechaCreacion);
        $("#title-edit").text("Entregar (" + date.dateToYMD() + ")");
        var jsonString = json.arrayProductosSolicitadosString;
        var array = JSON.parse(jsonString);
        //cargamos los items
        var subtotal = 0;
        $.get("pages_in/item_entrega.jsp", function (itemHtml) {
            for (var i = 0; i < array.length; i++) {
                var inicial = itemHtml;
                var jsonIn = array[i];
                inicial = inicial.replace("$itemtext", jsonIn.nombre);
                inicial = inicial.replace("$itemref", jsonIn.referencia);
                inicial = inicial.replace("$cantidad", jsonIn.cantidad);
                inicial = inicial.replace("$item", jsonIn.itemid);
                var empacados = 0;
                if (jsonIn.empacados) {
                    inicial = inicial.replace("$empacados", jsonIn.empacados);
                    empacados = jsonIn.empacados;
                } else {
                    inicial = inicial.replace("$empacados", 0);
                }
                var valorVenta = jsonIn.valorVenta / jsonIn.cantidad * empacados;
                inicial = inicial.replace("$total", jsonIn.valorVenta);
                subtotal += parseInt(valorVenta);
                $("#items_empacados_container").append(inicial);

            }
            $("#subtotalemp").val(subtotal);
            formatCurrency($("#subtotalemp"));
            var iva = subtotal * 0.19;
            $("#ivaemp").val(iva);
            formatCurrency($("#ivaemp"));
            var total = subtotal + iva;
            $("#totalemp").val(total);
            formatCurrency($("#totalemp"));
            $("#gestion-modal_empacados").modal("show");
        });

    });
}
function despachar(e) {

    var items = $(".item_empacados");
    var json = [];
    $.each(items, function (key, data) {
        var itemid = $(data).find(".itemid").val();
        var cantidad = $(data).find(".cantidad").val();
        var itemname = $(data).find(".item").val();
        var itemref = $(data).find(".itemref").val();
        var total = $(data).find(".total").val();
        var empacados = $(data).find(".empacados").val();
        var myObjectData = {
            itemid: itemid,
            cantidad: cantidad,
            nombre: itemname,
            empacados: empacados,
            valorVenta: total,
            referencia: itemref
        };
        if (itemid && cantidad) {
            json.push(myObjectData);
        }
    });
    $("#arrayProductosSolicitadosStringEmpacados").val(JSON.stringify(json));
    var formData = new FormData(e);
    $("#modal-esperar").modal('show');
    $.ajax({
        type: "POST",
        cache: false,
        url: "DespacharPedido.sga",
        data: formData,
        contentType: false,
        processData: false,
        success: function (json) {
            $("#modal-esperar").modal('hide');
            $("#gestion-modal_empacados").modal('hide');
            if (json.error) { 
                $("#errorLabel").text(json.error);
                $("#modal-error").modal("show");
            } else {
                if (json.pedidos) {
                    $("#items").empty();
                    $("#gestion-modal_empacados").modal('hide');
                    $('#datatable').dataTable().fnClearTable();
                    if (json.pedidos != '') {
                        $('#datatable').dataTable().fnAddData(json.pedidos);
                    }
                }
            }
        }
    });
}
function empacar(e) {
    var items = $(".item_empacados");
    var json = [];
    var completo = true;
    $.each(items, function (key, data) {
        var itemid = $(data).find(".itemid").val();
        var cantidad = $(data).find(".cantidad").val();
        var itemname = $(data).find(".item").val();
        var empacados = $(data).find(".empacados").val();
        if (parseInt(cantidad) < parseInt(empacados)) {
            $("#errorLabel").text("la cantidad de: " + itemname + " empacada debe ser inferior a la solicitada");
            $("#modal-error").modal("show");
            return;
        } else if (parseInt(cantidad) > parseInt(empacados)) {
            $("#estadoemp").val(2);
            completo = false;
        }

        var myObjectData = {
            itemid: itemid,
            empacados: empacados
        };
        if (itemid && cantidad) {
            json.push(myObjectData);
        }
    });
    if (completo) {
        $("#estadoemp").val(3);
    }

    $("#arrayProductosSolicitadosStringEmpacados").val(JSON.stringify(json));
    var formData = new FormData(e);
    $("#modal-esperar").modal('show');
    $.ajax({
        type: "POST",
        cache: false,
        url: "EmpacarPedido.sga",
        data: formData,
        contentType: false,
        processData: false,
        success: function (json) {
            $("#modal-esperar").modal('hide');
            if (json.error) {
                $("#errorLabel").text(json.error);
                $("#modal-error").modal("show");
            } else {
                $("#gestion-modal_empacados").modal('hide');
//                $('#datatable').dataTable().fnClearTable();
//                if (json.pedidos != '') {
//                    $('#datatable').dataTable().fnAddData(json.pedidos);
//                }
            }
        }
    });
}
function interval() {
//    alert("find interval: " + find);
    if (find) {
        $.ajax({
            type: "POST",
            cache: false,
            url: "LoadInterval.sga",
            contentType: false,
            processData: false,
            success: function (json) {
                $("#modal-esperar").modal('hide');
                if (json.error) {
                    $("#errorLabel").text(json.error);
                    $("#modal-error").modal("show");
                } else {
                    if (json.pedidos.length > 0) {
                        $('#datatable').dataTable().fnClearTable();
                        if (json.pedidos != '') {
                            $('#datatable').dataTable().fnAddData(json.pedidos);
                        }
                    }
                }
            }
        });
    }
}

$('#datatable').on('search.dt', function () {
    if (table.search() != '') {
        find = false;
    }
});
$(".modal").on('show.bs.modal', function () {
    find = false;
});
$('.modal').on('hide.bs.modal', function () {
    var info = table.page.info();
    if (info.page == 0) {
        find = true;
    }
});
$('#datatable').on('page.dt', function () {
    var info = table.page.info();
    if (info.page == 0) {
        alert("first page");
        find = true;
    } else {
        find = false;
    }
});
$("input[data-type='currency']").on({
    keyup: function () {
        formatCurrency($(this));
    },
    blur: function () {
        formatCurrency($(this), "blur");
    }
});



function print() {
    window.location.href = "ImprimirPedidoID.sga?id=" + idPedido;
}
setInterval(interval, 10000);