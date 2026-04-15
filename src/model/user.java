package model;
public class user {
    private int id;               // ID người dùng
    private String username;      // Tên đăng nhập
    private String fullname;      // Tên đầy đủ
    private String password;      // Mật khẩu (nên mã hóa trong thực tế)
    private String role;          // Vai trò của người dùng (admin, user, etc.)
    private String schedule;      // Chu kỳ sao lưu (daily, weekly, etc.)
    private int kind;             // Phân loại người dùng
    
    public user(){
        
    }
    // Constructor đầy đủ
    public user(int id, String username, String fullname, String password, String role, String schedule, int kind) {
        this.id = id;
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.role = role;
        this.schedule = schedule;
        this.kind = kind;
    }

    // Constructor không có ID (khi thêm mới người dùng)
    public user(String username, String fullname, String password, String role, String schedule, int kind) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.role = role;
        this.schedule = schedule;
        this.kind = kind;
    }

    // Getters và Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getusername() {
        return username;
    }

    public void setusername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSchedule() {
        return schedule;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
    
    public int getKind() {
        return kind;
    }

    public void setKind(int kind) {
        this.kind = kind;
    }

    // Phương thức toString() để hiển thị thông tin người dùng
    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", role='" + role + '\'' +
                ", schedule='" + schedule + '\'' +
                '}';
    }
}