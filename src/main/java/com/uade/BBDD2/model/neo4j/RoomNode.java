package com.uade.BBDD2.model.neo4j;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;
import lombok.Data;

import java.util.List;

@Node("Room")  // Especifica que este nodo es del tipo "Room"
@Data
public class RoomNode {

    @Id
    private String mongoId; // ID de MongoDB de la habitación

    private String roomNumber;
    private String type;

    @Relationship(type = "IN_HOTEL", direction = Relationship.Direction.INCOMING)
    private HotelNode hotel; // Relación con el hotel al que pertenece

    //@Relationship(type = "HAS_AMENITY", direction = Relationship.Direction.OUTGOING)
    //private List<AmenityNode> amenities; // Relación con amenities de la habitación
}