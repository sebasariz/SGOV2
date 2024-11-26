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
    var table = $('#datatable').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
        },
        dom: 'Bfrtip',
        buttons: ['copy', 'excel', 'pdf', 'print'],
        columns: [
            {title: "Fecha"},
            {title: "Valor"}
        ]
        , 'rowCallback': function (row, data, index) {
            var dateUltimaFecha = new Date(data[5]);
            var OneDay = new Date().getTime() + (1 * 24 * 60 * 60 * 1000);
            var halfDay = new Date().getTime() + (1 * 12 * 60 * 60 * 1000);
            if (halfDay < dateUltimaFecha) {
                //rojo
                $(row).find('td:eq(5)').css('color', 'white').css('background', '#f24c36').css('vertical-align', 'middle');
            } else if (OneDay < dateUltimaFecha) {
                //amarillo
                $(row).find('td:eq(5)').css('color', 'white').css('background', '#f2e536').css('vertical-align', 'middle');
            } else {
                //verde
                $(row).find('td:eq(5)').css('color', 'white').css('background', '#1d9432').css('vertical-align', 'middle');
            }
        }
    });

}

function buscarReporte() {
    var fechaInicio = $("#datetimepickerInicio").data("DateTimePicker").date();
    var fechaFin = $("#datetimepickerFin").data("DateTimePicker").date();
    var idIOTDevice = Number($("#idIOTDevice").val());
//    alert("idIOTDevice: " + idIOTDevice)
    $("#modal-esperar").modal("show");
    $.ajax({
        type: "POST",
        cache: false,
        data: {
            fechaInicio: fechaInicio + "",
            fechaFin: fechaFin + "",
            idIOTDevice: idIOTDevice
        },
        url: 'BuscarIotReporte.sga',
        success: function (jsonCompleto) {
            var alertas = 0;
            var iOTDeviceAlarmas = jsonCompleto.iOTDeviceAlarmas;
            for (var i = 0; i < iOTDeviceAlarmas.length; i++) {
                alertas += iOTDeviceAlarmas[i].jSONArrayHisotiralActivaciones.myArrayList.length;
            }
            $("#numeroAlertas").text(alertas);

            var valorMax = [];
            var valorMin = [];

            var dBObjectsRegister = jsonCompleto.dBObjectsRegister;
            //armamos el chart

            var tricks = new Array();
            var keys;
            var dataAlarmas = {};
            var dataFullOject = [];// new Array()];

            var dataArrayTable = new Array();
            var numeroParticiones = dBObjectsRegister.length / $("#numeroPuntos").val();
//            alert("numeroParticiones: " + numeroParticiones)
            var cont = 0;
            for (var i = 0; i < dBObjectsRegister.length; i++) {
                var register = dBObjectsRegister[i];
//                alert("register: " + JSON.stringify(Object.keys(register.jSONObject.map)));
                if (!tricks || i % parseInt(numeroParticiones) == 0) {
                    var trick = new Array();
                    trick.push(cont);
                    trick.push(moment(register.fecha).format("MM/DD"));
                    tricks.push(trick);


                    keys = Object.keys(register.jSONObject.map);
                    for (var w = 0; w < keys.length; w++) {
                        if (dataFullOject[w] == null) {
                            dataFullOject[w] = new Array();
                        }
                        if ((valorMax[w] == null) || (valorMax[w] < register.jSONObject.map[keys[w]])) {
                            valorMax[w] = register.jSONObject.map[keys[w]];
                        }
                        if ((valorMin[w] == null) || (valorMin[w] > register.jSONObject.map[keys[w]])) {
                            valorMin[w] = register.jSONObject.map[keys[w]];
                        }
                        var data = new Array();
                        data.push(cont);
                        data.push(register.jSONObject.map[keys[w]]);
                        dataFullOject[w].push(data);
                    }
                    cont++;

                }



                //armamos la tabla
                var arrayFila = new Array();
                arrayFila.push(moment(register.fecha).format("MM/DD/YYYY h:mm:ss a"));
                arrayFila.push(JSON.stringify(register.jSONObject).replaceAll("{", "").replaceAll("}", "").replaceAll("\"map\":", "").replaceAll("\"", ""));
                dataArrayTable.push(arrayFila);

            }

//            load table
            $('#datatable').dataTable().fnClearTable();
            if (dataArrayTable != '') {
                $('#datatable').dataTable().fnAddData(dataArrayTable);
            }
            var allSeriesData = new Array();
            for (var i = 0; i < dataFullOject.length; i++) {
                var dataSerieRegister = {};
                dataSerieRegister.label = keys[i];
                dataSerieRegister.data = dataFullOject[i];
                allSeriesData.push(dataSerieRegister);
            }
//            alert("sali 1: " + JSON.stringify(allSeriesData) + " tricks; " + JSON.stringify(tricks));
            activateChart(allSeriesData, tricks);
            var fecha1 = moment(fechaInicio);
            var fecha2 = moment(fechaFin);
            $("#dias").text(fecha2.diff(fecha1, 'days'));
            $("#totalMuestas").text(dBObjectsRegister.length);
            $("#valMax").text(valorMax);
            $("#valMin").text(valorMin);
            $("#modal-esperar").modal("hide");

        }
    });


    function activateChart(dataFullArray, tricks) {
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
                };
        $.plot($('.line-placeholder'), data, options);
    }
}

     