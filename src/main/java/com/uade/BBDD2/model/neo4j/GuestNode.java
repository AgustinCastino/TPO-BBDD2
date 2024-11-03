package com.uade.BBDD2.model.neo4j;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node("Guest")
@Data
public class GuestNode {

    @Id
    private String mongoId; // ID de MongoDB del huésped

    private String name;
    private String email;

//    @Relationship(type = "HAS_RESERVATION", direction = Relationship.Direction.OUTGOING)
//    private List<ReservationNode> reservations; // Reservas asociadas al huésped
}
