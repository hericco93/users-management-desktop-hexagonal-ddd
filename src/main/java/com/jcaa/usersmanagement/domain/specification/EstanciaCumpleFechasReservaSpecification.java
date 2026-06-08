package com.jcaa.usersmanagement.domain.specification;

import com.jcaa.usersmanagement.domain.model.Estancia;

public class EstanciaCumpleFechasReservaSpecification {

    // Este método evalúa si la estancia cumple con la regla de negocio
    public boolean esSatisfechaPor(Estancia estancia) {
        // Validamos que el periodo de la estancia esté DENTRO del periodo de su reserva
        return estancia.getPeriodoEstancia().estaDentroDe(estancia.getReservaAsociada().getPeriodo());
    }
}