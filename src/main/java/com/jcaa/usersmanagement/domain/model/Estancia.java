package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.valueobject.Periodo;

public class Estancia {
    private final String id;
    private final Reserva reservaAsociada;
    private Periodo periodoEstancia;

    public Estancia(String id, Reserva reservaAsociada, Periodo periodoEstancia) {
        this.id = id;
        this.reservaAsociada = reservaAsociada;
        this.periodoEstancia = periodoEstancia;
    }

    public String getId() { return id; }
    public Periodo getPeriodoEstancia() { return periodoEstancia; }
    public Reserva getReservaAsociada() { return reservaAsociada; }
}