/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga.autoinit;

import com.google.gson.Gson;
import com.iammagis.sga.mongo.EmpresaMongoController;
import com.iammagis.sga.mongo.UsuarioMongoController;
import com.iammagis.sga.mongo.beans.Empresa;
import com.iammagis.sga.mongo.beans.Usuario;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 *
 * @author sebastianarizmendy
 */
public class AutostartSync implements ServletContextListener {

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
    Gson gson = new Gson();

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("===============START DAEMONS======================");
        TimerTask timerTask = new TimerTask() {

            @Override
            public void run() {
                System.out.println("inicializando DAMONS");

            }
        };

        Timer timer = new Timer();
        int minutos = 10 * 60 * 1000;
        //timer.schedule(timerTask, 10000, minutos);

    }

    @Override

    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("==============STOP FTP READER===========================");
    }
}
