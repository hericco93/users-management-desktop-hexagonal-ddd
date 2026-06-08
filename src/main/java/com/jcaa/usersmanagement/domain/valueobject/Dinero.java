package com.jcaa.usersmanagement.domain.valueobject;

public record Dinero(double monto) {
    public Dinero {
        if (monto < 0) {
            throw new IllegalArgumentException("El dinero no puede ser un valor negativo");
        }
    }
}
