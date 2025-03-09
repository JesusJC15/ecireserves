package edu.eci.cvds.ecireserves.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.eci.cvds.ecireserves.dto.LaboratoryDTO;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
import edu.eci.cvds.ecireserves.model.DaysOfWeek;
import edu.eci.cvds.ecireserves.model.Laboratory;
import edu.eci.cvds.ecireserves.repository.LaboratoryRepository;

@Service
public class LaboratoryService {
    @Autowired
    private LaboratoryRepository laboratoryRepository;

    public List<Laboratory> getAllLaboratories() {
        return laboratoryRepository.findAll();
    }

    public Laboratory getLaboratoryById(String id) throws EciReservesException {
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

    public List<Laboratory> findAvailableLaboratories() {
        return laboratoryRepository.findByAvailableTrue();
    }

    public Laboratory createLaboratory(LaboratoryDTO laboratoryDTO) throws EciReservesException{
        if(laboratoryRepository.findById(laboratoryDTO.getId()).isPresent()){
            throw new EciReservesException(EciReservesException.LABORATORY_ALREADY_EXISTS);
        }else{
            Laboratory laboratory = new Laboratory();
            laboratory.setId(laboratoryDTO.getId());
            laboratory.setClassroom(laboratoryDTO.getClassroom());
            laboratory.setName(laboratoryDTO.getName());
            laboratory.setCapacity(laboratoryDTO.getCapacity());
            laboratory.setDescription(laboratoryDTO.getDescription());
            laboratory.setDay(laboratoryDTO.getDay());
            laboratory.setTimeSlots(laboratoryDTO.getTimeSlots());
            laboratory.setAvailables(laboratoryDTO.getAvailables());
            
            return laboratoryRepository.save(laboratory);
        }
    }

    public Laboratory updateLaboratory(String id, LaboratoryDTO laboratoryDTO) throws EciReservesException {
        Laboratory laboratory = laboratoryRepository.findById(id).orElseThrow(() -> new EciReservesException(EciReservesException.LABORATORY_NOT_FOUND));
        if(laboratoryDTO.getClassroom() != null) laboratory.setClassroom(laboratoryDTO.getClassroom());
        if(laboratoryDTO.getName() != null) laboratory.setName(laboratoryDTO.getName());
        if(laboratoryDTO.getCapacity() != 0) laboratory.setCapacity(laboratoryDTO.getCapacity());
        if(laboratoryDTO.getDescription() != null) laboratory.setDescription(laboratoryDTO.getDescription());
        if(laboratoryDTO.getDay() != null) laboratory.setDay(laboratoryDTO.getDay());
        if(laboratoryDTO.getTimeSlots() != null) laboratory.setTimeSlots(laboratoryDTO.getTimeSlots());
        if(laboratoryDTO.getAvailables() != null) laboratory.setAvailables(laboratoryDTO.getAvailables());

        return laboratoryRepository.save(laboratory);
    }

    public void deleteLaboratory(String id) throws EciReservesException {
        if(!laboratoryRepository.existsById(id)){
            throw new EciReservesException(EciReservesException.LABORATORY_NOT_FOUND);
        }
        laboratoryRepository.deleteById(id);
    }
}
