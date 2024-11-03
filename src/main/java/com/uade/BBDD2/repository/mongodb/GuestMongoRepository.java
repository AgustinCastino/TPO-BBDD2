package com.uade.BBDD2.repository.mongodb;


import com.uade.BBDD2.model.mongodb.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface GuestMongoRepository extends MongoRepository<Guest, String> {
    Optional<Guest> findByEmailsContaining(String email);

    List<Guest> findByLastNameContainingIgnoreCase(String lastName);

    @Query("{ $or: [ { 'emails': ?0 }, { 'phones': ?0 } ] }")
    Optional<Guest> findByEmailOrPhone(String contactInfo);

    @Query("{ 'address.country': ?0 }")
    List<Guest> findByCountry(String country);

    @Query(value = "{ 'lastName': { $regex: ?0, $options: 'i' } }")
    Page<Guest> searchByLastName(String lastName, Pageable pageable);

    @Query("{ 'address.postalCode': ?0 }")
    List<Guest> findByPostalCode(String postalCode);
}