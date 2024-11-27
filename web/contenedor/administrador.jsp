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
        <bean:message key="sga.tableroiot"/>
        <div class="header-toolbar font-main">
            <div class="btn-toolbar font-12" role="toolbar">
 
            </div>
        </div>
    </div>
    <ol class="breadcrumb">
        <li><a href="#">Inicio</a></li>
        <li><a href="#"><bean:message key="sga.tableroiot"/></a></li> 
    </ol>
     <div class="panel panel-default">
        <div class="panel-heading font-header">Sensores</div>
        <div class="panel-body">
            <div class="content-wrap" id="dispositivos">  
            </div> 
        </div>
    </div>

 

    <div class="panel panel-default">
        <div class="panel-heading font-header">Actuadores</div>
        <div class="panel-body">
            <div class="content-wrap" id="actuadores">  
            </div> 
        </div>
    </div>
    <script src="js-in/iot/iotdevices_tablerocontrol.js"></script>
    <script>
        $(function () {
            init(<%=request.getAttribute("devices")%>,<%=request.getAttribute("actuadores")%>);
        });
    </script>


</script>
</html>
