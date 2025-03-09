package edu.eci.cvds.ecireserves.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "laboratories")
public class Laboratory {
    @Id
    private String id;

    private String classroom;
    private String name;
    private int capacity;
    private String description;
    private DaysOfWeek day;
    private List<TimeSlot> timeSlots;
    private List<Boolean> availables;
}
