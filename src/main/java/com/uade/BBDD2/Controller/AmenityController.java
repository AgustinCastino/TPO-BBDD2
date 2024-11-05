package com.uade.BBDD2.Controller;

import com.uade.BBDD2.model.mongodb.Amenity;
import com.uade.BBDD2.model.mongodb.Hotel;
import com.uade.BBDD2.model.neo4j.AmenityNode;
import com.uade.BBDD2.repository.mongodb.AmenityMongoRepository;
import com.uade.BBDD2.repository.neo4j.AmenityNeoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("amenity")
@RequiredArgsConstructor
public class AmenityController {
    private final AmenityMongoRepository amenityMongoRepo;
    private final AmenityNeoRepository amenityNeoRepo;

    @PostMapping
    public Amenity createAmenity(@RequestBody Amenity amenity) {
        Amenity savedAmenity = amenityMongoRepo.save(amenity);
        AmenityNode hotelNode = new AmenityNode();
        hotelNode.setMongoId(savedAmenity.getId());
        amenityNeoRepo.save(hotelNode);
        return savedAmenity;
    }

    @GetMapping("/{id}")
    public Amenity getAmenity(@PathVariable String id) {
        return amenityMongoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Amenity not found"));
    }

    @PutMapping("/{id}")
    public Amenity updateAmenity(@PathVariable String id, @RequestBody Amenity amenityDetails) {
        Amenity amenity = amenityMongoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        if(amenityDetails.getNombre() != null){
            amenity.setNombre(amenityDetails.getNombre());
        }
        if(amenityDetails.getDescripcion() != null){
            amenity.setDescripcion(amenityDetails.getDescripcion());
        }
        // Actualiza otros atributos según sea necesario
        return amenityMongoRepo.save(amenity);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable String id) {
        amenityMongoRepo.deleteById(id);
        amenityNeoRepo.deleteByMongoId(id);
    }
}
