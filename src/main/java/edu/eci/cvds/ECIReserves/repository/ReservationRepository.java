package edu.eci.cvds.ECIReserves.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.eci.cvds.ECIReserves.model.Reservation;
import edu.eci.cvds.ECIReserves.model.ReservationStatus;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {
    List<Reservation> findByUserId(String userId);
    List<Reservation> findByLaboratoryId(String laboratoryId);
    List<Reservation> findByStatus(ReservationStatus status);
    List<Reservation> findByUserIdAndStatus(String userId, ReservationStatus status);
    List<Reservation> findByLaboratoryIdAndStartTimeBetween(String laboratoryId, LocalTime startTime, LocalTime endTime);
    Reservation findByLaboratoryIdAndStartDateAndEndDateAndStartTimeAndEndTime(String laboratoryId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime);
    //More methods can be added

}
