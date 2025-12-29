

package com.example.poolrest.Schedule;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "holidays")
public class Holidays extends Schedule{
    @Id
    @SequenceGenerator(
            name = "holidays_sequence",
            sequenceName = "holidays_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "holidays_sequence"
    )
    private Long id;

    private String holidayTitle;

    private LocalDate dateHolidays;

    public Holidays() {
    }

    public Holidays(LocalTime openTime, LocalTime closeTime, Long maxSlots, boolean isWork, Long id, String holidayTitle, LocalDate dateHolidays) {
        super(openTime, closeTime, maxSlots, isWork);
        this.id = id;
        this.holidayTitle = holidayTitle;
        this.dateHolidays = dateHolidays;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHolidayTitle() {
        return holidayTitle;
    }

    public void setHolidayTitle(String holidayTitle) {
        this.holidayTitle = holidayTitle;
    }

    public LocalDate getDateHolidays() {
        return dateHolidays;
    }

    public void setDateHolidays(LocalDate dateHolidays) {
        this.dateHolidays = dateHolidays;
    }



}
