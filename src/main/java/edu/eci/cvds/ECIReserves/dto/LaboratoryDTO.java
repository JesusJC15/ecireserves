package edu.eci.cvds.ecireserves.dto;

import java.util.List;

import edu.eci.cvds.ecireserves.model.DaysOfWeek;
import edu.eci.cvds.ecireserves.model.TimeSlot;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LaboratoryDTO {
    private String id;
    private String classroom;
    private String name;
    private int capacity;
    private String description;
    private DaysOfWeek day;
    private List<TimeSlot> timeSlots;
    private List<Boolean> availables;
}
