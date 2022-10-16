package com.tanupro.app.stayingza.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tanupro.app.stayingza.data.Guest;
import com.tanupro.app.stayingza.data.GuestRepository;
import com.tanupro.app.stayingza.data.Reservation;
import com.tanupro.app.stayingza.data.ReservationRepository;
import com.tanupro.app.stayingza.data.Room;
import com.tanupro.app.stayingza.data.RoomRepository;

@Service
public class ReservationService {

    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationsForDate(Date date) {
        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservationMap.put(room.getId(), roomReservation);
        });
        Iterable<Reservation> reservations = this.reservationRepository.findReservationByReservationDate(new java.sql.Date(date.getTime()));
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
            roomReservation.setDate(date);
            Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
            roomReservation.setGuestId(guest.getGuestId());
        });
        List<RoomReservation> roomReservations = new ArrayList<>();
        for (Long id : roomReservationMap.keySet()) {
            roomReservations.add(roomReservationMap.get(id));
        }
        roomReservations.sort((o1, o2) -> {
            if (o1.getRoomName().equals(o2.getRoomName())) {
                return o1.getRoomNumber().compareTo(o2.getRoomNumber());
            }
            return o1.getRoomName().compareTo(o2.getRoomName());
        });
        return roomReservations;
    }
    public List<Guest> getHotelGuests(){
        Iterable<Guest> guests = this.guestRepository.findAll();
        List<Guest> guestList = new ArrayList<>();
        guests.forEach(guest->{guestList.add(guest);});
        guestList.sort((o1, o2) -> {
            if (o1.getLastName().equals(o2.getLastName())){
                return o1.getFirstName().compareTo(o2.getFirstName());
            }
            return o1.getLastName().compareTo(o2.getLastName());
        });
        return guestList;
    }
    public void addGuest(Guest guest) {
        if (null == guest){
            throw new RuntimeException("Guest cannot be null");
        }
        this.guestRepository.save(guest);
    }

    public List<Room> getRooms() {
        Iterable<Room> rooms = this.roomRepository.findAll();
        List<Room> roomList = new ArrayList<>();
        rooms.forEach(room -> roomList.add(room));
        roomList.sort((o1, o2) -> o1.getRoomNumber().compareTo(o2.getRoomNumber()));
        return roomList;
    }

}

