package edu.eci.cvds.ecireserves.service;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import edu.eci.cvds.ecireserves.model.Reservation;
import edu.eci.cvds.ecireserves.repository.ReservationRepository;

@Service
public class ReservationCleanupService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Scheduled(cron = "0 0 * * * ?")
    public void cleanupReservations() {
        LocalTime now = LocalTime.now();
        List<Reservation> expired = reservationRepository.findByEndTimeBefore(now);
        if(!expired.isEmpty()) {
            reservationRepository.deleteAll(expired);
        }
    } 
}
