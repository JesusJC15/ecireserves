package edu.eci.cvds.ecireserves.model;

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
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.eci.cvds.ecireserves.dto.LaboratoryDTO;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.repository.LaboratoryRepository;
import edu.eci.cvds.ecireserves.service.LaboratoryService;

@ExtendWith(MockitoExtension.class)
class LaboratoryServiceTest {

    @Mock
    private LaboratoryRepository laboratoryRepository;

    @InjectMocks
    private LaboratoryService laboratoryService;

    private Laboratory laboratory;
    private LaboratoryDTO laboratoryDTO;

    @BeforeEach
    void setUp() {
        laboratory = new Laboratory("1", "A101", "Software Lab", 30, "Lab for software engineering", DaysOfWeek.LUNES, List.of(), List.of());
        laboratoryDTO = new LaboratoryDTO("1", "A101", "Software Lab", 30, "Lab for software engineering", DaysOfWeek.LUNES, List.of(), List.of());
    }

    @Test
    void testGetAllLaboratories() {
        when(laboratoryRepository.findAll()).thenReturn(List.of(laboratory));

        List<Laboratory> laboratories = laboratoryService.getAllLaboratories();

        assertFalse(laboratories.isEmpty());
        assertEquals(1, laboratories.size());
        assertEquals(laboratory.getId(), laboratories.get(0).getId());
    }

    @Test
    void testGetLaboratoryById_Success() throws EciReservesException {
        when(laboratoryRepository.findById("1")).thenReturn(Optional.of(laboratory));

        Laboratory foundLab = laboratoryService.getLaboratoryById("1");

        assertNotNull(foundLab);
        assertEquals(laboratory.getId(), foundLab.getId());
    }

    @Test
    void testGetLaboratoryById_NotFound() {
        when(laboratoryRepository.findById("2")).thenReturn(Optional.empty());

        EciReservesException exception = assertThrows(EciReservesException.class, () -> laboratoryService.getLaboratoryById("2"));

        assertEquals(EciReservesException.LABORATORY_NOT_FOUND, exception.getMessage());
    }

    @Test
    void testCreateLaboratory_Success() throws EciReservesException {
        when(laboratoryRepository.findById("1")).thenReturn(Optional.empty());
        when(laboratoryRepository.save(any(Laboratory.class))).thenReturn(laboratory);

        Laboratory createdLab = laboratoryService.createLaboratory(laboratoryDTO);

        assertNotNull(createdLab);
        assertEquals(laboratory.getName(), createdLab.getName());
    }

    @Test
    void testCreateLaboratory_AlreadyExists() {
        when(laboratoryRepository.findById("1")).thenReturn(Optional.of(laboratory));

        EciReservesException exception = assertThrows(EciReservesException.class, () -> laboratoryService.createLaboratory(laboratoryDTO));

        assertEquals(EciReservesException.LABORATORY_ALREADY_EXISTS, exception.getMessage());
    }
}