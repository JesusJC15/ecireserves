package edu.eci.cvds.ecireserves.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.quality.Strictness;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;

import edu.eci.cvds.ecireserves.dto.ReservationDTO;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.repository.LaboratoryRepository;
import edu.eci.cvds.ecireserves.repository.ReservationRepository;
import edu.eci.cvds.ecireserves.service.ReservationService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private LaboratoryRepository laboratoryRepository;


    @InjectMocks
    private ReservationService reservationService;

    private Reservation reservation;
    private ReservationDTO reservationDTO;
    private Laboratory laboratory;

    @BeforeEach
    void setUp() {
        laboratory = new Laboratory("lab456", "A101", "Software Lab", 30, "Lab for software engineering", DaysOfWeek.LUNES, List.of(), List.of());
        reservation = new Reservation("1", "user123", "lab456", LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0), "Practice", ReservationStatus.APPROVED);
        reservationDTO = new ReservationDTO("user123", "lab456", LocalDate.now(), LocalTime.of(10, 0), LocalTime.of(12, 0), "Practice");
    }

    @Test
    void testGetAllReservations() {
        when(reservationRepository.findAll()).thenReturn(List.of(reservation));

        List<Reservation> reservations = reservationService.getAllReservations();

        assertFalse(reservations.isEmpty());
        assertEquals(1, reservations.size());
        assertEquals(reservation.getId(), reservations.get(0).getId());
    }

    @Test
    void testGetReservationById_Success() throws EciReservesException {
        when(reservationRepository.findById("1")).thenReturn(Optional.of(reservation));

        Reservation foundReservation = reservationService.getReservationById("1");

        assertNotNull(foundReservation);
        assertEquals(reservation.getId(), foundReservation.getId());
    }

    @Test
    void testGetReservationById_NotFound() {
        when(reservationRepository.findById("2")).thenReturn(Optional.empty());

        EciReservesException exception = assertThrows(EciReservesException.class, () -> reservationService.getReservationById("2"));

        assertEquals(EciReservesException.RESERVATION_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testCreateReservation_Success() throws EciReservesException {
        when(laboratoryRepository.findById("lab456")).thenReturn(Optional.of(laboratory));
        when(reservationRepository.existsByLaboratoryIdAndStartTimeBetween("lab456", reservationDTO.getStartTime(), reservationDTO.getEndTime())).thenReturn(false);
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);

        Reservation createdReservation = reservationService.createReservation(reservationDTO);

        assertNotNull(createdReservation);
        assertEquals(reservation.getUserId(), createdReservation.getUserId());
    }
}
