package com.uade.BBDD2.model.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "habitaciones")
@Data
public class Room {

    @Id
    private String id; // ID de MongoDB

    private String hotelId; // ID de MongoDB del hotel al que pertenece
    private String roomNumber;
    private String tipo; // Tipo de habitación (por ejemplo, estándar, suite, etc.)
}
