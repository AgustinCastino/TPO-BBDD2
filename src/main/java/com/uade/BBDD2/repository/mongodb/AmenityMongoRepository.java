package com.uade.BBDD2.repository.mongodb;

import com.uade.BBDD2.model.mongodb.Amenity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AmenityMongoRepository extends MongoRepository<Amenity, String> {
    Amenity findByNombre(String aID);
}
