package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase responsable de realizar las operaciones CRUD (crear, leer, actualizar y eliminar)
 * para los objetos de tipo {@link Team} en la base de datos.
 * Proporciona métodos para insertar, eliminar, actualizar y buscar equipos en la base de datos.
 *
 * @author Jorge
 * @version 1.0
 * @since 2025-02-05
 */

public class TeamDAO {
    DatabaseConection conection = new DatabaseConection();

    /**
     * Constructor de la clase {@link TeamDAO}.
     * Inicializa la conexión con la base de datos.
     */
    public TeamDAO(){

    }

    /**
     * Crea un nuevo equipo en la base de datos.
     *
     * @param team El equipo que se va a crear.
     */
    public void createTeam(Team team) {
        String consulta = "Insert into Equipos Values ((SELECT ISNULL(max(id), 0 )+1 FROM Equipos),?,?,?)";

        try(Connection con= conection.getConection();
            PreparedStatement pstmt = con.prepareStatement(consulta)){

            pstmt.setString(1, team.getName());
            pstmt.setString(2,team.getCity());
            pstmt.setString(3,team.getStadium());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    /**
     * Elimina un equipo de la base de datos.
     *
     * @param teamId El identificador del equipo a eliminar.
     * @return true si el equipo fue eliminado con éxito, false si ocurrió un error.
     */
    public boolean deleteTeam(int teamId) {
        String sql = "DELETE FROM equipos WHERE id = ?";

        try (Connection conn = conection.getConection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, teamId);
            int rowsDeleted = stmt.executeUpdate();

            return rowsDeleted > 0; // Devuelve true si se eliminó al menos una fila
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Actualiza los detalles de un equipo en la base de datos.
     *
     * @param team El equipo con los nuevos datos.
     * @return true si el equipo fue actualizado con éxito, false si ocurrió un error.
     */
    public boolean updateTeam(Team team) {

        String sql = "UPDATE equipos SET nombre = ?, ciudad = ?, estadio = ? WHERE id = ?";
        try (Connection conn = conection.getConection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, team.getName());
            stmt.setString(2, team.getCity());
            stmt.setString(3, team.getStadium());
            stmt.setInt(4, team.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Busca equipos en la base de datos según un filtro y un valor.
     *
     * @param f El campo por el que se realiza la búsqueda (por ejemplo, "nombre", "ciudad").
     * @param n El valor que se buscará en el campo especificado.
     * @return Una lista de equipos que coinciden con el filtro y el valor proporcionado.
     */
    public List<Team> searchPlayer(String f,String n) {
        List<Team> listTeams = new ArrayList<>();
        String sql = "SELECT * FROM equipos WHERE "+f+" LIKE ?";

        try (Connection conn = conection.getConection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + n + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                listTeams.add(new Team(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listTeams;
    }

    /**
     * Obtiene la lista de todos los equipos almacenados en la base de datos.
     *
     * @return Una lista de todos los equipos.
     */
    public List<Team> TeamList() {


        List<Team> listTeams = new ArrayList<Team>();
        String consulta = "SELECT * from Equipos";

        try(Connection con = conection.getConection();
            PreparedStatement pstmt = con.prepareStatement(consulta);
            ResultSet rs = pstmt.executeQuery()){

            while (rs.next()) {

                listTeams.add(new Team(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));

            }

        }catch (SQLException e) {

            e.printStackTrace();
        }
        return listTeams;


    }

}
