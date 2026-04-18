/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.biblioteca;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import java.util.List;

/**
 *
 * @author @rocta
 */
public class LibroDAO implements ILibroDAO {
    
    private EntityManager em;

    public LibroDAO(EntityManager em) {
        this.em = em;
    }

    @Override
    public void insertar(Libro libro) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.persist(libro);
        tx.commit();
    }

    @Override
    public List<Libro> obtenerTodos() {
        return em.createQuery("SELECT l FROM Libro l", Libro.class).getResultList();
    }

    @Override
    public Libro buscarPorId(int id) {
        return em.find(Libro.class, id);
    }

    @Override
    public void actualizar(Libro libro) {
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        em.merge(libro);
        tx.commit();
    }

    @Override
    public void eliminar(int id) {
        Libro libro = buscarPorId(id);
        if (libro != null) {
            EntityTransaction tx = em.getTransaction();
            tx.begin();
            em.remove(libro);
            tx.commit();
        }
    }
}