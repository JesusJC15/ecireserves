package edu.eci.cvds.ecireserves.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "laboratories")
public class Laboratory {
    @Id
    private String id;
    private String name;
    private int capacity;
}
