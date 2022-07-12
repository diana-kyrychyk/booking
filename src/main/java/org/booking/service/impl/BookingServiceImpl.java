package org.booking.service.impl;

import org.booking.exception.InvalidEntityException;
import org.booking.model.Booking;
import org.booking.model.Employee;
import org.booking.model.Room;
import org.booking.model.dto.BookingDTO;
import org.booking.repository.BookingRepository;
import org.booking.repository.EmployeeRepository;
import org.booking.repository.RoomRepository;
import org.booking.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import org.booking.exception.EntityNotFoundException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingServiceImpl implements BookingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingServiceImpl.class);

    private BookingRepository bookingRepository;

    private RoomRepository roomRepository;

    private EmployeeRepository employeeRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, RoomRepository roomRepository, EmployeeRepository employeeRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<BookingDTO> findAllBookings() {
        LOGGER.debug("findAllDto()");
        List<Booking> bookings = bookingRepository.findAll();
        return bookings.stream()
                .map(booking -> toBookingDto(booking))
                .collect(Collectors.toList());
    }

    @Override
    public BookingDTO create(BookingDTO bookingDto) {
        if (bookingDto.getId() != null) {
            throw new InvalidEntityException(String.format(
                    "Failure to create as id >%s< was passed. ID should be null to create a booking", bookingDto.getId()));
        }

        Booking booking = toBookingEntity(bookingDto);
        if (!isAvailable(booking)) {
            throw new InvalidEntityException("Not available for booking");
        }
        Booking bookingSaved = bookingRepository.save(booking);
        return toBookingDto(bookingSaved);
    }

    @Override
    public BookingDTO update(BookingDTO bookingDto) {
        if (bookingDto.getId() == null) {
            throw new InvalidEntityException("ID should be provided to update a booking");
        }
        Booking booking = toBookingEntity(bookingDto);
        if (!isAvailable(booking)) {
            throw new InvalidEntityException("Not available for booking");
        }
        Booking updatedBooking = bookingRepository.save(booking);
        return toBookingDto(updatedBooking);
    }

    @Override
    public void delete(Integer id) {
        try {
            bookingRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException();
        }
    }

    private boolean isAvailable(Booking booking) {
        List<Booking> notAvailableBookings =
                bookingRepository.findByRoomIdAndDateStartAndDateEnd(booking.getRoom().getId(), booking.getDateStart(), booking.getDateEnd());
        return notAvailableBookings.isEmpty();
    }

    @Override
    public BookingDTO findById(Integer id) {
        Booking booking = findEntityById(id);
        return toBookingDto(booking);
    }

    private Booking findEntityById(Integer id) {
        Optional<Booking> bookingOpt = bookingRepository.findById(id);
        return bookingOpt.orElseThrow(EntityNotFoundException::new);
    }

    private BookingDTO toBookingDto(Booking booking) {
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setDateStart(booking.getDateStart());
        bookingDTO.setDateEnd(booking.getDateEnd());
        bookingDTO.setEmployeeId(booking.getEmployee() != null ? booking.getEmployee().getId() : null);
        bookingDTO.setRoomId(booking.getRoom() != null ? booking.getRoom().getId() : null);
        return bookingDTO;
    }

    private Booking toBookingEntity(BookingDTO bookingDTO) {
        //TODO Room Repository and Employee Repository

        Optional<Room> roomOptional = roomRepository.findById(bookingDTO.getRoomId());
        Optional<Employee> employeeOptional = employeeRepository.findById(bookingDTO.getEmployeeId());

        Room room = roomOptional.orElseThrow(EntityNotFoundException::new);
        Employee employee = employeeOptional.orElseThrow(EntityNotFoundException::new);

        //якщо айдішніка немає то
        Booking booking = new Booking();
        if (bookingDTO.getId() != null) {
            booking = findEntityById(bookingDTO.getId());
        }
        booking.setDateStart(bookingDTO.getDateStart());
        booking.setDateEnd(bookingDTO.getDateEnd());
        booking.setRoom(room);
        booking.setEmployee(employee);
        return booking;
    }
}
