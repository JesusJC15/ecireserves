package edu.eci.cvds.ecireserves.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.cvds.ecireserves.dto.ReservationDTO;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.Laboratory;
import edu.eci.cvds.ecireserves.model.Reservation;
import edu.eci.cvds.ecireserves.model.ReservationStatus;
import edu.eci.cvds.ecireserves.model.TimeSlot;
import edu.eci.cvds.ecireserves.repository.LaboratoryRepository;
import edu.eci.cvds.ecireserves.repository.ReservationRepository;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private LaboratoryRepository laboratoryRepository;

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation getReservationById(String id) throws EciReservesException {
        return reservationRepository.findById(id).orElseThrow(() -> new EciReservesException(EciReservesException.RESERVATION_NOT_FOUND));
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
        Laboratory laboratory = laboratoryRepository.findById(reservationDTO.getLaboratoryId()).orElseThrow(() -> new EciReservesException(EciReservesException.LABORATORY_NOT_FOUND));

        List<TimeSlot> timeSlots = laboratory.getTimeSlots();
        List<Boolean> availables = laboratory.getAvailables();

        // Verificar que los timeSlots existen y están disponibles
        for (int i = 0; i < timeSlots.size(); i++) {
            TimeSlot slot = timeSlots.get(i);
            if (slot.getStartTime().equals(reservationDTO.getStartTime()) && slot.getEndTime().equals(reservationDTO.getEndTime()) && !availables.get(i)) {
                throw new EciReservesException(EciReservesException.RESERVATION_ALREADY_EXISTS);
            }
        }

        // Marcar los timeSlots correspondientes como reservados
        for (int i = 0; i < timeSlots.size(); i++) {
            TimeSlot slot = timeSlots.get(i);
            if (slot.getStartTime().equals(reservationDTO.getStartTime()) && slot.getEndTime().equals(reservationDTO.getEndTime())) {
                availables.set(i, false);
            }
        }
        laboratoryRepository.save(laboratory);

        // Crear la reserva
        Reservation reservation = new Reservation();
        reservation.setUserId(reservationDTO.getUserId());
        reservation.setLaboratoryId(reservationDTO.getLaboratoryId());
        reservation.setDate(reservationDTO.getDate());
        reservation.setStartTime(reservationDTO.getStartTime());
        reservation.setEndTime(reservationDTO.getEndTime());
        reservation.setPurpose(reservationDTO.getPurpose());
        reservation.setStatus(ReservationStatus.APPROVED);

        return reservationRepository.save(reservation);
    }

    public Reservation updateReservation(String id, ReservationDTO reservationDTO) throws EciReservesException {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new EciReservesException(EciReservesException.RESERVATION_NOT_FOUND));

        reservation.setStatus(ReservationStatus.PENDING);
        reservation.setUserId(reservationDTO.getUserId());
        reservation.setLaboratoryId(reservationDTO.getLaboratoryId());
        reservation.setDate(reservationDTO.getDate());
        reservation.setStartTime(reservationDTO.getStartTime());
        reservation.setEndTime(reservationDTO.getEndTime());
        reservation.setPurpose(reservationDTO.getPurpose());
        reservation.setStatus(ReservationStatus.APPROVED);
        return reservationRepository.save(reservation);
    }

    public void deleteReservation(String id) throws EciReservesException {
        Reservation reservation = reservationRepository.findById(id).orElseThrow(() -> new EciReservesException(EciReservesException.RESERVATION_NOT_FOUND));

        Laboratory laboratory = laboratoryRepository.findById(reservation.getLaboratoryId()).orElseThrow(() -> new EciReservesException(EciReservesException.LABORATORY_NOT_FOUND));

        List<TimeSlot> timeSlots = laboratory.getTimeSlots();
        List<Boolean> availables = laboratory.getAvailables();

        // Marcar los timeSlots correspondientes como disponibles nuevamente
        for (int i = 0; i < timeSlots.size(); i++) {
            TimeSlot slot = timeSlots.get(i);
            if (slot.getStartTime().equals(reservation.getStartTime()) && slot.getEndTime().equals(reservation.getEndTime())) {
                availables.set(i, true);
            }
        }
        laboratoryRepository.save(laboratory);

        reservationRepository.deleteById(id);
    }
}
