package com.jcaa.usersmanagement.domain.factory;

import com.jcaa.usersmanagement.domain.exception.ReglaNegocioException;
import com.jcaa.usersmanagement.domain.model.Reserva;
import com.jcaa.usersmanagement.domain.valueobject.Dinero;
import com.jcaa.usersmanagement.domain.valueobject.Periodo;

import java.util.UUID;

public class ReservaFactory {

    public static Reserva crearReservaConSenal(String clienteId, String hotelId, Periodo periodo, Dinero senal) {
        // Regla de Negocio: La reserva exige el pago de una señal
        if (senal.monto() <= 0) {
            throw new ReglaNegocioException("La reserva debe incluir el pago de una señal mayor a cero.");
        }

        // Si todo está bien, creamos la reserva generando un ID único automáticamente
        String idGenerado = UUID.randomUUID().toString();
        return new Reserva(idGenerado, clienteId, hotelId, periodo, senal);
    }
}
