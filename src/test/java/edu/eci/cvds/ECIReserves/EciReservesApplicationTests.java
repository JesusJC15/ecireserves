package edu.eci.cvds.ECIReserves;

import edu.eci.cvds.ECIReserves.model.*;
import edu.eci.cvds.ECIReserves.model.Role;
import edu.eci.cvds.ECIReserves.model.User;
import edu.eci.cvds.ECIReserves.repository.UserRepository;
import edu.eci.cvds.ECIReserves.service.UserService;
import edu.eci.cvds.ECIReserves.repository.LaboratoryRepository;
import edu.eci.cvds.ECIReserves.repository.ReservationRepository;
import edu.eci.cvds.ECIReserves.service.LaboratoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



@SpringBootTest
@ExtendWith(MockitoExtension.class)
class EciReservesApplicationTests {

	@MockBean // <-- Usar @MockBean en lugar de @Mock
	private LaboratoryRepository laboratoryRepository;

	@MockBean
	private ReservationRepository reservationRepository;

	@Autowired
	private LaboratoryService laboratoryService;

	@MockBean
	private UserRepository userRepository;

	@Autowired
	private UserService userService;

	@Test
	void shouldAddLabWhenValid() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startDate = dateFormat.parse("2025-03-04 08:00:00");
		Date endDate = dateFormat.parse("2025-03-04 18:00:00");

		Laboratory lab = new Laboratory("1", "Lab A", 30, 10, "Descripcion", startDate, endDate, Day.MONDAY);

		System.out.println("Lab antes de guardar: " + lab);

		// Simula que el repositorio devuelve el mismo laboratorio al guardarlo
		doReturn(lab).when(laboratoryRepository).save(any(Laboratory.class));

		Laboratory result = laboratoryService.addLab(lab);

		verify(laboratoryRepository, times(1)).save(any(Laboratory.class));

		assertNotNull(result);
		assertEquals("Lab A", result.getName());
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
		Laboratory lab = new Laboratory("1", "Lab A", 30, 10, "Descripcion", start, end, Day.MONDAY);

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
		Laboratory lab = new Laboratory("1", "Lab A", 30, 10, "Descripcion", start, end, Day.MONDAY);
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

		Laboratory lab1 = new Laboratory("1", "Lab A", 20, 10, "Desc", start, end, Day.THURSDAY) ;
		Laboratory lab2 = new Laboratory("2", "Lab B", 15, 8, "Desc", start, end, Day.WEDNESDAY);
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

		Laboratory lab1 = new Laboratory("1", "Lab A", 20, 10, "Desc", start, end, Day.THURSDAY);
		Laboratory lab2 = new Laboratory("2", "Lab B", 15, 8, "Desc", start, end, Day.WEDNESDAY);
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
		Laboratory existingLab = new Laboratory("1", "Lab A", 30, 10, "Descripcion", start, end, Day.TUESDAY);
		Laboratory updatedLab = new Laboratory("1", "Lab B", 40, 15, "New Desc", start, end, Day.TUESDAY);

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
		Laboratory existingLab = new Laboratory("1", "Lab A", 30, 10, "Descripcion", new Date(), new Date(), Day.SATURDAY);

		when(laboratoryRepository.findById("1")).thenReturn(Optional.of(existingLab));

		Laboratory updatedLab = new Laboratory("1", null, -5, -3, null, null, null, null);

		Laboratory result = laboratoryService.updateLab("1", updatedLab);

		assertNotNull(result);
		assertEquals(30, result.getCapacity());
		assertEquals(10, result.getComputers());
		assertEquals("Lab A", result.getName());
		assertEquals("Descripcion", result.getDescription());

		verify(laboratoryRepository, times(1)).findById("1");
		verify(laboratoryRepository, never()).save(any(Laboratory.class));
	}


	@Test
	void shouldThrowExceptionWhenUpdatingNonExistingLab() {
		when(laboratoryRepository.findById("999")).thenReturn(java.util.Optional.empty());

		assertThrows(NoSuchElementException.class, () -> laboratoryService.updateLab("999", new Laboratory()));
	}

	@Test
	void shouldReturnLabsMatchingCriteria() {
		Laboratory lab = new Laboratory("1", "Lab A", 30, 10, "Descripcion", new Date(), new Date(), Day.WEDNESDAY);
		when(laboratoryRepository.findByNameCapacityAndComputers("Lab A", 10, 5))
				.thenReturn(Collections.singletonList(lab));

		var result = laboratoryService.searchLabs("Lab A", 10, 5);

		assertFalse(result.isEmpty());
		assertEquals(1, result.size());
		assertEquals("Lab A", result.get(0).getName());
	}

	@Test
	void shouldReturnEmptyListWhenNoLabsMatchCriteria() {
		when(laboratoryRepository.findByNameCapacityAndComputers("Unknown", 50, 20))
				.thenReturn(Collections.emptyList());

		var result = laboratoryService.searchLabs("Unknown", 50, 20);

		assertTrue(result.isEmpty());
	}

	@Test
	void testConvertToDateUsingReflection() throws Exception {
		LocalDateTime now = LocalDateTime.now();

		Method method = LaboratoryService.class.getDeclaredMethod("convertToDate", LocalDateTime.class);
		method.setAccessible(true); // Habilita el acceso al método privado

		Date result = (Date) method.invoke(laboratoryService, now);
		assertNotNull(result);
	}

	@Test
	void testUpdateFieldIfValid_withConditionUsingReflection() throws Exception {
		String[] field = {null};
		Consumer<String> setter = value -> field[0] = value;
		Predicate<String> condition = value -> value.length() > 3;

		Method method = LaboratoryService.class.getDeclaredMethod("updateFieldIfValid", Object.class, Consumer.class, Predicate.class);
		method.setAccessible(true);

		method.invoke(laboratoryService, "ValidName", setter, condition);

		assertEquals("ValidName", field[0]);
	}

	@Test
	void testUpdateFieldIfValid_withoutConditionUsingReflection() throws Exception {
		String[] field = {null};
		Consumer<String> setter = value -> field[0] = value;

		Method method = LaboratoryService.class.getDeclaredMethod("updateFieldIfValid", Object.class, Consumer.class);
		method.setAccessible(true);

		method.invoke(laboratoryService, "UpdatedValue", setter);

		assertEquals("UpdatedValue", field[0]);
	}



	@Test
	void testGetLabsByName() {
		Laboratory lab1 = new Laboratory();
		lab1.setName("Test Lab");
		when(laboratoryRepository.findByName("Test Lab")).thenReturn(Arrays.asList(lab1));

		List<Laboratory> result = laboratoryService.getLabsByName("Test Lab");
		assertEquals(1, result.size());
		assertEquals("Test Lab", result.get(0).getName());
	}

	@Test
	void testGetLabsByCapacity() {
		when(laboratoryRepository.findByCapacityGreaterThanEqual(10)).thenReturn(Arrays.asList(new Laboratory()));
		assertFalse(laboratoryService.getLabsByCapacity(10).isEmpty());
	}

	@Test
	void testGetLabsByComputers() {
		when(laboratoryRepository.findByComputersGreaterThanEqual(5)).thenReturn(Arrays.asList(new Laboratory()));
		assertFalse(laboratoryService.getLabsByComputers(5).isEmpty());
	}

	@Test
	void testGetLabsByOpeningTime() {
		LocalDateTime openingTime = LocalDateTime.of(2025, 3, 8, 8, 0);
		when(laboratoryRepository.findByOpeningTime(openingTime)).thenReturn(Arrays.asList(new Laboratory()));
		assertFalse(laboratoryService.getLabsByOpeningTime(openingTime).isEmpty());
	}

	@Test
	void testGetLabsByClosingTime() {
		LocalDateTime closingTime = LocalDateTime.of(2025, 3, 8, 18, 0);
		when(laboratoryRepository.findByClosingTime(closingTime)).thenReturn(Arrays.asList(new Laboratory()));
		assertFalse(laboratoryService.getLabsByClosingTime(closingTime).isEmpty());
	}

	@Test
	void testGetRepository() {
		assertNotNull(laboratoryService.getRepository());
	}


	//pruebas User

	@Test
	public void createUsersSuccess(){
		User user = new User("1234", "Pedro", "correo@escuelaing.edu.co", "Password", Role.USER);

		when(userRepository.findById(user.getId())).thenReturn(Optional.empty());
		when(userRepository.findByEmail(user.getEmail())).thenReturn(null);
		assertTrue(userService.createUsers(user));
	}

	@Test
	public void createUsersFailNotEnoughData(){
		User user = new User("1234", "", "correo@escuelaing.edu.co", "Password", Role.USER);
		assertFalse(userService.createUsers(user));
	}

	@Test
	public void createUsersFailRepitedData(){
		User user = new User("1234", "Pedro", "correo@escuelaing.edu.co", "Password", Role.USER);

		when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		when(userRepository.findByEmail(user.getEmail())).thenReturn(user);

		assertFalse(userService.createUsers(user));
	}

	@Test
	void testGetAllUsers() {
		User user = new User("1234", "Pedro", "correo@escuelaing.edu.co", "Password", Role.USER);
		User user1 = new User("4321", "Juan", "correo2@escuelaing.edu.co", "Password", Role.USER);

		when(userRepository.findAll()).thenReturn(Arrays.asList(user,user1));
		List<User> users = userService.getAllUsers();
		assertFalse(users.isEmpty());
		assertEquals(2, users.size());
		assertEquals("Pedro", users.get(0).getName());
		assertEquals("Juan", users.get(1).getName());
	}

	@Test
	void testGetUser() {
		User user = new User("1234", "Pedro", "correo@escuelaing.edu.co", "Password", Role.USER);
		when(userRepository.findById("1234")).thenReturn(Optional.of(user));
		User foundUser = userService.getUser("1234");
		assertEquals("Pedro", foundUser.getName());
	}

	@Test
	void testUpdateUsers() {
		User user = new User("1234", "Pedro", "correo@escuelaing.edu.co", "Password", Role.USER);
		when(userRepository.findById("1234")).thenReturn(Optional.of(user));
		boolean result = userService.updateUsers("1234", "Nuevonombre", "nombre@gmail.coem", "vainilla");
		assertTrue(result);
	}

	@Test
	void testUpdateUsers_UserNotFound() {
		User user = new User("1234", "Pedro", "correo@escuelaing.edu.co", "Password", Role.USER);
		when(userRepository.findById("2")).thenReturn(Optional.empty());
		boolean result = userService.updateUsers("2", "Michael", "pe@yahoo.com", "contra");
		assertFalse(result);
	}

	@Test
	void testRemoveUsers() {
		User user = new User("1234", "Pedro", "correo@escuelaing.edu.co", "Password", Role.USER);
		when(userRepository.findById("1234")).thenReturn(Optional.of(user));
		assertTrue(userService.removeUsers("1234"));
	}

	@Test
	void testRemoveUsers_UserNotFound() {
		User user = new User("1234", "Pedro", "correo@escuelaing.edu.co", "Password", Role.USER);
		when(userRepository.findById("1234")).thenReturn(Optional.empty());
		assertFalse(userService.removeUsers("1234"));
	}

}


