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
        <bean:message key="sga.dispositivos"/>
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
        <li><a href="#"><bean:message key="sga.dispositivos"/></a></li> 
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
                        <input type="hidden" name="idIOTDevice" id="idIOTDevice"> 
                        <div class="form-group" id="dispositivos">
                            <label for="text-input"><bean:message key="sga.empresa"/></label>
                            <select name="idEmpresa" id="idEmpresa" class="form-control chosen-select select">
                                <logic:iterate id="empresa" name="empresas">
                                    <option value="<bean:write name="empresa" property="idEmpresa"/>"><bean:write name="empresa" property="nombre"/></option> 
                                </logic:iterate>
                            </select> 
                        </div> 
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.nombre"/></label>
                            <input type="text" name="nombre" id="nombre" required="true" class="form-control"/>
                        </div>  
                        <div class="form-group">
                            <label for="text-input"><bean:message key="sga.uuid"/></label>
                            <input type="text" name="uuid" id="uuid" required="true" class="form-control"/>
                        </div>

                        <div class="form-group css" style="text-align: center;">
                            <select name="tipoString" id="tipoString" class="form-control chosen-select select">
                                <logic:iterate id="tipo" name="tipos">
                                    <option value="<bean:write name="tipo"/>"><bean:write name="tipo"/></option> 
                                </logic:iterate>
                            </select>
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


    <script src="js-in/iot/iotdevices.js"></script>
    <script>
        $(function () {
            init(<%=request.getAttribute("devices")%>);
        });
        if (1 != <bean:write name="usuario" property="idTipoUsuario"/>) {
            $("#dispositivos").hide();
        }
    </script>


</script>
</html>
