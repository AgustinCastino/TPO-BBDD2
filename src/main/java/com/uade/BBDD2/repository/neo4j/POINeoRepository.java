package com.uade.BBDD2.repository.neo4j;

import com.uade.BBDD2.model.neo4j.HotelNode;
import com.uade.BBDD2.model.neo4j.POINode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface POINeoRepository extends Neo4jRepository<POINode, String> {
    @Query("MATCH (n)" +
            "WHERE n.mongoId = $mongoID " +
            "DELETE n")
    void deleteByMongoId(String mongoID);

    @Query("MATCH (h:Hotel)-[r:Cerca_De]->(p:POI) " +
            "WHERE p.mongoId = $poiID " +
            "RETURN h {.mongoId}")
    List<HotelNode> findHotelNearPOI(String poiID);



}
