package com.example.poolrest.Schedule;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name = "regularDateSchedule")
public class RegularDateSchedule extends Schedule {
    @Id
    @SequenceGenerator(
            name = "regularDateSchedule_sequence",
            sequenceName = "regularDateSchedule_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "regularDateSchedule_sequence"
    )
    private Long id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayOfWeek dayOfWeek;


    public RegularDateSchedule(Long id, DayOfWeek dayOfWeek, LocalTime openTime, LocalTime closeTime, Long maxSlots, boolean isWork) {
        super(openTime, closeTime, maxSlots, isWork);
        this.id = id;
        this.dayOfWeek = dayOfWeek;
    }

    public RegularDateSchedule() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
}
