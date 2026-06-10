package com.jcaa.usersmanagement.infrastructure.adapter.in;

import com.jcaa.usersmanagement.application.usecases.GestionConsultasUseCase;
import com.jcaa.usersmanagement.infrastructure.adapter.out.MySQLHotelAdapter;
import java.util.Scanner;

public class ConsolaHotelAdapter {
    public static void main(String[] args) {
        MySQLHotelAdapter adaptadorDatos = new MySQLHotelAdapter();
        GestionConsultasUseCase casosDeUso = new GestionConsultasUseCase(adaptadorDatos);

        Scanner scanner = new Scanner(System.in);
        String opcion = "";

        while (!opcion.equals("6")) {
            System.out.println("\n=================================");
            System.out.println("   SISTEMA HOTELERO SAFARI'S     ");
            System.out.println("=================================");
            System.out.println("1. Listar todos los hoteles de la cadena");
            System.out.println("2. Consultar habitaciones disponibles por Hotel");
            System.out.println("3. Ver reservas activas de un Cliente");
            System.out.println("4. Mostrar historial de reservas de un Cliente");
            System.out.println("5. Consultar estancias activas por Hotel");
            System.out.println("6. Salir del Sistema");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextLine().trim();
            System.out.println("");

            try {
                switch (opcion) {
                    case "1" -> {
                        System.out.println("=== LISTADO DE HOTELES ===");
                        casosDeUso.consultaHoteles();
                    }

                    case "2" -> {
                        System.out.print("Ingrese el ID del hotel (ej: H-001): ");
                        String hotelId = scanner.nextLine().trim().toUpperCase();
                        if (!hotelId.isEmpty()) {
                            System.out.println("\n=== HABITACIONES DISPONIBLES ===");
                            casosDeUso.consultaHabitaciones(hotelId);
                        } else {
                            System.out.println("El ID del hotel no puede estar vacío.");
                        }
                    }

                    case "3" -> {
                        System.out.print("Ingrese el ID del cliente (ej: C-123): ");
                        String clienteId = scanner.nextLine().trim().toUpperCase();
                        if (!clienteId.isEmpty()) {
                            System.out.println("\n=== RESERVAS ACTIVAS ===");
                            casosDeUso.consultaReservasActivas(clienteId);
                        } else {
                            System.out.println("El ID del cliente no puede estar vacío.");
                        }
                    }

                    case "4" -> {
                        System.out.print("Ingrese el ID del cliente (ej: C-123): ");
                        String clienteId = scanner.nextLine().trim().toUpperCase();
                        if (!clienteId.isEmpty()) {
                            System.out.println("\n=== HISTORIAL DE RESERVAS ===");
                            casosDeUso.consultaHistorialReservas(clienteId);
                        } else {
                            System.out.println("El ID del cliente no puede estar vacío.");
                        }
                    }

                    case "5" -> {
                        System.out.print("Ingrese el ID del hotel (ej: H-001): ");
                        String hotelId = scanner.nextLine().trim().toUpperCase();
                        if (!hotelId.isEmpty()) {
                            System.out.println("\n=== ESTANCIAS ACTIVAS ===");
                            casosDeUso.consultaEstanciasActivas(hotelId);
                        } else {
                            System.out.println("El ID del hotel no puede estar vacío.");
                        }
                    }

                    case "6" -> System.out.println("Cerrando sesión en Safari'S... ¡Hasta luego!");

                    default -> System.out.println("Opción inválida. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
            }
        }
        scanner.close();
    }
}