package hospital.models;
public class User {
    private int id;
    private String username;
    private String passwordHash;
    private String role;
    private String staff;
    private Boolean active;
    
    public User(){ 
        
    }

    public User(String username, String passwordHash, String role, String staff, Boolean active) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.staff = staff;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
    
    
}