package com.uade.BBDD2.repository.neo4j;


import com.uade.BBDD2.model.neo4j.HotelNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelNeoRepository extends Neo4jRepository<HotelNode, String> {
    @Query("MATCH (h:Hotel)-[r:NEAR_TO]->(p:POI) " +
            "WHERE p.mongoId = $poiId " +
            "RETURN h, r, p")
    List<HotelNode> findHotelsNearPOI(String poiId);

    @Query("MATCH (n)" +
            "WHERE n.mongoId = $mongoID " +
            "DELETE n")
    void deleteByMongoId(String mongoID);




//    @Query("MATCH (h:Hotel {mongoId: $hotelId})-[r:NEAR_TO]->(p:POI) " +
//            "RETURN p")
//    List<POINode> findNearbyPOIs(String hotelId);
}

//package com.hotel.repository.neo4j;
//
//import com.hotel.model.neo4j.HotelNode;
//import org.springframework.data.neo4j.repository.Neo4jRepository;
//import org.springframework.data.neo4j.repository.query.Query;
//import java.util.List;
//
//public interface HotelNeoRepository extends Neo4jRepository<HotelNode, String> {
//    HotelNode findByMongoId(String mongoId);
//
//    @Query("MATCH (hotel:Hotel)-[:NEAR]->(poi:POI {id: $poiId}) RETURN hotel")
//    List<HotelNode> findHotelsNearPOI(String poiId);
//
//    @Query("MATCH (hotel:Hotel {mongoId: $hotelId})-[:HAS_POI]->(poi:POI) RETURN poi")
//    List<POINode> findPOIsForHotel(String hotelId);
//}