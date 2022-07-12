package org.booking.controller;

import org.booking.exception.InvalidEntityException;
import org.booking.model.dto.BookingDTO;
import org.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.booking.exception.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @GetMapping
    public List<BookingDTO> findAllBookings() {
        return bookingService.findAllBookings();
    }

    @GetMapping("/{id}")
    public BookingDTO findBookingById(@PathVariable(value = "id") Integer id) {
        return bookingService.findById(id);
    }

    @PostMapping
    public BookingDTO createBooking(@RequestBody BookingDTO booking) {
        return bookingService.create(booking);
    }

    @PutMapping
    public BookingDTO update(@RequestBody BookingDTO booking) {
        return bookingService.update(booking);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(value = "id") Integer id) {
        bookingService.delete(id);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException() {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<String> handleEntityExistsException(InvalidEntityException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
