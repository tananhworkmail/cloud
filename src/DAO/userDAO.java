package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Folder;
import model.user;
import org.mindrot.jbcrypt.BCrypt;

public class userDAO {

    // Singleton Pattern để tạo instance
    public static userDAO getInstance() {
        return new userDAO();
    }

    // Lấy tất cả người dùng từ CSDL
    public List<user> getAllusers() {
        List<user> users = new ArrayList<>();
        Connection conn = JDBCConnection.getConnection();

        String sql = "SELECT * FROM `user` WHERE role!=\"admin\";";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user user = new user(rs.getInt("id"), rs.getString("username"), rs.getString("fullname"),
                        rs.getString("password"), rs.getString("role"), rs.getString("schedule"), rs.getInt("kind"));
                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(userDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            JDBCConnection.closeConnection(conn); // Đóng kết nối
        }
        return users;
    }

    // Lấy danh sách người dùng là máy trạm
    public List<user> getAllClient() {
        List<user> users = new ArrayList<>();
        Connection conn = JDBCConnection.getConnection();

        String sql = "SELECT * FROM `user` WHERE kind=1;";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                user user = new user(rs.getInt("id"), rs.getString("username"), rs.getString("fullname"),
                        rs.getString("password"), rs.getString("role"), rs.getString("schedule"), rs.getInt("kind"));
                users.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(userDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            JDBCConnection.closeConnection(conn); // Đóng kết nối
        }
        return users;
    }
    
    // Thêm người dùng mới
    public void adduser(user user) {
        Connection conn = JDBCConnection.getConnection();
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            // B1: Lấy ID nhỏ nhất chưa dùng
            String findIdSql = "SELECT COALESCE(MIN(t1.id + 1), 1) AS next_id "
                    + "FROM user t1 LEFT JOIN user t2 ON t1.id + 1 = t2.id "
                    + "WHERE t2.id IS NULL";
            pst = conn.prepareStatement(findIdSql);
            rs = pst.executeQuery();
            int nextId = 1;
            if (rs.next()) {
                nextId = rs.getInt("next_id");
            }
            rs.close();
            pst.close();

            // B2: Insert với ID đó
            String insertSql = "INSERT INTO user (id, username, fullname, password, role, schedule, kind) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";
            pst = conn.prepareStatement(insertSql);
            pst.setInt(1, nextId);
            pst.setString(2, user.getusername());
            pst.setString(3, user.getFullname());
            pst.setString(4, user.getPassword());
            pst.setString(5, user.getRole());
            pst.setString(6, user.getSchedule());
            pst.setInt(7, user.getKind());
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(userDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            JDBCConnection.closeConnection(conn); // Đóng kết nối
        }
    }

    // Tìm người dùng theo tên đăng nhập
    public user finduserByusername(String username) {
        Connection conn = JDBCConnection.getConnection();
        String sql = "SELECT * FROM user WHERE username = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                user user = new user(rs.getInt("id"), rs.getString("username"), rs.getString("fullname"),
                        rs.getString("password"), rs.getString("role"), rs.getString("schedule"), rs.getInt("kind"));
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(userDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            JDBCConnection.closeConnection(conn); // Đóng kết nối
        }
        return null;
    }

    public user finduserByFullname(String fullname) {
        Connection conn = JDBCConnection.getConnection();
        String sql = "SELECT * FROM user WHERE fullname = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, fullname);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                user user = new user(rs.getInt("id"), rs.getString("username"), rs.getString("fullname"),
                        rs.getString("password"), rs.getString("role"), rs.getString("schedule"), rs.getInt("kind"));
                return user;
            }
        } catch (SQLException ex) {
            Logger.getLogger(userDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            JDBCConnection.closeConnection(conn); // Đóng kết nối
        }
        return null;
    }

    // Cập nhật thông tin người dùng
    public void updateuser(user user) {
        Connection conn = JDBCConnection.getConnection();

        String sql = "UPDATE user SET username = ?, fullname = ?, password = ?, role = ? WHERE id = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user.getusername());
            pst.setString(2, user.getFullname());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getRole());
            pst.setString(5, Integer.toString(user.getId()));

            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(userDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            JDBCConnection.closeConnection(conn); // Đóng kết nối
        }
    }

    // Cập nhật thông tin lịch trình
    public void updateuserschedule(user user) {
        Connection conn = JDBCConnection.getConnection();

        String sql = "UPDATE user SET username = ?, fullname = ?, password = ?, role = ?, schedule = ? WHERE id = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, user.getusername());
            pst.setString(2, user.getFullname());
            pst.setString(3, user.getPassword());
            pst.setString(4, user.getRole());
            pst.setString(5, user.getSchedule());
            pst.setString(6, Integer.toString(user.getId()));

            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(userDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            JDBCConnection.closeConnection(conn); // Đóng kết nối
        }
    }

    // Xóa người dùng theo tên đăng nhập
    public void deleteuser(String username) {
        Connection conn = JDBCConnection.getConnection();

        String sql = "DELETE FROM user WHERE username = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(userDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            JDBCConnection.closeConnection(conn); // Đóng kết nối
        }
    }

    public boolean checkLogin(String username, String plainPassword) {
        Connection conn = JDBCConnection.getConnection(); // Kết nối tới CSDL
        String sql = "SELECT password FROM user WHERE username = ?"; // Câu truy vấn

        try {
            PreparedStatement pst = conn.prepareStatement(sql); // Chuẩn bị câu lệnh SQL
            pst.setString(1, username); // Gán giá trị cho tham số thứ nhất (username)

            ResultSet rs = pst.executeQuery(); // Thực thi truy vấn và nhận kết quả

            // Kiểm tra nếu có bản ghi (người dùng tồn tại trong bảng)
            try {
            if (rs.next()) {
                String storedHash = rs.getString("password");
                return BCrypt.checkpw(plainPassword, storedHash);
            }
            }
            catch (IllegalArgumentException e){
                return false;
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Xử lý ngoại lệ
        } finally {
            JDBCConnection.closeConnection(conn); // Đóng kết nối
        }
        return false; // Đăng nhập thất bại
    }

    public Folder getfolder(int userID) {
        Connection conn = JDBCConnection.getConnection(); // Kết nối tới CSDL
        String sql = "SELECT * FROM `folders` WHERE user_id = ?"; // Câu truy vấn

        String userid = Integer.toString(userID);

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, userid);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Folder folder = new Folder(rs.getInt("id"), rs.getInt("user_id"), rs.getString("folder_path"),
                        rs.getString("folder_name"), rs.getString("last_backup"));
                return folder;
            }
        } catch (SQLException ex) {
            Logger.getLogger(userDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            JDBCConnection.closeConnection(conn); // Đóng kết nối
        }
        return null;

    }

    public boolean checkUsername(String username) {
        Connection conn = JDBCConnection.getConnection();
        String sql = "SELECT * FROM user WHERE username = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(userDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            JDBCConnection.closeConnection(conn); // Đóng kết nối
        }
        //return null;
        return false;
    }
    
    
}
