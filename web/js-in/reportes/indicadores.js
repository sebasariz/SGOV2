/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */ 
var id;
function init() {
    $(".select").select2();
    $('#datetimepickerInicio').datetimepicker();
    $('#datetimepickerFin').datetimepicker();
    var date = new Date();
    var dateLastMount = new Date();
    dateLastMount.setMonth(dateLastMount.getMonth() - 1);
    $('#datetimepickerInicio').data("DateTimePicker").date(dateLastMount);
    $('#datetimepickerFin').data("DateTimePicker").date(date);
}

function initChart(datas, jsonCompleto) {

    var dias = Object.keys(datas);
    var tricks = new Array();
    var dataFullOject = {};
    var cont = 0;
    $.each(dias, function (index, dia) {
        var trick = new Array;
        trick.push(cont);
        trick.push(dia);
        tricks.push(trick);
        var valores = datas[dia];
        var productosUnicosVendedores = removeDuplicates(valores, "idUsuarioCreador");
        $.each(productosUnicosVendedores, function (index2, sumaDiaVendedor) {
            var usuario = "";
            for (var i in jsonCompleto.usuarios) {
                if (jsonCompleto.usuarios[i].idUsuario == sumaDiaVendedor.idUsuarioCreador) {
                    var usuarioObj = jsonCompleto.usuarios[i];
                    usuario = usuarioObj.nombre + " " + usuarioObj.apellidos;
                    break;
                }
            }
            var vendedorGraph = dataFullOject[sumaDiaVendedor.idUsuarioCreador];
            if (!vendedorGraph) {
                vendedorGraph = {};
                vendedorGraph.data = new Array();
                vendedorGraph.label = usuario;
            }
            var dataPoint = new Array();
            dataPoint.push(cont);
            dataPoint.push(sumaDiaVendedor.valorVentas);
            vendedorGraph.data.push(dataPoint);
            dataFullOject[sumaDiaVendedor.idUsuarioCreador] = vendedorGraph;
        });
        cont++;
    });
    var dataFullArray = new Array;
    for (i in dataFullOject) {
        dataFullArray.push(dataFullOject[i]);
    }
    alert("dataFullArray: " + JSON.stringify(dataFullArray) + " tricks: " + JSON.stringify(tricks));
    var data = dataFullArray,
            options = {
                xaxis: {
                    ticks: tricks
                },
                series: {
                    lines: {
                        show: true,
                    },
                    points: {
                        show: true,
                        radius: '3.5'
                    },
                    shadowSize: 0
                },
                grid: {
                    hoverable: true,
                    clickable: true,
                    color: '#bbb',
                    borderWidth: 1,
                    borderColor: '#eee'
                }
            },
    plot;
    $.plot($('.line-placeholder'), data, options);
}


function buscarIndicadores() {
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
        url: 'ResultadoIndicadores.sga',
        success: function (jsonCompleto) {
            $("#modal-esperar").modal("hide");
            $("#numeroPedido").text(jsonCompleto.pedidos.length);
            var pedidos = jsonCompleto.pedidos;
            var totalProductos = 0;
            var valorVentas = 0;
            var valorVentasDespachadas = 0; 
            var valorPedidosEliminados = 0;
            var totalProductosEliminados=0;
            var despachados = 0;
            var pendientes = 0;
            var parciales = 0;
            var eliminados = 0;
            var productosAll = new Array();
            for (var i = 0; i < pedidos.length; i++) {
                var estado;
                switch (pedidos[i].estado) {
                    case 1:
                        pendientes++;
                        break;
                    case 2:
                        estado = "Empacado parcial";
                        break;
                    case 3:
                        estado = "Completado";
                        break;
                    case 4:
                        estado = "Despachado";
                        despachados++;
                        break;
                    case 5:
                        estado = "Entregado";
                        break;
                    case 6:
                        estado = "Parcial";
                        parciales++;
                        break;
                    case -1:
                        estado = "Eliminado";
                        eliminados++;
                        break;
                }
                var productos = JSON.parse(pedidos[i].arrayProductosSolicitadosString);
                totalProductos = totalProductos + productos.length;
                var ventasPedido = 0;
                $.each(productos, function (index, producto) {
                    valorVentas = valorVentas + parseInt(producto.valorVenta);
                    if (producto.empacados && producto.empacados !=0) {
                        valorVentasDespachadas = valorVentasDespachadas + parseInt(producto.valorVenta) / parseInt(producto.empacados);
                    }
                    if(pedidos[i].estado==-1){
                        valorPedidosEliminados = valorPedidosEliminados+parseInt(producto.valorVenta);
                        totalProductosEliminados++;
                    }
                    ventasPedido = ventasPedido + parseInt(producto.valorVenta);
                    producto.idUsuarioCreador = pedidos[i].idUsuarioCreador;
                    productosAll.push(producto);
                });
                pedidos[i].valorVentasDespachadas = valorVentasDespachadas;
                pedidos[i].valorVentas = ventasPedido;
                pedidos[i].valorVentasPendientes=(ventasPedido-valorVentasDespachadas); 
            }
            //volvemos unicos los productos
            $("#listaProductos").empty();
            var productosUnicos = removeDuplicates(productosAll, "itemid");
            $.each(productosUnicos, function (index, producto) {
                $("#listaProductos").append('<li class="list-group-item">' + "(" + producto.referencia + ") " + producto.nombre + '<div>Cantidad: <span class="badge">' + producto.cantidad + '</span> Valor: <span class="badge">' + formatter.format(producto.valorVenta, 0) + '</span></div></li>');
                if (index == 9)
                    return false;
            });
            $("#totalReferencias").text(productosUnicos.length);
            $("#listaVendedores").empty();
            var productosUnicosVendedores = removeDuplicates(productosAll, "idUsuarioCreador");
            productosUnicosVendedores.sort((a, b) => a.valorVenta - b.valorVenta);
            $.each(productosUnicosVendedores, function (index, vendedor) {
                var usuario = "";
                for (var i in jsonCompleto.usuarios) {
                    if (jsonCompleto.usuarios[i].idUsuario == vendedor.idUsuarioCreador) {
                        var usuarioObj = jsonCompleto.usuarios[i];
                        usuario = usuarioObj.nombre + " " + usuarioObj.apellidos;
                        break;
                    }
                }
                $("#listaVendedores").append('<li class="list-group-item">' + usuario + '<span class="badge">' + formatter.format(vendedor.valorVenta) + '</span></li>');
                if (index == 9)
                    return false;
            });
            $("#totalVendedores").text(productosUnicosVendedores.length);
            pedidos.sort((a, b) => a.fechaCreacion - b.fechaCreacion)
            var porDia = groupByTimePeriod(pedidos, 'fechaCreacion', 'day');
            initChart(porDia, jsonCompleto); 
            $("#parciales").text(parciales);
            $("#pendientes").text(pendientes);
            $("#despachados").text(despachados);
            $("#totalProductos").text(totalProductos);
            $("#valorVentas").text(formatter.format(valorVentas)); 
            $("#valorVentasDespachadas").text(formatter.format(valorVentasDespachadas));
            $("#valodVentasPendientes").text(formatter.format((valorVentas-valorVentasDespachadas))); 
            $("#pedidosEliminados").text(eliminados);
            $("#valorPedidosEliminados").text(formatter.format(valorPedidosEliminados)); 
            $("#totalProductosEliminados").text(totalProductosEliminados);
            
        }
    });
}

function removeDuplicates(originalArray, prop) {
    var newArray = [];
    var lookupObject = {};
    for (var i in originalArray) {
        if (!lookupObject[originalArray[i][prop]]) {
            lookupObject[originalArray[i][prop]] = originalArray[i];
        } else {
            var cantidadAnterior = parseInt(lookupObject[originalArray[i][prop]].cantidad);
            var cantidadNueva = parseInt(originalArray[i]["cantidad"]);
            var total = parseInt(cantidadAnterior) + parseInt(cantidadNueva);
            var valorVentaAnterior = parseInt(lookupObject[originalArray[i][prop]].valorVenta);
            var valorVentaNueva = parseInt(originalArray[i]["valorVenta"]);
            var valorTotal = parseInt(valorVentaAnterior) + parseInt(valorVentaNueva);
            lookupObject[originalArray[i][prop]].cantidad = total;
            lookupObject[originalArray[i][prop]].valorVenta = valorTotal;
        }
    }

    for (i in lookupObject) {
        newArray.push(lookupObject[i]);
    }
    newArray.sort((a, b) => b.cantidad - a.cantidad)
    return newArray;
}

// Group by time period - By 'day' | 'week' | 'month' | 'year'
// ------------------------------------------------------------
var groupByTimePeriod = function (obj, timestamp, period) {
    var objPeriod = {};
    var oneDay = 24 * 60 * 60 * 1000; // hours * minutes * seconds * milliseconds
    for (var i = 0; i < obj.length; i++) {
        var label = "";
        var d = new Date(obj[i][timestamp]);
        if (period == 'day') {
            label = d.formatDate("dd/MM");
        } else if (period == 'month') {
            label = d.formatDate("MM");
        } else if (period == 'year') {
            label = d.formatDate("yyyy");
        } else {
            console.log('groupByTimePeriod: You have to set a period! day | week | month | year');
        }
// define object key
        objPeriod[label] = objPeriod[label] || [];
        objPeriod[label].push(obj[i]);
    }
    return objPeriod;
};
      