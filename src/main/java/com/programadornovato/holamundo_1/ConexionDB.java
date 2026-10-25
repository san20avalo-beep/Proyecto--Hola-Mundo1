package com.programadornovato.holamundo_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author 50496
 */
public class ConexionDB {

    // Intentamos leer la variable de entorno, y si es nula, usamos tu token por defecto
    private static final String AUTH_TOKEN = System.getenv("TURSO_AUTH_TOKEN") != null 
            ? System.getenv("TURSO_AUTH_TOKEN") 
            : "eyJhbGciOiJFZERTQSIsInR5cCI6IkpXVCJ9.eyJhIjoicnciLCJpYXQiOjE3OTI5MDQ5OTQsImlkIjoiMDFhMTcxNTctNjkwMS03YmI1LWI4NzUtZGRkNzFhYTA4NWRlIiwia2lkIjoiZzdyQzk3dUp1YTVGV1V1eVhVay0taVZ1TTVFV2V2bl9DNzN3M0pBcnRCUSIsInJpZCI6IjdjY2Q1NGYwLTJmN2ItNDllYi1hZjEyLThmMzMwMWZmNDNkYSJ9.2bm91gHL5qZ2HIiEs8uhaQugWp5C9T_xMiFTWRUOmXzbvFCjBFMqJMkrsRky1p98pkFCf0Y_UDMXSpm3_5WeAg";

    private static final String URL =
        "jdbc:dbeaver:libsql:https://avanzadaii-san20avalo-beep.aws-us-west-2.turso.io";
    
    public static Connection obtenerConexion() {
        try {
            Class.forName(
                "com.dbeaver.jdbc.driver.libsql.LibSqlDriver"
            );

            Connection cn = DriverManager.getConnection(
                URL, null, AUTH_TOKEN
            );

            System.out.println("Conexion exitosa con Turso.");
            return cn;

        } catch (ClassNotFoundException e) {
            System.err.println("No se encontro el driver JDBC.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Error al conectar con Turso.");
            e.printStackTrace();
        }

        return null;
    }
}