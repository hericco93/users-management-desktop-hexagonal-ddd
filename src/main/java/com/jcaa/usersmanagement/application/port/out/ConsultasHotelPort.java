package com.jcaa.usersmanagement.application.port.out;

import java.util.List;

public interface ConsultasHotelPort {
    List<String> listarHoteles();
    List<String> listarHabitacionesDisponibles(String hotelId);
    List<String> obtenerReservasActivasPorCliente(String clienteId);
    List<String> obtenerHistorialReservasCliente(String clienteId);
    List<String> consultarEstanciasActivas(String hotelId);
    boolean existeCliente(String clienteId);
}