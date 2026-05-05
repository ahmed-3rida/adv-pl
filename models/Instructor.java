package models;

public class Instructor extends User {
    private static final long serialVersionUID = 1L;

    private String specialization;
    private String phone;

    public Instructor(String id, String name, String email, String password,
                      String specialization, String phone) {
        super(id, name, email, password, "INSTRUCTOR");
        this.specialization = specialization;
        this.phone = phone;
    }

    public String getSpecialization() { return specialization; }
    public String getPhone()          { return phone; }
    public void setSpecialization(String s) { this.specialization = s; }
    public void setPhone(String p)          { this.phone = p; }

    @Override
    public String toFileLine() {
        return getId() + "|" + getName() + "|" + getEmail() + "|" + getPassword() + "|" + getRole()
                + "|" + specialization + "|" + phone;
    }

    /**
     * Parse an Instructor from a pipe-delimited file line.
     * Format: id|name|email|password|INSTRUCTOR|specialization|phone
     */
    public static Instructor fromFileLine(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 7) throw new IllegalArgumentException("Invalid Instructor line: " + line);
        return new Instructor(parts[0], parts[1], parts[2], parts[3], parts[5], parts[6]);
    }

    @Override
    public String toString() {
        return super.toString() + " | Specialization: " + specialization + " | Phone: " + phone;
    }
}
