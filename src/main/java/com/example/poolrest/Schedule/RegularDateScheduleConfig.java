package com.example.poolrest.Schedule;

import com.example.poolrest.Clients.Client;
import com.example.poolrest.Clients.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Configuration
public class RegularDateScheduleConfig {
    @Bean
    CommandLineRunner loadScheduleConfig(RegularDateScheduleRepository regularDateScheduleRepository) {
        return (args) -> {
             var scheduleList = List.of(
                    new RegularDateSchedule(null, DayOfWeek.MONDAY,
                            LocalTime.of(9,0), LocalTime.of(21,0),
                            10L,true),
                    new RegularDateSchedule(null, DayOfWeek.TUESDAY,
                            LocalTime.of(9,0), LocalTime.of(21,0),
                            10L,true),
                    new RegularDateSchedule(null, DayOfWeek.WEDNESDAY,
                            LocalTime.of(9,0), LocalTime.of(21,0),
                            10L,true),
                    new RegularDateSchedule(null, DayOfWeek.THURSDAY,
                            LocalTime.of(9,0), LocalTime.of(21,0),
                            2L,true),
                    new RegularDateSchedule(null, DayOfWeek.FRIDAY,
                            LocalTime.of(9,0), LocalTime.of(21,0),
                            10L,true),
                    new RegularDateSchedule(null, DayOfWeek.SATURDAY,
                            LocalTime.of(12,0), LocalTime.of(20,0),
                            10L,true),
                    new RegularDateSchedule(null, DayOfWeek.SUNDAY,
                            null, null,
                            0L,false)
            );
            regularDateScheduleRepository.saveAll(scheduleList);
        };
    }
}
