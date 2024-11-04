package com.uade.BBDD2.model.neo4j;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;


@Node("Guest")
@Data
public class GuestNode {

    @Id
    private String mongoId; // ID de MongoDB del huésped

}
