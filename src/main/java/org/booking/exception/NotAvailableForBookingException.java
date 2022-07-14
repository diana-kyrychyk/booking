package org.booking.exception;

public class NotAvailableForBookingException extends RuntimeException {

    public NotAvailableForBookingException(String message) {
        super(message);
    }
}
