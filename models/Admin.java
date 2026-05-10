package models;

public class Admin extends User {
    private static final long serialVersionUID = 1L;

    public Admin(String id, String name, String email, String password) {
        super(id, name, email, password, "ADMIN");
    }

    @Override
    public String toFileLine() {
        return getId() + "|" + getName() + "|" + getEmail() + "|" + getPassword() + "|" + getRole();
    }

    public static Admin fromFileLine(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 5) throw new IllegalArgumentException("Invalid Admin line: " + line);
        return new Admin(parts[0], parts[1], parts[2], parts[3]);
    }
}
