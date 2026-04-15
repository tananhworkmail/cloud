/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.util.ArrayList;
import java.util.List;
import model.user_storage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class storageDAO {

    public List<user_storage> getAllStorage() {
        List<user_storage> list = new ArrayList<>();
        String sql = "SELECT u.id AS user_id, u.username, u.fullname, u.role, "
                + "s.storage_quota, s.storage_used "
                + "FROM user u "
                + "JOIN user_storage s ON u.id = s.user_id "
                + "WHERE u.role != 'admin';";

        // Sử dụng try-with-resources để tự động đóng tài nguyên
        try (Connection conn = JDBCConnection.getConnection(); PreparedStatement pst = conn.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                user_storage us = new user_storage(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("fullname"),
                        rs.getString("role"),
                        rs.getLong("storage_quota"),
                        rs.getLong("storage_used")
                );
                list.add(us);
            }

        } catch (SQLException ex) {
            Logger.getLogger(userDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return list;
    }

    // Cập nhật dung lượng đã sử dụng theo user id
    public void updateUsedStorage(int userid, long storage_used) {
        Connection conn = JDBCConnection.getConnection();

        String sql = "UPDATE user_storage SET storage_used = ? WHERE user_id = ?";

        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setLong(1, storage_used);
            pst.setInt(2, userid);

            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(userDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            JDBCConnection.closeConnection(conn); // Đóng kết nối
        }
    }

    // Lấy thông tin storage của 1 user theo user_id
    public user_storage getUserStorage(int userid) {
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        user_storage us = null;

        try {
            conn = JDBCConnection.getConnection();

            String sql = "SELECT u.id AS user_id, u.username, u.fullname, u.role, "
                    + "s.storage_quota, s.storage_used "
                    + "FROM user u "
                    + "JOIN user_storage s ON u.id = s.user_id "
                    + "WHERE u.id = ? AND u.role != 'admin';";

            pst = conn.prepareStatement(sql);
            pst.setInt(1, userid);

            rs = pst.executeQuery();

            if (rs.next()) {
                us = new user_storage(
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("fullname"),
                        rs.getString("role"),
                        rs.getLong("storage_quota"),
                        rs.getLong("storage_used")
                );
            }

        } catch (SQLException ex) {
            Logger.getLogger(storageDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(storageDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            JDBCConnection.closeConnection(conn);
        }

        return us;
    }

    public boolean updateUserStorage(user_storage us) {
        Connection conn = null;
        PreparedStatement pst = null;

        try {
            conn = JDBCConnection.getConnection();

            String sql = "UPDATE user_storage SET storage_quota = ?, storage_used = ? WHERE user_id = ?";

            pst = conn.prepareStatement(sql);
            pst.setLong(1, us.getMaxStorage());
            pst.setLong(2, us.getUsedStorage());
            pst.setInt(3, us.getUserId());

            int affectedRows = pst.executeUpdate();
            return affectedRows > 0; // true nếu cập nhật thành công

        } catch (SQLException ex) {
            Logger.getLogger(storageDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pst != null) {
                    pst.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(storageDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
            JDBCConnection.closeConnection(conn);
        }

        return false;
    }

    //Tạo dòng mới khi thêm người dùng mới 
    public void CreateStorage(int userid) {
        Connection conn = JDBCConnection.getConnection();

        String sql = "INSERT INTO `user_storage` (`user_id`, `storage_quota`, `storage_used`) VALUES (?, 1073741824, NULL);";


        try {
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userid);

            pst.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(userDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            JDBCConnection.closeConnection(conn); // Đóng kết nối
        }
    }
    
}
