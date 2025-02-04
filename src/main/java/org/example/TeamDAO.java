package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamDAO {
    DatabaseConection conection = new DatabaseConection();


    public TeamDAO(){

    }



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
