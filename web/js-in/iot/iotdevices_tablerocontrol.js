/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var id;
var templateDevies;
var templateActuador;
function init(devices, actuadores) {
    $(".select").select2();
    $.get("pages_in/item_iotdevice_tablero.jsp", function (itemHtmlORG) {
        templateDevies = itemHtmlORG;
        $.each(devices, function (index, device) {
            var itemDispositivo = templateDevies;
            var dateUltimaFecha = new Date();
            var OneDay = parseInt(device.fechaUltimoRegistro.$numberLong) + (30 * 60 * 1000);
            var halfDay = parseInt(device.fechaUltimoRegistro.$numberLong) + (10 * 60 * 1000);
            if (OneDay < dateUltimaFecha.getTime()) {
                //rojo
                itemDispositivo = itemDispositivo.replace("$image", "img/state/red.png");
            } else if (halfDay < dateUltimaFecha.getTime()) {
                //amarillo
                itemDispositivo = itemDispositivo.replace("$image", "img/state/yellow.png");
            } else {
                //verde
                itemDispositivo = itemDispositivo.replace("$image", "img/state/green.png");
            }
            var valoresMax = "";
            var valoresMin = "";
            if (device.valoresMax)
                valoresMax = JSON.stringify(device.valoresMax).replaceAll("{", "").replaceAll("}", "").replaceAll("\"map\":", "").replaceAll("\"", "").replaceAll(",", "\n");
            if (device.valoresMin)
                valoresMin = JSON.stringify(device.valoresMin).replaceAll("{", "").replaceAll("}", "").replaceAll("\"map\":", "").replaceAll("\"", "").replaceAll(",", "\n");
            itemDispositivo = itemDispositivo.replace("$nombre", device.nombre);

            var valoresBr = "";
            var ultimoValorJson = device.ultimoValor.map;
            var keys = Object.keys(ultimoValorJson);
            $.each(keys, function (index, key) {
                valoresBr += key + " " + Number(ultimoValorJson[key]).toFixed(2) + "<BR>";
            });
            itemDispositivo = itemDispositivo.replace("$id", index);
            itemDispositivo = itemDispositivo.replace("$valor", valoresBr);
            itemDispositivo = itemDispositivo.replace("$fecha", moment(parseInt(device.fechaUltimoRegistro.$numberLong)).format('YYYY-MM-DD h:mma'));
            itemDispositivo = itemDispositivo.replace("$valorMin", valoresMin);
            itemDispositivo = itemDispositivo.replace("$valorMax", valoresMax);
            $("#dispositivos").append(itemDispositivo);
        });
    });
    $.get("pages_in/item_iotactuador_tablero.jsp", function (itemHtmlORG) {
        templateActuador = itemHtmlORG;
        $.each(actuadores, function (index, actuador) {
            var itemDispositivo = templateActuador;
            var dateUltimaFecha = new Date();
            var OneDay = parseInt(actuador.fechaUltimoRegistro.$numberLong) + (30 * 60 * 1000);
            var halfDay = parseInt(actuador.fechaUltimoRegistro.$numberLong) + (10 * 60 * 1000);
            if (OneDay < dateUltimaFecha.getTime()) {
                //rojo
                itemDispositivo = itemDispositivo.replace("$image", "img/state/red.png");
            } else if (halfDay < dateUltimaFecha.getTime()) {
                //amarillo
                itemDispositivo = itemDispositivo.replace("$image", "img/state/yellow.png");
            } else {
                //verde
                itemDispositivo = itemDispositivo.replace("$image", "img/state/green.png");
            }
            itemDispositivo = itemDispositivo.replace("$id", index);
            var valoresBr = "";
            var ultimoValorJson = actuador.ultimoValor.map;
            var keys = Object.keys(ultimoValorJson);
            $.each(keys, function (index, key) {
                if (ultimoValorJson[key] == 0) {
                    //Apagado
                    valoresBr += "Apagado<BR>";
                } else {
                    //Encendido
                    valoresBr += "Encendido<BR>";
                }
            });
            itemDispositivo = itemDispositivo.replace("$nombre", actuador.nombre);
            itemDispositivo = itemDispositivo.replace("$valor", valoresBr);
            itemDispositivo = itemDispositivo.replace("$fecha", moment(parseInt(actuador.fechaUltimoRegistro.$numberLong)).format('YYYY-MM-DD h:mma'));
            $("#actuadores").append(itemDispositivo);
        });
    });

}



function buscarReporte() {
    $.ajax({
        type: "POST",
        cache: false,
        url: 'BuscarTableroIot.sga',
        success: function (jsonCompleto) {
            $.each(jsonCompleto.devices, function (index, device) {
                var dateUltimaFecha = new Date();
                var OneDay = parseInt(device.fechaUltimoRegistro) + (30 * 60 * 1000);
                var halfDay = parseInt(device.fechaUltimoRegistro) + (10 * 60 * 1000);
                if (OneDay < dateUltimaFecha.getTime()) {
                    //rojo
                    $("#d" + index + " .imagen").attr("src", "img/state/red.png");
                } else if (halfDay < dateUltimaFecha.getTime()) {
                    //amarillo
                    $("#d" + index + " .imagen").attr("src", "img/state/yellow.png");
                } else {
                    //verde
                    $("#d" + index + " .imagen").attr("src", "img/state/green.png");
                }


                var valoresBr = "";
                var ultimoValorJson = device.ultimoValor.map;
                var keys = Object.keys(ultimoValorJson);
                $.each(keys, function (index, key) {
                    valoresBr += key + " " + Number(ultimoValorJson[key]).toFixed(2) + "<BR>";
                });
                $("#d" + index + " .valor").empty().append(valoresBr);

                var valoresMax = "";
                var valoresMin = "";
                if (device.valoresMax)
                    valoresMax = JSON.stringify(device.valoresMax).replaceAll("{", "").replaceAll("}", "").replaceAll("\"map\":", "").replaceAll("\"", "").replaceAll(",", "\n");
                if (device.valoresMin)
                    valoresMin = JSON.stringify(device.valoresMin).replaceAll("{", "").replaceAll("}", "").replaceAll("\"map\":", "").replaceAll("\"", "").replaceAll(",", "\n");
                $("#d" + index + " .minimo").empty().append(valoresMin);
                $("#d" + index + " .maximo").empty().append(valoresMax);
                $("#d" + index + " .fecha").text("Sincronización: " + moment(parseInt(device.fechaUltimoRegistro)).format('YYYY-MM-DD h:mm a'));

            });


            $.each(jsonCompleto.actuadores, function (index, actuador) {
                var dateUltimaFecha = new Date();
                var OneDay = parseInt(actuador.fechaUltimoRegistro) + (30 * 60 * 1000);
                var halfDay = parseInt(actuador.fechaUltimoRegistro) + (10 * 60 * 1000);
                if (OneDay < dateUltimaFecha.getTime()) {
                    //rojo
                    $("#a" + index + " .imagen").attr("src", "img/state/red.png");
                } else if (halfDay < dateUltimaFecha.getTime()) {
                    //amarillo
                    $("#a" + index + " .imagen").attr("src", "img/state/yellow.png");
                } else {
                    //verde
                    $("#a" + index + " .imagen").attr("src", "img/state/green.png");
                }
                var valoresBr = "";
                var ultimoValorJson = actuador.ultimoValor.map;
                var keys = Object.keys(ultimoValorJson);
                $.each(keys, function (index, key) {
                    if (ultimoValorJson[key] == 0) {
                        //Apagado
                        valoresBr +="Apagado<BR>";
                    } else {
                        //Encendido
                        valoresBr += "Encendido<BR>";
                    }
                });
                $("#a" + index + " .valor").empty().append(valoresBr);
                $("#a" + index + " .fecha").text("Sincronización: " + moment(parseInt(actuador.fechaUltimoRegistro)).format('YYYY-MM-DD h:mm a'));

            });



        }
    });
}

setInterval(buscarReporte, 10000);