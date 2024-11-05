package com.uade.BBDD2.model.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "habitaciones")
@Data
public class Room {

    @Id
    private String id;

    private String hotelId;
    private String roomNumber;
    private String tipo;
}
