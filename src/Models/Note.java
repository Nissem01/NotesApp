package Models;

public class Note {

    private int id;
    private int userId;
    private String content;

    public Note(int id, int userId, String content) {
        this.id = id;
        this.userId = userId;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}
