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
                        <label>
                            Mis pedidos
                        </label>
                    </div>
                    <div id="pedidos" class="container-flui">  
                        <table class="table table-striped font-12" id="datatable">

                        </table>
                    </div>
                </div>
            </div> 
        </div>
    </div> <!-- /#content-container --> 

    <div id="gestion-modal_empacados" class="modal modal-styled fade">
        <%-- por aca va el formulario de dita usuario --%>
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title" id="title-edit">Entrega</h3>
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
                        <input type="hidden" id="estadoemp" name="estado">

                        <div class="row" id="items_empacados_container" style="margin-top: 20px;">

                        </div>  
                        <div class="row" style="margin-top: 10px;">
                            <div class="col-lg-12">
                                <label for="text-input" ><bean:message key="sga.nota"/></label> 
                            </div>
                            <div class="col-lg-12">
                                <textarea id="notaemp" disabled class="form-control" style="min-height: 50px;"></textarea>
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
                        <div class="row"   style="margin-top: 20px;">
                            <div class="col-lg-5">
                                <label for="text-input">Subtotal:</label>
                            </div>
                            <div class="col-lg-5"></div>
                            <div class="col-lg-2">
                                <input type="text" class="form-control" disabled data-type='currency' id="subtotalemp" value="0">
                            </div>
                            <div class="col-lg-5">
                                <label for="text-input">Iva:</label>
                            </div>
                            <div class="col-lg-5"></div>
                            <div class="col-lg-2">
                                <input type="text" class="form-control" disabled data-type='currency' id="ivaemp" value="0">
                            </div>
                            <div class="col-lg-5">
                                <label for="text-input">Total:</label>
                            </div>
                            <div class="col-lg-5"></div>
                            <div class="col-lg-2">
                                <input type="text" class="form-control" disabled data-type='currency' id="totalemp" value="0">
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" onclick="print()" class="btn btn-orange pull-left" ><bean:message key="imprimir"/></button>
                        <button type="button" class="btn btn-tertiary" data-dismiss="modal"><bean:message key="cerrar"/></button>
                        <button type="submit" class="btn btn-primary" value="guardar"><bean:message key="guardar"/></button>
                        <button type="submit" class="btn btn-success" value="despachar"><bean:message key="despachar"/></button>
                    </div>
                </form>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal --> 

    <!--MODAL GESTION--> 
    <div id="gestion-modal" class="modal modal-styled fade">
        <%-- por aca va el formulario de dita usuario --%>
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h3 class="modal-title">Editar</h3>
                </div>
                <form id="gestion" method="post" enctype="multipart/form-data">
                    <div class="modal-body" >   
                        <input type="hidden" name="idPedido" id="idPedidoEdit"/>
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
                                <label id="codigoEmrpesaEdit" style="font-weight:500;"></label>
                            </div>
                            <div class="col-lg-4">
                                <label id="nitEmrpesaEdit" style="font-weight:500;"></label>
                            </div>
                            <div class="col-lg-6"> 
                                <label id="nombreEmrpesaEdit" style="font-weight:500;"></label>
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
                                <label id="direccionEmrpesaEdit" style="font-weight:500;"></label>
                            </div>
                            <div class="col-lg-6"> 
                                <label id="telefonoEmrpesaEdit" style="font-weight:500;"></label>
                            </div>
                        </div>   

                        <div class="row">  
                            <div class="col-lg-12">
                                <label for="text-input" ><bean:message key="sga.nota"/></label> 
                            </div> 
                        </div>
                        <div class="row">  
                            <div class="col-lg-12"> 
                                <label id="notaEmrpesaEdit" style="font-weight:500;"></label>
                            </div>
                        </div>   

                        <div class="row">
                            <div class="col-lg-5">
                                <label for="text-input"><bean:message key="sga.item"/></label>
                            </div>
                            <div class="col-lg-3">
                                <label for="text-input"><bean:message key="sga.cantidad"/></label>
                            </div>
                            <div class="col-lg-2">
                                <label for="text-input"><bean:message key="sga.total"/></label>
                            </div>
                        </div> 

                        <div class="row">    
                            <div class="col-lg-5">
                                <select id="idProducto" class="form-control chosen-select select">
                                    <option value="0">Seleccione un item</option>
                                    <logic:iterate name="productos" id="producto">
                                        <option data-valor="<bean:write name="producto" property="valor"/>" value="<bean:write name="producto" property="idProducto"/>"><bean:write name="producto" property="nombre"/> (Ref: <bean:write name="producto" property="referencia"/> - Cant: <bean:write name="producto" property="cantidad"/>) - valor: $<bean:write name="producto" property="valor"/></option>
                                    </logic:iterate>
                                </select> 
                            </div>
                            <div class="col-lg-3">
                                <input type="text" placeholder="Cantidad" class="form-control" id="cantidad" onchange="calculateValor()">
                            </div>
                            <div class="col-lg-2">
                                <input type="text" placeholder="Valor" class="form-control" id="valor">
                            </div>
                            <div class="col-lg-2">
                                <button class="btn btn-success form-control" onclick="addItem()" type="button">+</button>
                            </div>
                            <input type="hidden" id="arrayProductosSolicitadosString" name="arrayProductosSolicitadosString">
                        </div>

                        <div class="row" id="items" style="margin-top: 20px;">

                        </div>

                        <div class="row" style="margin-top: 10px;">
                            <div class="col-lg-12">
                                <label for="text-input" ><bean:message key="sga.nota"/></label> 
                            </div>
                            <div class="col-lg-12">
                                <textarea name="nota" id="nota" class="form-control" style="min-height: 50px;"></textarea>
                            </div>
                        </div>

                        <div class="row"   style="margin-top: 20px;">
                            <div class="col-lg-5">
                                <label for="text-input">Subtotal:</label>
                            </div>
                            <div class="col-lg-5"></div>
                            <div class="col-lg-2">
                                <input type="text" class="form-control" disabled data-type='currency' id="subtotal" value="0">
                            </div>
                            <div class="col-lg-5">
                                <label for="text-input">Iva:</label>
                            </div>
                            <div class="col-lg-5"></div>
                            <div class="col-lg-2">
                                <input type="text" class="form-control" disabled data-type='currency' id="iva" value="0">
                            </div>
                            <div class="col-lg-5">
                                <label for="text-input">Total:</label>
                            </div>
                            <div class="col-lg-5"></div>
                            <div class="col-lg-2">
                                <input type="text" class="form-control" disabled data-type='currency' id="total" value="0">
                            </div>
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
    <script src="js-in/operacion/pedido.js"></script>
    <script type="text/javascript">

                                    $(function () {
                                        window.addEventListener('resize', function (event) {
                                            $(".panel-body").each(function () {
                                                $(this).css('height', "");           // clear height values
                                            });
                                        });
                                    });


                                    $(document).ready(function () {
                                        init(<%=request.getAttribute("pedidos")%>);
                                    });

    </script>


</html>
