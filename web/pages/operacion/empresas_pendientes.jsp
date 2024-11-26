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
                    <!--<button type="button" class="btn btn-default btn-icon" id="add"><i class="fa fa-plus"></i></button>--> 
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

    <!--MODAL GESTION--> 
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
                        <img id="logo" style="width: 80%">
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.nit"/></label>
                            <input type="text" name="nit" id="nit" required="true" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.tipoEmpresa"/></label>
                            <select name="tipoEmpresa" id="tipoEmpresa" class="form-control chosen-select select">
                                <option value="2">Cliente</option>
                            </select> 
                        </div>  
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.codigo"/></label>
                            <input type="text" name="codigo" id="codigo" required="true" class="form-control"/>
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
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.logo"/></label> 
                            <input type="file" name="logo" class="form-control"/>
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


    <div class="modal fade" id="aprobar-modal">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>-->
                    <h4 class="modal-title">Aprobación</h4>
                </div>

                <div class="modal-body">
                    Desea aprobar esta empresa ?
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-tertiary" data-dismiss="modal"><bean:message key="cerrar"/></button>
                    <button type="button" onclick="AprobarEmpresa()" class="btn btn-primary" data-dismiss="modal"><bean:message key="guardar"/></button>
                </div>

            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 

    <script src="js-in/operacion/empresas_pendientes.js"></script>
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
