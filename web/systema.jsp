
<%-- 
    Document   : contentRoot
    Created on : 13/03/2015, 02:45:57 PM
    Author     : sebasariz
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<%
    if (request.getSession().getAttribute("usuario") == null) {
%>
<logic:redirect forward="login" />
<%        } else {

%>
<!DOCTYPE html>
<html lang="es">
    <head>
        <meta charset="utf-8">
        <title><bean:message key="sga.title"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
        <meta name="author" content="Iam Magis S.A.S." />    
        <meta name="format-detection" content="telephone=no">
        <meta name="keywords" content="smart city, ciudad inteligente, iot, fuerza de ventas, force sale, tecnologia, agroindustria, automatización, negocios inteligentes, business intelligence" />
        <meta name="description" content="Sistema de gestión operativo, permite controlar, automatizar y optimizar tus espacios, industrias y lugares productivos,
              controla todo en una sola plataforma." />

        <!-- Bootstrap core CSS -->
        <link href="bootstrap/bootstrap.min.css" rel="stylesheet">
        <!-- Icon Set -->
        <link href="css/icon.css" rel="stylesheet">
        <!-- Font Awesome -->
        <link href="css/font-awesome.min.css" rel="stylesheet">
        <!-- Hover Css -->
        <link href="css/hover-min.css" rel="stylesheet">
        <!-- Light Gallery -->
        <link href="css/lightgallery.min.css" rel="stylesheet">
        <!-- Animate Css -->
        <link href="css/animate.min.css" rel="stylesheet">
        <!-- Animsition -->
        <link href="css/animsition.min.css" rel="stylesheet">
        <!-- App min -->
        <link href="css/select2.min.css" rel="stylesheet">
        <!-- Select2 -->
        <link href="css/select2.min.css" rel="stylesheet">
        <!-- Core css -->
        <link href="css/app.css" rel="stylesheet" class="core-css"> 
        <!-- Jquery -->
        <script src="js/jquery-1.11.2.min.js"></script>


        <link href="codebase/dhtmlxgrid.css" rel="stylesheet" type="text/css" >
        <script src="codebase/dhtmlxgrid.js"></script>

        <link type="image/x-icon" href="img/ico.png" rel="shortcut icon"/>




        <!-- Calendar Css -->
        <link href="css/fullcalendar.min.css" rel="stylesheet">

        <!-- Animsition -->
        <link href="css/animsition.min.css" rel="stylesheet">

        <!-- WYSIWYG Editor -->
        <link href="css/summernote.min.css" rel="stylesheet">
        <!-- Moment -->
        <script src="js/moment.min.js"></script>
        <!-- Date Picker -->
        <link href="css/bootstrap-datetimepicker.min.css" rel="stylesheet"> 
        <!-- Datatable -->
        <link href="css/dataTables.bootstrap.css" rel="stylesheet">    


        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/buttons/1.6.1/css/buttons.bootstrap.min.css">
        <!-- Masonry -->
        <script src="js/masonry.min.js"></script> 
        <!-- Light Gallery -->
        <script src="js/jssor.slider-28.0.0.min.js" type="text/javascript"></script>
        <script src="js/uncompressed/lightgallery.js"></script>
        <script src="js-in/global.js"></script>
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.17.1/locale/es.js"></script>
        <style>
            .modal{
                direction:ltr;
                overflow-y: auto;
            }


            .modal-open{
                overflow:auto;
            } 


            /*            .btn-main {
                            background-color: 
                            border-color:  
                            color: #fff;
                        }
                        .btn-main:hover {
                            background-color: 
                            border-color: 
                            color: #ffffff;
                        }
                        .header-top .navbar-header {
                            background-color:  
                        }
                        .btn-primary {
                            border-color:  
                            background-color: 
                            color: #fff;
                        }
                        .pagination > .active > a,
                        .pagination > .active > a:focus,
                        .pagination > .active > a:hover,
                        .pagination > .active > span,
                        .pagination > .active > span:focus,
                        .pagination > .active > span:hover {
                            background-color:  
                            border-color:  
                        } */
        </style>
        <style>
            /*jssor slider loading skin double-tail-spin css*/
            .jssorl-004-double-tail-spin img {
                animation-name: jssorl-004-double-tail-spin;
                animation-duration: 1.6s;
                animation-iteration-count: infinite;
                animation-timing-function: linear;
            }

            @keyframes jssorl-004-double-tail-spin {
                from { transform: rotate(0deg); }
                to { transform: rotate(360deg); }
            }

            /*jssor slider bullet skin 031 css*/
            .jssorb031 {position:absolute;}
            .jssorb031 .i {position:absolute;cursor:pointer;}
            .jssorb031 .i .b {fill:#000;fill-opacity:0.6;stroke:#fff;stroke-width:1600;stroke-miterlimit:10;stroke-opacity:0.8;}
            .jssorb031 .i:hover .b {fill:#fff;fill-opacity:1;stroke:#000;stroke-opacity:1;}
            .jssorb031 .iav .b {fill:#fff;stroke:#000;stroke-width:1600;fill-opacity:.6;}
            .jssorb031 .i.idn {opacity:.3;}

            /*jssor slider arrow skin 051 css*/
            .jssora051 {display:block;position:absolute;cursor:pointer;}
            .jssora051 .a {fill:none;stroke:#fff;stroke-width:360;stroke-miterlimit:10;}
            .jssora051:hover {opacity:.8;}
            .jssora051.jssora051dn {opacity:.5;}
            .jssora051.jssora051ds {opacity:.3;pointer-events:none;}
        </style>
    </head>

    <body>

        <!-- /.wrapper -->
        <div class="wrapper animsition has-footer">

            <!-- Start header -->
            <header class="header-top navbar">


                <div class="navbar-header">
                    <button type="button" class="navbar-toggle side-nav-toggle">
                        <span class="sr-only">Navegacion</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>

                    <a class="navbar-brand" style="padding: 0px;" href="LoadInicio.sga"><img src="<bean:write name="logo"/>" height="100%;"></a>

                    <ul class="nav navbar-nav-xs">
                        <li>
                            <a href="#" class="font-lg collapse" data-toggle="collapse" data-target="#headerNavbarCollapse">
                                <i class="icon-user move-d-1"></i>
                            </a>
                        </li> 
                    </ul>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse navbar-collapse" id="headerNavbarCollapse">




                    <ul class="nav navbar-nav navbar-right">

                        <li class="user-profile dropdown">
                            <a href="#" class="clearfix dropdown-toggle" data-toggle="dropdown"> 
                                <div class="user-name"><bean:write name="usuario" property="nombre"/> <bean:write name="usuario" property="apellidos"/> <span class="caret m-l-5"></span></div>
                            </a>
                            <ul class="dropdown-menu dropdown-animated pop-effect" role="menu">
                                <!--<li><a href="#" onclick="loadPerfil()">Perfil</a></li>-->
                                <li><a href="#acercaDeModal" data-toggle="modal">Acerca de</a></li>
                                <li><a href="LogoutUsuario.sga">Salida segura</a></li>
                            </ul>
                        </li>

                    </ul>
                </div><!-- /.navbar-collapse -->
            </header>
            <!-- End Header -->

            <!-- Start Side Navigation -->
            <aside class="side-navigation-wrap sidebar-fixed">
                <div class="sidenav-inner">
                    <ul class="side-nav magic-nav">
                        <li class="side-nav-header">
                            Menu
                        </li>
                        <li class="first-link active"><a href="LoadInicio.sga" class="animsition-link"><i class="icon-stats-dots"></i> <span class="nav-text"><bean:message key="sga.tablerocontrol"/></span></a></li>
                                <% out.print(request.getSession().getAttribute("menu"));%>   
                    </ul>
                </div><!-- /.sidebar-inner -->
            </aside>
            <!-- End Side Navigation -->


            <!-- End Right Sidebar -->

            <!-- End Second Level Right Sidebar -->

            <!-- Start Main Container -->
            <div class="main-container">
                <%--aqui va el contenido --%>
                <%String contentID = request.getAttribute("contenido").toString();%>
                <jsp:include page='<%=contentID%>' />
            </div>
            <!-- End Main Container -->

            <!-- Start Footer -->
            <!--            <footer class="footer">
                            &copy; 2015. <b>ConsulTIC'S.</b> by <b><a href="http://www.iammagis.com">Iam Magis S.A.S.</a></b>
                        </footer>-->
            <!-- End Footer -->


            <div class="modal modal-scale fade" id="acercaDeModal">
                <div class="modal-dialog modal-md">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                            <h4 class="modal-title font-header text-dark">Acerca de</h4>
                        </div>
                        <div class="modal-body" style="text-align: center;">
                            <div class="table-row" >
                                <div class="table-cell-row"> 
                                    <table style="width: 100%;">
                                        <tr>
                                            <td><a  href="http://www.iammagis.com" ><img src="img/iammagis.png"/></a></td>
                                        </tr>
                                    </table> 
                                </div> 
                            </div>
                            <div class="table-row">
                                <div class="table-cell-row">
                                    Desarrollado por Iam Magis S.A.S.  
                                </div>
                            </div>
                            <div class="table-row">
                                <div class="table-cell-row">
                                    Medellin - Colombia
                                </div>
                            </div>
                            <div class="table-row">
                                <div class="table-cell-row">
                                    <a style="color: #000088; margin:0 auto;" href="http://www.iammagis.com">www.iammagis.com</a> 
                                </div>
                            </div

                        </div>
                        <div class="modal-footer"> 
                            <button type="button" class="btn btn-dark" data-dismiss="modal">Cerrar</button>
                        </div>
                    </div><!-- /.modal-content -->
                </div><!-- /.modal-dialog -->
            </div><!-- /.modal -->
            <!-- End Modal -->

        </div>

        <div class="modal fade" id="modal-perfil">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Perfil</h4>
                    </div>
                    <form name="Usuario" id="PerfilUsuario" enctype="multipart/form-data" method="post" accept-charset="utf-8">
                        <div class="modal-body"> 
                            <div class="modal-body">
                                <div class="input-group" style="width: 100%; padding: 5px;">
                                    <input type="hidden" name="idusuario" id="idusuarioP">
                                    <input class="form-control" type="text" name="nombre" id="nombreP" placeholder="<bean:message key="sga.usaurio.nombre"/>" required="true">
                                </div>
                                <div class="input-group" style="width: 100%; padding: 5px;">
                                    <input class="form-control" type="text" name="apellidos" id="apellidosP" placeholder="<bean:message key="sga.usaurio.apellidos"/>" required="true">
                                </div> 
                                <div class="input-group" style="width: 100%; padding: 5px;">
                                    <input class="form-control" type="email" name="email" id="emailP" placeholder="<bean:message key="sga.usaurio.email"/>" required="true" readonly>
                                </div>
                                <div class="input-group" style="width: 100%; padding: 5px;">
                                    <input class="form-control" type="password" name="pass" id="passP" placeholder="<bean:message key="sga.usaurio.pass"/>" required="true">
                                </div> 
                            </div> 
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal"><bean:message key="cerrar"/></button>
                            <button type="submit" class="btn btn-primary"><bean:message key="guardar"/></button>
                        </div>
                    </form>
                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal --> 
        <div class="modal fade" id="modal-esperar">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <!--<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>-->
                        <h4 class="modal-title">Espere</h4>
                    </div>

                    <div class="modal-body">
                        Cargando ...
                    </div>
                    <div class="modal-footer">

                    </div>

                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal --> 
        <div class="modal fade" id="modal-error">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Info</h4>
                    </div>

                    <div class="modal-body">
                        <p id="errorLabel"></p>
                    </div>
                    <div class="modal-footer">

                    </div>

                </div><!-- /.modal-content -->
            </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->   
        <!--MODALES DE GESTIO-->
        <div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="mySmallModalLabel" aria-hidden="true" id="remove-modal">
            <div class="modal-dialog modal-sm">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title">Desea eliminarlo ?</h4>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-primary" data-dismiss="modal">Cancelar</button> 
                        <button type="button" class="btn btn-default" onclick="confirmarEliminar()">Confirmar</button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Bootstrap -->
        <script src="bootstrap/bootstrap.min.js"></script>

        <!-- Modernizr -->
        <script src="js/modernizr.min.js"></script>

        <!-- Slim Scroll -->
        <script src="js/jquery.slimscroll.min.js"></script>

        <!-- Animsition -->
        <script src="js/jquery.animsition.min.js"></script>

        <!-- Sparkline -->
        <script src="js/jquery.sparkline.min.js"></script>

        <!-- Flot -->
        <script src="js/jquery.flot.min.js"></script>
        <script src="js/jquery.flot.pie.js"></script>


        <!-- Simple Calendar -->
        <script src="js/uncompressed/simplecalendar.js"></script>

        <!-- Skycons -->
        <script src='js/uncompressed/skycons.js'></script>

        <!-- Noty -->
        <script src="js/jquery.noty.packaged.min.js"></script>

        <!-- Cookie -->
        <script src='js/uncompressed/jquery.cookie.js'></script>

        <!-- App -->
        <script src="js/app.min.js"></script>


        <!-- Datatable -->
        <script src="js/jquery.dataTables.min.js"></script>
        <script src="js/dataTables.bootstrap.min.js"></script> 

        <script src="https://cdn.datatables.net/buttons/1.6.1/js/dataTables.buttons.min.js"></script> 
        <script src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.flash.min.js"></script> 
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jszip/3.1.3/jszip.min.js"></script> 
        <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/pdfmake.min.js"></script> 
        <script src="https://cdnjs.cloudflare.com/ajax/libs/pdfmake/0.1.53/vfs_fonts.js"></script> 
        <script src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.html5.min.js"></script> 
        <script src="https://cdn.datatables.net/buttons/1.6.1/js/buttons.print.min.js"></script> 




        <!-- WYSIWYG Editor -->
        <script src="js/summernote.min.js"></script>

        <!-- Colorbox -->
        <script src="js/jquery.colorbox-min.js"></script>  

        <!--select 2-->
        <script src="js/select2.min.js"></script>
        <!-- Date Picker -->
        <script src="js/bootstrap-datetimepicker.min.js"></script>
        <!--Start of Tawk.to Script--> 
        <script async src="https://www.googletagmanager.com/gtag/js?id=UA-153537027-1"></script>
        <script type="text/javascript">
                            var Tawk_API = Tawk_API || {}, Tawk_LoadStart = new Date();
                            (function () {
                                var s1 = document.createElement("script"), s0 = document.getElementsByTagName("script")[0];
                                s1.async = true;
                                s1.src = 'https://embed.tawk.to/5d767fdb77aa790be3332fac/default';
                                s1.charset = 'UTF-8';
                                s1.setAttribute('crossorigin', '*');
                                s0.parentNode.insertBefore(s1, s0);
                            })();

                            window.dataLayer = window.dataLayer || [];
                            function gtag() {
                                dataLayer.push(arguments);
                            }
                            gtag('js', new Date());
                            gtag('config', 'UA-153537027-1');

                            colorReplace("#7f887f","<bean:write name="primary"/>"); 
//#b7cd24
        </script>

        <!-- new relic -->
<!--        <script src="js/newrelic.js"></script>-->
    </body>
</html>
<%
    }
%>