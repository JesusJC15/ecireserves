package edu.eci.cvds.ecireserves.model;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.eci.cvds.ecireserves.enums.DaysOfWeek;
import edu.eci.cvds.ecireserves.exception.EciReservesException;

class LaboratoryTest {

    private Laboratory laboratory;

    @BeforeEach
    void setUp() {
        laboratory = new Laboratory();
        laboratory.setId("L001");
        laboratory.setClassroom("C-101");
        laboratory.setName("Laboratorio de Desarrollo de Software");
        laboratory.setCapacity(30);
        laboratory.setDescription("Laboratorio de computadores para desarrollo de software, cuenta con 30 computadores.");
        laboratory.setDay(DaysOfWeek.MIERCOLES);
        laboratory.setOpeningTime(LocalTime.of(7, 0));
        laboratory.setClosingTime(LocalTime.of(19, 0));
    }
    @Test
    void shouldAddValidTimeSlot() throws EciReservesException{
        laboratory.addTimeSlot(LocalTime.of(7, 0), LocalTime.of(8, 30));
        
        assertEquals(1, laboratory.getTimeSlots().size());
        assertEquals(LocalTime.of(7, 0), laboratory.getTimeSlots().get(0).getStartTime());
        assertEquals(LocalTime.of(8, 30), laboratory.getTimeSlots().get(0).getEndTime());
        assertEquals(1, laboratory.getAvailables().size());
        assertFalse(laboratory.getAvailables().get(0));
    }

    @Test
    void shouldNotAddTimeSlotOutsideOpeningHours() {
        try {
            laboratory.addTimeSlot(LocalTime.of(6, 0), LocalTime.of(8, 30));
        } catch (EciReservesException e) {
            assertEquals(EciReservesException.INVALID_TIMESLOT, e.getMessage());
            assertTrue(laboratory.getTimeSlots().isEmpty());
            assertTrue(laboratory.getAvailables().isEmpty());
        }
    }

    @Test
    void shouldNotAddTimeSlotOutsideClosingHours() {
        try {
            laboratory.addTimeSlot(LocalTime.of(18, 0), LocalTime.of(20, 0));
        } catch (EciReservesException e) {
            assertEquals(EciReservesException.INVALID_TIMESLOT, e.getMessage());
            assertTrue(laboratory.getTimeSlots().isEmpty());
            assertTrue(laboratory.getAvailables().isEmpty());
        }
    }

    @Test
    void shouldNotAddDuplicateTimeSlot() throws EciReservesException {
        laboratory.addTimeSlot(LocalTime.of(7, 0), LocalTime.of(8, 30));
        try {
            laboratory.addTimeSlot(LocalTime.of(7, 0), LocalTime.of(8, 30));
        } catch (EciReservesException e) {
            assertEquals(EciReservesException.TIMESLOT_ALREADY_EXISTS, e.getMessage());
            assertEquals(1, laboratory.getTimeSlots().size());
            assertEquals(1, laboratory.getAvailables().size());
        }
    }

    @Test
    void shouldRemoveExistingTimeSlot() throws EciReservesException {
        laboratory.addTimeSlot(LocalTime.of(7, 0), LocalTime.of(8, 30));
        laboratory.removeTimeSlot(LocalTime.of(7, 0), LocalTime.of(8, 30));
        
        assertTrue(laboratory.getTimeSlots().isEmpty());
        assertTrue(laboratory.getAvailables().isEmpty());
    }

    @Test
    void shouldNotFailRemovingExistingTimeSlot() throws EciReservesException {
        laboratory.addTimeSlot(LocalTime.of(7, 0), LocalTime.of(8, 30));
        laboratory.removeTimeSlot(LocalTime.of(8, 30), LocalTime.of(10, 0));
        
        assertEquals(1, laboratory.getTimeSlots().size());
        assertEquals(1, laboratory.getAvailables().size());
    }
}
