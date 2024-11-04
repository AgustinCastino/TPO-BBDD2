package com.uade.BBDD2.Controller;

import com.uade.BBDD2.model.mongodb.Hotel;
import com.uade.BBDD2.model.mongodb.Room;
import com.uade.BBDD2.model.neo4j.RoomNode;
import com.uade.BBDD2.repository.mongodb.RoomMongoRepository;
import com.uade.BBDD2.repository.neo4j.RoomNeoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomMongoRepository roomMongoRepo;
    private final RoomNeoRepository roomNeoRepo;

    @PostMapping
    public Room createHotel(@RequestBody Room room) {
        Room savedRoom = roomMongoRepo.save(room);
        RoomNode roomNode = new RoomNode();
        roomNode.setMongoId(savedRoom.getId());
        roomNeoRepo.save(roomNode);
        return savedRoom;
    }

    @GetMapping("/{id}")
    public Room getRoom(@PathVariable String id) {
        return roomMongoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    @PutMapping("/{id}")
    public Room updateRoom(@PathVariable String id, @RequestBody Room roomDetails) {
        Room room = roomMongoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        room.setDisponible(roomDetails.isDisponible());
        return roomMongoRepo.save(room);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable String id) {
        roomMongoRepo.deleteById(id);
        roomNeoRepo.deleteByMongoId(id);
    }

}
