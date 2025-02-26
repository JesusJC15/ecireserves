package edu.eci.cvds.ecireserves.model;
import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "reservations")
public class Reservation {
    @Id
    private String id;
    
    private String userId;
    private String laboratoryId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String purpose;
    private String status;
}
