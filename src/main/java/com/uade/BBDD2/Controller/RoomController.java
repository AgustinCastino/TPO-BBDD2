package com.uade.BBDD2.Controller;

import com.uade.BBDD2.Service.RoomService;
import com.uade.BBDD2.model.mongodb.Amenity;
import com.uade.BBDD2.model.mongodb.Room;
import com.uade.BBDD2.model.neo4j.AmenityNode;
import com.uade.BBDD2.model.neo4j.RoomNode;
import com.uade.BBDD2.repository.mongodb.AmenityMongoRepository;
import com.uade.BBDD2.repository.mongodb.RoomMongoRepository;
import com.uade.BBDD2.repository.neo4j.RoomNeoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomMongoRepository roomMongoRepo;
    private final RoomNeoRepository roomNeoRepo;
    private final RoomService roomService;
    private final AmenityMongoRepository amenityMongoRepo;


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
    public Room updateRoom(@PathVariable String id) {
        Room room = roomMongoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        return roomMongoRepo.save(room);
    }

    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable String id) {
        roomMongoRepo.deleteById(id);
        roomNeoRepo.deleteByMongoId(id);
    }

    @GetMapping("/{roomId}/amenities")
    public ResponseEntity<List<Optional<Amenity>>> getAmenitiesByRoomId(@PathVariable String roomId) {
        List<AmenityNode> amenities = roomService.getAmenitiesByRoomId(roomId);
        System.out.println(amenities);
        return amenities.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok( roomService.printAmenity(amenities));
    }

    @PutMapping("/rel/amenity/{rID}/{aID}")
    public void relRoomAmenity(@PathVariable String rID, @PathVariable String aID ) {
        Room room =  roomMongoRepo.findById(rID)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        Amenity amenity =  amenityMongoRepo.findById(aID)
                .orElseThrow(() -> new RuntimeException("POI not found"));
        roomNeoRepo.relRoomAmenity(room.getId(), amenity.getId());
    }

}
