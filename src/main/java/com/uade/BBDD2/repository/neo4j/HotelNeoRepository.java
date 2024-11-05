package com.uade.BBDD2.repository.neo4j;


import com.uade.BBDD2.model.neo4j.HotelNode;
import com.uade.BBDD2.model.neo4j.POINode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelNeoRepository extends Neo4jRepository<HotelNode, String> {
    @Query("MATCH (h:Hotel)-[r:Cerca_De]->(p:POI) " +
            "WHERE h.mongoId = $hotelID " +
            "RETURN p {.mongoId}")
    List<POINode> findPOINearHotel(String hotelID);

    @Query("MATCH (n)" +
            "WHERE n.mongoId = $mongoID " +
            "DELETE n")
    void deleteByMongoId(String mongoID);

    @Query("MATCH (c1:Hotel {mongoId: $mongoID}),(p1:POI {mongoId: $mongoID2})" +
            "CREATE (c1)-[:Cerca_De]->(p1)")
    void relPOI(String mongoID,String mongoID2);

    @Query("MATCH (c1:Hotel {mongoId: $mongoID}),(p1:Room {mongoId: $mongoID2})" +
            "CREATE (c1)<-[:Room_de]-(p1)")
    void relRoom(String mongoID,String mongoID2);



}