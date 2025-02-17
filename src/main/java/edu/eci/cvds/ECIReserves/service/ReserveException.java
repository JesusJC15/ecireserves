package edu.eci.cvds.ECIReserves.service;

public class ReserveException extends Exception{

    public String RESERVED_LABORATORY = "El laboratorio ya está reservado.";
    public String EMPTY_PURPOSE = "No agregó motivo de la reserva.";
    public String EMPTY_DATE = "No agregó fecha de la reserva.";
    public String EMPTY_HOUR = "No agregó hora de la reserva.";
    public String EMPTY_LABORATORY = "No agregó laboratorio.";
    public String USED_EMAIL = "Ese Email ya está en uso.";
    public String USED_ID = "Ese Id ya está en uso.";

    public ReserveException(String message){
        super(message);
    }
}
