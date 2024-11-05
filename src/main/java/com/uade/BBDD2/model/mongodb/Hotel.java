package com.uade.BBDD2.model.mongodb;

import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "hoteles")
@Data
@Getter
public class Hotel {

    @Id
    private String id;
    private String nombre;
    private String direccion;
    private String telefonos;
    private String email;
    private String ubicacion;

}
