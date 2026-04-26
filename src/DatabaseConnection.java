import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;
import java.io.FileInputStream;

public class DatabaseConnection {

    private static Properties config = new Properties();

    static {
        try {
            FileInputStream input = new FileInputStream("config.properties");
            config.load(input);
        } catch (Exception e) {
            System.out.println("Kunde inte läsa config!");
        }
    }

    public static Connection connect() throws Exception {
        String url = config.getProperty("url");
        String user = config.getProperty("user");
        String password = config.getProperty("password");

        return DriverManager.getConnection(url, user, password);
    }
}