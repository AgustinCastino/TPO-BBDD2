package com.uade.BBDD2.model.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "amenities")
@Data
public class Amenity {

    @Id
    private String id;

    private String nombre;
    private String descripcion;
}
