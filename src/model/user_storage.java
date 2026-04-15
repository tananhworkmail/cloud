package model;
public class user_storage {
    private int userid;           // ID người dùng
    private String username;      // Tên đăng nhập
    private String fullname;      // Tên đầy đủ     
    private String role;          // chức vụ của người dùng 
    private long max_storage;      // dung lượng tối đa  
    private long used_storage;     // dung lượng đã dùng

    public user_storage(){
        
    }
    // Constructor đầy đủ
    public user_storage(int userid, String username, String fullname, String role, long max_storage, long used_storage) {
        this.userid = userid;
        this.username = username;
        this.fullname = fullname;
        this.role = role;
        this.max_storage = max_storage;
        this.used_storage = used_storage;
    }

    // Getters và Setters
    public int getUserId() {
        return userid;
    }

    public void setUserId(int userid) {
        this.userid = userid;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public long getMaxStorage() {
        return max_storage;
    }

    public void setMaxStorage(long max_storage) {
        this.max_storage = max_storage;
    }
    
    public long getUsedStorage() {
        return used_storage;
    }

    public void setUsedStorage(long used_storage) {
        this.used_storage = used_storage;
    }

    // Phương thức toString() để hiển thị thông tin người dùng
    @Override
    public String toString() {
        return "user_storage{" +
                "userid=" + userid +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                ", role='" + role + '\'' +
                ", max_storage='" + max_storage + '\'' +
                ", used_storage='" + used_storage + '\'' +
                '}';
    }
}