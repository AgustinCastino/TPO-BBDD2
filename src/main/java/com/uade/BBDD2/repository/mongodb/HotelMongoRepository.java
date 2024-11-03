package com.uade.BBDD2.repository.mongodb;

import com.uade.BBDD2.model.mongodb.Hotel;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HotelMongoRepository extends MongoRepository<Hotel, String> {
    List<Hotel> findByNombreContaining(String nombre);
    // Otros métodos de consulta personalizados si es necesario
}
