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
        <bean:message key="sga.logActividades"/>
        <div class="header-toolbar font-main">
            <div class="btn-toolbar font-12" role="toolbar"> 
            </div>
        </div>
    </div>
    <ol class="breadcrumb">
        <li><a href="#">Inicio</a></li>
        <li><a href="#"><bean:message key="sga.logActividades"/></a></li> 
    </ol>
    <div class="content-wrap">
        <div class="panel panel-default">
            <div class="panel-heading font-header">Citerios de busqueda</div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-sm-4">
                        <label class="col-sm-2 control-label">Desde:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control font-12" id="datetimepickerInicio"/>
                            <span class="icon-calendar h4 no-m form-control-feedback" aria-hidden="true"></span>
                        </div>

                    </div>
                    <div class="col-sm-4">
                        <label class="col-sm-2 control-label">Hasta:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control font-12" id="datetimepickerFin"/>
                            <span class="icon-calendar h4 no-m form-control-feedback" aria-hidden="true"></span>
                        </div>
                    </div>
                    <div class="col-sm-4">
                        <div class="form-group">
                            <button class="btn btn-main form-control" onclick="loadReporte()" type="button">Buscar</button>
                        </div>
                    </div>
                </div> 
            </div>
        </div>

        <div class="panel panel-default">
            <div class="table-responsive">
                <table class="table table-striped font-12" id="datatable">

                </table>
            </div>
        </div>
    </div> 




    <script src="js-in/administracion/logactividades.js"></script>
    <script>
                                $(function () {
                                    init(<%=request.getAttribute("logActividades")%>);
                                });
    </script>


</script>
</html>
