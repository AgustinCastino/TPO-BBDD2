package com.uade.BBDD2.repository.neo4j;

import com.uade.BBDD2.model.neo4j.AmenityNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface AmenityNeoRepository extends Neo4jRepository<AmenityNode, String> {
    @Query("MATCH (n)" +
            "WHERE n.mongoId = $mongoID " +
            "DELETE n")
    void deleteByMongoId(String mongoID);
}
