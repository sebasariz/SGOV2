/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var mapCenter;
var markers = new Array();
var heatmap;
var height = $(window).height() - 90;
$("#map-canvas").height(height);


var mapOptions = {
    zoom: 13
};
$(function () {
    $('#datetimepickerInicio').datetimepicker();
    $('#datetimepickerFin').datetimepicker();

    var date = new Date();
    var dateLastMount = new Date();
    dateLastMount.setMonth(dateLastMount.getMonth( ) - 1);
    $('#datetimepickerInicio').data("DateTimePicker").date(dateLastMount);
    $('#datetimepickerFin').data("DateTimePicker").date(date);

    mapCenter = new google.maps.Map(document.getElementById('map-canvas'),
            mapOptions);
    var trafficLayer = new google.maps.TrafficLayer();
    trafficLayer.setMap(mapCenter);
    var styles = [
        {
            stylers: [
                {hue: "#00ffe6"},
                {saturation: -20}
            ]
        }, {
            featureType: "road",
            elementType: "geometry",
            stylers: [
                {lightness: 100},
                {visibility: "on"}
            ]
        }, {
            featureType: "road",
            elementType: "labels",
            stylers: [
                {visibility: "on"}
            ]
        }
    ];
    mapCenter.setOptions({styles: styles});
    // Try HTML5 geolocation
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function (position) {
            var pos = new google.maps.LatLng(position.coords.latitude,
                    position.coords.longitude);
            mapCenter.setCenter(pos);
        }, function () {
            handleNoGeolocation(true);
        });
    } else {
        // Browser doesn't support Geolocation 
        $("#errorLabel").text("Tu navegador no soporta geoposicion.");
        $("#errorModal").modal("show");
    }


    function handleNoGeolocation(errorFlag) {
        $("#errorLabel").text("Actualiza tu navegador, tu versión no soporta la ubicación.");
        $("#errorModal").modal("show");

        var pos = new google.maps.LatLng(4.795417, -74.086304);
        mapCenter.setCenter(pos);
    }
//            var time = Math.random() * 1000 + 15000;
//            var intervalPosicion = setInterval(loadposicion, time);
    loadposicion();

});
function loadposicion() {
    $("#modal-esperar").modal("show");
    $.ajax({
        type: "POST",
        cache: false,
        url: 'LoadPosiciones.sga',
        success: function (jsonCompleto) {
            $("#modal-esperar").modal("hide");
            var array = jsonCompleto.posiciones;
            for (var key in markers) {
                if (markers.hasOwnProperty(key)) {
                    markers[key].setMap(null);
                    delete markers[key];
                }
            }

            var icon = jsonCompleto.icon;
            var bounds = new google.maps.LatLngBounds();
            for (var i = 0; i < array.length; i++) {
                var json = array[i];
                var pos1 = new google.maps.LatLng(json.lat, json.lng);
                var html = "<div class=\"scrollFix\" style=\"min-width: 220px; height:55px;\">" +
                        "<div style=\"display: inline-block; float:left;\">" +
                        "<div style=\"width: 100%;\">Hora: " + json.fecha + "</div>" +
                        "<div style=\"width: 100%;\">Nombre: " + json.nombre + "</div>" +
                        "<div style=\"width: 100%;\">Apellidos: " + json.apellidos + "</div>" +
                        "</div>" +
                        "</div>";


                var marker = new google.maps.Marker({
                    position: pos1,
                    map: mapCenter,
                    html: html,
                    icon: icon
                });
                bounds.extend(pos1);
                var contentString = "Service";
                var infowindow = new google.maps.InfoWindow({
                    content: contentString
                });
                google.maps.event.addListener(marker, "click", function () {
                    infowindow.setContent(this.html);
                    infowindow.open(mapCenter, this);
                });
                markers.push(marker);
            }
            mapCenter.fitBounds(bounds);
        }
    });
}

function loadMapaCalorVendedores() {
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
        url: 'LoadMapaCalorVendedores.sga',
        success: function (jsonCompleto) {
            $("#modal-esperar").modal("hide");
            if (heatmap) {
                heatmap.setMap(null);
            }
            var array = jsonCompleto.posiciones;
            for (var key in markers) {
                if (markers.hasOwnProperty(key)) {
                    markers[key].setMap(null);
                    delete markers[key];
                }
            }
            var heatMap = new Array();
            //aqui hacemos el has de heatmap
            var bounds = new google.maps.LatLngBounds();
            for (var i = 0; i < array.length; i++) {
                var json = array[i];
                var pos1 = new google.maps.LatLng(json.lat, json.lng);
                heatMap.push(pos1);
                bounds.extend(pos1);
            }
            heatmap = new google.maps.visualization.HeatmapLayer({
                data: heatMap
            });
            heatmap.setMap(mapCenter);
            mapCenter.fitBounds(bounds);
        }
    });
}

function loadMapaCalorPedidos() {
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
        url: 'LoadMapaCalorPedidos.sga',
        success: function (jsonCompleto) {
            $("#modal-esperar").modal("hide");
            if (heatmap) {
                heatmap.setMap(null);
            }
            var array = jsonCompleto.posiciones;
            for (var key in markers) {
                if (markers.hasOwnProperty(key)) {
                    markers[key].setMap(null);
                    delete markers[key];
                }
            }
            var heatMap = new Array();
            var bounds = new google.maps.LatLngBounds();
            //aqui hacemos el has de heatmap
            for (var i = 0; i < array.length; i++) {
                var json = array[i];
                var pos1 = new google.maps.LatLng(json.lat, json.lng);
                heatMap.push(pos1);
                bounds.extend(pos1);
            }
            heatmap = new google.maps.visualization.HeatmapLayer({
                data: heatMap
            });
            heatmap.setMap(mapCenter);
            mapCenter.fitBounds(bounds);
        }
    });
}
function search() {
    var tipo = $("#tipo").val();
    if (tipo == 0) {
        loadposicion();
    } else if (tipo == 1) {
        loadMapaCalorVendedores();
    } else if (tipo == 2) {
        loadMapaCalorPedidos();
    }
}