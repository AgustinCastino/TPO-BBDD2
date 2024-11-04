package com.uade.BBDD2.repository.mongodb;


import com.uade.BBDD2.model.mongodb.Guest;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface GuestMongoRepository extends MongoRepository<Guest, String> {


}