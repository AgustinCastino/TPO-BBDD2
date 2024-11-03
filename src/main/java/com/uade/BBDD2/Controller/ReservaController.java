package com.uade.BBDD2.Controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.BBDD2.model.mongodb.Guest;
import com.uade.BBDD2.model.mongodb.Reservation;
import com.uade.BBDD2.model.neo4j.ReservationNode;
import com.uade.BBDD2.repository.mongodb.ReservationMongoRepository;
import com.uade.BBDD2.repository.neo4j.ReservationNodeRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("reserva")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservationMongoRepository reservationMongoRepo;
    private final ReservationNodeRepository reservationNodeRepo;

    @PostMapping
    public Reservation createReservation(@RequestBody Reservation reservation) {

        Reservation savedReservation = reservationMongoRepo.save(reservation);
        ReservationNode rNode = new ReservationNode();
        rNode.setMongoId(reservation.getId());
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
        reservation.setFechaEntrada(reservationDetails.getFechaEntrada());
        reservation.setFechaSalida(reservationDetails.getFechaSalida());
        // Actualiza otros atributos según sea necesario
        return reservationMongoRepo.save(reservation);
    }

    @DeleteMapping("/{id}")
    public void deleteHotel(@PathVariable String id) {
        reservationMongoRepo.deleteById(id);
        reservationNodeRepo.deleteById(id);
    }
}
