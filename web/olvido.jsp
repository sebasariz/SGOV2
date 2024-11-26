
<%@page contentType="text/html"%>
<%@page pageEncoding="UTF-8"%>

<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.apache.org/tags-logic" prefix="logic" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title><bean:message key="sga.title"/></title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
        <meta name="format-detection" content="telephone=no">
        <meta name="description" content="">
        <meta name="author" content="Iam Magis S.A.S.">

        <!-- Bootstrap core CSS -->
        <link href="bootstrap/bootstrap.min.css" rel="stylesheet">

        <!-- Icon Set -->
        <link href="css/icon.css" rel="stylesheet">

        <!-- Font Awesome -->
        <link href="css/font-awesome.min.css" rel="stylesheet">

        <!-- Animsition -->
        <link href="css/animsition.min.css" rel="stylesheet">

        <!-- Core css -->
        <link href="css/app.css" rel="stylesheet">
        <link type="image/x-icon" href="img/car.png" rel="shortcut icon"/>
    </head>

    <body class="bg-light">

        <!-- wrapper -->
        <div class="wrapper animsition">
            <div class="container text-center">
                <div class="single-wrap">
                    <div class="single-inner-padding text-center"> 
                        <img src="img/logo.png" width="70%"/>  

                        <h3 class="font-header no-m-t">Recuperación de credenciales</h3>
                        <form class="form col-md-12 center-block" action="Recuperar.sga" method="post">
                            <label style="color: green;"><html:errors property="complete" /></label>
                            <label style="color: red;"><html:errors property="register" /></label>
                            <div class="dots-divider m-t-20 center-block"><span class="icon-flickr4"></span></div>
                            <div class="form-group form-input-group m-t-30 m-b-5">
                                <input type="text" name="email" class="form-control input-lg font-14" placeholder="Correo electronico">
                            </div>

                            <div class="m-l-10 font-11 text-left"><a href="Login.sga">Inicio</a></div>

                            <button type="submit" class="btn btn-main btn-lg btn-block font-14 m-t-30">Recuperar</button>
                        </form>
                        <div class="m-t-20 text-muted font-11">&nbsp;</div>
                    </div><!-- /.login-inner -->
                </div><!-- /.login-wrap -->
            </div><!-- /.container -->
        </div>
        <!-- /.wrapper -->

        <!-- Jquery -->
        <script src="js/jquery-1.11.2.min.js"></script>
        <!-- Bootstrap -->
        <script src="bootstrap/bootstrap.min.js"></script>
        <!-- Modernizr -->
        <script src="js/modernizr.min.js"></script>
        <!-- Slim Scroll -->
        <script src="js/jquery.slimscroll.min.js"></script>
        <!-- Animsition -->
        <script src="js/jquery.animsition.min.js"></script>
        <script>
            $(function () {
                $(".animsition").animsition({
                    inClass: 'zoom-in-sm',
                    outClass: 'zoom-out-sm',
                    inDuration: 1500,
                    outDuration: 800,
                    linkElement: '.animsition-link',
                    // e.g. linkElement   :   'a:not([target="_blank"]):not([href^=#])'
                    loading: true,
                    loadingParentElement: 'body', //animsition wrapper element
                    loadingClass: 'animsition-loading',
                    unSupportCss: ['animation-duration',
                        '-webkit-animation-duration',
                        '-o-animation-duration'
                    ],
                    overlay: false,
                    overlayClass: 'animsition-overlay-slide',
                    overlayParentElement: 'body'
                });
            });
        </script>
        <!--Start of Tawk.to Script-->
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
        </script>
        <!--End of Tawk.to Script-->
    </body>
</html>