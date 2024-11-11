package com.erikat.examenprimeraevaad_erikat.Model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "equipos")
public class Equipo implements Serializable {
    @Id
    @Column(name = "idEquipo")
    private int idEquipo;

    @Column(name = "nombreEquipo")
    private String nombreEquipo;

    @Column(name = "patrocinador")
    private String patrocinador;

    @Column(name = "categoria")
    private String categoria;

    @Column(name = "sancionado")
    private boolean sancionado;

//    @OneToMany(mappedBy = "equipo", cascade = CascadeType.ALL)
//    private List<Jugador> jugadores;


    public Equipo() {
    }

    public Equipo(int idEquipo, String nombreEquipo, String patrocinador, String categoria, boolean sancionado) {
        this.idEquipo = idEquipo;
        this.nombreEquipo = nombreEquipo;
        this.patrocinador = patrocinador;
        this.categoria = categoria;
        this.sancionado = sancionado;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombreEquipo() {
        return nombreEquipo;
    }

    public void setNombreEquipo(String nombreEquipo) {
        this.nombreEquipo = nombreEquipo;
    }

    public String getPatrocinador() {
        return patrocinador;
    }

    public void setPatrocinador(String patrocinador) {
        this.patrocinador = patrocinador;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isSancionado() {
        return sancionado;
    }

    public void setSancionado(boolean sancionado) {
        this.sancionado = sancionado;
    }

//    public List<Jugador> getJugadores() {
//        return jugadores;
//    }
//
//    public void setJugadores(List<Jugador> jugadores) {
//        this.jugadores = jugadores;
//    }
}
