package com.example;

import com.example.model.Reservation;
import com.example.service.ReservationService;
import javax.persistence.OptimisticLockException;
import java.time.LocalDateTime;
import java.util.Objects;

public class ReservationRetryHandler {
    private final ReservationService service;
    private final int maxTentatives;
    public ReservationRetryHandler(ReservationService service) { this(service, 3); }
    public ReservationRetryHandler(ReservationService service, int maxTentatives) {
        this.service = Objects.requireNonNull(service);
        if (maxTentatives < 1) throw new IllegalArgumentException("Au moins une tentative");
        this.maxTentatives = maxTentatives;
    }
    public Reservation modifierAvecRetry(Reservation reservation) {
        Objects.requireNonNull(reservation);
        String nomSalle = reservation.getNomSalle();
        LocalDateTime debut = reservation.getDebut();
        LocalDateTime fin = reservation.getFin();
        for (int tentative = 1; tentative <= maxTentatives; tentative++) {
            try { return service.modifierReservation(reservation); }
            catch (OptimisticLockException conflit) {
                if (tentative == maxTentatives)
                    throw new OptimisticLockException("Echec apres " + maxTentatives + " tentatives", conflit);
                System.out.println("Conflit detecte : rechargement avant la tentative " + (tentative + 1));
                reservation = service.trouverParId(reservation.getIdReservation());
                if (reservation == null) throw new IllegalArgumentException("Reservation supprimee", conflit);
                // Politique du TP : reappliquer les trois valeurs demandees sur la derniere version.
                reservation.setNomSalle(nomSalle);
                reservation.setDebut(debut);
                reservation.setFin(fin);
            }
        }
        throw new IllegalStateException("Aucune tentative executee");
    }
}
