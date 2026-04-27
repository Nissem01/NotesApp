import java.sql.*;

public class UserUtilities {

    public static void registerUser(String username, String password) {
        try (Connection conn = DatabaseConnection.connect()){

            String sqlCheck = "SELECT * FROM users WHERE username = ?";
            PreparedStatement checkStatement = conn.prepareStatement(sqlCheck);
            checkStatement.setString(1, username);

            ResultSet rs = checkStatement.executeQuery();

            if (rs.next()) {
                System.out.println("Användarnamnet finns redan");
                return;
            }

            String hashedPassword = PasswordUtilities.hashPassword(password);

            String sqlInsert = "INSERT INTO USERS (USERNAME, PASSWORD, ROLE) VALUES (?, ?,?)";
            PreparedStatement statement = conn.prepareStatement(sqlInsert);

            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            statement.setString(3, "USER");

            statement.executeUpdate();

            System.out.println("Ny användare har registreras");

        } catch (Exception e){
            System.out.println("Error! Kunde inte registrera användare.");
        }
    }

    public static int loginUser(String username, String password) {
        try (Connection conn = DatabaseConnection.connect()) {

            String sqlSelect = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = conn.prepareStatement(sqlSelect);
            statement.setString(1, username);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String hashedPassword = rs.getString("password");

                if (PasswordUtilities.checkPassword(password, hashedPassword)) {
                    System.out.println("Inloggning lyckades.");
                    return rs.getInt("id");
                }
            }


        } catch (Exception e) {
            System.out.println("Error! Fel användarnamn eller lösen");
        }
        return -1;
    }

    public static void changePassword(int userId, String newPassword) {
        try (Connection conn = DatabaseConnection.connect()) {

            String hashedPassword = PasswordUtilities.hashPassword(newPassword);

            String sqlUpdate = "UPDATE users SET PASSWORD = ? WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sqlUpdate);

            statement.setString(1, hashedPassword);
            statement.setInt(2, userId);

            statement.executeUpdate();

            System.out.println("Lösenord updpdaterat!");
        } catch (Exception e) {
            System.out.println("Kunde inte uppdatera lösenord.");
        }
    }

    public static String getUserRole(int userId) {
        try (Connection conn = DatabaseConnection.connect()) {

            String sqlSelect = "SELECT role FROM users WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(sqlSelect);
            statement.setInt(1, userId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getString("role");
            }

        } catch (Exception e) {
            System.out.println("Kunde inte hämta roll för användare med id " + userId);
        }
        return "USER";
    }
}
