package com.uade.BBDD2.repository.mongodb;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.uade.BBDD2.model.mongodb.Reservation;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationMongoRepository extends MongoRepository<Reservation, String>{
    List<Reservation> findByHotelIdAndFechaReserva(String hotelId, String fechaReserva);

    List<Reservation> findByGuestId(String guestId);

    Optional<Reservation> findByCodigoConfirmacion(String codigoConfirmacion);

}
