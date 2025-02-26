package edu.eci.cvds.ecireserves.model;


import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "laboratories")
public class Laboratory {
    @Id
    private String id;

    private String classroom;
    private String name;
    private int capacity;
    private int computers;	
    private String description;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;
    private boolean available; 
}
