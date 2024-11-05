package com.uade.BBDD2.model.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "puntosDeInteres")
@Data
public class POI {

    @Id
    private String id;

    private String nombre;
    private String descripcion;
    private String ubicacion;
}
