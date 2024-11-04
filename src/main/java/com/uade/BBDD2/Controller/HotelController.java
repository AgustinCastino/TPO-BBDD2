package com.uade.BBDD2.Controller;

import com.uade.BBDD2.Service.HotelService;
import com.uade.BBDD2.model.mongodb.Hotel;
import com.uade.BBDD2.model.mongodb.POI;
import com.uade.BBDD2.model.mongodb.Room;
import com.uade.BBDD2.model.neo4j.HotelNode;
import com.uade.BBDD2.repository.mongodb.HotelMongoRepository;
import com.uade.BBDD2.repository.mongodb.POIMongoRepository;
import com.uade.BBDD2.repository.mongodb.RoomMongoRepository;
import com.uade.BBDD2.repository.neo4j.HotelNeoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelMongoRepository hotelMongoRepo;
    private final HotelNeoRepository hotelNeoRepo;
    private final POIMongoRepository POIMongoRepo;
    private final HotelService hotelService;
    private final RoomMongoRepository roomMongoRepo;

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
    @PutMapping("/rel/poi{hID}/{pID}")
    public void relHotelPOI(@PathVariable String hID, @PathVariable String pID ) {
        Hotel hotel =  hotelMongoRepo.findById(hID)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        POI poi =  POIMongoRepo.findById(pID)
                .orElseThrow(() -> new RuntimeException("POI not found"));
        hotelNeoRepo.relPOI(hotel.getId(),poi.getId());
    }
 // Punto 5 Encontrar puntos de interes en base a el hotel
    @GetMapping("/pois/{hID}")
    public  List<Optional<POI>> getPOI(@PathVariable String hID ) {
        Hotel hotel =  hotelMongoRepo.findById(hID)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        return hotelService.printHotels(hotelNeoRepo.findPOINearHotel(hotel.getId()));
    }

    @PutMapping("/rel/room/{hID}/{rID}")
    public void relHotelRoom(@PathVariable String hID, @PathVariable String rID ) {
        Hotel hotel =  hotelMongoRepo.findById(hID)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        Room room =  roomMongoRepo.findById(rID)
                .orElseThrow(() -> new RuntimeException("POI not found"));
        hotelNeoRepo.relRoom(hotel.getId(),room.getId());
    }
}
