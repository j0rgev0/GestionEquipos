package org.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConection {

    private static final String conection = "jdbc:sqlserver://localhost:1433;"
            + "database=futteam;"
            + "encrypt=false;"
            + "user=sa;"
            + "password=1234";


    public static Connection getConection() {
        try {

            return DriverManager.getConnection(conection);

        }catch (SQLException ex) {
            // TODO: handle exception
            System.out.println(ex.toString());
            return null;
        }

    }



}
