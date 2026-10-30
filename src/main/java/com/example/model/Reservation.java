package com.example.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservations")
public class Reservation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reservation")
    private Long idReservation;
    @Column(name = "nom_salle", nullable = false)
    private String nomSalle;
    @Column(name = "debut", nullable = false)
    private LocalDateTime debut;
    @Column(name = "fin", nullable = false)
    private LocalDateTime fin;
    @Version
    @Column(name = "version_numero", nullable = false)
    private int version;

    public Reservation() { }
    public Reservation(String nomSalle, LocalDateTime debut, LocalDateTime fin) {
        this.nomSalle = nomSalle;
        this.debut = debut;
        this.fin = fin;
    }
    public Long getIdReservation() { return idReservation; }
    public String getNomSalle() { return nomSalle; }
    public void setNomSalle(String nomSalle) { this.nomSalle = nomSalle; }
    public LocalDateTime getDebut() { return debut; }
    public void setDebut(LocalDateTime debut) { this.debut = debut; }
    public LocalDateTime getFin() { return fin; }
    public void setFin(LocalDateTime fin) { this.fin = fin; }
    public int getVersion() { return version; }
    @Override public String toString() {
        return "Reservation{id=" + idReservation + ", salle='" + nomSalle + "', debut=" + debut
                + ", fin=" + fin + ", version=" + version + "}";
    }
}
