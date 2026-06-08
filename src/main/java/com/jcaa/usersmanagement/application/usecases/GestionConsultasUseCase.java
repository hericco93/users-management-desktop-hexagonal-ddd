package com.jcaa.usersmanagement.application.usecases;

import com.jcaa.usersmanagement.application.port.out.ConsultasHotelPort;
import java.util.List;

public class GestionConsultasUseCase {
    private final ConsultasHotelPort puertoSalida;

    public GestionConsultasUseCase(ConsultasHotelPort puertoSalida) {
        this.puertoSalida = puertoSalida;
    }

    public void ejecutarConsulta1() {
        System.out.println("--- Hoteles de la Cadena ---");
        puertoSalida.listarHoteles().forEach(System.out::println);
    }

    public void ejecutarConsulta5(String hotelId) {
        System.out.println("--- Habitaciones Disponibles en Hotel " + hotelId + " ---");
        puertoSalida.listarHabitacionesDisponibles(hotelId).forEach(System.out::println);
    }

    public void ejecutarConsulta7(String clienteId) {
        System.out.println("--- Reservas Activas del Cliente " + clienteId + " ---");
        List<String> reservas = puertoSalida.obtenerReservasActivasPorCliente(clienteId);
        if (reservas.isEmpty()) System.out.println("No hay reservas activas.");
        reservas.forEach(System.out::println);
    }

    public void ejecutarConsulta8(String clienteId) {
        System.out.println("--- Historial de Reservas del Cliente " + clienteId + " ---");
        List<String> reservas = puertoSalida.obtenerHistorialReservasCliente(clienteId);
        if (reservas.isEmpty()) System.out.println("No se encontró historial.");
        reservas.forEach(System.out::println);
    }

    public void ejecutarConsulta12(String hotelId) {
        System.out.println("--- Estancias Activas en el Hotel " + hotelId + " ---");
        puertoSalida.consultarEstanciasActivas(hotelId).forEach(System.out::println);
    }
}