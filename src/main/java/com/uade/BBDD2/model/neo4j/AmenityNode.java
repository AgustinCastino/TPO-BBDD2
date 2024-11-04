package com.uade.BBDD2.model.neo4j;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import lombok.Data;

import java.util.List;

@Node("Amenity")  // Especifica que este nodo es del tipo "Amenity"
@Data
public class AmenityNode {

    @Id
    private String mongoId; // ID de MongoDB del amenity

    private String name;
    private String description;

    //@Relationship(type = "AMENITY_FOR", direction = Relationship.Direction.INCOMING)
    //private List<RoomNode> rooms; // Relación con las habitaciones que ofrecen este amenity
}