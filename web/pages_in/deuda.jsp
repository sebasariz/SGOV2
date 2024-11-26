<%-- 
    Document   : item
    Created on : 10-sep-2019, 11:07:26
    Author     : sebastianarizmendy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <div class="item">
        <div class="col-lg-3">
            <input type="hidden" value="$fechaemision" class="fechaEmision"> 
            <input type="text" value="$fechaemisionString" placeholder="Fecha" disabled class="form-control fechaemisionString">
        </div>
        <div class="col-lg-3">
            <input type="hidden" value="$fechavencimiento" class="fechaVencimiento"> 
            <input type="text" value="$fechavencimientoString" placeholder="Fecha" disabled class="form-control fechavencimientoString">
        </div>
        <div class="col-lg-2"> 
            <input type="text" value="$obligacion" placeholder="Obligacion" disabled class="form-control obligacion">
        </div>
        <div class="col-lg-2">
            <input type="text" value="$valor" placeholder="Valor" disabled class="form-control valor">
        </div> 
        <div class="col-lg-2">
            <button class="btn btn-danger form-control" onclick="$(this).parent().parent().remove()" type="button">-</button>
        </div>
    </div>
</html>
