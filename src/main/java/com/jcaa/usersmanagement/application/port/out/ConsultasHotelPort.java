package com.jcaa.usersmanagement.application.port.out;

import com.jcaa.usersmanagement.domain.model.Reserva;
import com.mysql.cj.x.protobuf.MysqlxDatatypes;

import java.util.List;

public interface ConsultasHotelPort {
    List<String> listarHoteles();
    List<String> listarHabitacionesDisponibles(String hotelId);
    List<String> obtenerReservasActivasPorCliente(String clienteId);
    List<String> obtenerHistorialReservasCliente(String clienteId);
    List<String> consultarEstanciasActivas(String hotelId);
}