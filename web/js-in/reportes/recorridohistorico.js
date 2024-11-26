/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var markers = new Array();
var height = $(window).height() - 90;
$("#map-canvas").height(height);


var mapOptions = {
    zoom: 13
};
$(function () {
    $(".select").select2();
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
});
function loadposiciones() {
    var fechaInicio = $("#datetimepickerInicio").data("DateTimePicker").date();
    var fechaFin = $("#datetimepickerFin").data("DateTimePicker").date();
    var idVendedor = $("#idvendedor").val()
    $("#modal-esperar").modal("show");
    $.ajax({
        type: "POST",
        cache: false,
        url: 'LoadRecorridoHistoricoJson.sga',
        data: {
            idVendedor: idVendedor,
            fechaInicio: fechaInicio + "",
            fechaFin: fechaFin + ""
        },
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
                var date = new Date(json.fecha);
                var html = "<div class=\"scrollFix\" style=\"min-width: 170px; \">" +
                        "<div style=\"width: 100%;\">Fecha y hora: " + date.formatDate("yyyy/MM/dd hh:mm t") + "</div>" +
                        "</div>" +
                        "</div>";


                var marker = new google.maps.Marker({
                    position: pos1,
                    map: mapCenter,
                    html: html,
                    icon: icon
                });
                bounds.extend(pos1);
                var contentString = "Vendedor";
                var infowindow = new google.maps.InfoWindow({
                    content: contentString
                });
                google.maps.event.addListener(marker, "click", function () {
                    infowindow.setContent(this.html);
                    infowindow.open(mapCenter, this);
                });
                markers.push(marker);
                mapCenter.fitBounds(bounds);
            }
        }
    });
}


  