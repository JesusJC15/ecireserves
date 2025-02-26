package edu.eci.cvds.ECIReserves.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "laboratories")
public class Laboratory {
    @Id
    private String id;
    private String name;
    private int capacity;
    private Integer computers;
    private String description;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;
    private boolean available;

}
