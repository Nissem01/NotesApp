import java.util.Scanner;

public class ConsoleApp {

    private Scanner input = new Scanner(System.in);

    public void start(){
        System.out.println("Vad vill du göra?");
        System.out.println("1. Registrera ny användare");
        System.out.println("2. Logga in");
        System.out.println("3. Exit");

        int choice = input.nextInt();
        input.nextLine();

        switch(choice){
            case 1:
                register();
                break;
            case 2:
                login();
                break;
            case 3:
                System.out.println("Stänger av appen");
                break;
            default:
                System.out.println("Fel val input");
        }
    }

    private void register(){
        System.out.println("Registrera användarnamn:");
        String username = input.nextLine();

        System.out.println("Registrera lösenord:");
        String password = input.nextLine();

        UserUtilities.registerUser(username, password);
        start();
    }

    private void login(){
        System.out.println("Användarnamn:");
        String username = input.nextLine();

        System.out.println("Lösenord:");
        String password = input.nextLine();

        int userId = UserUtilities.loginUser(username, password);
        String role = UserUtilities.getUserRole(userId);

        if (role.equals("ADMIN")) {
            adminMenu(userId);
        }
        if (role.equals("USER")) {
            userMenu(userId);
        }
        else{
            start();
        }
    }

    private void userMenu(int userId){
        while (true){
            System.out.println("Vad vill du göra?");
            System.out.println("1. Skapa note");
            System.out.println("2. Visa notes");
            System.out.println("3. Logga ut");

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
                    start();
                    return;

                default:
                    System.out.println("Fel val input");
            }
        }
    }

    private void adminMenu(int userId) {
        while (true) {
            System.out.println("1. Se alla notes");
            System.out.println("2. Radera note");
            System.out.println("3. Logga ut");

            int choice = input.nextInt();
            input.nextLine();

            switch (choice) {
                case 1:
                    NotesUtilities.showAllNotes();
                    break;
                case 2:
                    System.out.println("Ange note ID:");
                    int id = input.nextInt();
                    input.nextLine();
                    NotesUtilities.adminDeleteNote(id);
                    break;
                case 3:
                    return;
            }
        }
    }
}
