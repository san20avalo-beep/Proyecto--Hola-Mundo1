/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.programadornovato.holamundo_1;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author 50496
 */

public class ConexionDB{ 
    private static final String URL = "jdbc:sqlserver://SANDRIS\\SQLEXPRESS;" 
            + "databaseName=AvanzadaII;" 
            +"encrypt=true;" 
            +"trustServerCertificate=true;";
    private static final String USER = "sa";
    private static final String PASSWORD = "SQLSandra";
    
    private static Connection conexion = null;
    private ConexionDB(){}

    public static Connection getConexion (){
        try{
            if(conexion == null || conexion.isClosed()){
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                conexion = DriverManager.getConnection(URL,USER,PASSWORD);
            System.out.println("Conexion exitosa a Sql");
            }
        }catch(ClassNotFoundException e){
            System.out.println("Error no se encontro el Driver de mssql-jdbc");
        }catch(SQLException e){
            System.out.println("Error al conectar con SQL" + e.getMessage());
            }
        return conexion;
    }

    public static void cerrarConexion(){
        try{
            if(conexion != null && !conexion.isClosed()){
                conexion.close();
                System.out.print("->La conexion con SQL fue Cerrada");
        }
        }catch(SQLException e){
            System.err.println("Error al cerrar la conexion" + e.getMessage());
        }
    }
}