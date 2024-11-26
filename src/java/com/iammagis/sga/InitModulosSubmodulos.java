/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga;

import com.iammagis.sga.mongo.ModuloMongoController;
import com.iammagis.sga.mongo.SubmoduloMongoController;
import com.iammagis.sga.mongo.beans.Modulo;
import com.iammagis.sga.mongo.beans.Submodulo;

/**
 *
 * @author sebastianarizmendy
 */
public class InitModulosSubmodulos {

    public static void main(String[] args) {

        ModuloMongoController moduloMongoController = new ModuloMongoController();
        SubmoduloMongoController submoduloMongoController = new SubmoduloMongoController();

//        Modulo modulo1 = new Modulo();
//        modulo1.setIdModulo(1);
//        modulo1.setNombre("Administración");
//        moduloMongoController.createModulo(modulo1);
//
//        Submodulo submodulo1 = new Submodulo();
//        submodulo1.setIdSubmodulo(1);
//        submodulo1.setNombre("Usuarios");
//        submodulo1.setAccion("LoadUsuarios");
//        submodulo1.setIdModulo(1);
//        submoduloMongoController.createSubmodulo(submodulo1);
//
//        Submodulo submodulo2 = new Submodulo();
//        submodulo2.setIdSubmodulo(2);
//        submodulo2.setNombre("Asugnación submodulos");
//        submodulo2.setAccion("LoadAsignacionSubmodulos");
//        submodulo2.setIdModulo(1);
//        submoduloMongoController.createSubmodulo(submodulo2);
//
//        Submodulo submodulo3 = new Submodulo();
//        submodulo3.setIdSubmodulo(3);
//        submodulo3.setNombre("Empresas");
//        submodulo3.setAccion("LoadEmpresas");
//        submodulo3.setIdModulo(1);
//        submoduloMongoController.createSubmodulo(submodulo3); 
//
//        Submodulo submodulo5 = new Submodulo();
//        submodulo5.setIdSubmodulo(5);
//        submodulo5.setNombre("Productos");
//        submodulo5.setAccion("LoadProductos");
//        submodulo5.setIdModulo(1);
//        submoduloMongoController.createSubmodulo(submodulo5);
//  
//        Modulo modulo2 = new Modulo();
//        modulo2.setIdModulo(2);
//        modulo2.setNombre("Operación");
//        moduloMongoController.createModulo(modulo2);
//
//        Submodulo submodulo6 = new Submodulo();
//        submodulo6.setIdSubmodulo(6);
//        submodulo6.setNombre("Pedidos");
//        submodulo6.setAccion("LoadPedidos");
//        submodulo6.setIdModulo(2);
//        submoduloMongoController.createSubmodulo(submodulo6);
//        Modulo modulo3 = new Modulo();
//        modulo3.setIdModulo(3);
//        modulo3.setNombre("Reportes");
//        moduloMongoController.createModulo(modulo3);
//        Submodulo submodulo7 = new Submodulo();
//        submodulo7.setIdSubmodulo(7);
//        submodulo7.setNombre("Inventario");
//        submodulo7.setAccion("LoadInventarios");
//        submodulo7.setIdModulo(3);
//        submoduloMongoController.createSubmodulo(submodulo7);
//        Submodulo submodulo8 = new Submodulo();
//        submodulo8.setIdSubmodulo(8);
//        submodulo8.setNombre("Estado de cuenta");
//        submodulo8.setAccion("LoadEstadoCuenta");
//        submodulo8.setIdModulo(3);
//        submoduloMongoController.createSubmodulo(submodulo8);
//        Submodulo submodulo8 = new Submodulo();
//        submodulo8.setIdSubmodulo(8);
//        submodulo8.setNombre("Estado de cuenta");
//        submodulo8.setAccion("LoadEstadoCuenta");
//        submodulo8.setIdModulo(3);
//        submoduloMongoController.createSubmodulo(submodulo8);
//        Submodulo submodulo9 = new Submodulo();
//        submodulo9.setIdSubmodulo(9);
//        submodulo9.setNombre("Indicadores");
//        submodulo9.setAccion("LoadIndicadores");
//        submodulo9.setIdModulo(3);
//        submoduloMongoController.createSubmodulo(submodulo9);
//        Submodulo submodulo10 = new Submodulo();
//        submodulo10.setIdSubmodulo(10);
//        submodulo10.setNombre("Historial por vendedor");
//        submodulo10.setAccion("LoadHistorialPorVendedor");
//        submodulo10.setIdModulo(2);
//        submoduloMongoController.createSubmodulo(submodulo10);
//        Submodulo submodulo11 = new Submodulo();
//        submodulo11.setIdSubmodulo(10);
//        submodulo11.setNombre("Historial por cliente");
//        submodulo11.setAccion("LoadHistorialPorCliente");
//        submodulo11.setIdModulo(2);
//        submoduloMongoController.createSubmodulo(submodulo11);
//        Submodulo submodulo12 = new Submodulo();
//        submodulo12.setIdSubmodulo(11);
//        submodulo12.setNombre("Pedidos eliminados");
//        submodulo12.setAccion("LoadPedidosEliminados");
//        submodulo12.setIdModulo(2);
//        submoduloMongoController.createSubmodulo(submodulo12);
//        Submodulo submodulo13 = new Submodulo();
//        submodulo13.setIdSubmodulo(12);
//        submodulo13.setNombre("Clientes pendientes");
//        submodulo13.setAccion("LoadEmpresasPendientes");
//        submodulo13.setIdModulo(2);
//        submoduloMongoController.createSubmodulo(submodulo13);
//        Submodulo submodulo14 = new Submodulo();
//        submodulo14.setNombre("Historial por pedidos");
//        submodulo14.setAccion("LoadHistorialPorPedido");
//        submodulo14.setIdModulo(2);
//        submoduloMongoController.createSubmodulo(submodulo14);
//        Submodulo submodulo15 = new Submodulo();
//        submodulo15.setNombre("Recorrido historico vendedores");
//        submodulo15.setAccion("LoadRecorridoHistorico");
//        submodulo15.setIdModulo(3);
//        submoduloMongoController.createSubmodulo(submodulo15);
//        Submodulo submodulo17 = new Submodulo();
//        submodulo17.setNombre("Asignación de submodulos a empresas");
//        submodulo17.setAccion("LoadSubmodulosEmpresas");
//        submodulo17.setIdModulo(1);
//        submoduloMongoController.createSubmodulo(submodulo17);
//         
//CARTERA
//        Modulo modulo4 = new Modulo();
//        modulo4.setIdModulo(4);
//        modulo4.setNombre("Cartera");
//        moduloMongoController.createModulo(modulo4);
//        
//        Submodulo submodulo18 = new Submodulo();
//        submodulo18.setNombre("Deudores");
//        submodulo18.setAccion("LoadCartera");
//        submodulo18.setIdModulo(4);
//        submoduloMongoController.createSubmodulo(submodulo18);
//IOT
//        Submodulo submodulo19 = new Submodulo();
//        submodulo19.setNombre("Lista de atención");
//        submodulo19.setAccion("LoadlistaAtencion");
//        submodulo19.setIdModulo(4);
//        submoduloMongoController.createSubmodulo(submodulo19);
//        Modulo modulo5 = new Modulo();
//        modulo5.setIdModulo(5);
//        modulo5.setNombre("IOT");
//        moduloMongoController.createModulo(modulo5);
//
//        Submodulo submodulo20 = new Submodulo();
//        submodulo20.setNombre("Dispositivos");
//        submodulo20.setAccion("LoadDispositivosIot");
//        submodulo20.setIdModulo(5);
//        submoduloMongoController.createSubmodulo(submodulo20);
//
//        Submodulo submodulo21 = new Submodulo();
//        submodulo21.setNombre("Tablero de control");
//        submodulo21.setAccion("LoadTableroIot");
//        submodulo21.setIdModulo(5);
//        submoduloMongoController.createSubmodulo(submodulo21);
//        
//        Submodulo submodulo22 = new Submodulo();
//        submodulo22.setNombre("Reportes");
//        submodulo22.setAccion("LoadReportesIot");
//        submodulo22.setIdModulo(5);
//        submoduloMongoController.createSubmodulo(submodulo22);
//        
//        Submodulo submodulo23 = new Submodulo();
//        submodulo23.setNombre("Alarmas");
//        submodulo23.setAccion("LoadAlarmasIot");
//        submodulo23.setIdModulo(5);
//        submoduloMongoController.createSubmodulo(submodulo23);

//        Submodulo submodulo24 = new Submodulo();
//        submodulo24.setNombre("Log de acciones");
//        submodulo24.setAccion("LoadLogActividades");
//        submodulo24.setIdModulo(1);
//        submoduloMongoController.createSubmodulo(submodulo24);
//AQUI SERVICIOS BPO
//        Modulo modulo6 = new Modulo();
//        modulo6.setIdModulo(6);
//        modulo6.setNombre("Servicios B.P.O.");
//        moduloMongoController.createModulo(modulo6);
//
//        Submodulo submodulo25 = new Submodulo();
//        submodulo25.setNombre("Servicios");
//        submodulo25.setAccion("LoadServiciosRiesgosInternos");
//        submodulo25.setIdModulo(6);
//        submoduloMongoController.createSubmodulo(submodulo25);
//        
//        Submodulo submodulo26 = new Submodulo();
//        submodulo26.setNombre("Tablero de control");
//        submodulo26.setAccion("LoadTableroDeControlServiciosInternos");
//        submodulo26.setIdModulo(6);
//        submoduloMongoController.createSubmodulo(submodulo26);
//        
//        Submodulo submodulo27 = new Submodulo();
//        submodulo27.setNombre("Historial");
//        submodulo27.setAccion("LoadHistorialRiesgosInternos");
//        submodulo27.setIdModulo(6);
//        submoduloMongoController.createSubmodulo(submodulo27);
//        Submodulo submodulo28 = new Submodulo();
//        submodulo28.setNombre("Solicitud");
//        submodulo28.setAccion("LoadSolicitudRiesgosInternos");
//        submodulo28.setIdModulo(6);
//        submoduloMongoController.createSubmodulo(submodulo28);
//AQUI COMIENZA EL CURRIER
//        Modulo modulo7 = new Modulo();
//        modulo7.setIdModulo(7);
//        modulo7.setNombre("Distribución");
//        moduloMongoController.createModulo(modulo7);
//
//        Submodulo submodulo29 = new Submodulo();
//        submodulo29.setNombre("Solicitud servicio");
//        submodulo29.setAccion("LoadSolicitudservicioRuta");
//        submodulo29.setIdModulo(7);
//        submoduloMongoController.createSubmodulo(submodulo29);
//        
//        Submodulo submodulo30 = new Submodulo();
//        submodulo30.setNombre("Programación paqueteo");
//        submodulo30.setAccion("LoadPaqueteo");
//        submodulo30.setIdModulo(7);
//        submoduloMongoController.createSubmodulo(submodulo30);
//        
//        Submodulo submodulo31 = new Submodulo();
//        submodulo31.setNombre("Seguimiento ruteo");
//        submodulo31.setAccion("LoadSeguimientoRuteo");
//        submodulo31.setIdModulo(7);
//        submoduloMongoController.createSubmodulo(submodulo31);
//        Submodulo submodulo32 = new Submodulo();
//        submodulo32.setNombre("Historial paquete");
//        submodulo32.setAccion("LoadHistorialPaquete");
//        submodulo32.setIdModulo(7);
//        submoduloMongoController.createSubmodulo(submodulo32);
//        Submodulo submodulo33 = new Submodulo();
//        submodulo33.setNombre("Geocercas");
//        submodulo33.setAccion("LoadGeocercas");
//        submodulo33.setIdModulo(7);
//        submoduloMongoController.createSubmodulo(submodulo33);
//        Submodulo submodulo34 = new Submodulo();
//        submodulo34.setNombre("Programación recolección");
//        submodulo34.setAccion("LoadRecoleccion");
//        submodulo34.setIdModulo(7);
//        submoduloMongoController.createSubmodulo(submodulo34);
//
//        Submodulo submodulo35 = new Submodulo();
//        submodulo35.setNombre("Almacenamiento");
//        submodulo35.setAccion("LoadBodega");
//        submodulo35.setIdModulo(7);
//        submoduloMongoController.createSubmodulo(submodulo35);
        
        Submodulo submodulo36 = new Submodulo();
        submodulo36.setNombre("Georeferenciacion");
        submodulo36.setAccion("LoadGeoreferenciacion");
        submodulo36.setIdModulo(7);
        submoduloMongoController.createSubmodulo(submodulo36);
    }

}
