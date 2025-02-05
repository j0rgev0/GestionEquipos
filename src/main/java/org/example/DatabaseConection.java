package org.example;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Esta clase gestiona la conexión a la base de datos SQL Server.
 * Se utiliza el controlador JDBC para establecer la conexión con una base de datos llamada 'futteam'.
 * La clase proporciona un metodo estático {@link #getConection()} para obtener una conexión a la base de datos.
 *
 * @author Jorge
 * @version 1.0
 * @since 2025-02-05
 */
public class DatabaseConection {

    /**
     * Cadena de conexión a la base de datos SQL Server.
     * Esta cadena incluye detalles como la dirección del servidor, el puerto, el nombre de la base de datos,
     * y las credenciales de usuario necesarias para la conexión.
     */
    private static final String conection = "jdbc:sqlserver://localhost:1433;"
            + "database=futteam;"
            + "encrypt=false;"
            + "user=sa;"
            + "password=1234";

    /**
     * Obtiene una conexión a la base de datos configurada.
     *
     * @return Una conexión {@link Connection} a la base de datos si la conexión es exitosa.
     *         En caso de error, devuelve {@code null}.
     */
    public static Connection getConection() {
        try {
            // Intenta obtener la conexión a la base de datos
            return DriverManager.getConnection(conection);
        } catch (SQLException ex) {
            // Si ocurre un error al conectar, imprime el mensaje de error
            System.out.println(ex.toString());
            return null;
        }
    }
}