/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.biblioteca;

import java.sql.Connection;
import java.sql.SQLException;


/**
 *
 * @author @rocta
 */
public interface IConexionBD {
    Connection crearConexion() throws SQLException;
}
