package com.uade.BBDD2.model.neo4j;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;


@Node("Hotel")
@Data
public class HotelNode {

    @Id
    private String mongoId;
}

