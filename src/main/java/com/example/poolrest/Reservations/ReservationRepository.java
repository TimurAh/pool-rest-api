package com.example.poolrest.Reservations;

import com.example.poolrest.Clients.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    //заглушка
    @Query(value = """
            SELECT r from Reservation r
            """)
    List<Reservation> searchReservations(
            @Param("date") LocalDate date,
            @Param("time") LocalTime time
    );

    List<Reservation> findAllByDate(LocalDate date);
    @Query("""
    SELECT new com.example.poolrest.Reservations.ReservationSlotDto(r.startTime,COUNT(r)) from Reservation r
        where r.date =:date and r.status = :status
         group by r.startTime
             order by r.startTime
    """)
    List<ReservationSlotDto> getListApprovedReservationSlotDtoOnDate(@Param("date") LocalDate date,
                                                            @Param("status") ReservationStatus status);

    Optional<Reservation> getReservationsByClientAndDateAndStartTime(Client client, LocalDate date, LocalTime startTime);
}
