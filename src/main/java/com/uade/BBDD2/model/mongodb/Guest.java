package com.uade.BBDD2.model.mongodb;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "huespedes")
@Data
@Getter
public class Guest {

    @Id
    private String id;

    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
}
