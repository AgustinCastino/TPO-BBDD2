package com.uade.BBDD2.repository.neo4j;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import com.uade.BBDD2.model.neo4j.ReservationNode;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface ReservationNodeRepository extends Neo4jRepository<ReservationNode, String>{

    @Query("MATCH (g:Guest {mongoId: $guestId}), (r:Reservation {mongoId: $reservationId}), (room:Room {mongoId: $roomId}) " +
            "CREATE (g)-[:Tiene_Reserva]->(r) " +
            "CREATE (r)-[:Para_Habitacion]->(room)")
    void relateReservationToRoomAndGuest(String guestId, String reservationId, String roomId);


    // Consulta para obtener todas las reservas relacionadas con una habitación
    @Query("MATCH (r:Reservation)-[:Para_Habitacion]->(room:Room {mongoId: $roomId}) RETURN r")
    List<ReservationNode> findReservationsByRoomId(String roomId);

    @Query("MATCH (n)" +
            "WHERE n.mongoId = $mongoID " +
            "DELETE n")
    void deleteByMongoId(String mongoID);
    
}
