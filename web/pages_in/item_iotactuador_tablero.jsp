<%--
    Document   : item
    Created on : 10-sep-2019, 11:07:26
    Author     : sebastianarizmendy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <div class="col-md-4">
        <div class="panel no-b" id="a$id">
            <div class="panel-heading font-header bg-main p-tb-30">
                <div class="text-center"><img src="$image" class="img-circle img-70 imagen" alt=""></div>
                <div class="m-t-10 text-center h2">
                    $nombre
                    <div style="min-height:80px; word-wrap:break-word;" class="h4 valor">$valor</div>
                    <div class="font-11 text-muted fecha">sincronización : $fecha</div>
                </div>
            </div>
        </div><!-- /.panel -->
    </div>
</html>