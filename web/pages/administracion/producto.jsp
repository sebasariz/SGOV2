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
        <bean:message key="sga.productos"/>
        <div class="header-toolbar font-main">
            <div class="btn-toolbar font-12" role="toolbar">

                <div class="btn-group" role="group">
                    <button type="button" class="btn btn-default btn-icon" id="template"><i class="fa fa-archive"></i></button> 
                    <button type="button" class="btn btn-default btn-icon" id="cargar"><i class="fa fa-file"></i></button> 
                    <button type="button" class="btn btn-default btn-icon" id="add"><i class="fa fa-plus"></i></button> 
                </div>
            </div>
        </div>
    </div>
    <ol class="breadcrumb">
        <li><a href="#">Inicio</a></li>
        <li><a href="#"><bean:message key="sga.productos"/></a></li> 
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
                        <label>Recuerda que el formato del archivo debe ser XLS</label>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.reinicio"/></label>
                            <select class="form-control" name="reinicio">
                                <option value="0">No</option>
                                <option value="1">Si</option>
                            </select>
                        </div> 
                    </div> 
                    <div class="modal-body" >  
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.arvhico"/></label>
                            <input type="file" name="file"  required="true" accept="application/vnd.ms-excel" class="form-control"/>
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
                        <input type="hidden" name="idProducto" id="idProducto"> 
 
                       
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.nombre"/></label>
                            <input type="text" name="nombre" id="nombre" required="true" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.marca"/></label>
                            <input type="text" name="marca" id="marca" required="true" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.referencia"/></label>
                            <input type="text" name="referencia" id="referencia" required="true" class="form-control"/>
                        </div>
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.bodega"/></label>
                            <input type="text" name="bodega" id="bodega" required="true"  class="form-control"/>
                        </div> 
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.cantidad"/></label> 
                            <input type="text" name="cantidad" id="cantidad" required="true" class="form-control"/>
                        </div> 
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.valor"/></label> 
                            <input type="text" name="valor" id="valor" required="true" class="form-control"/>
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


    <script src="js-in/administracion/producto.js"></script>
    <script>
        $(function () {
            init(<%=request.getAttribute("productos")%>);
        });
    </script>


</script>
</html>
