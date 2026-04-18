/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.biblioteca.main;

import mx.itson.biblioteca.dao.ILibroDAO;
import mx.itson.biblioteca.dao.LibroDAO;
import mx.itson.biblioteca.modelo.Libro;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author @rocta
 */
public class Main {
    public static void main(String[] args) {
        // Inicialización de JPA
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("BibliotecaPU");
        EntityManager em = emf.createEntityManager();
        
        ILibroDAO libroDAO = new LibroDAO(em);
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        System.out.println("¡Bienvenido al Sistema de Gestión de la Biblioteca!");
        System.out.println("Hecho por: Vannia Jaretzy León Velázquez y Raúl Octavio Alvarado Castro");

        while (opcion != 6) {
            System.out.println("\n================ MENÚ PRINCIPAL ================");
            System.out.println("1. Agregar un nuevo libro");
            System.out.println("2. Mostrar todos los libros");
            System.out.println("3. Actualizar un libro existente");
            System.out.println("4. Eliminar un libro");
            System.out.println("5. Buscar libro por ID");
            System.out.println("6. Salir del sistema");
            System.out.print("Elige una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        System.out.println("\n--- AGREGAR LIBRO ---");
                        System.out.print("Título: ");
                        String titulo = scanner.nextLine();
                        System.out.print("Autor: ");
                        String autor = scanner.nextLine();
                        System.out.print("Año de publicación: ");
                        int anio = Integer.parseInt(scanner.nextLine());
                        System.out.print("Género: ");
                        String genero = scanner.nextLine();

                        Libro nuevoLibro = new Libro(titulo, autor, anio, genero);
                        libroDAO.insertar(nuevoLibro);
                        System.out.println("✅ ¡Libro agregado con éxito!");
                        break;

                    case 2:
                        System.out.println("\n--- CATÁLOGO DE LIBROS ---");
                        List<Libro> todosLosLibros = libroDAO.obtenerTodos();
                        if (todosLosLibros.isEmpty()) {
                            System.out.println("La biblioteca está vacía.");
                        } else {
                            for (Libro l : todosLosLibros) {
                                System.out.println(l.toString());
                            }
                        }
                        break;

                    case 3:
                        System.out.println("\n--- ACTUALIZAR LIBRO ---");
                        System.out.print("Ingresa el ID del libro que deseas actualizar: ");
                        int idActualizar = Integer.parseInt(scanner.nextLine());
                        
                        Libro libroAActualizar = libroDAO.buscarPorId(idActualizar);
                        if(libroAActualizar != null){
                            System.out.print("Nuevo Título: ");
                            libroAActualizar.setTitulo(scanner.nextLine());
                            System.out.print("Nuevo Autor: ");
                            libroAActualizar.setAutor(scanner.nextLine());
                            System.out.print("Nuevo Año de publicación: ");
                            libroAActualizar.setAnioPublicacion(Integer.parseInt(scanner.nextLine()));
                            System.out.print("Nuevo Género: ");
                            libroAActualizar.setGenero(scanner.nextLine());

                            libroDAO.actualizar(libroAActualizar);
                            System.out.println("✅ ¡Libro actualizado correctamente!");
                        } else {
                            System.out.println("❌ Libro no encontrado.");
                        }
                        break;

                    case 4:
                        System.out.println("\n--- ELIMINAR LIBRO ---");
                        System.out.print("Ingresa el ID del libro que deseas eliminar: ");
                        int idEliminar = Integer.parseInt(scanner.nextLine());
                        
                        libroDAO.eliminar(idEliminar);
                        System.out.println("✅ Petición procesada.");
                        break;

                    case 5:
                        System.out.println("\n--- BUSCAR LIBRO POR ID ---");
                        System.out.print("Ingresa el ID a buscar: ");
                        int idBuscar = Integer.parseInt(scanner.nextLine());
                        
                        Libro encontrado = libroDAO.buscarPorId(idBuscar);
                        if (encontrado == null) {
                            System.out.println("No se encontró ningún libro con ese ID.");
                        } else {
                            System.out.println("Resultado: " + encontrado.toString());
                        }
                        break;

                    case 6:
                        System.out.println("Saliendo del sistema...");
                        em.close();
                        emf.close();
                        break;

                    default:
                        System.out.println("⚠️ Opción no válida.");
                        break;
                }
            } catch (Exception e) {
                System.out.println("⚠️ Ocurrió un error: " + e.getMessage());
            }
        }
        scanner.close();
    }
}