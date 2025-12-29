package com.example.poolrest.Schedule;

import com.example.poolrest.Clients.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;

public interface RegularDateScheduleRepository  extends JpaRepository<RegularDateSchedule, Long> {
    RegularDateSchedule findByDayOfWeek(DayOfWeek dayOfWeek);
}
