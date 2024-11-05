package com.uade.BBDD2.model.neo4j;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import lombok.Data;


@Node("POI")
@Data
public class POINode {

    @Id
    private String mongoId;

}