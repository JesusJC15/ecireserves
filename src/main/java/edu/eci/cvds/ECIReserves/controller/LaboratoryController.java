package edu.eci.cvds.ECIReserves.controller;

import edu.eci.cvds.ECIReserves.model.Laboratory;
import edu.eci.cvds.ECIReserves.service.LaboratoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/laboratory")
public class LaboratoryController {

    private final LaboratoryService laboratoryService;

    @Autowired
    public LaboratoryController(LaboratoryService laboratoryService) {
        this.laboratoryService = laboratoryService;
    }

    @PostMapping
    public ResponseEntity<Laboratory> addLab(@RequestBody Laboratory lab) {
        Laboratory createdLab = laboratoryService.addLab(lab);
        return createdLab != null ? ResponseEntity.ok(createdLab) : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Laboratory> updateLab(@PathVariable String id, @RequestBody Laboratory updatedLab) {
        try {
            Laboratory lab = laboratoryService.updateLab(id, updatedLab);
            return ResponseEntity.ok(lab);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLab(@PathVariable String id) {
        laboratoryService.deleteLab(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available")
    public ResponseEntity<List<Laboratory>> findAvailableLabs(@RequestParam Date start, @RequestParam Date end) {
        List<Laboratory> availableLabs = laboratoryService.findAvailableLabsBetween(start, end);
        return ResponseEntity.ok(availableLabs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Laboratory>> searchLabs(@RequestParam(required = false) String name,
                                                       @RequestParam(required = false) Integer minCapacity,
                                                       @RequestParam(required = false) Integer minComputers) {
        List<Laboratory> labs = laboratoryService.searchLabs(name, minCapacity, minComputers);
        return ResponseEntity.ok(labs);
    }

    @GetMapping
    public ResponseEntity<List<Laboratory>> getAllLabs() {
        List<Laboratory> labs = laboratoryService.getRepository().findAll();
        return ResponseEntity.ok(labs);
    }

    @GetMapping("/list")
    public String listLabs(Model model) {
        List<Laboratory> labs = laboratoryService.getAllLabs();
        model.addAttribute("laboratories", labs);
        return "laboratory-list"; // Nombre del HTML
    }


}

