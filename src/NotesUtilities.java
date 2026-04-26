import java.sql.*;

public class NotesUtilities {

    public static void createNote(int userId, String content){
        try(Connection conn = DatabaseConnection.connect()){

            String sqlInsert = "INSERT INTO notes (user_id, content) VALUES (?, ?)";
            PreparedStatement statement = conn.prepareStatement(sqlInsert);

            statement.setInt(1, userId);
            statement.setString(2, content);

            statement.executeUpdate();

            System.out.println("Note skapad.");
        } catch (Exception e){
            System.out.println("Error! Kunde ej spara note.");
        }
    }

    public static void showNotes(int userId){
        try(Connection conn = DatabaseConnection.connect()){

            String sqlSelect = "SELECT * FROM notes WHERE user_id = ?";
            PreparedStatement statement = conn.prepareStatement(sqlSelect);

            statement.setInt(1,userId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                System.out.println("Note: " + rs.getString("content"));
            }

        }catch (Exception e){
            System.out.println("Error! Kunde ej hämta notes.");
        }
    }

    public static void deleteNote(int noteId, int userId) {
        try (Connection conn = DatabaseConnection.connect()) {

            String sqlDelete = "DELETE FROM notes WHERE id = ? AND user_id = ?";
            PreparedStatement statement = conn.prepareStatement(sqlDelete);

            statement.setInt(1, noteId);
            statement.setInt(2, userId);

            statement.executeUpdate();

            System.out.println("Note raderad!");

        } catch (Exception e) {
            System.out.println("Kunde inte radera note.");
        }
    }

    public static void updateNote(int noteId, int userId, String newContent) {
        try (Connection conn = DatabaseConnection.connect()) {

            String sqlUpdate = "UPDATE notes SET content = ? WHERE id = ? AND user_id = ?";
            PreparedStatement statement = conn.prepareStatement(sqlUpdate);

            statement.setString(1, newContent);
            statement.setInt(2, noteId);
            statement.setInt(3, userId);

            statement.executeUpdate();

            System.out.println("Note uppdaterad!");

        } catch (Exception e) {
            System.out.println("Kunde inte uppdatera note.");
        }
    }

    public static void showAllNotes() {
        try (Connection conn = DatabaseConnection.connect()) {

            String sqlSelect = "SELECT users.username, notes.content FROM notes JOIN users ON notes.user_id = users.id";
            PreparedStatement statement = conn.prepareStatement(sqlSelect);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                System.out.println(rs.getString("username") + ": " + rs.getString("content"));
            }

        } catch (Exception e) {
            System.out.println("Kunde inte visa notes.");
        }
    }

    public static void adminDeleteNote(int noteId) {
        try (Connection conn = DatabaseConnection.connect()) {

            String sql = "DELETE FROM notes WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setInt(1, noteId);
            statement.executeUpdate();

            System.out.println("Note raderad av admin.");

        } catch (Exception e) {
            System.out.println("Fel vid radering.");
        }
    }
}
