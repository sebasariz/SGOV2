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
            {title: "Nombre"},
            {title: "Fecha registro"},
            {title: "Fecha ultimo pedido"},
            {title: "Usuarios activos"},
            {title: "Valor Facturación"},
            {title: "Fecha de facturación"}
        ]
        , 'rowCallback': function (row, data, index) {
            var dateUltimaFecha = new Date(data[1]);
            var OneDay = new Date().getTime() + (1 * 24 * 60 * 60 * 1000);
            var halfDay = new Date().getTime() + (1 * 12 * 60 * 60 * 1000);
            if (halfDay > dateUltimaFecha) {
                //rojo
                $(row).find('td:eq(1)').css('color', 'white').css('background', '#f24c36').css('vertical-align', 'middle');
            } else if (OneDay > dateUltimaFecha) {
                //amarillo
                $(row).find('td:eq(1)').css('color', 'white').css('background', '#f2e536').css('vertical-align', 'middle');
            } else {
                //verde
                $(row).find('td:eq(1)').css('color', 'white').css('background', '#1d9432').css('vertical-align', 'middle');
            }
        }
    });
    $(".select").select2();
}


     