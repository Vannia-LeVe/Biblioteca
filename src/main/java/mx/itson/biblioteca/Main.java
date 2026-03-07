/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.itson.biblioteca;

import java.util.List;
import java.util.Scanner;

/**
 *
 * @author @rocta
 */
public class Main {
    public static void main(String[] args) {
        IConexionBD conexion = new ConexionBD();
        ILibroDAO libroDAO = new LibroDAO(conexion);
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        System.out.println("¡Bienvenido al Sistema de Gestión de la Biblioteca!");
        System.out.println("Hecho por: ");
        System.out.println("Vannia Jaretzy León Velázquez");
        System.out.println("Raúl Octavio Alvarado Castro");

        while (opcion != 6) {
            System.out.println("\n================ MENÚ PRINCIPAL ================");
            System.out.println("1. Agregar un nuevo libro");
            System.out.println("2. Consultar todos los libros");
            System.out.println("3. Actualizar un libro existente");
            System.out.println("4. Eliminar un libro");
            System.out.println("5. Buscar libros por título o autor");
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
                        System.out.print("¿Está disponible? (1 = Sí, 2 = No): ");
                        boolean disponible = scanner.nextLine().equals("1");

                        Libro nuevoLibro = new Libro(0, titulo, autor, anio, disponible);
                        if (libroDAO.agregar(nuevoLibro)) {
                            System.out.println("✅ ¡Libro agregado con éxito! ID asignado: " + nuevoLibro.getId());
                        } else {
                            System.out.println("❌ Ocurrió un error al agregar el libro.");
                        }
                        break;

                    case 2:
                        System.out.println("\n--- CATÁLOGO DE LIBROS ---");
                        List<Libro> todosLosLibros = libroDAO.consultarTodos();
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
                        List<Libro> librosParaActualizar = libroDAO.consultarTodos();
                        
                        // Validar si hay libros antes de pedir el ID
                        if (librosParaActualizar.isEmpty()) {
                            System.out.println("La biblioteca está vacía. No hay libros para actualizar.");
                            break; 
                        }
                        
                        System.out.println("Libros disponibles:");
                        for (Libro l : librosParaActualizar) {
                            System.out.println(l.toString());
                        }
                        
                        System.out.print("\nIngresa el ID del libro que deseas actualizar: ");
                        int idActualizar = Integer.parseInt(scanner.nextLine());
                        
                        System.out.print("Nuevo Título: ");
                        String nuevoTitulo = scanner.nextLine();
                        System.out.print("Nuevo Autor: ");
                        String nuevoAutor = scanner.nextLine();
                        System.out.print("Nuevo Año de publicación: ");
                        int nuevoAnio = Integer.parseInt(scanner.nextLine());
                        System.out.print("¿Está disponible? (1 = Sí, 2 = No): ");
                        boolean nuevaDisponibilidad = scanner.nextLine().equals("1");

                        Libro libroAActualizar = new Libro(idActualizar, nuevoTitulo, nuevoAutor, nuevoAnio, nuevaDisponibilidad);
                        if (libroDAO.actualizar(libroAActualizar)) {
                            System.out.println("✅ ¡Libro actualizado correctamente!");
                        } else {
                            System.out.println("❌ No se pudo actualizar. Verifica que el ID exista.");
                        }
                        break;

                    case 4:
                        System.out.println("\n--- ELIMINAR LIBRO ---");
                        List<Libro> librosParaEliminar = libroDAO.consultarTodos();
                        
                        // Validar si hay libros antes de pedir el ID
                        if (librosParaEliminar.isEmpty()) {
                            System.out.println("La biblioteca está vacía. No hay libros para eliminar.");
                            break;
                        }
                        
                        System.out.println("Libros disponibles:");
                        for (Libro l : librosParaEliminar) {
                            System.out.println(l.toString());
                        }
                        
                        System.out.print("\nIngresa el ID del libro que deseas eliminar: ");
                        int idEliminar = Integer.parseInt(scanner.nextLine());
                        
                        System.out.print("¿Estás seguro? (S/N): ");
                        String confirmacion = scanner.nextLine();
                        if (confirmacion.equalsIgnoreCase("S")) {
                            if (libroDAO.eliminar(idEliminar)) {
                                System.out.println("✅ ¡Libro eliminado del sistema!");
                            } else {
                                System.out.println("❌ No se pudo eliminar. Verifica que el ID exista.");
                            }
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                        break;

                    case 5:
                        System.out.println("\n--- BUSCAR LIBRO ---");
                        System.out.print("Ingresa el título o autor a buscar: ");
                        String criterio = scanner.nextLine();
                        
                        List<Libro> resultados = libroDAO.buscarPorCriterio(criterio);
                        if (resultados.isEmpty()) {
                            System.out.println("No se encontraron libros que coincidan con: '" + criterio + "'");
                        } else {
                            System.out.println("Resultados de la búsqueda:");
                            for (Libro l : resultados) {
                                System.out.println(l.toString());
                            }
                        }
                        break;

                    case 6:
                        System.out.println("Saliendo del sistema... ¡Hasta luego!");
                        break;

                    default:
                        System.out.println("⚠️ Opción no válida. Por favor, elige un número del 1 al 6.");
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Error: Debes ingresar un número válido.");
            } catch (Exception e) {
                System.out.println("⚠️ Ocurrió un error inesperado: " + e.getMessage());
            }
        }
        
        scanner.close();
    }
}