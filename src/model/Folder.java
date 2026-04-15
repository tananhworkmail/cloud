package model;

public class Folder {
    private int id;             // ID của thư mục
    private int userId;         // ID của người dùng (liên kết với bảng Users)
    private String folderPath;  // Đường dẫn của thư mục
    private String folderName;  // Tên thư mục
    private String lastBackup;  // Ngày sao lưu cuối cùng

    // Constructor đầy đủ (dành cho khi cần cả ID)
    public Folder(int id, int userId, String folderPath, String folderName, String lastBackup) {
        this.id = id;
        this.userId = userId;
        this.folderPath = folderPath;
        this.folderName = folderName;
        this.lastBackup = lastBackup;
    }

    // Constructor đơn giản hơn (thường dùng cho tạo mới, không cần ID)
    public Folder(int userId, String folderPath, String folderName, String lastBackup) {
        this.userId = userId;
        this.folderPath = folderPath;
        this.folderName = folderName;
        this.lastBackup = lastBackup;
    }

    // Getter và Setter cho từng thuộc tính
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public String getLastBackup() {
        return lastBackup;
    }

    public void setLastBackup(String lastBackup) {
        this.lastBackup = lastBackup;
    }

    // Phương thức toString() để dễ dàng hiển thị thông tin
    @Override
    public String toString() {
        return "Folder{" +
                "id=" + id +
                ", userId=" + userId +
                ", folderPath='" + folderPath + '\'' +
                ", folderName='" + folderName + '\'' +
                ", lastBackup='" + lastBackup + '\'' +
                '}';
    }
}