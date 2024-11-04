package com.uade.BBDD2.repository.mongodb;

import com.uade.BBDD2.model.mongodb.POI;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface POIMongoRepository extends MongoRepository<POI, String> {
}
