package edu.eci.cvds.ecireserves.model;


import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "laboratories")
public class Laboratory {
    @Id
    private String id;

    private String classroom;
    private String name;
    private int capacity;
    private int computers;	
    private String description;
    private LocalDate openingDate;
    private LocalDate closingDate;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private boolean available; 
}
