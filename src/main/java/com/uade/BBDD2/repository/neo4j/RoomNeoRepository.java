package com.uade.BBDD2.repository.neo4j;

import com.uade.BBDD2.model.neo4j.RoomNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

public interface RoomNeoRepository extends Neo4jRepository<RoomNode, String> {
    @Query("MATCH (n)" +
            "WHERE n.mongoId = $mongoID " +
            "DELETE n")
    void deleteByMongoId(String mongoID);
}
