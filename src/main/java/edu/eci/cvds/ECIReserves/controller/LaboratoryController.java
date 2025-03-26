package edu.eci.cvds.ecireserves.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.cvds.ecireserves.dto.LaboratoryDTO;
import edu.eci.cvds.ecireserves.enums.LaboratoryStatus;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.ApiResponse;
import edu.eci.cvds.ecireserves.model.Laboratory;
import edu.eci.cvds.ecireserves.service.LaboratoryService;


@RestController
@RequestMapping("/api")
public class LaboratoryController {
    
    private final LaboratoryService laboratoryService;

    public LaboratoryController(LaboratoryService laboratoryService) {
        this.laboratoryService = laboratoryService;
    }

    @GetMapping("/user/laboratories")
    @PreAuthorize("hasAnyRole('USUARIO', 'ADMINISTRADOR', 'PROFESOR')")
    public ResponseEntity<ApiResponse<List<Laboratory>>> getAllLaboratories() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorios obtenidos exitosamente", laboratoryService.getAllLaboratories()));
    }

    @GetMapping("/user/laboratories/{id}")
    @PreAuthorize("hasAnyRole('USUARIO', 'ADMINISTRADOR', 'PROFESOR')")
    public ResponseEntity<ApiResponse<Laboratory>> getLaboratoryById(@PathVariable("id") String id) throws EciReservesException {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorio con id " + id +  " encontrado", laboratoryService.getLaboratoryById(id)));
    }

    @GetMapping("/user/laboratories/classroom/{classroom}")
    @PreAuthorize("hasAnyRole('USUARIO', 'ADMINISTRADOR', 'PROFESOR')")
    public ResponseEntity<ApiResponse<List<Laboratory>>> getLaboratoriesByClassroom(@PathVariable("classroom") String classroom) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorios del salon " + classroom, laboratoryService.getLaboratoriesByClassroom(classroom)));
    }
    
    @GetMapping("/user/laboratories/search")
    @PreAuthorize("hasAnyRole('USUARIO', 'ADMINISTRADOR', 'PROFESOR')")
    public ResponseEntity<ApiResponse<List<Laboratory>>> getLaboratoriesByName(@RequestParam String name) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorios por nombre " + name, laboratoryService.getLaboratoriesByName(name)));
    }

    @GetMapping("/user/laboratories/capacity/{capacity}")
    @PreAuthorize("hasAnyRole('USUARIO', 'ADMINISTRADOR', 'PROFESOR')")
    public ResponseEntity<ApiResponse<List<Laboratory>>> getLaboratoriesByCapacity(@PathVariable("capacity") int capacity) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorios de capacidad " + capacity, laboratoryService.getLaboratoriesByCapacity(capacity)));
    }

    @GetMapping("/user/laboratories/date/{date}")
    @PreAuthorize("hasAnyRole('USUARIO', 'ADMINISTRADOR', 'PROFESOR')")
    public ResponseEntity<ApiResponse<List<Laboratory>>> getLaboratoriesByDate(@PathVariable("date") LocalDate date) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorios del dia " + date, laboratoryService.getLaboratoriesByDate(date)));
    }

    @GetMapping("/user/laboratories/opening-time/{openingTime}")
    @PreAuthorize("hasAnyRole('USUARIO', 'ADMINISTRADOR', 'PROFESOR')")
    public ResponseEntity<ApiResponse<List<Laboratory>>> getLaboratoriesByOpeningTime(@PathVariable("openingTime") LocalTime openingTime) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorios que abren a las: " + openingTime.toString(), laboratoryService.getLaboratoriesByOpeningTime(openingTime)));
    }

    @GetMapping("/user/laboratories/status/{status}")
    @PreAuthorize("hasAnyRole('USUARIO', 'ADMINISTRADOR', 'PROFESOR')")
    public ResponseEntity<ApiResponse<List<Laboratory>>> getLaboratoriesByStatus(@PathVariable("status") LaboratoryStatus status) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorios con estado " + status, laboratoryService.getLaboratoriesByStatus(status)));
    }

    @PostMapping("/admin/laboratories")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<Laboratory>> createLaboratory(@RequestBody LaboratoryDTO laboratoryDTO) throws EciReservesException {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorio creado", laboratoryService.createLaboratory(laboratoryDTO)));
    }

    @PutMapping("/admin/laboratories/{id}")
    @PreAuthorize("hasRole('ADMINISTRADOR')")
    public ResponseEntity<ApiResponse<Laboratory>> updateLaboratory(@PathVariable("id") String id, @RequestBody LaboratoryDTO laboratoryDTO) throws EciReservesException {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorio actualizado", laboratoryService.updateLaboratory(id, laboratoryDTO)));
    }

    @DeleteMapping("/admin/laboratories/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteLaboratory(@PathVariable("id") String id) throws EciReservesException {
        laboratoryService.deleteLaboratory(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorio eliminado", null));
    }
}
