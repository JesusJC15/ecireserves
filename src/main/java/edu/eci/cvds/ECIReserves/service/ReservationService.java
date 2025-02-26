package edu.eci.cvds.ECIReserves.service;

import org.springframework.stereotype.Service;

@Service
public class ReservationService {
    public void createReservation(String userId, String laboratoryId, String dateTime, String purpose, String status) {
        // TODO
    }

    public void cancelReservation(String id, String userId, String laboratoryId, String dateTime, String purpose, String status) {
        // TODO
    }
    public void updateReservation(String id, String userId, String laboratoryId, String dateTime, String purpose, String status) {
        // TODO
    }
}