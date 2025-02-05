package org.example;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase {@code PlayerDAO} se encarga de gestionar las operaciones CRUD (crear, leer, actualizar, eliminar)
 * para los jugadores en la base de datos.
 *
 * <p>
 * Esta clase utiliza una instancia de {@link DatabaseConection} para conectarse a la base de datos y ejecutar las consultas SQL.
 * </p>
 */
public class PlayerDAO {

    DatabaseConection conection = new DatabaseConection();

    public PlayerDAO() {
        // TODO Auto-generated constructor stub
    }


    /**
     * Crea un nuevo jugador en la base de datos.
     *
     * <p>
     * Este metodo inserta un nuevo registro en la tabla "Jugadores" con el nombre, posición y el ID del equipo del jugador.
     * </p>
     *
     * @param player El objeto {@link Player} que representa al jugador que se desea agregar.
     */
    public void createPlayer(Player player) {
        String consulta = "Insert into Jugadores Values ((SELECT ISNULL(max(id), 0 )+1 FROM Jugadores),?,?,?)";

        try(Connection con= conection.getConection();
            PreparedStatement pstmt = con.prepareStatement(consulta)){

            pstmt.setString(1, player.getName());
            pstmt.setString(2,player.getPos());
            pstmt.setInt(3,player.getIdTeam());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /**
     * Elimina un jugador de la base de datos.
     *
     * <p>
     * Este metodo elimina el jugador con el ID proporcionado de la tabla "Jugadores".
     * </p>
     *
     * @param playerId El ID del jugador que se desea eliminar.
     * @return {@code true} si el jugador fue eliminado correctamente, {@code false} en caso contrario.
     */
    public boolean deletePlayer(int playerId) {
        String sql = "DELETE FROM jugadores WHERE id = ?";

        try (Connection conn = conection.getConection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, playerId);
            int rowsDeleted = stmt.executeUpdate();

            return rowsDeleted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Busca jugadores según un campo específico y un valor de búsqueda.
     *
     * <p>
     * Este metodo permite buscar jugadores por un campo dado (como nombre, posicion, etc.) que coincidan parcialmente
     * con el valor de búsqueda proporcionado.
     * </p>
     *
     * @param f El campo por el cual se realiza la búsqueda (por ejemplo, "nombre").
     * @param n El valor de búsqueda que se busca en el campo indicado.
     * @return Una lista de objetos {@link Player} que coinciden con la búsqueda.
     */
    public List<Player> searchPlayer(String f,String n) {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM jugadores WHERE "+f+" LIKE ?";

        try (Connection conn = conection.getConection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + n + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int id = rs.getInt(1);
                String name = rs.getString(2);
                String position = rs.getString(3);
                int idTeam = rs.getInt(4);

                players.add(new Player(id, name, position, idTeam));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }

    /**
     * Obtiene una lista de jugadores pertenecientes a un equipo específico.
     *
     * <p>
     * Este metodo busca todos los jugadores cuyo ID de equipo coincida con el ID proporcionado.
     * </p>
     *
     * @param teamId El ID del equipo cuyos jugadores se desean obtener.
     * @return Una lista de objetos {@link Player} que pertenecen al equipo con el ID proporcionado.
     */
    public List<Player> getPlayersByTeam(int teamId) {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM jugadores WHERE equipo_id = ?";

        try (Connection conn = conection.getConection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, teamId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                int id = rs.getInt(1);
                String name = rs.getString(2);
                String position = rs.getString(3);
                int idTeam = rs.getInt(4);

                players.add(new Player(id, name, position, idTeam));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return players;
    }


    /**
     * Actualiza los detalles de un jugador en la base de datos.
     *
     * <p>
     * Este metodo actualiza el nombre, la posición y el ID del equipo de un jugador existente en la tabla "Jugadores".
     * </p>
     *
     * @param player El objeto {@link Player} con los nuevos datos del jugador.
     * @return {@code true} si el jugador fue actualizado correctamente, {@code false} en caso contrario.
     */

    public boolean updatePlayer(Player player) {
        String sql = "UPDATE jugadores SET nombre = ?, posicion = ?, equipo_id = ? WHERE id = ?";
        try (Connection conn = conection.getConection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, player.getName());
            stmt.setString(2, player.getPos());
            stmt.setInt(3, player.getIdTeam());
            stmt.setInt(4, player.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene una lista de todos los jugadores en la base de datos.
     *
     * <p>
     * Este metodo devuelve una lista de todos los jugadores registrados en la tabla "Jugadores".
     * </p>
     *
     * @return Una lista de objetos {@link Player} que representa a todos los jugadores en la base de datos.
     */
    public List<Player> PlayerList() {


        List<Player> listestudiantes = new ArrayList<Player>();
        String consulta = "SELECT * from Jugadores";

        try(Connection con = conection.getConection();
            PreparedStatement pstmt = con.prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery()){

            while (rs.next()) {

                listestudiantes.add(new Player(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4)));

            }

        }catch (SQLException e) {

            e.printStackTrace();
        }
        return listestudiantes;


    }



}
