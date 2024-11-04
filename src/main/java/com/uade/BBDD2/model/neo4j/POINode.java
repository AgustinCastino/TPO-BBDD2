package com.uade.BBDD2.model.neo4j;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import lombok.Data;


@Node("POI")  // Especifica que este nodo es del tipo "POI"
@Data
public class POINode {

    @Id
    private String mongoId; // ID de MongoDB del punto de interés

}