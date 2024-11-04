package com.uade.BBDD2.model.neo4j;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import lombok.Data;

import java.util.List;

@Node("POI")  // Especifica que este nodo es del tipo "POI"
@Data
public class POINode {

    @Id
    private String mongoId; // ID de MongoDB del punto de interés

    private String name;
    private String location;

    //@Relationship(type = "NEAR", direction = Relationship.Direction.INCOMING)
    //private List<HotelNode> hotels; // Relación con hoteles cercanos al punto de interés
}