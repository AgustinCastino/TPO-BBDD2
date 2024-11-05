package com.uade.BBDD2.model.neo4j;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import lombok.Data;


@Node("Amenity")
@Data
public class AmenityNode {

    @Id
    private String mongoId;

}