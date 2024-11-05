package com.uade.BBDD2.Controller;

import com.uade.BBDD2.Service.POIService;
import com.uade.BBDD2.model.mongodb.Hotel;
import com.uade.BBDD2.model.mongodb.POI;
import com.uade.BBDD2.model.neo4j.POINode;
import com.uade.BBDD2.repository.mongodb.POIMongoRepository;
import com.uade.BBDD2.repository.neo4j.POINeoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("poi")
@RequiredArgsConstructor
public class POIController {

    private final POIMongoRepository POIMongoRepo;
    private final POINeoRepository POINeoRepo;
    private final POIService POIservice;

    @PostMapping
    public POI createPOI(@RequestBody POI poi) {
        POI savedPOI = POIMongoRepo.save(poi);
        POINode poiNode = new POINode();
        poiNode.setMongoId(savedPOI.getId());
        POINeoRepo.save(poiNode);
        return savedPOI;
    }

    @GetMapping("/{id}")
    public POI getPOI(@PathVariable String id) {
        return POIMongoRepo.findByNombre(id);
    }

    @PutMapping("/{id}")
    public POI updateHotel(@PathVariable String id, @RequestBody POI poiDetails) {
        POI poi = POIMongoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("POI not found"));
        if(poiDetails.getNombre()!=null) {
            poi.setNombre(poiDetails.getNombre());
        }
        if(poiDetails.getUbicacion()!=null) {
            poi.setUbicacion(poiDetails.getUbicacion());
        }
        if(poiDetails.getDescripcion()!=null) {
            poi.setDescripcion(poiDetails.getDescripcion());
        }
        return POIMongoRepo.save(poi);
    }

    @DeleteMapping("/{id}")
    public void deletePOI(@PathVariable String id) {
        POIMongoRepo.deleteById(id);
        POINeoRepo.deleteByMongoId(id);
    }
    @GetMapping("/hoteles")
    public List<Optional<Hotel>> getHoteles(@RequestParam String pID ) {
        POI POI =  POIMongoRepo.findByNombre(pID);
        return POIservice.printHotels(POINeoRepo.findHotelNearPOI(POI.getId()));
    }

}
