package com.uade.BBDD2.repository.neo4j;

import com.uade.BBDD2.model.neo4j.GuestNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface GuestNodeRepository extends Neo4jRepository<GuestNode, String> {

    @Query("MATCH (n)" +
            "WHERE n.mongoId = $mongoID " +
            "DELETE n")
    void deleteByMongoId(String mongoID);
}
