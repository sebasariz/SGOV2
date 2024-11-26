<%-- 
    Document   : gestor
    Created on : 15-feb-2018, 21:41:35
    Author     : sebastianarizmendy
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<html>	 
    <div id="content-container"> 
        <div class="content-wrap">
            <div class="panel panel-default">
                <div class="table-responsive" style="padding-top: 15px;">
                    <div class="col-sm-12 col-lg-12">

                    </div>

                    <div class="col-sm-4 col-lg-4">
                        <div class="panel panel-default bg-danger panel-stat">
                            <div class="panel-body">
                                <div class="value ">
                                    <span><i class="icon-file-pdf"></i></span>
                                </div>
                                <div class="detail text-right">
                                    <h3 class="text-upper font-header no-m"><%=request.getAttribute("empresas_sgo_tamaño")%></h3>
                                    <small class="text-muted">Empresas SGO</small>
                                </div>
                            </div>
                        </div>
                    </div><!-- /.col --> 
                    <div class="col-sm-4 col-lg-4">
                        <div class="panel panel-default bg-warning panel-stat">
                            <div class="panel-body">
                                <div class="value">
                                    <span><i class="icon-coin-dollar"></i></span>
                                </div>
                                <div class="detail text-right">
                                    <h3 class="text-upper font-header no-m"><%=request.getAttribute("usuarios_registrados")%></h3>
                                    <small class="text-muted">Usuarios Registrados</small>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-4 col-lg-4">
                        <div class="panel panel-default bg-success panel-stat">
                            <div class="panel-body">
                                <div class="value">
                                    <span><i class="icon-books"></i></span>
                                </div>
                                <div class="detail text-right">
                                    <h3 class="text-upper font-header no-m"><%=request.getAttribute("empresas_clientes_tamaño")%></h3>
                                    <small class="text-muted">Empresas clientes</small>
                                </div>
                            </div>
                        </div>
                    </div>
                </div><!-- /.row --> 
            </div>

            <div class="panel panel-default">
                <div class="table-responsive" style="padding-top: 15px;">
                    <div class="col-sm-12 col-lg-12">
                        <label>
                            Empresas SGO
                        </label>
                    </div>
                    <table class="table table-striped font-12" id="datatable">

                    </table>
                </div>
            </div>



        </div>
    </div> <!-- /#content-container --> 

    <script src="js-in/root.js"></script>
    <script type="text/javascript">

        $(function () {
            window.addEventListener('resize', function (event) {
                $(".panel-body").each(function () {
                    $(this).css('height', "");           // clear height values
                });
            });
            //la carga de los proyectos 
        });
        $(document).ready(function () {
            init(<%=request.getAttribute("empresas_sgo")%>);
        });

    </script>

</html>
