package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.valueobject.Dinero;
import com.jcaa.usersmanagement.domain.valueobject.Periodo;

public class Reserva {
    private final String id;
    private final String clienteId;
    private final String hotelId;
    private Periodo periodo;
    private Dinero senalPagada;

    public Reserva(String id, String clienteId, String hotelId, Periodo periodo, Dinero senalPagada) {
        this.id = id;
        this.clienteId = clienteId;
        this.hotelId = hotelId;
        this.periodo = periodo;
        this.senalPagada = senalPagada;
    }

    public String getId() { return id; }
    public Periodo getPeriodo() { return periodo; }
    public Dinero getSenalPagada() { return senalPagada; }
}