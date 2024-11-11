package com.erikat.examenprimeraevaad_erikat.DAO;

import com.erikat.examenprimeraevaad_erikat.Model.Equipo;
import com.erikat.examenprimeraevaad_erikat.Model.Jugador;
import com.erikat.examenprimeraevaad_erikat.Utils.HibernateUtils;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

public class EquipoDAOHibernate implements DAO<Equipo, Integer> {
    Session mySession;
    public EquipoDAOHibernate(){
        mySession = HibernateUtils.getSession();
    }
    @Override
    public boolean insertar(Equipo obj) {
        boolean finalizadoCorrectamente = false;
        try {
            mySession.beginTransaction();
            mySession.save(obj);
            mySession.getTransaction().commit();
            finalizadoCorrectamente = true;
        }catch (Exception e){
            mySession.getTransaction().rollback();
            System.out.println("Error de Hibernate: " + e.getMessage());
        }
        mySession.clear();
        return finalizadoCorrectamente;
    }

    @Override
    public boolean editar(Equipo obj) {
        boolean finalizadoCorrectamente = false;
        try {
            mySession.beginTransaction();
            mySession.update(obj);
            mySession.getTransaction().commit();
            finalizadoCorrectamente = true;
        }catch (Exception e){
            mySession.getTransaction().rollback();
            System.out.println("Error de Hibernate: " + e.getMessage());
        }
        mySession.clear();
        return finalizadoCorrectamente;
    }

    @Override
    public boolean eliminar(Equipo obj) {
        boolean finalizadoCorrectamente = false;
        try {
            mySession.beginTransaction();
            mySession.remove(obj);
            for (Jugador j : new JugadorDAO().listar()){
                if (j.getEquipo().getIdEquipo()==obj.getIdEquipo()){
                    new JugadorDAO().eliminar(j);
                }
            }
            mySession.getTransaction().commit();
            finalizadoCorrectamente = true;
        }catch (Exception e){
            mySession.getTransaction().rollback();
            System.out.println("Error de Hibernate: " + e.getMessage());
        }
        mySession.clear();
        return finalizadoCorrectamente;
    }

    @Override
    public Equipo buscar(Integer valor) {
        Equipo equipo = null;
        try{
            equipo = mySession.createQuery("from Equipo where idEquipo = " + valor, Equipo.class).getSingleResult();
        }catch (Exception e){
            System.out.println("Error de Hibernate: " + e.getMessage());
        }
        mySession.clear();
        return equipo;
    }

    @Override
    public List<Equipo> listar() {
        List<Equipo> equipos = new ArrayList<>();
        try{
            equipos = mySession.createQuery("from Equipo", Equipo.class).getResultList();
        }catch (Exception e){
            System.out.println("Error de Hibernate: " + e.getMessage());
        }
        mySession.clear();
        return equipos;
    }

    //Ante la necesidad de buscar un objeto con un criterio a mayores, he creado esto.
    public Equipo buscarPorNombre(String nombre){
        Equipo equipo = null;
        try{
            equipo = mySession.createQuery("from Equipo where nombreEquipo = '" + nombre + "'", Equipo.class).getSingleResult();
        }catch (Exception e){
            System.out.println("Error de Hibernate: " + e.getMessage());
        }
        mySession.clear();
        return equipo;
    }
}
