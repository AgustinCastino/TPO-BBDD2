package com.uade.BBDD2.Service;

import com.uade.BBDD2.model.mongodb.POI;
import com.uade.BBDD2.model.neo4j.POINode;
import com.uade.BBDD2.repository.mongodb.POIMongoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class HotelService {
    private final POIMongoRepository POIMongoRepo;


    public List<Optional<POI>> printHotels (List<POINode> pois ) {

        List<Optional<POI>> poiMDB = new ArrayList<>();

        for (POINode poi : pois) {
            poiMDB.add(POIMongoRepo.findById(poi.getMongoId())) ;
        }

        return(poiMDB);
    }
}
