package com.uade.BBDD2.Service;

import com.uade.BBDD2.model.DTO.ReservaDTO;
import com.uade.BBDD2.model.mongodb.Guest;
import com.uade.BBDD2.model.mongodb.Hotel;
import com.uade.BBDD2.model.mongodb.Reservation;
import com.uade.BBDD2.model.mongodb.Room;
import com.uade.BBDD2.model.neo4j.HotelNode;
import com.uade.BBDD2.model.neo4j.ReservationNode;
import com.uade.BBDD2.repository.mongodb.HotelMongoRepository;
import com.uade.BBDD2.repository.mongodb.ReservationMongoRepository;
import com.uade.BBDD2.repository.mongodb.RoomMongoRepository;
import com.uade.BBDD2.repository.neo4j.ReservationNodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor

public class ReservaService {
    private final HotelMongoRepository hotelMongoRepository;
    private final ReservationNodeRepository reservationNodeRepository;
    private final ReservationMongoRepository reservationMongoRepository;
    private final RoomMongoRepository roomMongoRepository;


    public List<Optional<Hotel>> printHotels (List<HotelNode> hoteles ) {

        List<Optional<Hotel>> hotelesMDB = new ArrayList<>();

        for (HotelNode hotel : hoteles) {
            hotelesMDB.add(hotelMongoRepository.findById(hotel.getMongoId())) ;
        }

        return(hotelesMDB);
    }
    public Reservation reserva(ReservaDTO Rdto){
        Reservation reservation = new Reservation();
        reservation.setFechaSalida(Rdto.getFechaSalida());
        reservation.setFechaEntrada(Rdto.getFechaEntrada());
        reservation.setCodigoConfirmacion(generateConfirmationCode());
        reservation.setFechaReserva(String.valueOf(LocalDateTime.now()));

        return reservation;
    }
    public Guest guest(ReservaDTO Rdto){
        Guest guest = new Guest();
        guest.setDireccion(Rdto.getDireccion());
        guest.setNombre(Rdto.getNombre());
        guest.setApellido(Rdto.getApellido());
        guest.setEmail(Rdto.getEmail());
        guest.setTelefono(Rdto.getTelefono());
        return guest;

    }

    private String generateConfirmationCode() {
        return "RES-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
  }
    public boolean isRoomAvailable(String roomNum, LocalDate checkInDate, LocalDate checkOutDate) {
        // 1. Obtener todas las reservas relacionadas con la habitación en Neo4j
        Room room = roomMongoRepository.findByRoomNumber(roomNum);
        List<ReservationNode> reservations = reservationNodeRepository.findReservationsByRoomId(room.getId());

        // 2. Verificar en MongoDB si alguna reserva tiene conflicto de fechas
        for (ReservationNode reservationNode : reservations) {
            // Obtener la reserva en MongoDB usando el mongoId
            Reservation reservation = reservationMongoRepository.findById(reservationNode.getMongoId())
                    .orElse(null);

            if (reservation != null) {
                // Convertir las fechas de la reserva
                LocalDate existingCheckIn = LocalDate.parse(reservation.getFechaEntrada());
                LocalDate existingCheckOut = LocalDate.parse(reservation.getFechaSalida());

                // Verificar superposición de fechas
                if (datesOverlap(checkInDate, checkOutDate, existingCheckIn, existingCheckOut)) {
                    return false; // La habitación no está disponible
                }
            }
        }

        return true; // La habitación está disponible en el rango de fechas solicitado
    }

    private boolean datesOverlap(LocalDate start1, LocalDate end1, LocalDate start2, LocalDate end2) {
        return (start1.isBefore(end2) && end1.isAfter(start2));
    }

    public List<Reservation> getReservationsByHotelAndDate(String hotelId, String fechaReserva) {
        return reservationMongoRepository.findByHotelIdAndFechaReserva(hotelId, fechaReserva);
    }

    public List<Reservation> getReservationsByGuestId(String guestId) {
        return reservationMongoRepository.findByGuestId(guestId);
    }

    public Optional<Reservation> getReservationByConfirmationCode(String codigoConfirmacion) {
        return reservationMongoRepository.findByCodigoConfirmacion(codigoConfirmacion);
    }

}
