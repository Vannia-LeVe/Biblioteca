/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.biblioteca;

import java.util.List;

/**
 *
 * @author @rocta
 */
public interface ILibroDAO {
    boolean agregar(Libro libro);
    List<Libro> consultarTodos();
    boolean actualizar(Libro libro);
    boolean eliminar(int id);
    List<Libro> buscarPorCriterio(String criterio);
}