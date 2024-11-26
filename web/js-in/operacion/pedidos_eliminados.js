/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var idPedido;
var table;
var itemsGlobal;
function init(grid) {
    table = $('#datatable').DataTable({
        "order": [[0, "desc"]],
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
        },
        dom: 'Bfrtip',
        buttons: [
            'print'
        ],
        data: grid,
        columns: [
            {title: "No."},
            {title: "Nit"},
            {title: "Nombre"},
            {title: "Fecha elimnación"},
            {title: "Usuario eliminador"},
            {title: "Estado"},
            {title: "Acciones", "width": "80px"}
        ]
    });
    $(".select").select2();
}
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
    $("#total").val(0);
    $("#itemsCrear").empty();
    $("#items").empty();
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
            if (json.pedidos) {
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

        $("#title-edit").text("Entregar (" + dateToYMD(new Date(json.fechaCreacion)) + ")");
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
                if (jsonIn.empacados) {
                    inicial = inicial.replace("$empacados", jsonIn.empacados);
                } else {
                    inicial = inicial.replace("$empacados", 0);
                }
                inicial = inicial.replace("$total", jsonIn.valorVenta);
                subtotal += parseInt(jsonIn.valorVenta);
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
function dateToYMD(date) {
    var d = date.getDate();
    var m = date.getMonth() + 1; //Month from 0 to 11
    var y = date.getFullYear();

    return '' + y + '-' + (m <= 9 ? '0' + m : m) + '-' + (d <= 9 ? '0' + d : d);
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
            if (json.error) {
                $("#errorLabel").text(json.error);
                $("#modal-error").modal("show");
            } else {
                $("#gestion-modal").modal('hide');
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
                $('#datatable').dataTable().fnClearTable();
                if (json.pedidos != '') {
                    $('#datatable').dataTable().fnAddData(json.pedidos);
                }
            }
        }
    });
}
 
var find = true;
$('#datatable').on('search.dt', function () {
    if (table.search() != '') {
        find = false;
    }
});
$(".modal").on('show.bs.modal', function () {
    find = false;
});
$('.modal').on('hide.bs.modal', function () {
    find = true;
});
$('#datatable').on('page.dt', function () {
    var info = table.page.info();
    if (info.page != 0) {
        find = false;
    } else {
        find = true;
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


function formatNumber(n) {
    // format number 1000000 to 1,234,567
    return n.replace(/\D/g, "").replace(/\B(?=(\d{3})+(?!\d))/g, ",")
}


function formatCurrency(input, blur) {
    // appends $ to value, validates decimal side
    // and puts cursor back in right position.

    // get input value
    var input_val = input.val();

    // don't validate empty input
    if (input_val === "") {
        return;
    }

    // original length
    var original_len = input_val.length;

    // initial caret position 
    var caret_pos = input.prop("selectionStart");

    // check for decimal
    if (input_val.indexOf(".") >= 0) {

        // get position of first decimal
        // this prevents multiple decimals from
        // being entered
        var decimal_pos = input_val.indexOf(".");

        // split number by decimal point
        var left_side = input_val.substring(0, decimal_pos);
        var right_side = input_val.substring(decimal_pos);

        // add commas to left side of number
        left_side = formatNumber(left_side);

        // validate right side
        right_side = formatNumber(right_side);

        // On blur make sure 2 numbers after decimal
        if (blur === "blur") {
            right_side += "00";
        }

        // Limit decimal to only 2 digits
        right_side = right_side.substring(0, 2);

        // join number by .
        input_val = "$" + left_side + "." + right_side;

    } else {
        // no decimal entered
        // add commas to number
        // remove all non-digits
        input_val = formatNumber(input_val);
        input_val = "$" + input_val;

        // final formatting
        if (blur === "blur") {
            input_val += ".00";
        }
    }

    // send updated string to input
    input.val(input_val);

    // put caret back in the right position
    var updated_len = input_val.length;
    caret_pos = updated_len - original_len + caret_pos;
    input[0].setSelectionRange(caret_pos, caret_pos);
}
function print() {
    window.location.href = "ImprimirPedidoID.sga?id=" + idPedido;
}