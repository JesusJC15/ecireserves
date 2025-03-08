package edu.eci.cvds.ecireserves.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.DaysOfWeek;
import edu.eci.cvds.ecireserves.model.Laboratory;
import edu.eci.cvds.ecireserves.model.TimeSlot;

@Service
public class LaboratoryService {
    @Autowired
    private LaboratoryRepository laboratoryRepository;

    public List<Laboratory> getAllLaboratories() {
        return laboratoryRepository.findAll();
    }

    public Laboratory getLaboratoryById(String id) {
        return laboratoryRepository.findById(id).orElseThrow(() -> new EciReservesException(EciReservesException.LABORATORY_NOT_FOUND));
    }

    public List<Laboratory> getLaboratoryByClassroom(String classroom) {
        return laboratoryRepository.findByClassroom(classroom);
    }

    public List<Laboratory> getLaboratoryByName(String name) {
        return laboratoryRepository.findByName(name);
    }

    public List<Laboratory> getLaboratoryByCapacity(int capacity) {
        return laboratoryRepository.findByCapacity(capacity);
    }

    public List<Laboratory> getLaboratoryByDay(DaysOfWeek day) {
        return laboratoryRepository.findByDay(day);
    }

    public List<Laboratory> getLaboratoryByTimeSlot(TimeSlot timeSlot) {
        return laboratoryRepository.findByTimeSlot(timeSlot.getStartTime(), timeSlot.getEndTime());
    }

    public List<Laboratory> getLaboratoryAvailables() {
        return laboratoryRepository.findByAvailables(true);
    }
}
