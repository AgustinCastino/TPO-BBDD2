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

    @GetMapping("/get")
    public Hotel getHotel(@RequestParam String hotelNom) {
        Hotel hotel = hotelMongoRepo.findByNombreContaining(hotelNom);

        return hotel;
    }
    @PutMapping("/{id}")
    public Hotel updateHotel(@PathVariable String id, @RequestBody Hotel hotelDetails) {
        Hotel hotel = hotelMongoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        if(hotelDetails.getDireccion() != null){
            hotel.setDireccion(hotelDetails.getDireccion());
        }
        if(hotelDetails.getNombre() != null){
            hotel.setNombre(hotelDetails.getNombre());
        }
        if (hotelDetails.getTelefonos() != null){
            hotel.setTelefonos(hotelDetails.getTelefonos());
        }
        if(hotelDetails.getEmail() != null){
            hotel.setEmail(hotelDetails.getEmail());
        }
        if(hotelDetails.getUbicacion() != null){
            hotel.setUbicacion(hotelDetails.getUbicacion());
        }
        return hotelMongoRepo.save(hotel);
    }
    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable String id) {
        hotelMongoRepo.deleteById(id);
        hotelNeoRepo.deleteByMongoId(id);
    }
    @PutMapping("/rel/poi")
    public void relHotelPOI(@RequestParam String hID, @RequestParam String pID ) {
        Hotel hotel =  hotelMongoRepo.findByNombreContaining(hID);
        POI poi =  POIMongoRepo.findByNombre(pID);
        hotelNeoRepo.relPOI(hotel.getId(),poi.getId());
    }
 // Punto 5 Encontrar puntos de interes en base a el hotel
    @GetMapping("/pois")
    public  List<Optional<POI>> getPOI(@RequestParam String hID ) {
        Hotel hotel =  hotelMongoRepo.findByNombreContaining(hID);
        return hotelService.printHotels(hotelNeoRepo.findPOINearHotel(hotel.getId()));
    }

    @PutMapping("/rel/room")
    public void relHotelRoom(@RequestParam String hID, @RequestParam String rID ) {
        Hotel hotel =  hotelMongoRepo.findByNombreContaining(hID);
        Room room =  roomMongoRepo.findByRoomNumber(rID);
        hotelNeoRepo.relRoom(hotel.getId(),room.getId());
    }
}
