/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//VARIABLES GLOBALES DE INTERACCION
var idInterfer = 2162;

var formatter = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
    minimumFractionDigits: 0
});

var optionsTable = {
    "order": [[0, "desc"]],
    "language": {
        "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
    },
    dom: 'Bfrtip',
    buttons: ['copy', 'excel', 'pdf', 'print'],
    columns: [
        {title: "No."},
        {title: "Nit", width: "105px"},
        {title: "Nombre"},
        {title: "Fecha solicitud"},
        {title: "Vendedor"},
        {title: "Estado"},
        {title: "Acciones", width: "110px"}
    ],
    'rowCallback': function (row, data, index) {
        if (data[5] == 'Pendiente') {
            $(row).find('td:eq(5)').css('color', 'white').css('background', '#ecbe3a').css('vertical-align', 'middle');
        }
        if (data[5] == 'Empacado parcial') {
            $(row).find('td:eq(5)').css('color', 'white').css('background', '#1f2892').css('vertical-align', 'middle');
        }
        if (data[5] == 'Completado') {
            $(row).find('td:eq(5)').css('color', 'white').css('background', '#1f9242').css('vertical-align', 'middle');
        }
        if (data[5] == 'Despachado') {
            $(row).find('td:eq(5)').css('color', 'white').css('background', '#1f925d').css('vertical-align', 'middle');
        }
        if (data[5] == 'Entregado') {
            $(row).find('td:eq(5)').css('color', 'white').css('background', '#5b650e').css('vertical-align', 'middle');
        }
        if (data[5] == 'Despacho parcial') {
            $(row).find('td:eq(5)').css('color', 'white').css('background', '#4886b3').css('vertical-align', 'middle');
        }
        if (data[5] == 'Eliminado') {
            $(row).find('td:eq(5)').css('color', 'white').css('background', '#000000').css('vertical-align', 'middle');
        }
    }
};


var optionsTableCartera = {
    "order": [[0, "desc"]],
    "language": {
        "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
    },
    dom: 'Bfrtip',
    responsive: true,
    buttons: ['copy', 'excel', 'pdf', 'print'],
    autoWidth: true,
    columns: [
        {title: "Cliente"},
        {title: "Identificación"},
        {title: "Nombre"},
        {title: "Telefono"},
//        {title: "Contacto"},
        {title: "Estado"},
//        {title: "Dirección"},
//        {title: "Email"},
        {title: "Acciones"}
    ],
    'rowCallback': function (row, data, index) {
        var color = '';
        switch (data[4]) {
            case 'Nuevo':
                color = "#ecbe3a";
                break;
            case 'Acuerdo de pago':
                color = "#ecbe3a";
                break;
            case 'Sin contacto':
                color = "#EC863A";
                break;
            case 'Cobro jurídico':
                color = "#BD3D3D";
                break;
            case 'Pagado':
                color = "#2a7b4b";
                break;
            case 'Localizado':
                color = "#64d4e4";
                break;
        }
        $(row).find('td:eq(4)').css('color', 'white').css('background', color).css('vertical-align', 'middle');
    }
};

var optionsTableCarteraGestion = {
    "order": [[0, "desc"]],
    "language": {
        "url": "//cdn.datatables.net/plug-ins/9dcbecd42ad/i18n/Spanish.json"
    },
    dom: 'Bfrtip',
    responsive: true,
    buttons: ['copy', 'excel', 'pdf', 'print'],
//    lengthChange: false,

    columnDefs: [{"width": "950px"}],
    columns: [
        {title: "Cliente"},
        {title: "Identificación"},
        {title: "Nombre"},
//        {title: "Telefono"},
        {title: "Valor Total"},
        {title: "Valor Pagado"},
        {title: "Estado"},
        {title: "Acciones"}
    ],
    'rowCallback': function (row, data, index) {
        var color = "";
        switch (data[5]) {
            case 'Nuevo':
                color = '#3AA0E9';
                break;
            case 'Acuerdo de pago':
                color = '#ecbe3a';
                break;
            case 'Sin contacto':
                color = '#EC863A';
                break;
            case 'Cobro jurídico':
                color = '#BD3D3D';
                break;
            case 'Pagado':
                color = '#2a7b4b';
                break;
            case 'Localizado':
                color = '#64d4e4';
                break;
        }
        $(row).find('td:eq(5)').css('color', 'white').css('background', color).css('vertical-align', 'middle').css('text-align', 'center');
    }
};

Date.prototype.formatDate = function (format)
{
    var date = this;
    if (!format)
        format = "dd/MM/yyyy";

    var month = date.getMonth() + 1;
    var year = date.getFullYear();

    format = format.replace("MM", month.toString());

    if (format.indexOf("yyyy") > -1)
        format = format.replace("yyyy", year.toString());
    else if (format.indexOf("yy") > -1)
        format = format.replace("yy", year.toString().substr(2, 2));

    format = format.replace("dd", date.getDate().toString());

    var hours = date.getHours();
    if (format.indexOf("t") > -1)
    {
        if (hours > 11)
            format = format.replace("t", "pm")
        else
            format = format.replace("t", "am")
    }
    if (format.indexOf("HH") > -1)
        format = format.replace("HH", hours.toString());
    if (format.indexOf("hh") > -1) {
        if (hours > 12)
            hours - 12;
        if (hours == 0)
            hours = 12;
        format = format.replace("hh", hours.toString());
    }
    if (format.indexOf("mm") > -1)
        format = format.replace("mm", date.getMinutes().toString());
    if (format.indexOf("ss") > -1)
        format = format.replace("ss", date.getSeconds().toString());
    return format;
}

Date.prototype.dateToYMD = function ()
{
    var date = this;
    var d = date.getDate();
    var m = date.getMonth() + 1; //Month from 0 to 11
    var y = date.getFullYear();

    return '' + y + '-' + (m <= 9 ? '0' + m : m) + '-' + (d <= 9 ? '0' + d : d);
}

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


String.prototype.replaceAll = function (search, replacement) {
    var target = this;
    return target.replace(new RegExp(search, 'g'), replacement);
};


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


function colorReplace(findHexColor, replaceWith) {
    // Convert rgb color strings to hex
    // REF: https://stackoverflow.com/a/3627747/1938889
    function rgb2hex(rgb) {
        if (/^#[0-9A-F]{6}$/i.test(rgb))
            return rgb;
        rgb = rgb.match(/^rgb\((\d+),\s*(\d+),\s*(\d+)\)$/);
        function hex(x) {
            return ("0" + parseInt(x).toString(16)).slice(-2);
        }
        return "#" + hex(rgb[1]) + hex(rgb[2]) + hex(rgb[3]);
    }

    // Select and run a map function on every tag
    $('*').map(function (i, el) {
        // Get the computed styles of each tag
        var styles = window.getComputedStyle(el);

        // Go through each computed style and search for "color"
        Object.keys(styles).reduce(function (acc, k) {
            var name = styles[k];
            var value = styles.getPropertyValue(name);
            if (value !== null && (name.indexOf("color") >= 0)) {
                // Convert the rgb color to hex and compare with the target color 
                if (value.indexOf("rgb(") >= 0 && rgb2hex(value) === findHexColor) {
//                    alert("value: " + value)
                    // Replace the color on this found color attribute
                    $(el).css(name, replaceWith);
                }
            }
        });
    });
    var c = $(".side-nav > li .sub-menu.list-style-circle > ul > li > a:before").css("color","background-color");
    console.log("c: "+c);
     
}
