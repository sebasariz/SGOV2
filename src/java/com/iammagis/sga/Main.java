/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga;

import com.iammagis.sga.mongo.PedidoMongoController;
import com.mongodb.DBObject;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author sebasariz
 */
public class Main {

    public static void main(String[] args) throws IOException {
        PedidoMongoController pedidoMongoController = new PedidoMongoController();
        List<DBObject> pedidos = pedidoMongoController.getPedidos();
        
    }

}
