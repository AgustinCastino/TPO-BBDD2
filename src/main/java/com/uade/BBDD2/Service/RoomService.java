package com.uade.BBDD2.Service;

import com.uade.BBDD2.model.mongodb.Amenity;
import com.uade.BBDD2.model.neo4j.AmenityNode;
import com.uade.BBDD2.repository.mongodb.AmenityMongoRepository;

import com.uade.BBDD2.repository.neo4j.RoomNeoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class RoomService {

    private final RoomNeoRepository RoomNeoRepo;
    private final AmenityMongoRepository AmenityMongoRepo;


    public List<Optional<Amenity>> printAmenity (List<AmenityNode> amenities ) {

        List<Optional<Amenity>> amenityMDB = new ArrayList<>();

        for (AmenityNode amenity : amenities) {
            amenityMDB.add(AmenityMongoRepo.findById(amenity.getMongoId())) ;
        }

        return(amenityMDB);
    }

    public List<AmenityNode> getAmenitiesByRoomId(String roomId) {
        return RoomNeoRepo.findAmenitiesByRoomId(roomId);
    }

}
