package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Folder;

public class FolderDAO {
    
    public Folder getFolder(int userId) {
        Connection conn = JDBCConnection.getConnection();
        String sql = "SELECT * FROM folders WHERE user_id = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Folder(
                    rs.getInt("id"),
                    rs.getInt("user_id"),
                    rs.getString("folder_path"),
                    rs.getString("folder_name"),
                    rs.getString("last_backup")
                );
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            JDBCConnection.closeConnection(conn);
        }
        return null; // Trả về null nếu không tìm thấy
    }
    
    public String getFolderName(int userId) {
        Connection conn = JDBCConnection.getConnection();
        String sql = "SELECT folder_name FROM folders WHERE user_id = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("folder_name");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            JDBCConnection.closeConnection(conn);
        }
        return null; // Trả về null nếu không tìm thấy
    }
    
    public String getFolderPath(int userId) {
        Connection conn = JDBCConnection.getConnection();
        String sql = "SELECT folder_path FROM folders WHERE user_id = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("folder_path");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            JDBCConnection.closeConnection(conn);
        }
        return null; // Trả về null nếu không tìm thấy
    }
    
    public String getLastBackup(int userId) {
        Connection conn = JDBCConnection.getConnection();
        String sql = "SELECT last_backup FROM folders WHERE user_id = ?";
        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getString("last_backup");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            JDBCConnection.closeConnection(conn);
        }
        return null; // Trả về null nếu không tìm thấy
    }
    
    public void addFolder(Folder folder) {
        Connection conn = JDBCConnection.getConnection();
        String sql = "INSERT INTO folders (user_id, folder_path, folder_name, last_backup) VALUES (?, ?, ?, ?)";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, folder.getUserId());
            pst.setString(2, folder.getFolderPath());
            pst.setString(3, folder.getFolderName());
            pst.setString(4, folder.getLastBackup());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            JDBCConnection.closeConnection(conn); // Đảm bảo đóng kết nối
        }
    }
    
    public void updateFolder(Folder folder) {
        Connection conn = JDBCConnection.getConnection();
        String sql = "UPDATE folders SET folder_path = ?, folder_name = ?, last_backup = ? WHERE user_id = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, folder.getFolderPath());
            pst.setString(2, folder.getFolderName());
            pst.setString(3, folder.getLastBackup());
            pst.setInt(4, folder.getUserId());
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            JDBCConnection.closeConnection(conn); // Đảm bảo đóng kết nối
        }
    }
    
    public void updateLastBackup(int folderID, String time){
        Connection conn = JDBCConnection.getConnection();
        String sql = "UPDATE folders SET last_backup = ? WHERE id = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, time);
            pst.setInt(2, folderID);
            
            pst.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            JDBCConnection.closeConnection(conn); // Đảm bảo đóng kết nối
        }
    }
}