package edu.eci.cvds.ecireserves.controller;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import edu.eci.cvds.ecireserves.dto.LaboratoryDTO;
import edu.eci.cvds.ecireserves.enums.DaysOfWeek;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.ApiResponse;
import edu.eci.cvds.ecireserves.model.Laboratory;
import edu.eci.cvds.ecireserves.service.LaboratoryService;


@RestController
@RequestMapping("/api")
public class LaboratoryController {
    private final LaboratoryService laboratoryService;

    @Autowired
    public LaboratoryController(LaboratoryService laboratoryService) {
        this.laboratoryService = laboratoryService;
    }

    @GetMapping("/user/laboratories")
    public ResponseEntity<ApiResponse<List<Laboratory>>> getAllLaboratories() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorios obtenidos exitosamente", laboratoryService.getAllLaboratories()));
    }

    @GetMapping("/user/laboratories/{id}")
    public ResponseEntity<ApiResponse<Laboratory>> getLaboratoryById(@PathVariable("id") String id) throws EciReservesException {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorio con id " + id +  " encontrado", laboratoryService.getLaboratoryById(id)));
    }

    @GetMapping("/user/laboratories/classroom/{classroom}")
    public ResponseEntity<ApiResponse<List<Laboratory>>> getLaboratoriesByClassroom(@PathVariable("classroom") String classroom) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorios del salon " + classroom, laboratoryService.getLaboratoriesByClassroom(classroom)));
    }
    
    @GetMapping("/user/laboratories/search")
    public ResponseEntity<ApiResponse<List<Laboratory>>> getLaboratoriesByName(@RequestParam String name) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorios por nombre " + name, laboratoryService.getLaboratoriesByName(name)));
    }

    @GetMapping("/user/laboratories/capacity/{capacity}")
    public ResponseEntity<ApiResponse<List<Laboratory>>> getLaboratoriesByCapacity(@PathVariable("capacity") int capacity) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorios de capacidad " + capacity, laboratoryService.getLaboratoriesByCapacity(capacity)));
    }

    @GetMapping("/user/laboratories/day/{day}")
    public ResponseEntity<ApiResponse<List<Laboratory>>> getLaboratoriesByDay(@PathVariable("day") DaysOfWeek day) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorios del dia " + day, laboratoryService.getLaboratoriesByDay(day)));
    }

    @GetMapping("/user/laboratories/opening-time/{openingTime}")
    public ResponseEntity<ApiResponse<List<Laboratory>>> getLaboratoriesByOpeningTime(@PathVariable("openingTime") LocalTime openingTime) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorios que abren a las: " + openingTime.toString(), laboratoryService.getLaboratoriesByOpeningTime(openingTime)));
    }

    @PostMapping("/admin/laboratories")
    public ResponseEntity<ApiResponse<Laboratory>> createLaboratory(@RequestBody LaboratoryDTO laboratoryDTO) throws EciReservesException {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorio creado", laboratoryService.createLaboratory(laboratoryDTO)));
    }

    @PutMapping("/admin/laboratories/{id}")
    public ResponseEntity<ApiResponse<Laboratory>> updateLaboratory(@PathVariable("id") String id, @RequestBody LaboratoryDTO laboratoryDTO) throws EciReservesException {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorio actualizado", laboratoryService.updateLaboratory(id, laboratoryDTO)));
    }

    @DeleteMapping("/admin/laboratories/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteLaboratory(@PathVariable("id") String id) throws EciReservesException {
        laboratoryService.deleteLaboratory(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorio eliminado", null));
    }
}
