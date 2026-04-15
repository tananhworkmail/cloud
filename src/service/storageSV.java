/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import DAO.storageDAO;
import java.util.List;
import model.user;
import model.user_storage;

/**
 *
 * @author ASUS
 */
public class storageSV {
    private storageDAO storageDao;
    
    public storageSV() {
        storageDao = new storageDAO();
    }
    
    // Lấy danh sách tất cả người dùng
    public List<user_storage> getAllStorage() {
        return storageDao.getAllStorage();
    }
    
    //cập nhật dung lượng đã dùng
    public void updateUsedStorage(int userid, long storage_used) {
        storageDao.updateUsedStorage(userid, storage_used);
    }
    
    // Lấy thông tin storage của 1 user theo user_id
    public user_storage getUserStorage(int userid) {
        return storageDao.getUserStorage(userid);
    }
    
    public boolean updateUserStorage(user_storage us) {
        return storageDao.updateUserStorage(us);
    }
    
    public void CreateStorage(int userid) {
        storageDao.CreateStorage(userid);
    }
}
