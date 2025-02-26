package edu.eci.cvds.ECIReserves.repository;



import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.eci.cvds.ECIReserves.model.Reservation;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {
    //verificar si un laboratorio ya está reservado en una fecha y hora específicas
    ArrayList<Reservation> findByLaboratoryIdAndDateTime(String laboratoryId, LocalDateTime dateTime);
}
