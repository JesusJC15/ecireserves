package edu.eci.cvds.ecireserves.exception;

public class EciReservesException extends Exception {
    public static final String RESERVATION_ALREADY_EXISTS = "The reservation already exists";

    public EciReservesException(String message) {
        super(message);
    }

}
