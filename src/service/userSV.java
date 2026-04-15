/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;


import DAO.userDAO;
import java.util.List;
import model.Folder;
import model.user;

public class userSV {
    private userDAO userDao;

    public userSV() {
        userDao = new userDAO();
    }

    // Lấy danh sách tất cả người dùng
    public List<user> getAllUsers() {
        return userDao.getAllusers();
    }
    
    // Lấy danh sách người dùng là máy trạm
    public List<user> getAllClient() {
        return userDao.getAllClient();
    }

    // Thêm người dùng mới
    public void addUser(user user) {
        userDao.adduser(user);
    }

    // Tìm người dùng theo tên đăng nhập
    public user findUserByUsername(String username) {
        return userDao.finduserByusername(username);
    }
    
    public user finduserByFullname(String fullname) {
        return userDao.finduserByFullname(fullname);
    }

    // Cập nhật thông tin người dùng
    public void updateUser(user user) {
        userDao.updateuser(user);
    }

    //cập nhật lịch trình
     public void updateuserschedule(user user) {
         userDao.updateuserschedule(user);
     }
    
    // Xóa người dùng theo tên đăng nhập
    public void deleteUser(String username) {
        userDao.deleteuser(username);
    }
    
     public boolean checkLogin(String username, String password) {
         return userDao.checkLogin(username, password);
     }
     
     public Folder getfolder(int userID){
         return userDao.getfolder(userID);
     }
     
     public boolean checkUsername(String username){
         return userDao.checkUsername(username);
     }
}