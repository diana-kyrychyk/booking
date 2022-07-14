package org.booking.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.booking.controller.sort.SortType;
import org.booking.exception.EntityNotFoundException;
import org.booking.exception.InvalidEntityException;
import org.booking.exception.NotAvailableForBookingException;
import org.booking.model.dto.BookingDTO;
import org.booking.service.BookingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/bookings")
public class BookingController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingController.class);

    @Autowired
    BookingService bookingService;

    @Operation(summary = "Returns a list of bookings and sorted/filtered based on the query parameters")
    @ApiResponse(responseCode = "200", description = "Booking were selected",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = BookingDTO.class))})
    @GetMapping
    public Page<BookingDTO> findAllBookings(
            @RequestParam(required = false, name = "page",
                    defaultValue = "0") int page,
            @RequestParam(required = false, name = "size",
                    defaultValue = "20") int size,
            @RequestParam(required = false, name = "sortType",
                    defaultValue = "START_DATE") SortType sortType) {

        Pageable paging = specifyPageable(sortType, page, size);
        final Page<BookingDTO> bookingPage =
                bookingService.findAllBookings(paging);
        return bookingPage;
    }

    private Pageable specifyPageable(SortType sortType, int page, int size) {
        Sort sort = SortType.ROOM_ID.equals(sortType)
                ? Sort.by("room.id").descending()
                : Sort.by("dateStart").descending();

        return PageRequest.of(page, size, sort);
    }

    @Operation(summary = "Get a booking by the id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Booking found",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = BookingDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = @Content)})
    @GetMapping("/{id}")
    public BookingDTO findBookingById(@PathVariable(value = "id") Integer id) {
        return bookingService.findById(id);
    }

    @Operation(summary = "Create a new booking")
    @ApiResponse(responseCode = "201", description = "Booking is created",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = BookingDTO.class))})
    @PostMapping
    public BookingDTO createBooking(@Valid @RequestBody BookingDTO booking) {
        LOGGER.debug("Booking is starting to be created...");
        return bookingService.create(booking);
    }

    @Operation(summary = "Update a booking by the id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Booking was updated",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = BookingDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = @Content)})
    @PutMapping
    public BookingDTO update(@Valid @RequestBody BookingDTO booking) {
        return bookingService.update(booking);
    }

    @Operation(summary = "Delete a booking by the id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Booking was deleted",
            content = {@Content(mediaType = APPLICATION_JSON_VALUE, schema = @Schema(implementation = BookingDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Booking not found", content = @Content)})
    @DeleteMapping("/{id}")
    public void delete(@PathVariable(value = "id") Integer id) {
        bookingService.delete(id);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException() {
        LOGGER.debug("No entity found");
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(InvalidEntityException.class)
    public ResponseEntity<String> handleEntityExistsException(InvalidEntityException e) {
        LOGGER.debug("ID is not null - unable to create a new booking");
        return ResponseEntity.badRequest().body("Failure to create - ID should be null to create a booking");
    }

    @ExceptionHandler(NotAvailableForBookingException.class)
    public ResponseEntity<String> handleNotAvailableForBookingException(NotAvailableForBookingException e) {
        LOGGER.debug("Not available timeslot for booking");
        return ResponseEntity.badRequest().body("Not available for booking");
    }
}
