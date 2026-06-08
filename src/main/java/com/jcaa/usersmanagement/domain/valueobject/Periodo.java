package com.jcaa.usersmanagement.domain.valueobject;

import java.time.LocalDate;

public record Periodo(LocalDate fechaInicio, LocalDate fechaFin) {

    public Periodo {
        if (fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }
        if (fechaInicio.isAfter(fechaFin)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }
    }

    public boolean estaDentroDe(Periodo otroPeriodo) {
        return !this.fechaInicio.isBefore(otroPeriodo.fechaInicio) &&
                !this.fechaFin.isAfter(otroPeriodo.fechaFin);
    }
}