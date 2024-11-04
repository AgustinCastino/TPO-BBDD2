package com.uade.BBDD2.repository.neo4j;

import com.uade.BBDD2.model.neo4j.AmenityNode;
import com.uade.BBDD2.model.neo4j.RoomNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface RoomNeoRepository extends Neo4jRepository<RoomNode, String> {
    @Query("MATCH (n)" +
            "WHERE n.mongoId = $mongoID " +
            "DELETE n")
    void deleteByMongoId(String mongoID);

    @Query("MATCH (room:Room {mongoId: $roomId})-[:Tiene]->(amenity:Amenity) RETURN amenity")
    List<AmenityNode> findAmenitiesByRoomId(String roomId);

    @Query("MATCH (c1:Room {mongoId: $mongoID}),(p1:Amenity {mongoId: $mongoID2})" +
            "CREATE (c1)-[:Tiene]->(p1)")
    void relRoomAmenity(String mongoID,String mongoID2);
}
