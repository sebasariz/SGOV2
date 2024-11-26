<%-- 
    Document   : item
    Created on : 10-sep-2019, 11:07:26
    Author     : sebastianarizmendy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <div class="item">
        <div class="col-lg-2">
            <input type="hidden" value="$item" class="itemid">
            <input type="hidden" value="$total" class="total"> 
            <input type="text" value="$itemref" placeholder="Ref" disabled class="form-control itemref">
        </div>
        <div class="col-lg-6"> 
            <input type="text" value="$itemtext" placeholder="Item" disabled class="form-control item">
        </div>
        <div class="col-lg-2">
            <input type="text" value="$cantidad" placeholder="Cantidad" disabled class="form-control cantidad">
        </div> 
        <div class="col-lg-2">
            <button class="btn btn-danger form-control" onclick="$(this).parent().parent().remove()" type="button">-</button>
        </div>
    </div>
</html>
