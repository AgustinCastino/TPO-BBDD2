package com.uade.BBDD2.model.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reservas")
@Data
public class Reservation {

    @Id
    private String id; // ID de MongoDB

    private String codigoConfirmacion;
    private String fechaEntrada;
    private String fechaSalida;

    private String guestId; // ID de MongoDB del huésped que hizo la reserva
    private String roomId; // ID de MongoDB de la habitación reservada

    private String hotelId; // ID de MongoDB del hotel asociado
}
