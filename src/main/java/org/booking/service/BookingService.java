package org.booking.service;

import org.booking.model.dto.BookingDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookingService {

    Page<BookingDTO> findAllBookings(Pageable pageable);

    BookingDTO create(BookingDTO bookingDTO);

    BookingDTO findById(Integer id);

    BookingDTO update(BookingDTO bookingDTO);

    void delete(Integer id);


}
