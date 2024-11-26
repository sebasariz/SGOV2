<%-- 
    Document   : item
    Created on : 10-sep-2019, 11:07:26
    Author     : sebastianarizmendy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <div class="col-md-4">
        <div class="panel no-b">
            <div class="panel-heading font-header bg-main p-tb-30">
                <div class="text-center"><img src="$image" class="img-circle img-70" alt=""></div>
                <div class="m-t-10 text-center">
                    $nombre
                    <div style="min-height:80px; word-wrap:break-word;" class="h1">$valor</div> 
                    <div class="font-11 text-muted">Ultima sincronización : $fecha</div> 
                </div> 
            </div>
            <div class="panel-body no-p">
                <ul class="list-unstyled list-float list-bordered two-col clearfix b-t no-m-b">
                    <li class="p-10 p-tb-10 text-center font-12 pointer">
                        Min: $valorMin
                    </li>
                    <li class="p-10 p-tb-10 text-center font-12 pointer">
                        MAx: $valorMax
                    </li> 
                </ul>
            </div>
            <div class="panel-footer no-p no-b-t bg-white">
                <ul class="list-unstyled list-float list-bordered two-col clearfix b-all b-t no-m-b">
                    <li class="p-10 p-tb-10 text-center font-12 pointer">
                        <div class="font-header text-upper">Historico</div>
                    </li>
                    <li class="p-10 p-tb-10 text-center font-12 pointer">
                        <div class="font-header text-upper">Alertas</div>
                    </li>
                </ul>
            </div>
        </div><!-- /.panel -->
    </div>
</html>
