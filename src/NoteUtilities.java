import java.sql.*;

public class NoteUtilities {

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

            String sqlSelect = "SELECT id, content FROM notes WHERE user_id = ?";
            PreparedStatement statement = conn.prepareStatement(sqlSelect);

            statement.setInt(1,userId);

            ResultSet rs = statement.executeQuery();

            System.out.println("Dina notes:");

            boolean hasNotes = false;

            while (rs.next()){
                hasNotes = true;
                int id = rs.getInt("id");
                String content = rs.getString("content");

                System.out.println("Note ID " + id + ": " + content);
            }

            if(!hasNotes){
                System.out.println("Du har inga notes.");
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

            int rowsChanged = statement.executeUpdate();

            if (rowsChanged > 0) {
                System.out.println("Note raderad!");
            } else {
                System.out.println("Ingen note hittades");
            }

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

            int rowsChanged = statement.executeUpdate();

            if (rowsChanged > 0) {
                System.out.println("Note uppdaterad");
            } else {
                System.out.println("Ingen note hittades med matchande ID.");
            }

        } catch (Exception e) {
            System.out.println("Kunde inte uppdatera note.");
        }
    }

    public static void showAllUserNotes() {
        try (Connection conn = DatabaseConnection.connect()) {

            String sqlSelect = "SELECT notes.id, users.username, notes.content " + "FROM notes JOIN users ON notes.user_id = users.id";

            PreparedStatement statement = conn.prepareStatement(sqlSelect);
            ResultSet rs = statement.executeQuery();

            System.out.println("Alla notes i systemet:");

            boolean hasNotes = false;


            while (rs.next()) {
                hasNotes = true;

                int id = rs.getInt("id");
                String username = rs.getString("username");
                String content = rs.getString("content");

                System.out.println("Note ID " + id + ": " + username + ": " + content);
            }

            if (!hasNotes){
                System.out.println("Inga notes finns i systemet");
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
            int rowsChanged = statement.executeUpdate();

            if (rowsChanged > 0) {
                System.out.println("Note raderad av admin.");
            } else {
                System.out.println("Ingen note hittades med det ID:t.");
            }
        } catch (Exception e) {
            System.out.println("Fel vid radering.");
        }
    }
}
