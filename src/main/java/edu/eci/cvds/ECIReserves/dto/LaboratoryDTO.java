package edu.eci.cvds.ecireserves.dto;

import java.util.ArrayList;

import edu.eci.cvds.ecireserves.model.DaysOfWeek;
import edu.eci.cvds.ecireserves.model.TimeSlot;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaboratoryDTO {
    private String id;
    private String classroom;
    private String name;
    private int capacity;
    private String description;
    private DaysOfWeek day;
    private ArrayList<TimeSlot> timeSlots;
    private ArrayList<Boolean> availables;
}
