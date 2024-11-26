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
        data: grid,
        columns: [
            {title: "Nit"},
            {title: "Nombre"},
            {title: "Direccion"},
            {title: "Contacto"},
            {title: "Telefono"},
            {title: "Acciones"},
        ]
    });

    var table = $('#datatable-grupos').DataTable({
        "language": {
            "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
        },
        data: grid,
        columns: [
            {title: "Nombre"},
            {title: "Modulo"},
            {title: "Estado"}
        ]
    });
    $(".select").select2();
}


function LoadSubmodulosJson(idUsuario) {
    id = idUsuario;
    $.ajax({
        url: "LoadSubmodulosEmpresaJson.sga",
        data: {id: id}
    }).done(function (json) {
//        alert("test: " + JSON.stringify(json.grid)); 
        $('#datatable-grupos').dataTable().fnClearTable();
        $('#datatable-grupos').dataTable().fnAddData(json.grid);
        $("#gestion-modal-grupos").modal("show");
    });
}

function slect(select, idGrupo) {
    $.ajax({
        url: "SaveSubmodulosEmpresa.sga",
        data: {
            id: idGrupo,
            state: select.checked,
            idEmpresa: id
        }
    });
}