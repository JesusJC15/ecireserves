package edu.eci.cvds.ecireserves.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.cvds.ecireserves.dto.ReservationDTO;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.Reservation;
import edu.eci.cvds.ecireserves.model.ReservationStatus;
import edu.eci.cvds.ecireserves.service.ReservationService;

@RestController
@RequestMapping("/api/reservations")
@CrossOrigin(origins = "*")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public Reservation getReservationById(@PathVariable String id) throws EciReservesException {
        return reservationService.getReservationById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Reservation> getReservationsByUserId(@PathVariable("userId") String userId) {
        return reservationService.getReservationsByUserId(userId);
    }

    @GetMapping("/laboratory/{laboratoryId}")
    public List<Reservation> getReservationsByLaboratoryId(@PathVariable("laboratoryId") String laboratoryId) {
        return reservationService.getReservationsByLaboratoryId(laboratoryId);
    }

    @GetMapping("/status/{status}")
    public List<Reservation> getReservationsByStatus(@PathVariable("status") ReservationStatus status) {
        return reservationService.getReservationsByStatus(status);
    }

    @GetMapping("/user/{userId}/status/{status}")
    public List<Reservation> getReservationsByUserIdAndStatus(@PathVariable("userId") String userId, @PathVariable("status") ReservationStatus status) {
        return reservationService.getReservationsByUserIdAndStatus(userId, status);
    }

    @PostMapping
    public Reservation createReservation(@RequestBody ReservationDTO reservationDTO) throws EciReservesException {
        return reservationService.createReservation(reservationDTO);
    }

    @PutMapping("/{id}")
    public Reservation updateReservation(@PathVariable("id") String id, @RequestBody ReservationDTO reservationDTO) throws EciReservesException {
        return reservationService.updateReservation(id, reservationDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteReservation(@PathVariable("id") String id) throws EciReservesException {
        reservationService.deleteReservation(id);
    }
}
