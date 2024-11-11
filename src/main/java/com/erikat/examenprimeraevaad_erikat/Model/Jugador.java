package com.erikat.examenprimeraevaad_erikat.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "jugadores")
public class Jugador implements Serializable {
    @Id
    @Column(name = "idjugador")
    private int idJugador;

    @Column(name = "aliasJugador")
    private String aliasJugador;

    @Column(name = "fechaNacimiento")
    private LocalDate fechaNacimiento;

    @ManyToOne
    @JoinColumn(name = "id_equipo", referencedColumnName = "idEquipo")
    private Equipo equipo;

    public Jugador() {
    }

    public Jugador(int idJugador, String aliasJugador, LocalDate fechaNacimiento, Equipo equipo) {
        this.idJugador = idJugador;
        this.aliasJugador = aliasJugador;
        this.fechaNacimiento = fechaNacimiento;
        this.equipo = equipo;
    }

    public Jugador(String aliasJugador, LocalDate fechaNacimiento, Equipo equipo) {
        this.aliasJugador = aliasJugador;
        this.fechaNacimiento = fechaNacimiento;
        this.equipo = equipo;
    }

    public int getIdJugador() {
        return idJugador;
    }

    public void setIdJugador(int idJugador) {
        this.idJugador = idJugador;
    }

    public String getAliasJugador() {
        return aliasJugador;
    }

    public void setAliasJugador(String aliasJugador) {
        this.aliasJugador = aliasJugador;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Equipo getEquipo() {
        return equipo;
    }

    public void setEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    @Override
    public String toString() {
        return aliasJugador + " - " + fechaNacimiento.toString();
    }
}
