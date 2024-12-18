package com.uade.BBDD2.Controller;

import com.uade.BBDD2.Service.ReservaService;
import com.uade.BBDD2.model.DTO.ReservaDTO;
import com.uade.BBDD2.model.mongodb.Hotel;
import com.uade.BBDD2.model.neo4j.GuestNode;
import com.uade.BBDD2.repository.mongodb.GuestMongoRepository;
import com.uade.BBDD2.repository.mongodb.HotelMongoRepository;
import com.uade.BBDD2.repository.mongodb.RoomMongoRepository;
import com.uade.BBDD2.repository.neo4j.GuestNodeRepository;
import com.uade.BBDD2.repository.neo4j.HotelNeoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;


import com.uade.BBDD2.model.mongodb.Guest;
import com.uade.BBDD2.model.mongodb.Reservation;
import com.uade.BBDD2.model.neo4j.ReservationNode;
import com.uade.BBDD2.repository.mongodb.ReservationMongoRepository;
import com.uade.BBDD2.repository.neo4j.ReservationNodeRepository;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("reserva")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservationMongoRepository reservationMongoRepo;
    private final ReservationNodeRepository reservationNodeRepo;
    private final ReservaService reservaService;
    private final HotelMongoRepository hotelMongoRepo;
    private final GuestMongoRepository guestMongoRepo;
    private final GuestNodeRepository guestNodeRepo;
    private final RoomMongoRepository roomMongoRepo;

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {
        Reservation savedReservation = reservationMongoRepo.save(reservation);
        ReservationNode rNode = new ReservationNode();
        rNode.setMongoId(savedReservation.getId());
        reservationNodeRepo.save(rNode);
        return savedReservation;
    }

    @GetMapping("/{id}")
    public Reservation getReservation(@PathVariable String id) {
        return reservationMongoRepo.findById(id).orElseThrow(() -> new RuntimeException("Reservation not found"));
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable String id, @RequestBody Reservation reservationDetails) {
        Reservation reservation = reservationMongoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        if (reservationDetails.getHotelId() != null) {
            reservation.setHotelId(reservationDetails.getHotelId());
        }
        if (reservationDetails.getFechaSalida() != null){
            reservation.setHotelId(reservationDetails.getFechaSalida());
        }
        if (reservationDetails.getFechaReserva() != null){
            reservation.setHotelId(reservationDetails.getFechaReserva());
        }
        if (reservationDetails.getFechaEntrada() != null) {
            reservation.setHotelId(reservationDetails.getFechaEntrada());
        }
        if (reservationDetails.getGuestId() != null) {
            reservation.setHotelId(reservationDetails.getGuestId());
        }
        return reservationMongoRepo.save(reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable String id) {
        reservationMongoRepo.deleteById(id);
        reservationNodeRepo.deleteByMongoId(id);
    }
    @PostMapping("/crear")
    public Reservation crearReservation(@RequestBody ReservaDTO reservation) {
        LocalDate checkIn = LocalDate.parse(reservation.getFechaEntrada());
        LocalDate checkOut = LocalDate.parse(reservation.getFechaSalida());
        Hotel hotel2 = hotelMongoRepo.findByNombreContaining(reservation.getNombreHotel());

        boolean available = reservaService.isRoomAvailable(reservation.getNroRoom(), checkIn, checkOut,hotel2.getId());

        if (available) {
            Reservation reserva = reservaService.reserva(reservation);
            Guest guest = reservaService.guest(reservation);
            Guest savedGuest = guestMongoRepo.save(guest);
            GuestNode guestNode = new GuestNode();
            guestNode.setMongoId(savedGuest.getId());
            guestNodeRepo.save(guestNode);
            Hotel hotel = hotelMongoRepo.findByNombreContaining(reservation.getNombreHotel());
            reserva.setHotelId(hotel.getId());
            reserva.setGuestId(guest.getId());
            Reservation savedReservation = reservationMongoRepo.save(reserva);
            ReservationNode rNode = new ReservationNode();
            rNode.setMongoId(savedReservation.getId());
            reservationNodeRepo.save(rNode);
            reservationNodeRepo.relateReservationToRoomAndGuest(savedGuest.getId(),savedReservation.getId(),roomMongoRepo.findByHotelIdAndRoomNumber(hotel2.getId(),reservation.getNroRoom()).getId());
            return savedReservation;
        }

        return null;
    }

    @GetMapping("/verDisponibilidad")
    public ResponseEntity<String> checkRoomAvailability(
            @RequestParam String roomNum,
            @RequestParam String hotelNom,
            @RequestParam String checkInDate,
            @RequestParam String checkOutDate) {

        Hotel hotel = hotelMongoRepo.findByNombreContaining(hotelNom);
        LocalDate checkIn = LocalDate.parse(checkInDate);
        LocalDate checkOut = LocalDate.parse(checkOutDate);

        boolean available = reservaService.isRoomAvailable(roomNum, checkIn, checkOut, hotel.getId());

        if (available) {
            return ResponseEntity.ok("La habitación está disponible.");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("La habitación no está disponible.");
        }
    }
    @GetMapping("/por_hotel_fecha")
    public ResponseEntity<List<Reservation>> getReservationsByHotelAndDate(
            @RequestParam String hotelNom,
            @RequestParam String fechaReserva) {

        Hotel hotel = hotelMongoRepo.findByNombreContaining(hotelNom);

        List<Reservation> reservations = reservaService.getReservationsByHotelAndDate(hotel.getId(), fechaReserva);
        return reservations.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(reservations);
    }

    @GetMapping("/por_huesped")
    public ResponseEntity<List<Reservation>> getReservationsByGuestId(@RequestParam String email) {

        Guest guest = guestMongoRepo.findByEmail(email);
        List<Reservation> reservations = reservaService.getReservationsByGuestId(guest.getId());
        return reservations.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(reservations);
    }

    @GetMapping("/por_codReserva")
    public ResponseEntity<Reservation> getReservationByConfirmationCode(@RequestParam String codigoConfirmacion) {
        return reservaService.getReservationByConfirmationCode(codigoConfirmacion)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
