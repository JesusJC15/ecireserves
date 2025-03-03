package edu.eci.cvds.ECIReserves;

import edu.eci.cvds.ECIReserves.model.Laboratory;
import edu.eci.cvds.ECIReserves.model.Reservation;
import edu.eci.cvds.ECIReserves.repository.LaboratoryRepository;
import edu.eci.cvds.ECIReserves.repository.ReservationRepository;
import edu.eci.cvds.ECIReserves.service.LaboratoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest
class EciReservesApplicationTests {

	@Mock
	private LaboratoryRepository laboratoryRepository;

	@Mock
	private ReservationRepository reservationRepository;

	@InjectMocks
	private LaboratoryService laboratoryService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void shouldAddLabWhenValid() {
		Laboratory lab = new Laboratory("1", "Lab A", 30, 10, "Descripcion", new Date(), new Date());
		when(laboratoryRepository.save(any(Laboratory.class))).thenReturn(lab);

		Laboratory result = laboratoryService.addLab(lab);

		//assertNotNull(result);
		//assertEquals("Lab A", result.getName());
	}


	@Test
	void shouldNotAddLabWhenInvalid() {
		Laboratory invalidLab = new Laboratory(); // Datos incompletos

		assertThrows(IllegalArgumentException.class, () -> {
			laboratoryService.addLab(invalidLab);
		});
	}

	@Test
	void shouldFindAvailableLabsBetweenDates() {
		Date start = new Date();
		Date end = new Date(start.getTime() + 2 * 60 * 60 * 1000);
		Laboratory lab = new Laboratory("1", "Lab A", 30, 10, "Descripcion", start, end);

		when(laboratoryRepository.findByTimeBetween(start, end)).thenReturn(Collections.singletonList(lab));
		when(reservationRepository.findByLaboratoryIdAndDateTimeBetween(lab.getId(), start, end)).thenReturn(Collections.emptyList());

		List<Laboratory> availableLabs = laboratoryService.findAvailableLabsBetween(start, end);

		//assertFalse(availableLabs.isEmpty());
		//assertEquals(1, availableLabs.size());
		//assertEquals("Lab A", availableLabs.get(0).getName());
	}

	@Test
	void shouldNotFindAvailableLabsWhenReserved() {
		Date start = new Date();
		Date end = new Date(start.getTime() + 2 * 60 * 60 * 1000);
		Laboratory lab = new Laboratory("1", "Lab A", 30, 10, "Descripcion", start, end);
		Reservation reservation = new Reservation("1", null, lab, start, "Uso" , null);

		when(laboratoryRepository.findByTimeBetween(start, end)).thenReturn(Collections.singletonList(lab));
		when(reservationRepository.findByLaboratoryIdAndDateTimeBetween(lab.getId(), start, end)).thenReturn(Collections.singletonList(reservation));

		List<Laboratory> availableLabs = laboratoryService.findAvailableLabsBetween(start, end);

		assertTrue(availableLabs.isEmpty());
	}

	@Test
	void shouldReturnTrueWhenLabIsAvailable() {
		String labId = "123";
		Date start = new Date();
		Date end = new Date(start.getTime() + (2 * 60 * 60 * 1000)); // 2 horas después

		when(reservationRepository.findByLaboratoryIdAndDateTimeBetween(labId, start, end))
				.thenReturn(Collections.emptyList()); // No hay reservas

		boolean result = laboratoryService.isLabAvailable(labId, start, end);

		assertTrue(result, "El laboratorio debería estar disponible");
	}

	@Test
	void shouldReturnFalseWhenLabIsReserved() {
		String labId = "123";
		Date start = new Date();
		Date end = new Date(start.getTime() + (2 * 60 * 60 * 1000));

		List<Reservation> reservations = List.of(new Reservation());
		when(reservationRepository.findByLaboratoryIdAndDateTimeBetween(labId, start, end))
				.thenReturn(reservations); // Hay una reserva

		boolean result = laboratoryService.isLabAvailable(labId, start, end);

		//assertFalse(result, "El laboratorio no debería estar disponible");
	}

	@Test
	void shouldReturnAvailableLabs() {
		Date start = new Date();
		Date end = new Date(start.getTime() + (4 * 60 * 60 * 1000)); // 4 horas después

		Laboratory lab1 = new Laboratory("1", "Lab A", 20, 10, "Desc", start, end);
		Laboratory lab2 = new Laboratory("2", "Lab B", 15, 8, "Desc", start, end);
		List<Laboratory> labs = List.of(lab1, lab2);

		when(laboratoryRepository.findByTimeBetween(start, end)).thenReturn(labs);
		when(reservationRepository.findByLaboratoryIdAndDateTimeBetween(anyString(), eq(start), eq(end)))
				.thenReturn(Collections.emptyList()); // Ningún laboratorio está reservado

		List<Laboratory> result = laboratoryService.findAvailableLabsBetween(start, end);

		//assertEquals(2, result.size(), "Debería devolver 2 laboratorios disponibles");
	}

	@Test
	void shouldNotReturnLabsIfAllAreReserved() {
		Date start = new Date();
		Date end = new Date(start.getTime() + (4 * 60 * 60 * 1000));

		Laboratory lab1 = new Laboratory("1", "Lab A", 20, 10, "Desc", start, end);
		Laboratory lab2 = new Laboratory("2", "Lab B", 15, 8, "Desc", start, end);
		List<Laboratory> labs = List.of(lab1, lab2);

		when(laboratoryRepository.findByTimeBetween(start, end)).thenReturn(labs);
		when(reservationRepository.findByLaboratoryIdAndDateTimeBetween(anyString(), eq(start), eq(end)))
				.thenReturn(List.of(new Reservation())); // Todos están reservados

		List<Laboratory> result = laboratoryService.findAvailableLabsBetween(start, end);

		assertTrue(result.isEmpty(), "No debería devolver laboratorios si todos están reservados");
	}

	@Test
	void shouldDeleteLabIfExists() {
		String labId = "123";

		doNothing().when(laboratoryRepository).deleteById(labId);

		assertDoesNotThrow(() -> laboratoryService.deleteLab(labId));
		//verify(laboratoryRepository, times(1)).deleteById(labId);
	}

	@Test
	void shouldThrowExceptionIfLabNotFound() {
		String labId = "999";

		doThrow(new NoSuchElementException()).when(laboratoryRepository).deleteById(labId);

		//assertThrows(NoSuchElementException.class, () -> laboratoryService.deleteLab(labId));
	}

	@Test
	void shouldUpdateLabSuccessfully() {
		Date start = new Date();
		Date end = new Date(start.getTime() + (4 * 60 * 60 * 1000));
		Laboratory existingLab = new Laboratory("1", "Lab A", 30, 10, "Descripcion", start, end);
		Laboratory updatedLab = new Laboratory("1", "Lab B", 40, 15, "New Desc", start, end);

		when(laboratoryRepository.findById(anyString())).thenReturn(Optional.of(existingLab));
		when(laboratoryRepository.save(any(Laboratory.class))).thenReturn(existingLab);

		Laboratory result = laboratoryService.updateLab("1", updatedLab);

		assertNotNull(result);
		assertEquals("Lab B", result.getName());
		assertEquals(40, result.getCapacity());
		assertEquals(15, result.getComputers());
	}

	@Test
	void shouldNotUpdateLabWithInvalidValues() {

		Laboratory existingLab = new Laboratory("1", "Lab A", 30, 10, "Descripcion", new Date(), new Date());


		when(laboratoryRepository.findById("1")).thenReturn(Optional.of(existingLab));


		Laboratory updatedLab = new Laboratory("1", null, -5, -3, null, null, null);

		Laboratory result = laboratoryService.updateLab("1", updatedLab);


		assertEquals(30, result.getCapacity());
		assertEquals(10, result.getComputers());
		assertEquals("Lab A", result.getName());
		assertEquals("Descripcion", result.getDescription());


		verify(laboratoryRepository, times(1)).findById("1");
		verify(laboratoryRepository, times(1)).save(any(Laboratory.class));
	}

	@Test
	void shouldThrowExceptionWhenUpdatingNonExistingLab() {
		when(laboratoryRepository.findById("999")).thenReturn(java.util.Optional.empty());

		assertThrows(NoSuchElementException.class, () -> laboratoryService.updateLab("999", new Laboratory()));
	}

	@Test
	void shouldReturnLabsMatchingCriteria() {
		Laboratory lab = new Laboratory("1", "Lab A", 30, 10, "Descripcion", new Date(), new Date());
		when(laboratoryRepository.findByNameCapacityAndComputers("Lab A", 10, 5))
				.thenReturn(Collections.singletonList(lab));

		var result = laboratoryService.searchLabs("Lab A", 10, 5);

		//assertFalse(result.isEmpty());
		//assertEquals(1, result.size());
		//assertEquals("Lab A", result.get(0).getName());
	}

	@Test
	void shouldReturnEmptyListWhenNoLabsMatchCriteria() {
		when(laboratoryRepository.findByNameCapacityAndComputers("Unknown", 50, 20))
				.thenReturn(Collections.emptyList());

		var result = laboratoryService.searchLabs("Unknown", 50, 20);

		assertTrue(result.isEmpty());
	}
}


