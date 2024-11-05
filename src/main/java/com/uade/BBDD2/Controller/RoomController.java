package com.uade.BBDD2.Controller;

import com.uade.BBDD2.Service.RoomService;
import com.uade.BBDD2.model.mongodb.Amenity;
import com.uade.BBDD2.model.mongodb.Hotel;
import com.uade.BBDD2.model.mongodb.Room;
import com.uade.BBDD2.model.neo4j.AmenityNode;
import com.uade.BBDD2.model.neo4j.RoomNode;
import com.uade.BBDD2.repository.mongodb.AmenityMongoRepository;
import com.uade.BBDD2.repository.mongodb.HotelMongoRepository;
import com.uade.BBDD2.repository.mongodb.RoomMongoRepository;
import com.uade.BBDD2.repository.neo4j.HotelNeoRepository;
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
    private final HotelMongoRepository hotelMongoRepository;
    private final HotelNeoRepository hotelNeoRepo;


    @PostMapping
    public Room createRoom(@RequestBody Room room) {
        Room savedRoom = roomMongoRepo.save(room);
        RoomNode roomNode = new RoomNode();
        roomNode.setMongoId(savedRoom.getId());
        roomNeoRepo.save(roomNode);
        hotelNeoRepo.relRoom(room.getHotelId(),room.getId());
        return savedRoom;
    }

    @GetMapping("/get")
    public Room getRoom(@RequestParam String roomNum, @RequestParam String hotelName) {
        Hotel hotel = hotelMongoRepository.findByNombreContaining(hotelName);
        return roomMongoRepo.findByHotelIdAndRoomNumber(hotel.getId(),roomNum);
    }

    @PutMapping("/{id}")
    public Room updateRoom(@PathVariable String id, @RequestBody Room roomDetails) {
        Room room = roomMongoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        if(roomDetails.getRoomNumber() != null){
            room.setRoomNumber(roomDetails.getRoomNumber());
        }
        if(roomDetails.getHotelId() != null){
            room.setHotelId(roomDetails.getHotelId());
        }
        if(roomDetails.getTipo() != null){
            room.setTipo(roomDetails.getTipo());
        }
        return roomMongoRepo.save(room);
    }


    @DeleteMapping("/{id}")
    public void deleteRoom(@PathVariable String id) {
        roomMongoRepo.deleteById(id);
        roomNeoRepo.deleteByMongoId(id);
    }

    @GetMapping("/amenities")
    public ResponseEntity<List<Optional<Amenity>>> getAmenitiesByRoomId(@RequestParam String roomNum, @RequestParam String hotelName) {
        Hotel hotel = hotelMongoRepository.findByNombreContaining(hotelName);
        Room room = roomMongoRepo.findByHotelIdAndRoomNumber(hotel.getId(),roomNum);
        List<AmenityNode> amenities = roomService.getAmenitiesByRoomId(room.getId());
        System.out.println(amenities);
        return amenities.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok( roomService.printAmenity(amenities));
    }

    @PutMapping("/rel/amenity")
    public void relRoomAmenity(@RequestParam String rID, @RequestParam String hotelName,@RequestParam String aID ) {
        Hotel hotel = hotelMongoRepository.findByNombreContaining(hotelName);
        Room room = roomMongoRepo.findByHotelIdAndRoomNumber(hotel.getId(),rID);
        Amenity amenity =  amenityMongoRepo.findByNombre(aID);
        roomNeoRepo.relRoomAmenity(room.getId(), amenity.getId());
    }

}
