package com.example.poolrest.Reservations;

import com.example.poolrest.Clients.Client;
import jakarta.persistence.*;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Table(name = "reservationsPool")
@Entity
@ToString(exclude = "client")
public class Reservation {
    @Id
    @SequenceGenerator(
            name = "reservations_sequence",
            sequenceName = "reservations_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reservations_sequence"
    )
    private Long id;


    // === ВНЕШНИЙ КЛЮЧ НА КЛИЕНТА ===
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "client_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_reservation_client")
    )
    private Client client ;
    @Column(name = "start_time",nullable = false)
    private LocalTime startTime;
    @Column(name = "end_date",nullable = false)
    private LocalTime endTime;
    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private ReservationStatus status;
    @Column(name = "reservation_date",nullable = false)
    private LocalDate date;
    //countHoursReservation?

    public Reservation() {
    }

    public Reservation(Long id, Client client, LocalTime startTime, LocalTime endTime, ReservationStatus status, LocalDate date) {
        this.id = id;
        this.client = client;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
