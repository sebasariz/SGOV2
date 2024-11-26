/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var id;
function init(servicios, tipousuario) {
    $(".select").select2();
    //cargamos la galeria con los servicios
    $.get("pages_in/item_servicio_tablero.jsp", function (itemHtmlORG) {
        templateDevies = itemHtmlORG;
        $.each(servicios, function (index, servicio) {
//            alert("JSON: " + JSON.stringify(servicio));
            var itemDispositivo = templateDevies;
            var dateEmisionString = moment(servicio.fechaUltimoServicio).format('DD/MM/YYYY');
            itemDispositivo = itemDispositivo.replace("$ultimoServicio", dateEmisionString);
            itemDispositivo = itemDispositivo.replace("$image", "img/servicio/" + servicio.img);
            itemDispositivo = itemDispositivo.replace("$nombre", servicio.nombre+" ("+servicio.numeroServiciosPendientes+")");
            itemDispositivo = itemDispositivo.replace("$descripcionCorta", servicio.descripcionCorta);
            itemDispositivo = itemDispositivo.replace("$descripcion", servicio.descripcion); 



            $("#servicios").append(itemDispositivo);
        });
    });

}

// 
//function LoadServicioRiesgoInternoJson(id) {
//
//    $(".modal-title").text("Editar");
//    $("#gestion").prop("accion", "editar");
//    $("#modal-esperar").modal("show");
//    $.ajax({
//        url: "LoadServicioRiesgosInternosJson.sga",
//        data: {id: id}
//    }).done(function (json) {
//        $("#modal-esperar").modal("hide");
//        $.each(json, function (key, data) {
//            console.log(key + ": " + data)
//            $("#" + key).val(data);
////            $("#" + key).text(data);
//            $("#" + key).val(data).change();
//            $("#" + key).attr("href", data);
//            $("#" + key).attr("src", data);
//        });
//        $("#gestion-modal").modal("show");
//    });
//}


     