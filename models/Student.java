package models;

public class Student extends User {
    private static final long serialVersionUID = 1L;

    private String phone;
    private String address;

    public Student(String id, String name, String email, String password, String phone, String address) {
        super(id, name, email, password, "STUDENT");
        this.phone = phone;
        this.address = address;
    }

    public String getPhone()   { return phone; }
    public String getAddress() { return address; }
    public void setPhone(String phone)     { this.phone = phone; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toFileLine() {
        return getId() + "|" + getName() + "|" + getEmail() + "|" + getPassword() + "|" + getRole()
                + "|" + phone + "|" + address;
    }

    public static Student fromFileLine(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 7) throw new IllegalArgumentException("Invalid Student line: " + line);
        return new Student(parts[0], parts[1], parts[2], parts[3], parts[5], parts[6]);
    }

    @Override
    public String toString() {
        return super.toString() + " | Phone: " + phone + " | Address: " + address;
    }
}
