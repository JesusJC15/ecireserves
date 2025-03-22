package edu.eci.cvds.ECIReserves.repository;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.eci.cvds.ECIReserves.enums.DaysOfWeek;
import edu.eci.cvds.ECIReserves.model.Laboratory;

@Repository
public interface LaboratoryRepository extends MongoRepository<Laboratory, String> {
    List<Laboratory> findByClassroom(String classroom);
    List<Laboratory> findByName(String name);
    List<Laboratory> findByCapacity(int capacity);
    List<Laboratory> findByDay(DaysOfWeek day);
    List<Laboratory> findByOpeningTime(LocalTime openingTime);
}
