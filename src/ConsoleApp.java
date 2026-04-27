import java.util.Scanner;

public class ConsoleApp {

    private Scanner input = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("Vad vill du göra?");
            System.out.println("1. Registrera ny användare");
            System.out.println("2. Logga in");
            System.out.println("3. Exit");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Stänger av appen");
                    return;
                default:
                    System.out.println("Fel val input");
            }
        }
    }

    private void register() {
        System.out.println("Registrera användarnamn:");
        String username = input.nextLine();

        System.out.println("Registrera lösenord:");
        String password = input.nextLine();

        UserUtilities.registerUser(username, password);
    }

    private void login() {
        System.out.println("Användarnamn:");
        String username = input.nextLine();

        System.out.println("Lösenord:");
        String password = input.nextLine();

        int userId = UserUtilities.loginUser(username, password);

        if (userId == -1) {
            System.out.println("Fel användarnamn eller lösenord");
            return;
        }
        String role = UserUtilities.getUserRole(userId);
        if (role.equals("ADMIN")) {
            adminMenu(userId);
        } else if (role.equals("USER")) {
            userMenu(userId);
        }
    }

    private void userMenu(int userId) {
        while (true) {
            System.out.println("Vad vill du göra?");
            System.out.println("1. Skapa note");
            System.out.println("2. Visa notes");
            System.out.println("3. Ändra note");
            System.out.println("4. Radera note");
            System.out.println("5. Ändra lösenord");
            System.out.println("6. Logga ut");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Skriv din note:");
                    String content = input.nextLine();
                    NotesUtilities.createNote(userId, content);
                    break;
                case 2:
                    NotesUtilities.showNotes(userId);
                    break;
                case 3:
                    NotesUtilities.showNotes(userId);
                    System.out.println("Ange note ID du vill ändra:");
                    int updateId = input.nextInt();
                    input.nextLine();

                    System.out.println("Ny text:");
                    String newContent = input.nextLine();

                    NotesUtilities.updateNote(updateId, userId, newContent);
                    break;
                case 4:
                    NotesUtilities.showNotes(userId);
                    System.out.println("Ange note ID du vill ta bort:");
                    int deleteId = input.nextInt();
                    input.nextLine();

                    NotesUtilities.deleteNote(deleteId, userId);
                    break;
                case 5:
                    System.out.println("Nytt lösenord:");
                    String newPassword = input.nextLine();
                    UserUtilities.changePassword(userId, newPassword);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Fel val input");
            }
        }
    }

    private void adminMenu(int userId) {
        while (true) {
            System.out.println("ADMIN MENY:");
            System.out.println("Vad vill du göra?");
            System.out.println("1. Se alla notes");
            System.out.println("2. Radera note");
            System.out.println("3. Logga ut");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    NotesUtilities.showAllUserNotes();
                    break;
                case 2:
                    System.out.println("Ange note ID:");
                    int id = input.nextInt();
                    input.nextLine();

                    NotesUtilities.adminDeleteNote(id);
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Fel val input");
            }
        }
    }
}
