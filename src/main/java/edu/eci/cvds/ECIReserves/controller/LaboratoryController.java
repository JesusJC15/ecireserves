package edu.eci.cvds.ecireserves.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.cvds.ecireserves.dto.LaboratoryDTO;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.DaysOfWeek;
import edu.eci.cvds.ecireserves.model.Laboratory;
import edu.eci.cvds.ecireserves.model.TimeSlot;
import edu.eci.cvds.ecireserves.service.LaboratoryService;

@RestController
@RequestMapping("/api/laboratories")
public class LaboratoryController {
    @Autowired
    private LaboratoryService laboratoryService;

    //R
    @GetMapping
    public List<Laboratory> getAllLaboratories() {
        return laboratoryService.getAllLaboratories();
    }

    @GetMapping("/{id}")
    public Laboratory getLaboratoryById(@PathVariable("id") String id) throws EciReservesException {
        return laboratoryService.getLaboratoryById(id);
    }

    @GetMapping("/classroom/{classroom}")
    public List<Laboratory> getLaboratoryByClassroom(@PathVariable("classroom") String classroom) throws EciReservesException {
        return laboratoryService.getLaboratoryByClassroom(classroom);
    }

    @GetMapping("/name/{name}")
    public List<Laboratory> getLaboratoryByName(@PathVariable("name") String name) throws EciReservesException {
        return laboratoryService.getLaboratoryByName(name);
    }

    @GetMapping("/capacity/{capacity}")
    public List<Laboratory> getLaboratoryByCapacity(@PathVariable("capacity") int capacity) {
        return laboratoryService.getLaboratoryByCapacity(capacity);
    }

    @GetMapping("/day/{day}")
    public List<Laboratory> getLaboratoryByDay(@PathVariable("day") DaysOfWeek day) {
        return laboratoryService.getLaboratoryByDay(day);
    }

    @GetMapping("timeSlot/{timeSlot}")
    public List<Laboratory> getLaboratoryByTimeSlot(@PathVariable("timeSlot") TimeSlot timeSlot) {
        return laboratoryService.getLaboratoryByTimeSlot(timeSlot);
    }

    @GetMapping("/availables")
    public List<Laboratory> getLaboratoryAvailables() {
        return laboratoryService.getLaboratoryAvailables();
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
