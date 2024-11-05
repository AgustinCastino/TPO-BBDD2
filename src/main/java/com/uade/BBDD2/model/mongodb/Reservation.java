package com.uade.BBDD2.model.mongodb;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "reservas")
@Data
@Getter
public class Reservation {

    @Id
    private String id;

    private String codigoConfirmacion;
    private String fechaReserva;
    private String fechaEntrada;
    private String fechaSalida;
    private String guestId;
    private String hotelId;

}
