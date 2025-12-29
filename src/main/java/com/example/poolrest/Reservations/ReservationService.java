package com.example.poolrest.Reservations;


import com.example.poolrest.Clients.AllClientDto;
import com.example.poolrest.Clients.ClientDto;
import com.example.poolrest.Schedule.Holidays;
import com.example.poolrest.Schedule.RegularDateSchedule;
import com.example.poolrest.Schedule.RegularDateScheduleRepository;
import com.example.poolrest.Schedule.Schedule;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


@Service
public class ReservationService {
    public static final Logger log = LoggerFactory.getLogger(ReservationService.class);


    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;
    private final RegularDateScheduleRepository regularDateScheduleRepository;

    public ReservationService(ReservationRepository reservationRepository, ReservationMapper reservationMapper, RegularDateScheduleRepository regularDateScheduleRepository) {
        this.reservationRepository = reservationRepository;
        this.reservationMapper = reservationMapper;
        this.regularDateScheduleRepository = regularDateScheduleRepository;
    }


    public List<ReservationSlotDto> getApprovedReservationSlotOnDate(LocalDate localDate) {
        //вынести логику в другую сущность таблицы? /timetable?
        log.info("Called getApprovedReservationSlotOnDate on {}", localDate);
        List<ReservationSlotDto> approveListOnDate = reservationRepository.getListApprovedReservationSlotDtoOnDate(
                localDate,
                ReservationStatus.APPROVED
        );
        return approveListOnDate;
    }
    //дописать метод
    public List<ReservationSlotDto> getFreeReservationSlotOnDate(LocalDate localDate) {
        //вынести логику в другую сущность таблицы? /timetable?
        log.info("Called getFreeReservationSlotOnDate on {}", localDate);

        return reservationMapper.toDomainSlotList(getMapAvailableReservation(localDate));
    }

    public ReservationDto createReservation(ReservationDto reservationDto) {
        log.info("Called createReservation with reservationDto {}", reservationDto.toString());
        Reservation reservation = reservationMapper.toEntity(reservationDto);
        var availableMap = getMapAvailableReservation(reservation.getDate());
        var slotInfo = regularDateScheduleRepository.findByDayOfWeek(reservation.getDate().getDayOfWeek());
        //выбор между Расписанию по празднику или регулярному
        Schedule scheduleInfo = checkHoliday(reservation);
        //проверка на рабочий день
        log.debug("scheduleInfo work = " + scheduleInfo.isWork() + " ");
        if(!scheduleInfo.isWork()){
            throw new IllegalArgumentException("Pool closed on day = " + reservation.getDate());
        }

        //проверка на рабочее время
        if(reservation.getStartTime().isBefore(scheduleInfo.getOpenTime())
                || reservation.getStartTime().isAfter(scheduleInfo.getCloseTime())){
            throw new IllegalArgumentException("Poll will be closed in " + reservation.getStartTime());
        }
        //проверка на существование брони
        log.debug("search exist reservation= " + reservationRepository.getReservationsByClientAndDateAndStartTime(
                reservation.getClient(),
                reservation.getDate(),
                reservation.getStartTime()).toString());
        if(
                !reservationRepository.getReservationsByClientAndDateAndStartTime(
                reservation.getClient(),
                reservation.getDate(),
                reservation.getStartTime()).isEmpty()
        ){
            throw new IllegalArgumentException("Reservation already exist");
        }
        // проверка на коллизию
        if(availableMap.get(reservation.getStartTime())<=0){
            throw new IllegalArgumentException("Reserved on %s closed".formatted(reservation.getStartTime()));
        }
        reservation.setStatus(ReservationStatus.APPROVED);
        reservationRepository.save(reservation);
        return reservationMapper.toDomain(reservation);//спапить в "orderId": string Идентификатор засиси в будущем....
    }
    private Map<LocalTime,Long> getMapAvailableReservation(LocalDate localDate){
        var approveMapOnDate = reservationMapper.domainSlotListToMap(reservationRepository.getListApprovedReservationSlotDtoOnDate(
                localDate,
                ReservationStatus.APPROVED
        ));
        var slotInfo = regularDateScheduleRepository.findByDayOfWeek(localDate.getDayOfWeek());
        Map<LocalTime,Long> freeMapOnDate =  new HashMap<>();
        LocalTime currentTime = slotInfo.getOpenTime();
        LocalTime closeTime = slotInfo.getCloseTime();
        Long maxSlot = slotInfo.getMaxSlots();
        while (!currentTime.isAfter(closeTime)){
            log.debug("i currentTime = {}", currentTime);
            freeMapOnDate.put(currentTime,maxSlot - approveMapOnDate.getOrDefault(currentTime, 0L));
            currentTime = currentTime.plusHours(1);
        }
        return freeMapOnDate;
    }
    private Schedule checkHoliday(Reservation reservation){
        log.info("Called checkHoliday with reservation = " + reservation.toString());
        RegularDateSchedule slotInfo = regularDateScheduleRepository.findByDayOfWeek(reservation.getDate().getDayOfWeek());
        log.debug("slotInfo = " + slotInfo.toString());
        Holidays holidays = null;//поиск празничнего дня в будущем....
        if (holidays == null){
            log.debug("holidays =null return slotInfo");//если нет праздника - по обычному
            return slotInfo;
        }
        if(!slotInfo.isWork()){ //если обычный день - не рабочий не работаем.( мб надо изменить для гибкости)
            return slotInfo;
        }
        if(!holidays.isWork()){//если в праздник не рабочий - по празднику
            return holidays;
        }
        if(slotInfo.getOpenTime().isBefore(holidays.getOpenTime())){ // если праздник начинается позже - праздник
            return holidays;
        }
        if(slotInfo.getCloseTime().isAfter(holidays.getCloseTime())){// если обычный заканчивается позже - праздник
            return holidays;
        }

        return slotInfo;
    }

    public ReservationCancelDto cancelReservation(@Valid ReservationCancelDto reservationCancelDto) {
        log.info("Called cancelReservation with ReservationCancelDto {}", reservationCancelDto.toString());
        Reservation reservation = reservationMapper.toEntity(reservationCancelDto);
        log.debug("Маппинг выполнен");
        var searchReservation = reservationRepository.findById(reservation.getId())
                .orElseThrow(() -> new NoSuchElementException("Not found reservation by id = %s".formatted(reservation.getId())));
        log.debug("Сущность найдена ");
        //тут возможно логический ошибка
        if(!searchReservation.getClient().equals(reservation.getClient())){
            throw new IllegalArgumentException("Reservation for another person");
        }
        log.debug("Клиент нужный");
        if(searchReservation.getStatus().equals(ReservationStatus.CANCELLED)){
            throw new IllegalArgumentException("Reservation already cancelled");
        }
        log.debug("Бронь еще не отменена");
        searchReservation.setStatus(ReservationStatus.CANCELLED);
        log.debug("Сущность для обновления: " + reservation.toString());
        reservationRepository.save(searchReservation);
        return reservationMapper.toDomainCancel(searchReservation);//спапить в "orderId": string Идентификатор засиси в будущем....
    }

    public List<ReservationDto> getFilterReservation(LocalDate datetime, LocalTime timeReservation) {
        var seachList = reservationRepository.searchReservations(datetime,timeReservation);
        return seachList.stream()
                .map( reservationMapper::toDomain
                ).toList();
    }
}
