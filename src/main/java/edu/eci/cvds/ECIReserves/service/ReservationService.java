package edu.eci.cvds.ecireserves.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.cvds.ecireserves.dto.ReservationDTO;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.Reservation;
import edu.eci.cvds.ecireserves.model.ReservationStatus;
import edu.eci.cvds.ecireserves.repository.ReservationRepository;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Optional<Reservation> getReservationById(String id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getReservationsByUserId(String userId) {
        return reservationRepository.findByUserId(userId);
    }

    public List<Reservation> getReservationsByLaboratoryId(String laboratoryId) {
        return reservationRepository.findByLaboratoryId(laboratoryId);
    }

    public List<Reservation> getReservationsByStatus(ReservationStatus status) {
        return reservationRepository.findByStatus(status);
    }

    public List<Reservation> getReservationsByUserIdAndStatus(String userId, ReservationStatus status) {
        return reservationRepository.findByUserIdAndStatus(userId, status);
    }

    public Reservation createReservation(ReservationDTO reservationDTO) throws EciReservesException{
        boolean exists = !reservationRepository.findByLaboratoryIdAndStartTimeBetween(reservationDTO.getLaboratoryId(), reservationDTO.getStartTime(), reservationDTO.getEndTime()).isEmpty();
        if(exists){
            throw new EciReservesException(EciReservesException.RESERVATION_ALREADY_EXISTS);
        }
        Reservation reservation = new Reservation();
        reservation.setUserId(reservationDTO.getUserId());
        reservation.setLaboratoryId(reservationDTO.getLaboratoryId());
        reservation.setStartDate(reservationDTO.getStartDate());
        reservation.setEndDate(reservationDTO.getEndDate());
        reservation.setStartTime(reservationDTO.getStartTime());
        reservation.setEndTime(reservationDTO.getEndTime());
        reservation.setPurpose(reservationDTO.getPurpose());

        return reservationRepository.save(reservation);
    }

    public void deleteReservation(String id) {
        Reservation reservation = reservationRepository.findById(id).orElse(null);
        if(reservation != null) {
            reservationRepository.delete(reservation);
        }
    }
}
