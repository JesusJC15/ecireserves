package edu.eci.cvds.ecireserves.exception;

public class EciReservesException extends Exception {
    public static final String USER_ALREADY_EXISTS = "El usuario ya existe";
    public static final String USER_NOT_FOUND = "El usuario no existe";
    public static final String USER_EMAIL_ALREADY_EXISTS = "El correo ya existe";
    public static final String RESERVATION_ALREADY_EXISTS = "La reserva ya existe";
    public static final String RESERVATION_NOT_FOUND = "La reserva no existe";
    public static final String LABORATORY_ALREADY_RESERVED = "El laboratorio ya esta reservado";
    public static final String LABORATORY_NOT_FOUND = "El laboratorio no existe";
    public static final String LABORATORY_ALREADY_EXISTS = "El laboratorio ya existe";
    public static final String LABORATORY_NOT_AVAILABLE = "El laboratorio no esta disponible";
    public static final String INVALID_TIMESLOT = "La franja horaria no es valida";

    public EciReservesException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return getMessage();
    }
}