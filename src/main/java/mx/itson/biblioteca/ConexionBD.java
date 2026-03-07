/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.biblioteca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author @rocta
 */
public class ConexionBD implements IConexionBD {
    
    private final String URL = "jdbc:mysql://localhost:3306/biblioteca_db";
    private final String USUARIO = "root";
    private final String PASSWORD = "admin";

    @Override
    public Connection crearConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}