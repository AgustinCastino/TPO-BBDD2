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

    @GetMapping("/{email}")
    public Guest getHuesped(@PathVariable String email) {
        return guestMongoRepo.findByEmail(email);
    }
    //
    @PutMapping("/{email}")
    public Guest updateHuesped(@PathVariable String email, @RequestBody Guest huespedDetails) {
        Guest guest = guestMongoRepo.findByEmail(email);
        if(huespedDetails.getDireccion() != null){
            guest.setDireccion(huespedDetails.getDireccion());
        }
        if(huespedDetails.getTelefono() != null){
            guest.setTelefono(huespedDetails.getTelefono());
        }
        if (huespedDetails.getNombre() != null){
            guest.setNombre(huespedDetails.getNombre());
        }
        if (huespedDetails.getApellido() != null){
            guest.setApellido(huespedDetails.getApellido());
        }
        if (huespedDetails.getEmail() != null){
            guest.setEmail(huespedDetails.getEmail());
        }

        return guestMongoRepo.save(guest);
    }
    //
    @DeleteMapping("/{id}")
    public void deleteHuesped(@PathVariable String id) {
        guestMongoRepo.deleteById(id);
        guestNodeRepo.deleteByMongoId(id);
    }
}
