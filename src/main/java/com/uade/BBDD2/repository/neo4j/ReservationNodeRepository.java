package com.uade.BBDD2.repository.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.uade.BBDD2.model.neo4j.ReservationNode;

public interface ReservationNodeRepository extends Neo4jRepository<ReservationNode, String>{
    
}
