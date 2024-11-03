package com.uade.BBDD2.model.neo4j;

import org.springframework.data.annotation.Id;
import org.springframework.data.neo4j.core.schema.Node;

import lombok.Data;

@Node("Reservation")
@Data
public class ReservationNode {
    
    @Id
    private String mongoId;

    private String codigoConfirmacion;
    private String fechaEntrada;
    private String fechaSalida;
}
