package models;

public class ParentCourse {
    private String id;
    private String title;
    private String description;

    public ParentCourse(String id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getId()          { return id; }
    public String getTitle()       { return title; }
    public String getDescription() { return description; }
    public void setTitle(String title)             { this.title = title; }
    public void setDescription(String description) { this.description = description; }

    public String toFileLine() {
        return id + "|" + title + "|" + description;
    }

    public static ParentCourse fromFileLine(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 3) throw new IllegalArgumentException("Invalid ParentCourse line: " + line);
        return new ParentCourse(parts[0], parts[1], parts[2]);
    }

    @Override
    public String toString() {
        return "[" + id + "] " + title + " - " + description;
    }
}
