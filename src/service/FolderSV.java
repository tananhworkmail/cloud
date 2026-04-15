package service;

import DAO.FolderDAO;
import model.Folder;

public class FolderSV {
    private FolderDAO folderDao;

    public FolderSV() {
        folderDao = new FolderDAO();
    }
    
    // Lấy tất cả thông tin thư mục của người dùng
    public Folder getFolder(int userId) {
        return folderDao.getFolder(userId);
    }
    
    // Lấy tên thư mục của người dùng
    public String getFolderName(int userId) {
        return folderDao.getFolderName(userId);
    }

    // Lấy đường dẫn thư mục của người dùng
    public String getFolderPath(int userId) {
        return folderDao.getFolderPath(userId);
    }

    // Lấy ngày lưu cuối của người dùng
    public String getLastBackup(int userId) {
        return folderDao.getLastBackup(userId);
    }
    
    public void addFolder(Folder folder) {
        folderDao.addFolder(folder);
    }
    
    public void updateFolder(Folder folder) {
        folderDao.updateFolder(folder);
    }
    
    public void updateLastBackup(int folderID, String time){
        folderDao.updateLastBackup(folderID, time);
    }
}