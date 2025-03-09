package edu.eci.cvds.ecireserves.controller;

import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import edu.eci.cvds.ecireserves.enums.DaysOfWeek;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.ApiResponse;
import edu.eci.cvds.ecireserves.model.Laboratory;
import edu.eci.cvds.ecireserves.service.LaboratoryService;

@RestController
@RequestMapping("/api/laboratories")
public class LaboratoryController {
    @Autowired
    private LaboratoryService laboratoryService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Laboratory>>> getAllLaboratories() {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorios obtenidos exitosamente", laboratoryService.getAllLaboratories()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Laboratory>> getLaboratoryById(@PathVariable("id") String id) throws EciReservesException {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorio encontrado", laboratoryService.getLaboratoryById(id)));
    }

    @GetMapping("/classroom/{classroom}")
    public ResponseEntity<ApiResponse<List<Laboratory>>> getLaboratoryByClassroom(@PathVariable("classroom") String classroom) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorio encontrado", laboratoryService.getLaboratoryByClassroom(classroom)));
    }
    
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<Laboratory>>> getLaboratoriesByName(@RequestParam String name) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorios encontrados", laboratoryService.getLaboratoryByName(name)));
    }

    @GetMapping("/capacity/{capacity}")
    public ResponseEntity<ApiResponse<List<Laboratory>>> getLaboratoryByCapacity(@PathVariable("capacity") int capacity) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboraorios encontrados", laboratoryService.getLaboratoryByCapacity(capacity)));
    }

    @GetMapping("/day/{day}")
    public ResponseEntity<ApiResponse<List<Laboratory>>> getLaboratoryByDay(@PathVariable("day") DaysOfWeek day) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorios encontrados", laboratoryService.getLaboratoryByDay(day)));
    }

    @GetMapping("/opening-time/{openingTime}")
    public ResponseEntity<ApiResponse<List<Laboratory>>> getLaboratoryByOpeningTime(@PathVariable("openingTime") LocalTime openingTime) {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorios encontrados", laboratoryService.getLaboratoryByOpeningTime(openingTime)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Laboratory>> createLaboratory(@RequestBody LaboratoryDTO laboratoryDTO) throws EciReservesException {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorio creado", laboratoryService.createLaboratory(laboratoryDTO)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Laboratory>> updateLaboratory(@PathVariable("id") String id, @RequestBody LaboratoryDTO laboratoryDTO) throws EciReservesException {
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorio actualizado", laboratoryService.updateLaboratory(id, laboratoryDTO)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteLaboratory(@PathVariable("id") String id) throws EciReservesException {
        laboratoryService.deleteLaboratory(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Laboratorio eliminado", null));
    }
}
