package com.erikat.examenprimeraevaad_erikat.DAO;

import com.erikat.examenprimeraevaad_erikat.Model.Equipo;
import com.erikat.examenprimeraevaad_erikat.Model.Jugador;
import com.erikat.examenprimeraevaad_erikat.Utils.HibernateUtils;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class JugadorDAO implements DAO<Jugador, Integer>{
    Session session;
    public JugadorDAO(){
        session = HibernateUtils.getSession();
    }
    @Override
    public boolean insertar(Jugador obj) {
        boolean finalizadoCorrectamente = false;
        try {
            session.beginTransaction();
            session.save(obj);
            session.getTransaction().commit();
            finalizadoCorrectamente = true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Error de Hibernate: " + e.getMessage());
        }
        session.clear();
        return finalizadoCorrectamente;
    }

    @Override
    public boolean editar(Jugador obj) {
        boolean finalizadoCorrectamente = false;
        try {
            session.beginTransaction();
            session.update(obj);
            session.getTransaction().commit();
            finalizadoCorrectamente = true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Error de Hibernate: " + e.getMessage());
        }
        session.clear();
        return finalizadoCorrectamente;    }

    @Override
    public boolean eliminar(Jugador obj) {
        boolean finalizadoCorrectamente = false;
        try {
            session.beginTransaction();
            session.remove(obj);
            session.getTransaction().commit();
            finalizadoCorrectamente = true;
        }catch (Exception e){
            session.getTransaction().rollback();
            System.out.println("Error de Hibernate: " + e.getMessage());
        }
        session.clear();
        return finalizadoCorrectamente;    }

    @Override
    public Jugador buscar(Integer valor) {
        Jugador jugador = null;
        try {
            jugador = session.createQuery("from Jugador where idjugador = " + valor, Jugador.class).getSingleResult();
        }catch (Exception e){
            System.out.println("Error de Hibernate: " + e.getMessage());
        }
        session.clear();
        return jugador;
    }

    @Override
    public List<Jugador> listar() {
        List<Jugador> listaJugadores = new ArrayList<>();
        try {
            listaJugadores = session.createQuery("from Jugador", Jugador.class).getResultList();
        }catch (Exception e){
            System.out.println("Error de Hibernate: " + e.getMessage());
        }
        session.clear();
        return listaJugadores;
    }
}
