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
        <bean:message key="sga.empresas"/>
        <div class="header-toolbar font-main">

        </div>
    </div>
    <ol class="breadcrumb">
        <li><a href="LoadInicio.sga">Inicio</a></li>
        <li><a href="LoadHistorialPorVendedor.sga"><bean:message key="sga.empresas"/></a></li> 
    </ol>
    <div class="content-wrap">
        <div class="panel panel-default">
            <div class="panel-heading font-header">Citerios de busqueda</div>
            <div class="panel-body">
                <div class="row m-t-15">
                    <div class="col-sm-3">
                        <label class="col-sm-2 control-label">Desde:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control font-12" id="datetimepickerInicio"/>
                            <span class="icon-calendar h4 no-m form-control-feedback" aria-hidden="true"></span>
                        </div>

                    </div>
                    <div class="col-sm-3">
                        <label class="col-sm-2 control-label">Hasta:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control font-12" id="datetimepickerFin"/>
                            <span class="icon-calendar h4 no-m form-control-feedback" aria-hidden="true"></span>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <label class="col-sm-2 control-label">Vendedor</label>
                        <div class="col-sm-10">
                            <select id="idvendedor" class="form-control select">
                                <logic:iterate id="usuario" name="usuarios">
                                    <option value="<bean:write name="usuario" property="idUsuario"/>"><bean:write name="usuario" property="nombre"/> <bean:write name="usuario" property="apellidos"/></option>
                                </logic:iterate>
                            </select> 
                        </div>
                    </div>
                    <div class="col-sm-2">
                       <div class="col-sm-12">
                            <button class="btn btn-main form-control" onclick="search()" type="button">Buscar</button>
                        </div>
                    </div>
                </div> 
            </div>
        </div><!-- /.panel -->

        <div class="panel panel-default">
            <div class="table-responsive">
                <table class="table table-striped font-12" id="datatable">

                </table>
            </div>
        </div>
    </div> 


    <div id="gestion-modal_empacados" class="modal modal-styled fade">
        <%-- por aca va el formulario de dita usuario --%>
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title"></h3>
                </div>
                <form id="gestion_empacados" method="post" enctype="multipart/form-data">
                    <div class="modal-body" >    
                        <input type="hidden" name="idPedido" id="idPedidoemp">  


                        <div class="row">
                            <div class="col-lg-2">
                                <label for="text-input"><bean:message key="sga.codigo"/></label>
                            </div>
                            <div class="col-lg-4">
                                <label for="text-input"><bean:message key="sga.nit"/></label>
                            </div>
                            <div class="col-lg-6">
                                <label for="text-input"><bean:message key="sga.empresa"/></label> 
                            </div>

                        </div>  
                        <!--AQUI LOS DATOS-->
                        <div class="row">
                            <div class="col-lg-2">
                                <label id="codigoEmrpesa" style="font-weight:500;"></label>
                            </div>
                            <div class="col-lg-4">
                                <label id="nitEmrpesa" style="font-weight:500;"></label>
                            </div>
                            <div class="col-lg-6"> 
                                <label id="nombreEmrpesa" style="font-weight:500;"></label>
                            </div> 
                        </div>
                        <div class="row">  
                            <div class="col-lg-6">
                                <label for="text-input" ><bean:message key="sga.direccion"/></label> 
                            </div>
                            <div class="col-lg-6">
                                <label for="text-input"><bean:message key="sga.telefono"/></label> 
                            </div>
                        </div>
                        <div class="row">  
                            <div class="col-lg-6"> 
                                <label id="direccionEmrpesa" style="font-weight:500;"></label>
                            </div>
                            <div class="col-lg-6"> 
                                <label id="telefonoEmrpesa" style="font-weight:500;"></label>
                            </div>
                        </div>
                        <br>
                        <span class="border-top my-3"></span> 
                        <div class="row">
                            <div class="col-lg-2">
                                <label for="text-input"><bean:message key="sga.referencia"/></label>
                            </div>
                            <div class="col-lg-6">
                                <label for="text-input"><bean:message key="sga.item"/></label>
                            </div>
                            <div class="col-lg-2">
                                <label for="text-input"><bean:message key="sga.cantidad"/></label>
                            </div>
                            <div class="col-lg-2">
                                <label for="text-input"><bean:message key="sga.empcados"/></label>
                            </div>
                        </div>

                        <input type="hidden" id="arrayProductosSolicitadosStringEmpacados" name="arrayProductosSolicitadosString"> 
                        <div class="row" id="items_empacados_container" style="margin-top: 20px;">

                        </div>

                        <div class="row" style="margin-top: 10px;">
                            <div class="col-lg-12">
                                <label for="text-input" ><bean:message key="sga.nota"/></label> 
                            </div>
                            <div class="col-lg-12">
                                <textarea id="nota" disabled class="form-control" style="min-height: 50px;"></textarea>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-lg-12">
                                <label for="text-input" ><bean:message key="sga.numeroguia"/></label> 
                            </div>
                            <div class="col-lg-12">
                                <input type="text" name="numeroGuia" id="numeroGuia" class="form-control"/>
                            </div>
                        </div> 
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-tertiary" data-dismiss="modal"><bean:message key="cerrar"/></button> 
                    </div>
                </form>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 

    <script src="js-in/operacion/historialvendedor.js"></script>
    <script>
                                $(function () {
                                    init();
                                });

    </script>


</script>
</html>
