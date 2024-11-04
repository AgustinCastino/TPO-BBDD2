package com.uade.BBDD2.repository.mongodb;

import com.uade.BBDD2.model.mongodb.Amenity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AmenityMongoRepository extends MongoRepository<Amenity, String> {
}
