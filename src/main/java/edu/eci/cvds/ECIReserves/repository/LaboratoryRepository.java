package edu.eci.cvds.ECIReserves.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import edu.eci.cvds.ECIReserves.model.Laboratory;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface LaboratoryRepository extends MongoRepository<Laboratory, String> {


    @Query("{ 'name' : ?0 }")
    List<Laboratory> findByName(String name);
    @Query("{ 'computers' : { $gte: ?0 } }")
    List<Laboratory> findByComputersGreaterThanEqual(int minComputers);

    @Query("{ 'openingTime' : { $gte: ?0 }, 'closingTime' : { $lte: ?1 } }")
    List<Laboratory> findByOpeningTimeBetween(Date start, Date end);

    @Query("{ 'openingTime' : ?0 }")
    List<Laboratory> findByOpeningTime(LocalDateTime openingTime);
    @Query("{ 'closingTime' : ?0 }")
    List<Laboratory> findByClosingTime(LocalDateTime closingTime);
    @Query("{ 'capacity' : { $gte: ?0 } }")
    List<Laboratory> findByCapacityGreaterThanEqual(int minCapacity);
}




