package com.jcaa.usersmanagement.application.usecases;

import com.jcaa.usersmanagement.infrastructure.adapter.out.MySQLHotelAdapter;
import java.util.List;

public class GestionConsultasUseCase {
    private final MySQLHotelAdapter adaptadorDatos;

    public GestionConsultasUseCase(MySQLHotelAdapter adaptadorDatos) {
        this.adaptadorDatos = adaptadorDatos;
    }

    // Método auxiliar para verificar si un hotel existe usando la lista actual
    private boolean existeHotelEnBD(String hotelId) {
        List<String> hoteles = adaptadorDatos.listarHoteles();
        if (hoteles != null) {
            for (String hotel : hoteles) {
                String[] datos = hotel.split("\\|");
                if (datos.length > 0 && datos[0].equalsIgnoreCase(hotelId)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void consultaHoteles() {
        List<String> hoteles = adaptadorDatos.listarHoteles();
        if (hoteles == null || hoteles.isEmpty()) {
            System.out.println("No se encontraron hoteles registrados.");
            return;
        }
        System.out.println("=========================================");
        for (String hotel : hoteles) {
            String[] datos = hotel.split("\\|");
            if (datos.length >= 6) {
                System.out.println("ID: " + datos[0]);
                System.out.println("Nombre: " + datos[1]);
                System.out.println("Categoría: " + datos[2] + " estrellas");
                System.out.println("Dirección: " + datos[3]);
                System.out.println("Teléfono: " + datos[4]);
                System.out.println("Director: " + datos[5]);
                System.out.println("-----------------------------------------");
            } else {
                System.out.println(" -> " + hotel);
            }
        }
        System.out.println("=========================================");
    }

    public void consultaHabitaciones(String hotelId) {
        // Validación: Verificar si el hotel existe en el sistema
        if (!existeHotelEnBD(hotelId)) {
            System.out.println("ERROR: El ID del hotel '" + hotelId + "' no existe en el sistema.");
            return;
        }

        List<String> habitaciones = adaptadorDatos.listarHabitacionesDisponibles(hotelId);
        if (habitaciones == null || habitaciones.isEmpty()) {
            System.out.println("El hotel existe, pero no tiene habitaciones disponibles actualmente.");
            return;
        }

        System.out.println("=========================================");
        for (String hab : habitaciones) {
            String[] datos = hab.split("\\|");
            if (datos.length >= 4) {
                System.out.println("ID Habitación: " + datos[0]);
                System.out.println("Número: " + datos[1]);
                System.out.println("Tipo: " + datos[2]);
                System.out.println("Precio/Noche: $" + datos[3]);
                System.out.println("-----------------------------------------");
            } else {
                System.out.println(" -> " + hab);
            }
        }
        System.out.println("=========================================");
    }

    public void consultaReservasActivas(String clienteId) {
        if (!adaptadorDatos.existeCliente(clienteId)) {
            System.out.println("ERROR: El ID del cliente '" + clienteId + "' no existe en el sistema.");
            return;
        }

        List<String> reservas = adaptadorDatos.obtenerReservasActivasPorCliente(clienteId);
        if (reservas == null || reservas.isEmpty()) {
            System.out.println("El cliente existe, pero no tiene reservas ACTIVAS en este momento.");
            return;
        }

        System.out.println("=========================================");
        for (String res : reservas) {
            String[] datos = res.split("\\|");
            if (datos.length >= 4) {
                System.out.println("ID Reserva: " + datos[0]);
                System.out.println("ID Habitación: " + datos[1]);
                System.out.println("Fecha Inicio: " + datos[2]);
                System.out.println("Fecha Fin: " + datos[3]);
                System.out.println("-----------------------------------------");
            } else {
                System.out.println(" -> " + res);
            }
        }
        System.out.println("=========================================");
    }

    public void consultaHistorialReservas(String clienteId) {
        if (!adaptadorDatos.existeCliente(clienteId)) {
            System.out.println("ERROR: El ID del cliente '" + clienteId + "' no existe en el sistema.");
            return;
        }

        List<String> historial = adaptadorDatos.obtenerHistorialReservasCliente(clienteId);
        if (historial == null || historial.isEmpty()) {
            System.out.println("El cliente existe, pero no registra historial de reservas.");
            return;
        }

        System.out.println("=========================================");
        for (String hist : historial) {
            String[] datos = hist.split("\\|");
            if (datos.length >= 4) {
                System.out.println("ID Reserva: " + datos[0]);
                System.out.println("Estado: " + datos[1]);
                System.out.println("Fecha Inicio: " + datos[2]);
                System.out.println("Fecha Fin: " + datos[3]);
                System.out.println("-----------------------------------------");
            } else {
                System.out.println(" -> " + hist);
            }
        }
        System.out.println("=========================================");
    }

    public void consultaEstanciasActivas(String hotelId) {
        // Validación: Verificar si el hotel existe en el sistema
        if (!existeHotelEnBD(hotelId)) {
            System.out.println("ERROR: El ID del hotel '" + hotelId + "' no existe en el sistema.");
            return;
        }

        List<String> estancias = adaptadorDatos.consultarEstanciasActivas(hotelId);
        if (estancias == null || estancias.isEmpty()) {
            System.out.println("El hotel existe, pero no registra estancias activas (huéspedes actuales).");
            return;
        }

        System.out.println("=========================================");
        for (String est : estancias) {
            String[] datos = est.split("\\|");
            if (datos.length >= 3) {
                System.out.println("ID Estancia: " + datos[0]);
                System.out.println("Número Habitación: " + datos[1]);
                System.out.println("ID Cliente: " + datos[2]);
                System.out.println("-----------------------------------------");
            } else {
                System.out.println(" -> " + est);
            }
        }
        System.out.println("=========================================");
    }
}