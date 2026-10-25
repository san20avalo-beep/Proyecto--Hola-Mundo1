package com.programadornovato.holamundo_1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author 50496
 */
public class HolaMundo_1 {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        try (Connection cn = ConexionDB.obtenerConexion()) {
            if (cn == null) {
                System.out.println("No se pudo establecer la conexion.");
                return;
            }

            int op;
            do {
                System.out.println("\n=== Operaciones en tabla Alumnos ===");
                System.out.println("1. Insertar nuevo alumno");
                System.out.println("2. Lista de alumnos");
                System.out.println("3. Actualizar alumno");
                System.out.println("4. Eliminar alumno");
                System.out.println("0. Salir");
                System.out.print("Seleccione una opcion: ");

                op = in.nextInt();
                in.nextLine(); // Limpiar el buffer

                switch (op) {
                    case 1: {
                        System.out.print("Nombre del alumno: ");
                        String nombre = in.nextLine();
                        System.out.print("Correo del alumno: ");
                        String correo = in.nextLine();

                        String sqlInsert = "INSERT INTO Alumnos(nombre, correo) VALUES (?, ?)";
                        try (PreparedStatement ps = cn.prepareStatement(sqlInsert)) {
                            ps.setString(1, nombre);
                            ps.setString(2, correo);
                            ps.execute();
                            System.out.println("¡Alumno insertado con éxito!");
                        }
                        break;
                    }
                    case 2: {
                        String sqlSelect = "SELECT id, nombre, correo FROM Alumnos";
                        try (PreparedStatement ps = cn.prepareStatement(sqlSelect);
                             ResultSet rs = ps.executeQuery()) {
                            System.out.println("---------------------------------------------");
                            System.out.printf("%-5s | %-20s | %s%n", "ID", "Nombre", "Correo");
                            System.out.println("---------------------------------------------");

                            while (rs.next()) {
                                System.out.printf("%-5d | %-20s | %s%n",
                                        rs.getInt("id"),
                                        rs.getString("nombre"),
                                        rs.getString("correo"));
                            }
                            System.out.println("---------------------------------------------");
                        }
                        break;
                    }
                    case 3: {
                        System.out.print("ID del alumno a actualizar: ");
                        int id = in.nextInt();
                        in.nextLine();

                        System.out.print("Nuevo nombre: ");
                        String nombre = in.nextLine();

                        System.out.print("Nuevo correo: ");
                        String correo = in.nextLine();

                        String sqlUpdate = "UPDATE Alumnos SET nombre = ?, correo = ? WHERE id = ?";
                        try (PreparedStatement ps = cn.prepareStatement(sqlUpdate)) {
                            ps.setString(1, nombre);
                            ps.setString(2, correo);
                            ps.setInt(3, id);
                            ps.execute();
                            System.out.println("¡Alumno actualizado con éxito!");
                        }
                        break;
                    }
                    case 4: {
                        System.out.print("ID del alumno que desea eliminar: ");
                        int id = in.nextInt();

                        String sqlDelete = "DELETE FROM Alumnos WHERE id = ?";
                        try (PreparedStatement ps = cn.prepareStatement(sqlDelete)) {
                            ps.setInt(1, id);
                            ps.execute();
                            System.out.println("¡Alumno eliminado con éxito!");
                        }
                        break;
                    }
                    case 0:
                        System.out.println("Saliendo del programa.");
                        break;
                    default:
                        System.out.println("Opcion no valida.");
                        break;
                }
            } while (op != 0);

        } catch (SQLException e) {
            System.err.println("Error de base de datos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            in.close();
            System.out.println("Programa finalizado.");
        }
    }
}