package com.uade.BBDD2.Controller;

import com.uade.BBDD2.model.mongodb.Hotel;
import com.uade.BBDD2.model.neo4j.HotelNode;
import com.uade.BBDD2.repository.mongodb.HotelMongoRepository;
import com.uade.BBDD2.repository.neo4j.HotelNeoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelMongoRepository hotelMongoRepo;
    private final HotelNeoRepository hotelNeoRepo;

    @PostMapping
    public Hotel createHotel(@RequestBody Hotel hotel) {
        Hotel savedHotel = hotelMongoRepo.save(hotel);
        HotelNode hotelNode = new HotelNode();
        hotelNode.setMongoId(savedHotel.getId());
        hotelNeoRepo.save(hotelNode);
        return savedHotel;
    }

    @GetMapping("/{id}")
    public Hotel getHotel(@PathVariable String id) {
        return hotelMongoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
    }
    @PutMapping("/{id}")
    public Hotel updateHotel(@PathVariable String id, @RequestBody Hotel hotelDetails) {
        Hotel hotel = hotelMongoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        hotel.setNombre(hotelDetails.getNombre());
        hotel.setDireccion(hotelDetails.getDireccion());
        // Actualiza otros atributos según sea necesario
        return hotelMongoRepo.save(hotel);
    }
    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable String id) {
        hotelMongoRepo.deleteById(id);
        hotelNeoRepo.deleteByMongoId(id);
    }
}
