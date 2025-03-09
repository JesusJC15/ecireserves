package edu.eci.cvds.ecireserves.model;

import java.time.LocalTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.eci.cvds.ecireserves.enums.DaysOfWeek;
import edu.eci.cvds.ecireserves.exception.EciReservesException;
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
    private LocalTime openingTime;
    private LocalTime closingTime;
    private List<TimeSlot> timeSlots;
    private List<Boolean> availables;

    /**
     * Add a time slot to the laboratory and set it as available
     * @param startTime
     * @param endTime
     * @throws EciReservesException 
    */
    public void setTimeSlot(LocalTime startTime, LocalTime endTime) throws EciReservesException {
        if(startTime.isBefore(openingTime) || endTime.isAfter(closingTime)) {
            throw new EciReservesException(EciReservesException.INVALID_TIMESLOT);
        }
        timeSlots.add(new TimeSlot(startTime, endTime));
        availables.add(false);
    }

    /**
     * Remove a time slot from the laboratory and set it as unavailable
     * @param startTime
     * @param endTime
     */
    public void removeTimeSlot(LocalTime startTime, LocalTime endTime) {
        for (int i = 0; i < timeSlots.size(); i++) {
            if (timeSlots.get(i).getStartTime().equals(startTime) && timeSlots.get(i).getEndTime().equals(endTime)) {
                timeSlots.remove(i);
                availables.remove(i);
                break;
            }
        }
    }
}