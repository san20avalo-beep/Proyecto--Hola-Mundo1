package com.programadornovato.holamundo_1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 *
 * @author 50496
 */
public class HolaMundo_1 {

    public static void main(String[] args) throws SQLException {
        System.out.println("==== Ejecutando operaciones en tabla alumnos ====\n");
        Connection cn = ConexionDB.getConexion(); // Aqui obtenemos la conexion
        if (cn == null)
        {
            System.out.println("No se puede establecer la conexion");
            return;
        }
        
        Scanner scanner = new Scanner(System.in);

        // Declaramos los objetos de SQL para nuestra base
        PreparedStatement psInsertar = null;
        PreparedStatement psSeleccionar = null;
        PreparedStatement psActualizar = null;
        PreparedStatement psEliminar = null;
        ResultSet rs = null;

        try {
            // Parte A. Creamos (Insercion)
            System.out.println("1. Intentando insertar nuevo alumno...");
            String sqlInsert = "INSERT INTO Alumnos(nombre, correo) VALUES(?, ?)";
            psInsertar = cn.prepareStatement(sqlInsert);
            psInsertar.setString(1, "Jairo Eduardo");
            psInsertar.setString(2, "jairo@uth.hn");

            int filasAfectadas = psInsertar.executeUpdate();
            if (filasAfectadas > 0)
            {
                System.out.println(" --> Alumno insertado exitosamente\n");
            }

            // Parte B. Seleccionar y visualizar datos en pantalla
            System.out.println("2. Consulta de lista de Alumnos en la base de datos:");
            String sqlSelect = "SELECT id, nombre, correo FROM Alumnos";
            psSeleccionar = cn.prepareStatement(sqlSelect);
            rs = psSeleccionar.executeQuery();
            mostrarListaAlumnos(cn);

            // Parte C. Actualizar correo pidiendo los datos por consola
            System.out.println("3. Actualizando registro...");
            System.out.print("Ingresa el ID del alumno que deseas actualizar: ");
            String idActualizarStr = scanner.nextLine();
            
            if (idActualizarStr != null && !idActualizarStr.isEmpty()) {
                int idActualizar = Integer.parseInt(idActualizarStr);
                
                System.out.print("Ingresa el nuevo correo para este alumno: ");
                String nuevoCorreo = scanner.nextLine();
                
                String sqlUpdate = "UPDATE Alumnos SET correo = ? WHERE id = ?";
                psActualizar = cn.prepareStatement(sqlUpdate);
                psActualizar.setString(1, nuevoCorreo);
                psActualizar.setInt(2, idActualizar);

                int filasActualizadas = psActualizar.executeUpdate();
                if (filasActualizadas > 0)
                {
                    System.out.println(" --> Alumno con ID " + idActualizar + " actualizado exitosamente\n");

                    // Nuevamente muestro la lista
                    System.out.println("--- Lista actualizada después del UPDATE ---");
                    mostrarListaAlumnos(cn);
                } else {
                    System.out.println(" --> No se encontró ningún alumno con el ID especificado.\n");
                }
            }

            // Parte D. Borrar pidiendo el ID por consola
            System.out.println("4. Eliminando registro...");
            System.out.print("Ingresa el ID del alumno que deseas eliminar: ");
            String idEliminarStr = scanner.nextLine();
            
            if (idEliminarStr != null && !idEliminarStr.isEmpty()) {
                int idEliminar = Integer.parseInt(idEliminarStr);

                String sqlDelete = "DELETE FROM Alumnos WHERE id = ?";
                psEliminar = cn.prepareStatement(sqlDelete);
                psEliminar.setInt(1, idEliminar);

                int filasEliminadas = psEliminar.executeUpdate();
                if (filasEliminadas > 0)
                {
                    System.out.println(" --> Alumno con ID " + idEliminar + " eliminado exitosamente del sistema\n");

                    // Se muestra la lista luego de eliminarse
                    System.out.println("---> Lista actualizada después del DELETE <---");
                    mostrarListaAlumnos(cn);

                } else
                {
                    System.out.println(" ---> No se encontró ningún alumno con ese ID para eliminar <---\n");
                }
            }

        } catch (SQLException e)
        {
            System.err.println(" --> Ocurrio un error con la base de datos: " + e.getMessage());
        } finally {
            // Cerrar recursos
            scanner.close();
            if (psInsertar != null){
                psInsertar.close();
            }
            if (psActualizar != null){
                psActualizar.close();
            }
            if (psEliminar != null){
                psEliminar.close();
            }
            ConexionDB.cerrarConexion();
        }
    }

    public static void mostrarListaAlumnos(Connection cn)
    {
        String sqlSelect = "SELECT id, nombre, correo FROM Alumnos";
        try (PreparedStatement psSeleccionar = cn.prepareStatement(sqlSelect); ResultSet rs = psSeleccionar.executeQuery()) {
            System.out.println("==================================================");
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String correo = rs.getString("correo");
                System.out.printf("%-5d | %-20s | %s\n", id, nombre, correo);
            }
            System.out.println("==================================================\n");
        } catch (SQLException e)
        {
            System.err.println(" ---> Error al consultar la lista: " + e.getMessage());
        }
    }
}