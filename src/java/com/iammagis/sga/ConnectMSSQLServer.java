/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iammagis.sga;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author sebastianarizmendy
 */
public class ConnectMSSQLServer {

    Connection conn;

    public void dbConnect(String db_connect_string, String db_userid, String db_password) throws ClassNotFoundException, SQLException {

        //en bodega = 1 son productos disponibles para la venta
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        conn = DriverManager.getConnection(db_connect_string,
                db_userid, db_password);
        System.out.println("connected");

    }

    public void dbDisconnected() throws SQLException {
        conn.close();
    }

    public ResultSet executeQuery(String queryString) throws SQLException {
        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery(queryString);
        return rs;
    }
    
    public void executeQueryInsert(String queryString) throws SQLException { 
        Statement statement = conn.createStatement();
        int resultado = statement.executeUpdate(queryString); 
        System.out.println("Resultado Insert: "+resultado);
    }

     
}
