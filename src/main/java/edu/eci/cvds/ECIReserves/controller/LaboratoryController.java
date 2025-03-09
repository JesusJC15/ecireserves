package edu.eci.cvds.ecireserves.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.DaysOfWeek;
import edu.eci.cvds.ecireserves.model.Laboratory;
import edu.eci.cvds.ecireserves.service.LaboratoryService;

@RestController
@RequestMapping("/api/laboratories")
public class LaboratoryController {
    @Autowired
    private LaboratoryService laboratoryService;

    @GetMapping
    public List<Laboratory> getAllLaboratories() {
        return laboratoryService.getAllLaboratories();
    }

    @GetMapping("/{id}")
    public Laboratory getLaboratoryById(@PathVariable("id") String id) throws EciReservesException {
        return laboratoryService.getLaboratoryById(id);
    }

    @GetMapping("/search")
    public List<Laboratory> getLaboratoryByClassroom(@RequestParam String classroom) {
        return laboratoryService.getLaboratoryByClassroom(classroom);
    }

    @GetMapping("/search")
    public List<Laboratory> getLaboratoryByName(@RequestParam String name) {
        return laboratoryService.getLaboratoryByName(name);
    }

    @GetMapping("{capacity}")
    public List<Laboratory> getLaboratoryByCapacity(@PathVariable("capacity") int capacity) {
        return laboratoryService.getLaboratoryByCapacity(capacity);
    }

    @GetMapping("{day}")
    public List<Laboratory> getLaboratoryByDay(@PathVariable("day") DaysOfWeek day) {
        return laboratoryService.getLaboratoryByDay(day);
    }

    //C
    @PostMapping
    public Laboratory createLaboratory(@RequestBody LaboratoryDTO laboratoryDTO) throws EciReservesException {
        return laboratoryService.createLaboratory(laboratoryDTO);
    }

    //U
    @PutMapping("/{id}")
    public Laboratory updateLaboratory(@PathVariable("id") String id, @RequestBody LaboratoryDTO laboratoryDTO) throws EciReservesException {
        laboratoryService.updateLaboratory(id, laboratoryDTO);
        return laboratoryService.getLaboratoryById(id);
    }

    //D
    @DeleteMapping("/{id}")
    public void deleteLaboratory(@PathVariable("id") String id) throws EciReservesException {
        laboratoryService.deleteLaboratory(id);
    }
}
