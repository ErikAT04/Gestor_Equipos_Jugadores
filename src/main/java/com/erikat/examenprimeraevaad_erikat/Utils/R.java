package com.erikat.examenprimeraevaad_erikat.Utils;

import java.io.InputStream;
import java.net.URL;

public class R {
    public static URL getHibernateConfig(String path){
        return Thread.currentThread().getContextClassLoader().getResource("config/"+path);
    }
    //HibernateConfig va por separado porque utiliza URL, mientras que las otras bases de datos utilizan InputStream para llegar a su fichero
    public static URL getUIResource(String path){
        return Thread.currentThread().getContextClassLoader().getResource("ui/"+path);
    }
    public static InputStream getDBConfig(String path){
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("config/"+path);
    }
}
