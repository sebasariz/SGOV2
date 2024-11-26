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
    </style>
    <div class="page-header no-breadcrumb font-header">
        <bean:message key="sga.reportes"/>
        <div class="header-toolbar font-main">
            <div class="btn-toolbar font-12" role="toolbar">

                <div class="btn-group" role="group"> 
                    <button type="button" class="btn btn-default btn-icon" id="add"><i class="fa fa-plus"></i></button> 
                </div>
            </div>
        </div>
    </div>
    <ol class="breadcrumb">
        <li><a href="#">Inicio</a></li>
        <li><a href="#"><bean:message key="sga.reportes"/></a></li> 
    </ol>


    <div class="content-wrap">
        <div class="panel panel-default">
            <div class="panel-heading font-header">Citerios de busqueda</div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-6">
                        <label class="col-sm-6 control-label">Desde:</label>
                        <div>
                            <input type="text" class="form-control font-12" id="datetimepickerInicio"/>
                            <span class="icon-calendar h4 no-m form-control-feedback" aria-hidden="true"></span>
                        </div>

                    </div>
                    <div class="col-md-6">
                        <label class="col-sm-6 control-label">Hasta:</label>
                        <div>
                            <input type="text" class="form-control font-12" id="datetimepickerFin"/>
                            <span class="icon-calendar h4 no-m form-control-feedback" aria-hidden="true"></span>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <label class="col-sm-6 control-label"><bean:message key="sga.iotDevice"/></label>
                        <select name="idIOTDevice" id="idIOTDevice" class="form-control chosen-select select">
                            <logic:iterate id="IOTDevice" name="iotDevices">
                                <option value="<bean:write name="IOTDevice" property="idIOTDevice"/>"><bean:write name="IOTDevice" property="nombre"/></option> 
                            </logic:iterate>
                        </select> 
                    </div>
                    <div class="col-md-2">
                        <label class="col-sm-6 control-label">Puntos</label>
                        <input type="number" class="form-control" id="numeroPuntos" value="10"/>
                    </div>
                    <div class="col-md-2"> 
                        <label class="col-sm-6 control-label"><br></label>
                        <button class="btn btn-main form-control" onclick="buscarReporte()" type="button">Buscar</button> 
                    </div>
                </div>  
            </div>
        </div>


        <div class="row"> 
            <!-- /.col -->
            <div class="col-sm-12 col-lg-4">
                <div class="panel panel-default panel-stat no-icon" style="background-color: #ecdf3c;">
                    <div class="panel-body content-wrap">
                        <div class="value">
                            <h2 class="font-header no-m" id="numeroAlertas">0</h2>
                        </div>
                        <div class="detail text-right">
                            <div class="text-upper">Numero de alertas</div> 
                        </div>
                    </div>
                </div>
            </div><!-- /.col -->
            <div class="col-sm-12 col-lg-4">
                <div class="panel panel-default bg-gray panel-stat no-icon">
                    <div class="panel-body content-wrap">
                        <div class="value">
                            <h2 class="font-header no-m" id="valMax">0</h2>
                        </div>
                        <div class="detail text-right">
                            <div class="text-upper">valor maximo</div>
                        </div>
                    </div>
                </div>
            </div><!-- /.col -->

            <div class="col-sm-12 col-lg-4">
                <div class="panel panel-default bg-success panel-stat no-icon">
                    <div class="panel-body content-wrap">
                        <div class="value">
                            <h2 class="font-header no-m" id="valMin">0</h2>
                        </div>
                        <div class="detail text-right">
                            <div class="text-upper">valor Minimo</div>
                        </div>
                    </div>
                </div>
            </div><!-- /.col -->

        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel">
                    <div class="panel-body">
                        <div class="row font-12">
                            <div class="col-xs-9">
                                <span class="text-upper font-header">Detalle de dispositivo</span> 
                            </div> 
                        </div>
                        <div class="chart-placeholder line-placeholder m-t-10" style="padding: 0px; position: relative;"></div>
                    </div>
                    <div class="panel-footer bg-white">
                        <div class="row text-center">
                            <div class="col-xs-6">
                                <h4 class="no-m text-dark m-t-5 font-header" id="totalMuestas">0</h4>
                                <div class="font-12 m-t-5 m-b-5">Total de muestras</div>
                            </div>
                            <div class="col-xs-6">
                                <h4 class="no-m text-dark m-t-5 font-header" id="dias">0</h4>
                                <div class="font-12 m-t-5 m-b-5">Periodo de tiempo</div>
                            </div> 
                        </div>
                    </div>
                    <div class="loading-wrap">
                        <div class="loading-dots">
                            <div class="dot1"></div>
                            <div class="dot2"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row">
            <div class="col-lg-12">
                <div class="panel panel-default">
                    <div class="panel-body">
                        <div class="table-responsive">
                            <table class="table table-striped font-12" id="datatable">

                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div> 


    <script src="js-in/iot/iotdevices_reportes.js"></script>
    <script>
                            $(function () {
                                init();
                            });
    </script>


</script>
</html>
