package com.erikat.examenprimeraevaad_erikat.Utils;

import com.erikat.examenprimeraevaad_erikat.Model.Equipo;
import com.erikat.examenprimeraevaad_erikat.Model.Jugador;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

//Clase que gestiona las configuraciones de Hibernate
public class HibernateUtils {
    private static SessionFactory sessionFactory;
    static {
        Configuration configuration = new Configuration();
        configuration.configure(R.getHibernateConfig("hibernate.cfg.xml"));
        configuration.addAnnotatedClass(Equipo.class);
        configuration.addAnnotatedClass(Jugador.class);

        sessionFactory = configuration.buildSessionFactory();
    }
    public static Session getSession(){
        return sessionFactory.openSession();
    }
    public static void cerrarSesion(Session session){
        session.close();
    }
}
