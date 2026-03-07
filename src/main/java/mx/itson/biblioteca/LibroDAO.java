/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.biblioteca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author @rocta
 */
public class LibroDAO implements ILibroDAO {
    private final IConexionBD conexion;

    public LibroDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    @Override
    public boolean agregar(Libro libro) {
        String sql = "{CALL sp_insertar_libro(?, ?, ?, ?)}";
        try (Connection con = conexion.crearConexion();
             CallableStatement cs = con.prepareCall(sql)) {
            
            cs.setString(1, libro.getTitulo());
            cs.setString(2, libro.getAutor());
            cs.setInt(3, libro.getAnioPublicacion());
            cs.setBoolean(4, libro.isDisponible());

            ResultSet rs = cs.executeQuery();
            if (rs.next()) {
                libro.setId(rs.getInt("id"));
                return true;
            }
        } catch (SQLException ex) {
            System.err.println("Error al agregar libro: " + ex.getMessage());
        }
        return false;
    }

    @Override
    public List<Libro> consultarTodos() {
        List<Libro> lista = new ArrayList<>();
        String sql = "{CALL sp_consultar_libros()}";
        try (Connection con = conexion.crearConexion();
             CallableStatement cs = con.prepareCall(sql);
             ResultSet rs = cs.executeQuery()) {

            while (rs.next()) {
                Libro libro = new Libro();
                libro.setId(rs.getInt("id"));
                libro.setTitulo(rs.getString("titulo"));
                libro.setAutor(rs.getString("autor"));
                libro.setAnioPublicacion(rs.getInt("anio_publicacion"));
                libro.setDisponible(rs.getString("estado").equals("Disponible"));
                lista.add(libro);
            }
        } catch (SQLException ex) {
            System.err.println("Error al consultar: " + ex.getMessage());
        }
        return lista;
    }

    @Override
    public boolean actualizar(Libro libro) {
        String sql = "{CALL sp_actualizar_libro(?, ?, ?, ?, ?)}";
        try (Connection con = conexion.crearConexion();
             CallableStatement cs = con.prepareCall(sql)) {
            
            cs.setInt(1, libro.getId());
            cs.setString(2, libro.getTitulo());
            cs.setString(3, libro.getAutor());
            cs.setInt(4, libro.getAnioPublicacion());
            cs.setBoolean(5, libro.isDisponible());

            ResultSet rs = cs.executeQuery();
            return rs.next() && rs.getInt("filas_afectadas") > 0;
        } catch (SQLException ex) {
            System.err.println("Error al actualizar: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "{CALL sp_eliminar_libro(?)}";
        try (Connection con = conexion.crearConexion();
             CallableStatement cs = con.prepareCall(sql)) {
            
            cs.setInt(1, id);
            ResultSet rs = cs.executeQuery();
            return rs.next() && rs.getInt("filas_afectadas") > 0;
        } catch (SQLException ex) {
            System.err.println("Error al eliminar: " + ex.getMessage());
            return false;
        }
    }

    @Override
    public List<Libro> buscarPorCriterio(String criterio) {
        List<Libro> lista = new ArrayList<>();
        String sql = "{CALL sp_buscar_libros(?)}";
        try (Connection con = conexion.crearConexion();
             CallableStatement cs = con.prepareCall(sql)) {
            
            cs.setString(1, criterio);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Libro libro = new Libro(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getString("autor"),
                    rs.getInt("anio_publicacion"),
                    rs.getString("estado").equals("Disponible")
                );
                lista.add(libro);
            }
        } catch (SQLException ex) {
            System.err.println("Error al buscar: " + ex.getMessage());
        }
        return lista;
    }
}