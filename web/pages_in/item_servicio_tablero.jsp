<%-- 
    Document   : item
    Created on : 10-sep-2019, 11:07:26
    Author     : sebastianarizmendy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <div class="col-md-4" >
        <div class="panel no-b">
            <div class="panel-heading font-header bg-main p-tb-30" style="min-height: 200px;">
                <div class="text-center"><img src="$image" class="img-circle img-70" alt=""></div>
                <div class="m-t-10 text-center">
                    Fecha ultimo servicio: $ultimoServicio
                    <div class="h1">$nombre</div>  
                    <div class="font-11 text-muted">$descripcionCorta</div> 
                </div> 
            </div>
            <div class="panel-body no-p">
                <div class="p-tb-10 font-header text-center" style="min-height: 120px;">$descripcion</div>

            </div>
            <div class="panel-footer no-p no-b-t bg-white">
                <div class="font-header text-center"><button class="btn btn-warning w-100  p-3" style="width: 100%;">Gestión</button></div>
            </div>
        </div><!-- /.panel -->
    </div>
</html>
