package com.uade.BBDD2.model.neo4j;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("Hotel")
@Data
public class HotelNode {

    @Id
    private String mongoId; // ID de MongoDB del hotel

    private String name;
    private String location;

//    @Relationship(type = "NEAR", direction = Relationship.Direction.OUTGOING)
//    private List<POINode> pointsOfInterest; // Puntos de interés cercanos al hotel
//
//    @Relationship(type = "HAS_ROOM", direction = Relationship.Direction.OUTGOING)
//    private List<RoomNode> rooms; // Habitaciones asociadas al hotel
}
