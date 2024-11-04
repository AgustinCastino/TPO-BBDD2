package com.uade.BBDD2.Service;

import com.uade.BBDD2.model.mongodb.Hotel;
import com.uade.BBDD2.model.neo4j.HotelNode;
import com.uade.BBDD2.repository.mongodb.HotelMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class AmenityService {
    private final HotelMongoRepository hotelMongoRepository;


    public List<Optional<Hotel>> printHotels (List<HotelNode> hoteles ) {

        List<Optional<Hotel>> hotelesMDB = new ArrayList<>();

        for (HotelNode hotel : hoteles) {
            hotelesMDB.add(hotelMongoRepository.findById(hotel.getMongoId())) ;
        }

        return(hotelesMDB);
    }
}
