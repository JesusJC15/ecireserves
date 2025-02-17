package edu.eci.cvds.ecireserves.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import edu.eci.cvds.ecireserves.model.Laboratory;

@Repository
public interface LaboratoryRepository extends MongoRepository<Laboratory, String> {
}
