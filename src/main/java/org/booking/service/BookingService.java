package org.booking.service;

import org.booking.model.dto.BookingDTO;

import java.util.List;

public interface BookingService {

    List<BookingDTO> findAllBookings();

    BookingDTO create(BookingDTO bookingDTO);

    BookingDTO findById(Integer id);

    BookingDTO update(BookingDTO bookingDTO);

    void delete(Integer id);


}
