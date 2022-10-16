package com.tanupro.app.stayingza.util;

import java.util.Date;
import java.util.List;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.tanupro.app.stayingza.business.ReservationService;
import com.tanupro.app.stayingza.business.RoomReservation;
import com.tanupro.app.stayingza.data.Guest;
import com.tanupro.app.stayingza.data.GuestRepository;
import com.tanupro.app.stayingza.data.Reservation;
import com.tanupro.app.stayingza.data.ReservationRepository;
import com.tanupro.app.stayingza.data.Room;
import com.tanupro.app.stayingza.data.RoomRepository;

@Component
public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;
    private final ReservationService reservationService;
    private final DateUtils dateUtils;

    public AppStartupEvent(RoomRepository roomRepository,GuestRepository guestRepository,ReservationRepository reservationRepository,ReservationService reservationService,DateUtils dateUtils) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
        this.reservationService = reservationService;
        this.dateUtils = dateUtils;

    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        Iterable<Room> rooms = this.roomRepository.findAll();
        rooms.forEach(System.out::println);
        Iterable<Guest> guests = this.guestRepository.findAll();
        guests.forEach(System.out::println);
        Iterable<Reservation> reservations = this.reservationRepository.findAll();
        reservations.forEach(System.out::println);
        Date date = this.dateUtils.createDateFromDateString("2022-10-16");
        System.out.println("-----------------------------");
        List<RoomReservation> roomreservations = this.reservationService.getRoomReservationsForDate(date);
        roomreservations.forEach(System.out::println);
    }
}
