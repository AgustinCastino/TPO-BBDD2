package com.uade.BBDD2.model.neo4j;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import lombok.Data;


@Node("Room")  // Especifica que este nodo es del tipo "Room"
@Data
public class RoomNode {

    @Id
    private String mongoId; // ID de MongoDB de la habitación

}