package com.example.poolrest.Web;

import com.example.poolrest.Clients.Client;
import com.example.poolrest.Clients.ClientRepository;
import com.example.poolrest.Reservations.Reservation;
import com.example.poolrest.Reservations.ReservationRepository;
import com.example.poolrest.Reservations.ReservationStatus;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Configuration
public class ReservationAndClientConfig {
    @Bean(name = "loadReservationAndClient")
    CommandLineRunner loadReservation(ReservationRepository reservationRepository,
                                      ClientRepository clientRepository) {
        return (args) -> {

            var clientsList = List.of(
                    new Client(null, "Timur", "test@gmai.com", "+71111111111"),
                    new Client(null, "Ula", "test2@gmai.com", "+71111111112"),
                    new Client(null, "Ola", "test3@gmai.com", "+71111111113"),
                    new Client(null, "Peta", "test4@gmai.com", "+71111111114"),
                    new Client(null, "Gala", "test5@gmai.com", "+71111111115")
            );
            clientRepository.saveAll(clientsList);

            List<Client> allClient = clientRepository.findAll();
            if (allClient.isEmpty()) {
                return;
            }
            var reservationsList = List.of(
                    new Reservation(
                            null,
                            allClient.get(0),
                            LocalTime.of(11, 0),
                            LocalTime.of(11, 0),
                            ReservationStatus.APPROVED,
                            LocalDate.now()
                    ),
                    new Reservation(
                            null,
                            allClient.get(1),
                            LocalTime.of(11, 0),
                            LocalTime.of(11, 0),
                            ReservationStatus.APPROVED,
                            LocalDate.now()
                    ),new Reservation(
                            null,
                            allClient.get(1),
                            LocalTime.of(12, 0),
                            LocalTime.of(12, 0),
                            ReservationStatus.APPROVED,
                            LocalDate.now()
                    ),new Reservation(
                            null,
                            allClient.get(1),
                            LocalTime.of(13, 0),
                            LocalTime.of(13, 0),
                            ReservationStatus.APPROVED,
                            LocalDate.now()
                    ),
                    new Reservation(
                            null,
                            allClient.get(2),
                            LocalTime.of(14, 0),
                            LocalTime.of(14, 0),
                            ReservationStatus.APPROVED,
                            LocalDate.now()
                    ));
            reservationRepository.saveAll(reservationsList);


        };
    }
}
