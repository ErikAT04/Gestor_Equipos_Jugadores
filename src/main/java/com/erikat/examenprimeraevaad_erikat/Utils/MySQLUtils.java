package com.erikat.examenprimeraevaad_erikat.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MySQLUtils {
    public static Connection conectar(){
        Connection con = null;
        try {
            Properties mysqlConfig = new Properties();
            mysqlConfig.load(R.getDBConfig("MySQL.properties"));
            String host = mysqlConfig.getProperty("host");
            String port = mysqlConfig.getProperty("port");
            String dbname = mysqlConfig.getProperty("dbname");
            String user = mysqlConfig.getProperty("uname");
            String passwd = mysqlConfig.getProperty("passwd");

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://"+host+":"+port+"/"+dbname+"?serverTimezone=UTC", user, passwd);
        }catch (Exception e){
            System.out.println("Error " + e.getMessage());
        }
        return con;
    }
    public static void cerrarConexion(Connection con){
        try {
            con.close();
        }catch (SQLException e){
            System.out.println("Error " + e.getMessage());
        }
    }
}
