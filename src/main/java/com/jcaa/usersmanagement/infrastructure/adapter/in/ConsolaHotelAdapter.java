package com.jcaa.usersmanagement.infrastructure.adapter.in;

import com.jcaa.usersmanagement.application.usecases.GestionConsultasUseCase;
import com.jcaa.usersmanagement.infrastructure.adapter.out.MySQLHotelAdapter;
import java.util.Scanner;

public class ConsolaHotelAdapter {
    public static void main(String[] args) {
        MySQLHotelAdapter adaptadorDatos = new MySQLHotelAdapter();
        GestionConsultasUseCase casosDeUso = new GestionConsultasUseCase(adaptadorDatos);

        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        while (opcion != 6) {
            System.out.println("\n=================================");
            System.out.println("   SISTEMA HOTELERO SAFARI'S     ");
            System.out.println("=================================");
            System.out.println("1. Listar todos los hoteles de la cadena");
            System.out.println("2. Consultar habitaciones disponibles (Hotel H-001)");
            System.out.println("3. Ver reservas activas del cliente C-123");
            System.out.println("4. Mostrar historial de reservas del cliente C-123");
            System.out.println("5. Consultar estancias activas (Hotel H-001)");
            System.out.println("6. Salir del Sistema");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            System.out.println("");

            switch (opcion) {
                case 1 -> casosDeUso.ejecutarConsulta1();
                case 2 -> casosDeUso.ejecutarConsulta5("H-001");
                case 3 -> casosDeUso.ejecutarConsulta7("C-123");
                case 4 -> casosDeUso.ejecutarConsulta8("C-123");
                case 5 -> casosDeUso.ejecutarConsulta12("H-001");
                case 6 -> System.out.println("Cerrando sesión en Safari'S...");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        }
        scanner.close();
    }
}