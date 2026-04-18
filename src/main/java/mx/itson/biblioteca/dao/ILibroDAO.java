/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.biblioteca.dao;

import mx.itson.biblioteca.modelo.Libro;
import java.util.List;

/**
 *
 * @author @rocta
 */
public interface ILibroDAO {
    void insertar(Libro libro);
    List<Libro> obtenerTodos();
    Libro buscarPorId(int id);
    void actualizar(Libro libro);
    void eliminar(int id);
}