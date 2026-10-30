package com.example;

import com.example.model.Reservation;
import com.example.service.ReservationServiceImpl;
import java.time.LocalDateTime;

public class ConcurrentReservationWithRetry {
    public static void main(String[] args) {
        try (ReservationServiceImpl service = new ReservationServiceImpl()) {
            LocalDateTime debut = LocalDateTime.of(2026, 11, 12, 9, 0);
            Reservation initiale = service.creerReservation(new Reservation("Salle A", debut, debut.plusHours(2)));
            Reservation tx1 = service.trouverParId(initiale.getIdReservation());
            Reservation tx2 = service.trouverParId(initiale.getIdReservation());
            tx1.setNomSalle("Salle B");
            System.out.println("Transaction 1 : " + service.modifierReservation(tx1));
            tx2.setNomSalle("Salle C");
            Reservation resultat = new ReservationRetryHandler(service).modifierAvecRetry(tx2);
            System.out.println("Transaction 2 : modification reussie apres retry : " + resultat);
            System.out.println("Etat final : " + service.trouverParId(initiale.getIdReservation()));
        }
    }
}
