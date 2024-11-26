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
        <bean:message key="sga.indicadores"/>
        <div class="header-toolbar font-main">
            <div class="btn-toolbar font-12" role="toolbar">


            </div>
        </div>
    </div>
    <ol class="breadcrumb">
        <li><a href="LoadInicio.sga">Inicio</a></li>
        <li><a href="LoadIndicadores.sga"><bean:message key="sga.indicadores"/></a></li> 
    </ol>
    <div class="content-wrap">
        <div class="panel panel-default">
            <div class="panel-heading font-header">Citerios de busqueda</div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-sm-5">
                        <label class="col-sm-6 control-label">Desde:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control font-12" id="datetimepickerInicio"/>
                            <span class="icon-calendar h4 no-m form-control-feedback" aria-hidden="true"></span>
                        </div>

                    </div>
                    <div class="col-sm-5">
                        <label class="col-sm-6 control-label">Hasta:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control font-12" id="datetimepickerFin"/>
                            <span class="icon-calendar h4 no-m form-control-feedback" aria-hidden="true"></span>
                        </div>
                    </div>

                    <div class="col-sm-2">
                        <div class="form-group">
                            <button class="btn btn-main form-control" onclick="buscarIndicadores()" type="button">Buscar</button>
                        </div>
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
                            <h2 class="font-header no-m" id="numeroPedido">0</h2>
                        </div>
                        <div class="detail text-right">
                            <div class="text-upper">Numero de pedidos</div> 
                        </div>
                    </div>
                </div>
            </div><!-- /.col -->
            <div class="col-sm-12 col-lg-4">
                <div class="panel panel-default bg-gray panel-stat no-icon">
                    <div class="panel-body content-wrap">
                        <div class="value">
                            <h2 class="font-header no-m" id="totalProductos">0</h2>
                        </div>
                        <div class="detail text-right">
                            <div class="text-upper">Total productos</div>
                        </div>
                    </div>
                </div>
            </div><!-- /.col -->

            <div class="col-sm-12 col-lg-4">
                <div class="panel panel-default bg-success panel-stat no-icon">
                    <div class="panel-body content-wrap">
                        <div class="value">
                            <h2 class="font-header no-m" id="pendientes">0</h2>
                        </div>
                        <div class="detail text-right">
                            <div class="text-upper">Pendientes</div>
                        </div>
                    </div>
                </div>
            </div><!-- /.col -->


            <div class="col-sm-12 col-lg-4">
                <div class="panel panel-default bg-purple panel-stat no-icon">
                    <div class="panel-body content-wrap">
                        <div class="value">
                            <h2 class="font-header no-m" id="valorVentas">0</h2>
                        </div>
                        <div class="detail text-right">
                            <div class="text-upper">Valor ventas pedidos</div>
                        </div>
                    </div>
                </div>
            </div><!-- /.col -->

            <div class="col-sm-12 col-lg-4">
                <div class="panel panel-default bg-purple panel-stat no-icon">
                    <div class="panel-body content-wrap">
                        <div class="value">
                            <h2 class="font-header no-m" id="valorVentasDespachadas">0</h2>
                        </div>
                        <div class="detail text-right">
                            <div class="text-upper">Valor ventas empacados</div>
                        </div>
                    </div>
                </div>
            </div>



            <div class="col-sm-12 col-lg-4">
                <div class="panel panel-default bg-purple panel-stat no-icon">
                    <div class="panel-body content-wrap">
                        <div class="value">
                            <h2 class="font-header no-m" id="valodVentasPendientes">0</h2>
                        </div>
                        <div class="detail text-right">
                            <div class="text-upper">Valor ventas pendietes</div>
                        </div>
                    </div>
                </div>
            </div> 

            <div class="col-sm-12 col-lg-4">
                <div class="panel panel-default bg-info panel-stat no-icon">
                    <div class="panel-body content-wrap">
                        <div class="value">
                            <h2 class="font-header no-m" id="pedidosEliminados">0</h2>
                        </div>
                        <div class="detail text-right">
                            <div class="text-upper">Pedidos eliminados</div>
                        </div>
                    </div>
                </div>
            </div> 

            <div class="col-sm-12 col-lg-4">
                <div class="panel panel-default bg-info panel-stat no-icon">
                    <div class="panel-body content-wrap">
                        <div class="value">
                            <h2 class="font-header no-m" id="valorPedidosEliminados">0</h2>
                        </div>
                        <div class="detail text-right">
                            <div class="text-upper">Valor pedidos eliminados</div>
                        </div>
                    </div>
                </div>
            </div>
            
           
            
            <div class="col-sm-12 col-lg-4">
                <div class="panel panel-default bg-info panel-stat no-icon">
                    <div class="panel-body content-wrap">
                        <div class="value">
                            <h2 class="font-header no-m" id="totalProductosEliminados">0</h2>
                        </div>
                        <div class="detail text-right">
                            <div class="text-upper">productos eliminados</div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-sm-12 col-lg-6">
                <div class="panel panel-default bg-warning panel-stat no-icon">
                    <div class="panel-body content-wrap">
                        <div class="value">
                            <h2 class="font-header no-m" id="parciales">0</h2>
                        </div>
                        <div class="detail text-right">
                            <div class="text-upper">Entregados parciales</div>
                        </div>
                    </div>
                </div>
            </div><!-- /.col -->
            
            <div class="col-sm-12 col-lg-6">
                <div class="panel panel-default bg-orange panel-stat no-icon">
                    <div class="panel-body content-wrap">
                        <div class="value">
                            <h2 class="font-header no-m" id="despachados">0</h2>
                        </div>
                        <div class="detail text-right">
                            <div class="text-upper">Despachados</div>
                        </div>
                    </div>
                </div>
            </div><!-- /.col -->
        </div>
        <div class="row">
            <div class="col-md-6">
                <div class="panel no-b">
                    <div class="panel-body">
                        <div class="">
                            <div class="p-t-12">
                                <div class="font-16 text-dark">Top 10 mas vendidos</div> 
                            </div>
                        </div>
                    </div>
                    <ul class="list-group font-12" id="listaProductos"> 
                    </ul>

                </div><!-- /.panel -->
            </div>

            <div class="col-md-6">
                <div class="panel no-b">
                    <div class="panel-body">
                        <div class="">
                            <div class="p-t-12">
                                <div class="font-16 text-dark">Top vendedores</div> 
                            </div>
                        </div>
                    </div>
                    <ul class="list-group font-12" id="listaVendedores"> 
                    </ul> 
                </div><!-- /.panel -->
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <div class="panel">
                    <div class="panel-body">
                        <div class="row font-12">
                            <div class="col-xs-9">
                                <span class="text-upper font-header">Detalle productos por dia</span> 
                            </div> 
                        </div>
                        <div class="chart-placeholder line-placeholder m-t-10" style="padding: 0px; position: relative;"></div>
                    </div>
                    <div class="panel-footer bg-white">
                        <div class="row text-center">
                            <div class="col-xs-6">
                                <h4 class="no-m text-dark m-t-5 font-header" id="totalReferencias">0</h4>
                                <div class="font-12 m-t-5 m-b-5">Total de referencias</div>
                            </div>
                            <div class="col-xs-6">
                                <h4 class="no-m text-dark m-t-5 font-header" id="totalVendedores">0</h4>
                                <div class="font-12 m-t-5 m-b-5">Total vendedores activos</div>
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
    </div> 


    <script src="js-in/reportes/indicadores.js"></script>
    <script>
                                $(function () {
                                    init();
                                });

    </script>


</script>
</html>
