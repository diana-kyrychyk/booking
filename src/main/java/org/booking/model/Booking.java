package org.booking.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime dateStart;
    private LocalDateTime dateEnd;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne
    private Employee employee;

    public Booking(int id, LocalDateTime dateStart, LocalDateTime dateEnd, Room room, Employee employee) {
        this.id = id;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.room = room;
        this.employee = employee;
    }

    public Booking() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDateTime date_start) {
        this.dateStart = date_start;
    }

    public LocalDateTime getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDateTime end_start) {
        this.dateEnd = end_start;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
}
