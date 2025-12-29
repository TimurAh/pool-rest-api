package com.example.poolrest.Reservations;

import com.example.poolrest.Clients.Client;
import com.example.poolrest.Clients.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Component
public class ReservationMapper {
    private final ClientRepository clientRepository;

    @Autowired
    public ReservationMapper(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public ReservationDto toDomain(Reservation reservation){
     return new ReservationDto(
             reservation.getId(),
             reservation.getClient().getId(),
             reservation.getDate().atTime(reservation.getStartTime())
     );
 }
    public ReservationCancelDto toDomainCancel(Reservation reservation){
        return new ReservationCancelDto(
                reservation.getClient().getId(),
                reservation.getId()
        );
    }

 public Reservation toEntity(ReservationDto reservationDto){
     Client client = clientRepository.findById(reservationDto.clientId())
             .orElseThrow(() -> new IllegalArgumentException(
                     "Client with id " + reservationDto.clientId() + " not found"));
     return new Reservation(
             reservationDto.id(),
             client,
             reservationDto.datetime().toLocalTime()
                     .truncatedTo(java.time.temporal.ChronoUnit.HOURS),
             reservationDto.datetime().toLocalTime()
                     .truncatedTo(java.time.temporal.ChronoUnit.HOURS), // для улучшения в выборе несколько часов
             ReservationStatus.CANCELLED,
             reservationDto.datetime().toLocalDate()

     );
 }
    public Reservation toEntity(ReservationCancelDto reservationCancelDto){
        Client client = clientRepository.findById(reservationCancelDto.clientId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Client with id " + reservationCancelDto.clientId() + " not found"));

        return new Reservation(
                reservationCancelDto.orderId(),
                client,
                null,
                null, // для улучшения в выборе несколько часов
                null,
                null

        );
    }

 public List<ReservationSlotDto> toDomainSlotList(Map<LocalTime,Long> mapSlotOnDate){
     List<ReservationSlotDto> reservationSlotDtos =mapSlotOnDate.entrySet().stream()
             .sorted(Map.Entry.comparingByKey())  // сортировка по LocalTime (по возрастанию)
             .map(entry -> new ReservationSlotDto(
                     entry.getKey(),
                     entry.getValue()
             )).toList();
        return reservationSlotDtos;
 }
 public Map<LocalTime,Long> domainSlotListToMap(List<ReservationSlotDto> reservationSlotDtos){
     Map<LocalTime, Long> map = reservationSlotDtos.stream()
             .collect(Collectors.toMap(
                     ReservationSlotDto::time,
                     ReservationSlotDto::count,
                     (a, b) -> a,
                     TreeMap::new
             ));
     return map;
 }

}
