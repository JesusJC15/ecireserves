package edu.eci.cvds.ECIReserves.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.eci.cvds.ECIReserves.model.Laboratory;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LaboratoryRepository extends MongoRepository<Laboratory, String> {


    List<Laboratory> findByName(String name);

    List<Laboratory> findByComputersGreaterThanEqual(int minComputers);

    List<Laboratory> findByOpeningTime(LocalDateTime openingTime);

    List<Laboratory> findByClosingTime(LocalDateTime closingTime);

    List<Laboratory> findByCapacityGreaterThanEqual(int minCapacity);
}
