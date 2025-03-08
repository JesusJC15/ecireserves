package edu.eci.cvds.ECIReserves.service;

import edu.eci.cvds.ECIReserves.model.Laboratory;
import edu.eci.cvds.ECIReserves.model.Reservation;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class ReservationService {

    /*
    * Método para crear una reserva, se comunica con Reservation del modelo
    * para crear la reserva con sus debidos atributos
    * @param res La reserva que se está creando.
    * return Boolean para indicar si se creo o no se creó
    * */
    public boolean createReservation(Reservation res){
        return false;
    }

    /*
    * Método para cancelar una reserva, al cancelar una reserva se debe validar que si exista una reserva previa
    * Además esta debe borrarse de donde esté almacenada
    * Por ultimo se debe enviar un mensaje confirmando que la reserva se canceló
    *
    * @param id el id de la reserva que se busca cancelar
    * return Boolean para saber si se canceló o no
    * */
    public boolean cancelReservation(String id){
        return false;
    }

    /*
    * Método para consultar las reservas de un usuario
    * @Param userID el ID del usuario
    * return ArrayLis<Reservation> una lista con las reservas del usuario*/
    public ArrayList<Reservation> getUserReservation(String userId){ return new ArrayList<Reservation>();
    }
}