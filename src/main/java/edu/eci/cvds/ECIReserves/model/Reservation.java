package edu.eci.cvds.ECIReserves.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reservations")
public class Reservation {
    @Id
    private String id;
    @DBRef
    private User user;
    @DBRef
    private Laboratory laboratory;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String purpose;
    private String status;
}
