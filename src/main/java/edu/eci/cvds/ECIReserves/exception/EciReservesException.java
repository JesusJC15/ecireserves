package edu.eci.cvds.ecireserves.exception;

public class EciReservesException extends Exception {
    public static final String RESERVATION_ALREADY_EXISTS = "The reservation already exists";
    public static final String USER_ALREADY_EXISTS = "already exists";
    public static final String USER_NOT_FOUND = "not found";

    public EciReservesException(String message) {
        super(message);
    }

}
