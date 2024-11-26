<%-- 
    Document   : usuarios
    Created on : 26/09/2012, 04:50:53 PM
    Author     : sebasariz
--%>

<%@page pageEncoding="UTF-8" contentType="text/html"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<html>
    <style>
        .select2 {
            width:100%!important;
        }

        .scrollFix {
            line-height: 1.35;
            overflow: hidden;
            white-space: nowrap;
        }
        #map-canvas {
            height: 100%;
            margin: 0px;
            padding: 0px
        }
    </style>
    <script type="text/javascript">
        var myArray = ["AIzaSyCoIAg4MmdAJfHBYRV0LkC0jN4Btq4ED3E"];
        var rand = myArray[Math.floor(Math.random() * myArray.length)];
        document.write("<script src='https://maps.googleapis.com/maps/api/js?key=" + rand + "&libraries=visualization'><\/script>");
    </script>
    <div class="page-header no-breadcrumb font-header">
        <bean:message key="sga.mapa"/>
        <div class="header-toolbar font-main">
            <div class="btn-toolbar font-12" role="toolbar"> 
            </div>
        </div>
    </div>
    <ol class="breadcrumb">
        <li><a href="#">Inicio</a></li>
        <li><a href="#"><bean:message key="sga.mapa"/></a></li> 
    </ol>



    <div class="content-wrap"> 
        <div class="panel panel-default">
            <div class="panel-heading font-header">Citerios de busqueda</div>
            <div class="panel-body">
                <div class="row m-t-15">
                    <div class="col-sm-6">
                        <div class="form-group"> 
                            <select id="tipo" class="form-control">
                                <option value="0">Fuerza de ventas tiempo real</option>
                                <option value="1">Mapa de calor vendedores</option>
                                <option value="2">Mapa calor pedidos</option>
                            </select> 
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <button class="btn btn-main form-control" onclick="search()" type="button">Buscar</button>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-6">
                        <label class="col-sm-2 control-label">Desde:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control font-12" id="datetimepickerInicio"/>
                            <span class="icon-calendar h4 no-m form-control-feedback" aria-hidden="true"></span>
                        </div>

                    </div>
                    <div class="col-sm-6">
                        <label class="col-sm-2 control-label">Hasta:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control font-12" id="datetimepickerFin"/>
                            <span class="icon-calendar h4 no-m form-control-feedback" aria-hidden="true"></span>
                        </div>
                    </div>
                </div> 
            </div>
        </div><!-- /.panel -->


        <div class="panel panel-default">
            <div class="table-responsive" style="padding-top: 15px;">
                <div id="map-canvas" ></div>
            </div>
        </div> 
    </div>

    <script src="js-in/reportes/mapa.js"></script>
    <script>

    </script>


</script>
</html>
