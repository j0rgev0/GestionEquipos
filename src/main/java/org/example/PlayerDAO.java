package org.example;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerDAO {

    DatabaseConection conection = new DatabaseConection();

    public PlayerDAO() {
        // TODO Auto-generated constructor stub
    }




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
