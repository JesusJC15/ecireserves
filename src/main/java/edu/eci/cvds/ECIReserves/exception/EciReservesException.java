package edu.eci.cvds.ecireserves.exception;

public class EciReservesException extends Exception {
    public static final String USER_ALREADY_EXISTS = "User already exists";
    public static final String USER_NOT_FOUND = "User not found";
    public static final String USER_EMAIL_ALREADY_EXISTS = "User email already exists";
    public static final String RESERVATION_ALREADY_EXISTS = "The reservation already exists";
    public static final String RESERVATION_NOT_FOUND = "Reservation not found";
    public static final String LABORATORY_ALREADY_RESERVED = "The laboratory is already reserved";
    public static final String LABORATORY_NOT_FOUND = "Laboratory not found";
    public static final String LABORATORY_ALREADY_EXISTS = "Laboratory already exists";
    public static final String LABORATORY_NOT_AVAILABLE = "Laboratory not available";
    

    public EciReservesException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}
