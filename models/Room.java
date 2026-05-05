package models;

public class Room {
    private String name;
    private String branchName;

    public Room(String name, String branchName) {
        this.name = name;
        this.branchName = branchName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String toFileLine() {
        return name + "|" + branchName;
    }

    public static Room fromFileLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length == 2) {
            return new Room(parts[0], parts[1]);
        }
        return null;
    }
}
