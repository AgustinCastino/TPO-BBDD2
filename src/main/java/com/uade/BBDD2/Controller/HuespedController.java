package com.uade.BBDD2.Controller;

import com.uade.BBDD2.model.mongodb.Guest;
import com.uade.BBDD2.model.neo4j.GuestNode;
import com.uade.BBDD2.repository.mongodb.GuestMongoRepository;
import com.uade.BBDD2.repository.neo4j.GuestNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("huesped")
@RequiredArgsConstructor
public class HuespedController {

    private final GuestMongoRepository guestMongoRepo;
    private final GuestNodeRepository guestNodeRepo;



    @PostMapping
    public Guest createHuesped(@RequestBody Guest huesped) {
        Guest savedGuest = guestMongoRepo.save(huesped);
        GuestNode guestNode = new GuestNode();
        guestNode.setMongoId(savedGuest.getId());
        guestNodeRepo.save(guestNode);
        return savedGuest;
    }

    @GetMapping("/{id}")
    public Guest getHuesped(@PathVariable String id) {
        return guestMongoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Huesped not found"));
    }
    //
    @PutMapping("/{id}")
    public Guest updateHuesped(@PathVariable String id, @RequestBody Guest hotelDetails) {
        Guest hotel = guestMongoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Huesped not found"));
        hotel.setNombre(hotelDetails.getNombre());
        hotel.setDireccion(hotelDetails.getDireccion());
        // Actualiza otros atributos según sea necesario
        return guestMongoRepo.save(hotel);
    }
    //
    @DeleteMapping("/{id}")
    public void deleteHuesped(@PathVariable String id) {
        guestMongoRepo.deleteById(id);
        guestNodeRepo.deleteByMongoId(id);
    }
}
