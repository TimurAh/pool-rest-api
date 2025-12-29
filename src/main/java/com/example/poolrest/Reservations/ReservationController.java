package com.example.poolrest.Reservations;

import com.example.poolrest.Clients.AllClientDto;
import com.example.poolrest.Clients.ClientDto;
import jakarta.validation.Valid;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RequestMapping("/api/v0/pool/timetable")
@RestController
public class ReservationController {

    public static final Logger log = LoggerFactory.getLogger(ReservationController.class);
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }
    @GetMapping("/all")
    public ResponseEntity<List<ReservationSlotDto>> getAllReservationOnDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("called getAllReservationOnDate date = {}", date);
        return ResponseEntity.status(HttpStatus.OK)
                .body(reservationService.getApprovedReservationSlotOnDate(date));
    }
    @GetMapping("/get")
    public ResponseEntity<List<ReservationDto>> getReservationOnFilter(
            @RequestParam(value = "datetime", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate datetime,
            @RequestParam(value = "timeReservation", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime timeReservation
            ) {
        log.info("called getReservationOnFilter datetime = {} timeReservation = {}", datetime,timeReservation);
        return ResponseEntity.status(HttpStatus.OK)
                .body(reservationService.getFilterReservation(datetime,timeReservation));
    }

    @GetMapping("/available")
    public ResponseEntity<List<ReservationSlotDto>> getAllFreeReservationOnDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("called getAllFreeReservationOnDate date = {}", date);
        return ResponseEntity.status(HttpStatus.OK)
                .body(reservationService.getFreeReservationSlotOnDate(date));
    }
    @PostMapping("/reserve")
    public ResponseEntity<ReservationDto> createReservation(@RequestBody @Valid ReservationDto reservationDto) {
        log.info("called createReservation data = {}", reservationDto.toString());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservationService.createReservation(reservationDto));
    }
    @PostMapping("/cancel")
    public ResponseEntity<ReservationCancelDto> cancelReservation(@RequestBody @Valid ReservationCancelDto reservationCancelDto) {
        log.info("called cancelReservation data = {}", reservationCancelDto.toString());
        return ResponseEntity.status(HttpStatus.OK)
                .body(reservationService.cancelReservation(reservationCancelDto));
    }
}
