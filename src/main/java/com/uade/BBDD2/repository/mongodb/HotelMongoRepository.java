package com.uade.BBDD2.repository.mongodb;

import com.uade.BBDD2.model.mongodb.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface HotelMongoRepository extends MongoRepository<Hotel, String> {
    Hotel findByNombreContaining(String nombre);
    Hotel findByNombreContainingIgnoreCase(String nombre);
    Hotel findByNombre(String nombre);


}
