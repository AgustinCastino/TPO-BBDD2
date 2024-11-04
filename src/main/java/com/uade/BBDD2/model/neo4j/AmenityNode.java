package com.uade.BBDD2.model.neo4j;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import lombok.Data;


@Node("Amenity")  // Especifica que este nodo es del tipo "Amenity"
@Data
public class AmenityNode {

    @Id
    private String mongoId; // ID de MongoDB del amenity

}