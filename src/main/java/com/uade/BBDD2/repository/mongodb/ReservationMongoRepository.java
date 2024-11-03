package com.uade.BBDD2.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.uade.BBDD2.model.mongodb.Reservation;

@Repository
public interface ReservationMongoRepository extends MongoRepository<Reservation, String>{

    
}
