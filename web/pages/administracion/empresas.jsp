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
            <div class="btn-toolbar font-12" role="toolbar">

                <div class="btn-group" role="group">
                    <button type="button" class="btn btn-default btn-icon" id="cargar"><i class="fa fa-file"></i></button> 
                    <button type="button" class="btn btn-default btn-icon" id="add"><i class="fa fa-plus"></i></button> 
                </div>
            </div>
        </div>
    </div>
    <ol class="breadcrumb">
        <li><a href="#">Inicio</a></li>
        <li><a href="#"><bean:message key="sga.empresas"/></a></li> 
    </ol>
    <div class="content-wrap">
        <div class="panel panel-default">
            <div class="table-responsive">
                <table class="table table-striped font-12" id="datatable">

                </table>
            </div>
        </div>
    </div> 

    <!--MODAL CARGAR--> 
    <div id="gestion-modal-load" class="modal modal-styled fade">
        <%-- por aca va el formulario de dita usuario --%>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title">Carga masiva</h3>
                </div> 
                <form id="load-masiva" method="post" enctype="multipart/form-data">
                    <div class="modal-body" >  
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.arvhico"/></label>
                            <input type="file" name="file"  required="true" class="form-control"/>
                        </div> 
                    </div> 
                    <div class="modal-footer">
                        <button type="button" class="btn btn-warning" data-dismiss="modal"><bean:message key="cerrar"/></button> 
                        <button type="submit" class="btn btn-success"><bean:message key="cargar"/></button> 
                    </div>
                </form>
            </div>
        </div>
    </div>

    <!--MODAL GESTION--> 
    <div id="gestion-modal" class="modal modal-styled fade">
        <%-- por aca va el formulario de dita usuario --%>
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title"></h3>
                </div>
                <form id="gestion" method="post" enctype="multipart/form-data">
                    <div class="modal-body" > 
                        <input type="hidden" name="idEmpresa" id="idEmpresa"> 
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.nit"/></label>
                            <input type="text" name="nit" id="nit" required="true" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.tipoEmpresa"/></label>
                            <select name="tipoEmpresa" id="tipoEmpresa" class="form-control chosen-select select" onchange="loadTipoEmpresa(this.value)">
                                <option value="2">Cliente</option>
                            </select> 
                        </div>  
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.codigo"/></label>
                            <input type="text" name="codigo" id="codigo" required="true" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.activa"/></label>
                            <select name="activa" id="activa">
                                <option value="1">Si</option>
                                <option value="0">No</option>
                            </select>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.nombre"/></label>
                            <input type="text" name="nombre" id="nombre" required="true" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.direccion"/></label>
                            <input type="text" name="direccion" id="direccion" required="true" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.ciudad"/></label>
                            <input type="text" name="ciudad" id="ciudad" required="true" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.contacto"/></label>
                            <input type="text" name="contacto" id="contacto" required="true"  class="form-control"/>
                        </div> 
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.telefono"/></label> 
                            <input type="text" name="telefono" id="telefono" required="true" class="form-control"/>
                        </div>
                        <div class="form-group css">
                            <label for="text-input"><bean:message key="sga.uuid"/></label> 
                            <input type="text" name="uuid" id="uuid" class="form-control"/>
                        </div> 
                        <div class="form-group css"> 
                            <label for="text-input"><bean:message key="sga.correodespacho"/></label> 
                            <input type="text" name="correodespacho" id="correodespacho" class="form-control"/>
                        </div> 
                        <div class="form-group css">
                            <img id="logo" style="width: 80%; text-align: center;" >
                            <label for="text-input"><bean:message key="sga.logo"/></label> 
                            <input type="file" name="file" class="form-control"/>
                        </div> 
                        <div class="form-group css">
                            <label for="text-input"><bean:message key="sga.primario"/></label> 
                            <input type="text" name="colorPrimario" id="colorPrimario"  class="form-control"/>
                        </div> 
                        <div class="form-group css">
                            <label for="text-input"><bean:message key="sga.secundario"/></label> 
                            <input type="text" name="colorSecundario" id="colorSecundario"  class="form-control"/>
                        </div> 
                        <div class="form-group css" style="text-align: center;">
                            <img id="puntero" style="width: 50%;">
                            <label for="text-input"><bean:message key="sga.puntero"/></label> 
                            <input type="file" name="filePuntero" class="form-control"/>
                        </div> 
                           
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-tertiary" data-dismiss="modal"><bean:message key="cerrar"/></button>
                        <button type="submit" class="btn btn-primary"><bean:message key="guardar"/></button>
                    </div>
                </form>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 


    <script src="js-in/administracion/empresas.js"></script>
    <script>
                                $(function () {
                                    init(<%=request.getAttribute("empresas")%>);
                                });
                                if (1 == <bean:write name="usuario" property="idTipoUsuario"/>) {
                                    $("#tipoEmpresa").append("<option value='1'>SGA</option>");
                                }
    </script>


</script>
</html>
