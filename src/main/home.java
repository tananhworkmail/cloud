/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import com.formdev.flatlaf.FlatLightLaf;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.Stack;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import com.jcraft.jsch.*;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;
import static java.nio.file.StandardWatchEventKinds.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import model.Folder;
import model.user;
import model.user_storage;
import service.FolderSV;
import service.storageSV;
import service.userSV;

/**
 *
 * @author ASUS
 */
public class home extends javax.swing.JFrame {

    /**
     * Creates new form home
     */
    //private String host = "192.168.0.111"; // IP của máy ảo
    private String host = "172.20.10.4"; // IP của máy ảo
    private String servername = "server";  // Tên người dùng của máy ảo
    private String serverpass = "0975372230soai";  // Mật khẩu của máy ảo
    private ChannelSftp sftpChannel;
    private Stack<String> previousDirs;
    private File rootDir;
    private File userDir;
    private String userID;
    private File selectedFile; // To keep track of the selected file or folder
    public String remoteDir;
    private String RootDir;
    private String folowDir; //Theo dõi thư mục này trên máy trạm
    private String autoDir; //folder data trên máy ảo
    private user user_login;

    public home(String username) {

        //lấy thông tin người dùng đang đăng nhập
        userSV usv = new userSV();
        user_login = usv.findUserByUsername(username);

        initComponents();

        //set fullname
        fullname.setText(getfullname(username));

        userID = username;
        RootDir = "/cloud/" + userID;
        remoteDir = "/cloud/" + userID;  // Đây là thư mục gốc người dùng

        this.setLocationRelativeTo(null);
        connectSFTP();

        rootDir = new File("/cloud/" + userID);
        previousDirs = new Stack<>();
        pathLabel.setText(">");
        userDir = rootDir;
        autoDir = remoteDir + "/data";
        //startWatchService(username);
        showstorage(remoteDir);
        showSchedule();
        //updateStorage(remoteDir, username);
        showtest();
        loadUserFiles();
        startBackupService(username);

        jScrollPane1.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                // Màu của thanh cuộn
                this.thumbColor = new Color(225, 225, 225);  // Màu của thumb (phần di chuyển)
                this.trackColor = new Color(255, 255, 255);  // Màu của track (nền)
            }

            protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
                // Vẽ thumb với góc tròn
                g.setColor(thumbColor);
                g.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 20, 20); // Góc tròn với bán kính là 20
            }

            @Override
            protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
                // Vẽ track với màu sắc nhẹ
                g.setColor(trackColor);
                g.fillRoundRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height, 20, 20); // Góc tròn cho track
            }

            @Override
            protected JButton createDecreaseButton(int orientation) {
                JButton button = super.createDecreaseButton(orientation);
                button.setBackground(new Color(255, 255, 255));  // Màu nền nút cuộn
                button.setPreferredSize(new Dimension(0, 0)); // Ẩn nút cuộn lên xuống
                return button;
            }

            @Override
            protected JButton createIncreaseButton(int orientation) {
                JButton button = super.createIncreaseButton(orientation);
                button.setBackground(new Color(255, 255, 255));  // Màu nền nút cuộn
                button.setPreferredSize(new Dimension(0, 0)); // Ẩn nút cuộn lên xuống
                return button;
            }
        });
        jScrollPane1.getVerticalScrollBar().setPreferredSize(new Dimension(10, Integer.MAX_VALUE));  // Chiều rộng thanh cuộn

        // Tùy chỉnh màu sắc của scrollPane
        jScrollPane1.setBackground(Color.WHITE);
        //tốc độ cuộn
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(16);

    }

    private String getfullname(String username) {
        userSV usv = new userSV();
        user User = usv.findUserByUsername(username);
        System.out.println(User.getFullname());
        return User.getFullname();
    }

    private void connectSFTP() {
        JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;

        try {
            // Thiết lập kết nối SSH
            session = jsch.getSession(servername, host, 22);
            session.setPassword(serverpass);
            session.setConfig("PreferredAuthentications", "password");
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            // Mở kênh SFTP
            channel = session.openChannel("sftp");
            sftpChannel = (ChannelSftp) channel;
            sftpChannel.connect();

            System.out.println("Kết nối SFTP thành công!");

        } catch (JSchException e) {
//            e.printStackTrace();
            System.out.println("Lỗi kết nối SFTP!!!");
        }
    }

    private void startWatchService(String username) {
        try {

            user User = new user();
            userSV usersv = new userSV();
            User = usersv.findUserByUsername(username);
            userSV usersv2 = new userSV();
            folowDir = usersv2.getfolder(User.getId()).getFolderPath();

            Path dirToWatch = Paths.get(folowDir);  // Đường dẫn thư mục cục bộ muốn theo dõi
            WatchService watchService = FileSystems.getDefault().newWatchService();
            registerAll(dirToWatch, watchService);  // Đăng ký các sự kiện theo dõi

            System.out.println("Đang theo dõi thư mục: " + folowDir);

            File folowF = new File(folowDir);

            // Mở một thread riêng để theo dõi sự kiện
            new Thread(() -> {
                while (true) {
                    try {
                        WatchKey key = watchService.take();  // Chờ đợi sự kiện
                        Path dir = (Path) key.watchable();
                        for (WatchEvent<?> event : key.pollEvents()) {
                            WatchEvent.Kind<?> kind = event.kind();
                            WatchEvent<Path> ev = (WatchEvent<Path>) event;
                            Path name = ev.context();
                            Path child = dir.resolve(name);

                            System.out.println("Sự kiện xảy ra: " + kind.name() + " - " + child);

                            // Nếu có sự kiện tạo file hoặc thư mục mới, sao lưu lên SFTP
                            if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                                if (Files.isDirectory(child, LinkOption.NOFOLLOW_LINKS)) {
                                    try {
                                        registerAll(child, watchService);  // Đăng ký thư mục con mới
                                    } catch (IOException ex) {
                                        java.util.logging.Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                try {
                                    replaceDirectoryOnSFTP(folowF, autoDir);  // Sao lưu
                                } catch (SftpException ex) {
                                    java.util.logging.Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                                try {
                                    replaceDirectoryOnSFTP(folowF, autoDir);  // Sao lưu
                                } catch (SftpException ex) {
                                    java.util.logging.Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
                                try {
                                    replaceDirectoryOnSFTP(folowF, autoDir);
                                } catch (SftpException ex) {
                                    java.util.logging.Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }

                            // Trì hoãn xử lý thêm sự kiện để gom nhóm
                            try {
                                TimeUnit.MILLISECONDS.sleep(100);  // Delay nhỏ 100ms để gom nhóm các sự kiện
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        boolean valid = key.reset();
                        if (!valid) {
                            break;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void deleteFileOrFolderOnSFTP(Path child) throws SftpException {
        // Đảm bảo kết nối SFTP đã được thiết lập
        if (sftpChannel == null || !sftpChannel.isConnected()) {
            connectSFTP();  // Kết nối SFTP nếu chưa có kết nối
        }
        // Tạo đường dẫn con của tệp hoặc thư mục
        Path relativePath = Paths.get(folowDir).relativize(child);
        String remotePath = autoDir + "/" + relativePath.toString().replace("\\", "/");  // Chuyển đổi dấu gạch chéo ngược sang dấu gạch chéo
        // Kiểm tra xem tệp hoặc thư mục có tồn tại trên SFTP không
        System.out.println(remotePath);

        // Kiểm tra nếu đó là thư mục
        if (sftpChannel.lstat(remotePath).isDir()) {
            // Nếu là thư mục, xóa tất cả các tệp trong thư mục trước
            deleteFilesInDirectory(remotePath);  // Xóa các tệp trong thư mục
            sftpChannel.rmdir(remotePath);  // Sau đó xóa thư mục chính
        } else {
            // Nếu là tệp, xóa trực tiếp
            sftpChannel.rm(remotePath);
            //                    JOptionPane.showMessageDialog(home.this, "Deleted file: " + selectedFile.getName());
        }

        System.out.println("Đã xóa: " + remotePath);
    }

    private void uploadFileOrFolder(Path path) {
        File fileOrFolder = path.toFile();
        if (!fileOrFolder.exists()) {
            System.out.println("Tệp hoặc thư mục không tồn tại: " + fileOrFolder.getAbsolutePath());
            return;
        }

        try {
            // Đảm bảo kết nối SFTP đã được thiết lập
            if (sftpChannel == null || !sftpChannel.isConnected()) {
                connectSFTP();  // Kết nối SFTP nếu chưa có kết nối
            }

            // Tạo đường dẫn con của tệp hoặc thư mục
            Path relativePath = Paths.get(folowDir).relativize(path);
            String remotePath = autoDir + "/" + relativePath.toString().replace("\\", "/");  // Chuyển đổi dấu gạch chéo ngược sang dấu gạch chéo

            if (fileOrFolder.isDirectory()) {
                // Nếu là thư mục, kiểm tra và thay thế thư mục trên SFTP
                try {
                    sftpChannel.mkdir(remotePath);
                } catch (SftpException e) { // Thư mục đã tồn tại, bỏ qua lỗi này
                }
                uploadFolderToSFTP(fileOrFolder, remotePath);
            } else {
                // Nếu là tệp, tải tệp lên SFTP
                sftpChannel.put(fileOrFolder.getAbsolutePath(), remotePath);
                System.out.println("Đã tải lên: " + remotePath);
            }
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    private void replaceDirectoryOnSFTP(File file, String dir) throws SftpException {
        // Xóa toàn bộ nội dung thư mục cũ trên máy chủ
//        File fileOrFolder = path.toFile();
        if (!file.exists()) {
            System.out.println("Tệp hoặc thư mục không tồn tại: " + file.getAbsolutePath());
            return;
        }

        // Đảm bảo kết nối SFTP đã được thiết lập
        if (sftpChannel == null || !sftpChannel.isConnected()) {
            connectSFTP();  // Kết nối SFTP nếu chưa có kết nối
        }

        // Tạo đường dẫn con của tệp hoặc thư mục
//        Path relativePath = Paths.get(folowDir).relativize(path);
//        String remotePath = autoDir + "/" + relativePath.toString().replace("\\", "/");  // Chuyển đổi dấu gạch chéo ngược sang dấu gạch chéo
        try {
            sftpChannel.cd(dir);  // Kiểm tra thư mục đã tồn tại trên máy ảo chưa
        } catch (SftpException e) {
            // Nếu không tồn tại, tạo thư mục
            sftpChannel.mkdir(dir);
        }

        try {
            deleteFilesInDirectory(dir);
        } catch (SftpException e) {
            // Nếu thư mục không tồn tại, bỏ qua lỗi này
        }

        // Tải lên thư mục mới từ máy trạm
        uploadFolderToSFTP(file, dir);
        System.out.println("Đã sao lưu!");
    }

    private void registerAll(final Path start, final WatchService watchService) throws IOException {
        // Đăng ký thư mục hiện tại và tất cả các thư mục con
        Files.walkFileTree(start, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                dir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.ENTRY_MODIFY);
                System.out.println("Đã đăng ký: " + dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

//    private void uploadFile(Path file) {
//        try {
//            File localFile = file.toFile();
//            // Tạo đường dẫn con của file sau T:/data
//            Path relativePath = Paths.get(folowDir).relativize(file);
//            String remoteFilePath = autoDir + "/" + relativePath.toString();
//            // Tải file lên SFTP
//            sftpChannel.put(localFile.getAbsolutePath(), remoteFilePath);
//            System.out.println("Đã tải lên tệp: " + remoteFilePath);
//        } catch (SftpException e) {
//            e.printStackTrace();
//        }
//    }
    private void uploadFolder(File folder) {
//        try {
        // Tạo đường dẫn con của thư mục
//            Path relativePath = Paths.get(folowDir).relativize(folder);
//             // Tạo đường dẫn thư mục trên SFTP
//            String remoteFolderPath = autoDir + "/" + relativePath.toString();

        try {
            // Đảm bảo kết nối SFTP đã được thiết lập
            if (sftpChannel == null || !sftpChannel.isConnected()) {
                connectSFTP();  // Kết nối SFTP nếu chưa có kết nối
            }

            // Kiểm tra và tạo thư mục trên máy ảo (nếu chưa tồn tại)
            try {
                sftpChannel.cd(autoDir);  // Kiểm tra thư mục đã tồn tại trên máy ảo chưa
            } catch (SftpException e) {
                // Nếu không tồn tại, tạo thư mục
                sftpChannel.mkdir(autoDir);
            }

            uploadFolderToSFTP(folder, autoDir);
            System.out.print("gửi folder: " + folder.getName());
//            // Kiểm tra và tạo thư mục trên SFTP
//            try {
//                sftpChannel.cd(remoteFolderPath);  // Kiểm tra thư mục đã tồn tại trên máy ảo chưa
//            } catch (SftpException e) {
//                // Nếu không tồn tại, tạo thư mục
//                sftpChannel.mkdir(remoteFolderPath);
//            }
//
//            // Duyệt qua tất cả các tệp và thư mục trong thư mục con
//            Files.walk(folder).forEach(path -> {
//                if (Files.isDirectory(path)) {
//                    try {
//                        sftpChannel.mkdir(remoteFolderPath + "/" + path.getFileName());
//                    } catch (SftpException e) {
//                        // Log nếu có lỗi khi tạo thư mục con
//                        e.printStackTrace();
//                    }
//                } else if (Files.isRegularFile(path)) {
//                    uploadFile(path);  // Sao lưu tệp
//                }
//            });
//
//            System.out.println("Đã tải lên thư mục: " + remoteFolderPath);
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    private JPanel createFilePanel(File file, boolean isDirectory) {
        JPanel filePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.anchor = GridBagConstraints.CENTER; // Center the button
        gbc.fill = GridBagConstraints.NONE;

        filePanel.setBackground(new java.awt.Color(255, 255, 255));

        // Tạo nút cho file/folder
        JButton fileButton = new JButton();
        fileButton.setPreferredSize(new Dimension(130, 130));
        fileButton.setFont(fileButton.getFont().deriveFont(90f));

        // Chọn biểu tượng theo loại file
        if (isDirectory) {
            fileButton.setText("📁");  // Biểu tượng thư mục
            fileButton.setForeground(Color.YELLOW);
        } else {
            fileButton.setText("📄");  // Biểu tượng tệp
            fileButton.setForeground(new java.awt.Color(0, 102, 255));
        }

        fileButton.setBackground(new java.awt.Color(255, 255, 254));
        fileButton.setBorder(null);

        // Tính dung lượng của file/folder và cập nhật toolTipText
        long fileSize = 0;
        if (isDirectory) {
            fileSize = getDirectorySize(remoteDir + "/" + file.getName());  // Sử dụng remoteDir + file.getName() thay vì file.getPath()

        } else {
            fileSize = getFileSizeFromSFTP(file.getName()); // Lấy dung lượng cho file

        }

        String formattedSize = formatstorage(fileSize);
        fileButton.setToolTipText("Size: " + formattedSize);  // Thiết lập toolTipText cho button

        // Sự kiện click vào file/folder
        Color originalBackground = fileButton.getBackground();
        Color hoverBackground = new Color(240, 240, 240);
        fileButton.addMouseListener(new MouseAdapter() {
            //Thêm hover

            @Override
            public void mouseEntered(MouseEvent e) {
                fileButton.setBackground(hoverBackground);
                fileButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Đổi cursor thành bàn tay
                fileButton.setBorder(BorderFactory.createLineBorder(new Color(150, 150, 150))); // Thêm viền nhẹ
            }

            @Override
            public void mouseExited(MouseEvent e) {
                fileButton.setBackground(originalBackground);
                fileButton.setCursor(Cursor.getDefaultCursor());
                fileButton.setBorder(null); // Hoặc bạn có thể giữ viền mờ nếu thích
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                selectedFile = file; // Cập nhật selectedFile mỗi khi click

                if (e.getClickCount() == 1) {
                    // Nếu nhấn 1 lần, chỉ cần chọn file/folder
                    //showtest();  // Gọi phương thức để hiển thị thông tin về file/folder đã chọn nếu cần
                    fileButton.setBackground(hoverBackground);
                } else if (e.getClickCount() == 2) {
                    // Nếu nhấn 2 lần, mở folder nếu là thư mục
                    if (isDirectory) {
                        //bỏ selectedFile
                        selectedFile = null;
                        // Lưu thư mục hiện tại vào stack
                        previousDirs.push(remoteDir);  // Lưu thư mục hiện tại vào stack
                        remoteDir = remoteDir + "/" + file.getName();  // Cập nhật remoteDir cho thư mục con
                        loadUserFiles();  // Mở thư mục mới
                    }
                }
            }

        });

        //border cho file
//        fileButton.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        filePanel.add(fileButton, gbc);

        // Lấy tên file/folder và xử lý với dấu cách và độ dài
        String fileName = file.getName();
        JLabel fileNameLabel;

        if (fileName.contains(" ") && fileName.length() < 32) {
            gbc.gridy = 1;
            fileNameLabel = new JLabel("<html><center>" + fileName + "</center></html>");
            fileNameLabel.setPreferredSize(new Dimension(110, 33));
        } else if (!fileName.contains(" ") && fileName.length() < 32) {
            gbc.gridy = 1;
            fileNameLabel = new JLabel("<html><center>" + fileName + "</center></html>");
            fileNameLabel.setPreferredSize(new Dimension(110, 33));
        } else {
            int maxNameLength = 15;
            String displayedName = fileName;
            String extension = "";
            int dotIndex = fileName.lastIndexOf('.');
            if (dotIndex > 0) {
                extension = fileName.substring(dotIndex);
                displayedName = fileName.substring(0, maxNameLength - extension.length()) + "..." + extension;
            } else {
                displayedName = fileName.substring(0, maxNameLength) + "...";
            }
            gbc.gridy = 1;
            fileNameLabel = new JLabel("<html><div style='word-wrap: break-word; text-align: center;'>" + displayedName + "</div></html>");
            fileNameLabel.setPreferredSize(new Dimension(110, 33));
        }

        fileNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fileNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        filePanel.add(fileNameLabel, gbc);

        return filePanel;
    }

    private void loadUserFiles() {
        filePanel.removeAll();  // Clear existing items

        try {
            // Thư mục gốc của người dùng trên máy chủ
            if (sftpChannel == null || !sftpChannel.isConnected()) {
                connectSFTP();  // Kết nối SFTP nếu chưa có kết nối
            }

            // Kiểm tra nếu thư mục người dùng không tồn tại trên máy chủ, tạo thư mục đó
            try {
                sftpChannel.cd(remoteDir);  // Thử thay đổi sang thư mục người dùng
            } catch (SftpException e) {
                System.out.println("Thư mục người dùng không tồn tại, tạo mới: " + remoteDir);
                sftpChannel.mkdir(remoteDir);  // Nếu không tồn tại, tạo thư mục
                sftpChannel.cd(remoteDir);  // Đổi sang thư mục vừa tạo
            }

            // Liệt kê các tệp và thư mục trong thư mục người dùng từ máy chủ
            Vector<ChannelSftp.LsEntry> files = sftpChannel.ls(remoteDir);
            if (files != null) {
                // Tạo danh sách riêng cho thư mục và tệp
                var directories = new ArrayList<ChannelSftp.LsEntry>();
                var regularFiles = new ArrayList<ChannelSftp.LsEntry>();

                for (ChannelSftp.LsEntry entry : files) {
                    // Kiểm tra nếu là thư mục hay tệp
                    if (entry.getAttrs().isDir()) {
                        directories.add(entry);  // Thêm thư mục vào danh sách thư mục
                    } else {
                        regularFiles.add(entry);  // Thêm tệp vào danh sách tệp
                    }
                }

                // Kết hợp các thư mục và tệp, đảm bảo thư mục xuất hiện trước
                directories.addAll(regularFiles);

                // Thêm các filePanel vào filePanel
                for (ChannelSftp.LsEntry entry : directories) {
                    // Kiểm tra nếu là thư mục hay tệp và tạo panel tương ứng
                    File file = new File(entry.getFilename());
                    JPanel fileItemPanel = createFilePanel(file, entry.getAttrs().isDir());  // Thêm một tham số để xác định loại
                    filePanel.add(fileItemPanel);  // Thêm vào filePanel để hiển thị
                }

                // Điều chỉnh kích thước ưa thích của filePanel
                int rows = (int) Math.ceil(directories.size() / 4.0); // Giả sử 4 cột
                filePanel.setPreferredSize(new Dimension(filePanel.getWidth(), rows * 130)); // Tăng chiều cao để vừa với label

                filePanel.revalidate();
                filePanel.repaint();

                // Cập nhật nhãn đường dẫn
                String relativePath = remoteDir.replace("/cloud/" + userID, "").replace("/", " > ");
                pathLabel.setText((relativePath.isEmpty() ? "" : relativePath));
            }

        } catch (SftpException e) {
            e.printStackTrace();
        }
    }

    private ScheduledExecutorService scheduler; // đưa ra ngoài nếu cần quản lý trong các hàm khác

    private void startBackupService(String username) {
        try {
            // Lấy thông tin người dùng
            userSV userService = new userSV();
            user userToBackup = userService.findUserByUsername(username);

            FolderSV fsv = new FolderSV();

            // Lấy lịch trình từ cột "Schedule"
            String schedule = userToBackup.getSchedule(); // "daily", "weekly", "monthly"

            // Khởi tạo scheduler
            scheduler = Executors.newScheduledThreadPool(1);

            Runnable backupTask = () -> {
                try {
                    // **** LẤY LẠI ĐƯỜNG DẪN MỚI NHẤT MỖI LẦN CHẠY ****
                    String latestFolderPath = userService.getfolder(userToBackup.getId()).getFolderPath();

                    user_storage u_s = new user_storage();
                    storageSV usSV = new storageSV();
                    u_s = usSV.getUserStorage(userToBackup.getId());

                    if (u_s.getUsedStorage() >= u_s.getMaxStorage()) {
                        JOptionPane.showMessageDialog(null,
                                "Quá trình sao lưu tự động đã tạm dừng do bạn đã vượt mức dung lượng lưu trữ tối đa!",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        File folder = new File(latestFolderPath);
                        long after_storage = folder_size(folder) + u_s.getUsedStorage();

                        if (after_storage > u_s.getMaxStorage()) {
                            JOptionPane.showMessageDialog(null,
                                    "Không thể sao lưu thư mục tự động do vượt mức dung lượng lưu trữ tối đa!",
                                    "Warning",
                                    JOptionPane.WARNING_MESSAGE);
                        } else {
                            // Thực hiện sao lưu lên SFTP
                            replaceDirectoryOnSFTP(folder, autoDir);

                            System.out.println("Sao lưu hoàn tất cho thư mục: " + latestFolderPath);

                            // Lấy thời gian hiện tại
                            LocalDateTime now = LocalDateTime.now();
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                            String formattedDateTime = now.format(formatter);

                            // Lưu vào CSDL
                            Folder fd = fsv.getFolder(userToBackup.getId());
                            fsv.updateLastBackup(fd.getId(), formattedDateTime);

                            System.out.println("Last backup at: " + formattedDateTime);

                            // Cập nhật dung lượng và load lại UI
                            updateStorage(RootDir, user_login.getusername());
                            showstorage(RootDir);
                            loadUserFiles();
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            };

            // Lập lịch theo cấu hình
            if (schedule.equalsIgnoreCase("daily")) {
                scheduler.scheduleAtFixedRate(backupTask, 0, 1, TimeUnit.DAYS);
            } else if (schedule.equalsIgnoreCase("weekly")) {
                scheduler.scheduleAtFixedRate(backupTask, 0, 7, TimeUnit.DAYS);
            } else if (schedule.equalsIgnoreCase("monthly")) {
                scheduler.scheduleAtFixedRate(backupTask, 0, 1, TimeUnit.MINUTES); // test nhanh
            } else {
                System.out.println("Lịch trình không hợp lệ: " + schedule);
                scheduler.shutdown();
            }

            System.out.println("Đã bắt đầu dịch vụ sao lưu theo lịch trình cho: " + username);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fullname = new javax.swing.JLabel();
        logout_button = new javax.swing.JButton();
        profile_button = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        uploadFileButton = new javax.swing.JButton();
        uploadFolderButton = new javax.swing.JButton();
        newFolderButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        downloadButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        filePanel = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        pathLabel = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        reset_button = new javax.swing.JButton();
        home_button = new javax.swing.JButton();
        showstorage = new javax.swing.JLabel();
        path = new javax.swing.JLabel();
        update = new javax.swing.JButton();
        schedule = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mega Space");
        setMinimumSize(new java.awt.Dimension(1100, 650));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(1100, 660));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(249, 249, 249));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon("T:\\JavaNetbeans\\Cloud\\img\\smalllogo.png")); // NOI18N
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 40, 40));

        jLabel2.setFont(new java.awt.Font("Trebuchet MS", 1, 20)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(51, 102, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Mega Space");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 0, 120, 40));

        fullname.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        fullname.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        fullname.setText("Fullname");
        jPanel2.add(fullname, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 0, 300, 40));

        logout_button.setBackground(new java.awt.Color(255, 51, 51));
        logout_button.setForeground(new java.awt.Color(255, 255, 255));
        logout_button.setText("Log out");
        logout_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logout_buttonActionPerformed(evt);
            }
        });
        jPanel2.add(logout_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 0, 80, 40));

        profile_button.setBackground(new java.awt.Color(247, 247, 247));
        profile_button.setIcon(new javax.swing.ImageIcon("T:\\JavaNetbeans\\Cloud\\img\\user1.png")); // NOI18N
        profile_button.setBorder(null);
        profile_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profile_buttonActionPerformed(evt);
            }
        });
        jPanel2.add(profile_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 0, -1, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 40));

        jPanel3.setBackground(new java.awt.Color(0, 153, 255));
        jPanel3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        uploadFileButton.setIcon(new javax.swing.ImageIcon("T:\\JavaNetbeans\\Cloud\\img\\Upload.png")); // NOI18N
        uploadFileButton.setText("Upload File");
        uploadFileButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        uploadFileButton.setMargin(new java.awt.Insets(2, 0, 3, 0));
        uploadFileButton.setPreferredSize(new java.awt.Dimension(115, 50));
        uploadFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadFileButtonActionPerformed(evt);
            }
        });
        jPanel3.add(uploadFileButton);

        uploadFolderButton.setIcon(new javax.swing.ImageIcon("T:\\JavaNetbeans\\Cloud\\img\\Upload.png")); // NOI18N
        uploadFolderButton.setText("Upload Folder");
        uploadFolderButton.setToolTipText("Upload Folder");
        uploadFolderButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        uploadFolderButton.setMargin(new java.awt.Insets(2, 0, 3, 0));
        uploadFolderButton.setPreferredSize(new java.awt.Dimension(115, 50));
        uploadFolderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uploadFolderButtonActionPerformed(evt);
            }
        });
        jPanel3.add(uploadFolderButton);

        newFolderButton.setIcon(new javax.swing.ImageIcon("T:\\JavaNetbeans\\Cloud\\img\\newfolderbutton.png")); // NOI18N
        newFolderButton.setText("  New Folder");
        newFolderButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        newFolderButton.setMargin(new java.awt.Insets(2, 0, 3, 0));
        newFolderButton.setPreferredSize(new java.awt.Dimension(115, 50));
        newFolderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newFolderButtonActionPerformed(evt);
            }
        });
        jPanel3.add(newFolderButton);

        deleteButton.setIcon(new javax.swing.ImageIcon("T:\\JavaNetbeans\\Cloud\\img\\Recycle Bin.png")); // NOI18N
        deleteButton.setText("  Delete");
        deleteButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        deleteButton.setMargin(new java.awt.Insets(2, 0, 3, 0));
        deleteButton.setPreferredSize(new java.awt.Dimension(115, 50));
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });
        jPanel3.add(deleteButton);

        downloadButton.setIcon(new javax.swing.ImageIcon("T:\\JavaNetbeans\\Cloud\\img\\Download.png")); // NOI18N
        downloadButton.setText("  Download");
        downloadButton.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        downloadButton.setMargin(new java.awt.Insets(2, 0, 3, 0));
        downloadButton.setPreferredSize(new java.awt.Dimension(115, 50));
        downloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadButtonActionPerformed(evt);
            }
        });
        jPanel3.add(downloadButton);

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 80, 130, 570));

        jScrollPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setToolTipText("");
        jScrollPane1.setViewportBorder(javax.swing.BorderFactory.createEtchedBorder());
        jScrollPane1.setColumnHeaderView(null);
        jScrollPane1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setMaximumSize(new java.awt.Dimension(1000, 608));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(12, 12));

        filePanel.setBackground(new java.awt.Color(255, 255, 255));
        filePanel.setBorder(null);
        filePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        jScrollPane1.setViewportView(filePanel);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 100, 970, 550));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        pathLabel.setBackground(new java.awt.Color(255, 255, 255));
        pathLabel.setFont(new java.awt.Font("Segoe UI Black", 0, 12)); // NOI18N
        pathLabel.setText("...................................................................................................................");
        pathLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pathLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(pathLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 40, 550, 40));

        backButton.setIcon(new javax.swing.ImageIcon("T:\\JavaNetbeans\\Cloud\\img\\Left.png")); // NOI18N
        backButton.setBorder(null);
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        jPanel1.add(backButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 37, 32));

        reset_button.setIcon(new javax.swing.ImageIcon("T:\\JavaNetbeans\\Cloud\\img\\Reset.png")); // NOI18N
        reset_button.setBorder(null);
        reset_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reset_buttonActionPerformed(evt);
            }
        });
        jPanel1.add(reset_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 40, 32, 32));

        home_button.setIcon(new javax.swing.ImageIcon("T:\\JavaNetbeans\\Cloud\\img\\homeicon.png")); // NOI18N
        home_button.setBorder(null);
        home_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                home_buttonActionPerformed(evt);
            }
        });
        jPanel1.add(home_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 40, 40, 30));

        showstorage.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        showstorage.setText("1024 / 1024 GB");
        jPanel1.add(showstorage, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 80, 254, 20));

        path.setBackground(new java.awt.Color(204, 204, 204));
        path.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        path.setText("file path");
        jPanel1.add(path, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 70, 382, 20));

        update.setText("Cập Nhật");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });
        jPanel1.add(update, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 50, 80, 40));

        schedule.setBackground(new java.awt.Color(255, 255, 255));
        schedule.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        schedule.setText("schedule");
        jPanel1.add(schedule, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 50, 240, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 650));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void reset_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reset_buttonActionPerformed
        // TODO add your handling code here:
        showstorage(RootDir);
        loadUserFiles();
    }//GEN-LAST:event_reset_buttonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        // TODO add your handling code here:
        // Kiểm tra nếu stack previousDirs không rỗng
        if (!previousDirs.isEmpty()) {
            // Lấy thư mục trước đó từ stack
            String previousDir = previousDirs.pop();
            try {
                // Chuyển đến thư mục đã lưu trước đó
                sftpChannel.cd(previousDir);
                remoteDir = previousDir;  // Cập nhật remoteDir với thư mục trước đó
                loadUserFiles();  // Tải lại các tệp trong thư mục trước đó
            } catch (SftpException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(home.this, "Error navigating back: " + e.getMessage());
            }
        }
    }//GEN-LAST:event_backButtonActionPerformed

    private void downloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downloadButtonActionPerformed
        // TODO add your handling code here:
        // Kiểm tra nếu có tệp hoặc thư mục được chọn
        if (selectedFile != null) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new File(selectedFile.getName()));

            // Hiển thị hộp thoại lưu tệp để chọn đường dẫn lưu
            if (fileChooser.showSaveDialog(home.this) == JFileChooser.APPROVE_OPTION) {
                File destinationFile = fileChooser.getSelectedFile();

                // Đường dẫn trên máy ảo của tệp hoặc thư mục đã chọn
                String remotePath = remoteDir + "/" + selectedFile.getName();

                try {
                    // Kiểm tra nếu đó là thư mục
                    if (sftpChannel.lstat(remotePath).isDir()) {
                        // Nếu là thư mục, tải xuống toàn bộ thư mục và nội dung của nó
                        downloadFolder(remotePath, destinationFile);
                        JOptionPane.showMessageDialog(home.this, "Folder downloaded: " + destinationFile.getAbsolutePath());
                    } else {
                        // Nếu là tệp, tải tệp về máy tính
                        sftpChannel.get(remotePath, destinationFile.getAbsolutePath());
                        JOptionPane.showMessageDialog(home.this, "File downloaded: " + destinationFile.getAbsolutePath());
                    }

                    // Tải lại danh sách các tệp sau khi tải xuống
                    loadUserFiles();

                } catch (SftpException | IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(home.this, "Error downloading: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(home.this, "Please select a file or folder to download.");
        }
    }//GEN-LAST:event_downloadButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // Kiểm tra nếu có tệp hoặc thư mục được chọn
        if (selectedFile != null) {
            // Hiển thị hộp thoại xác nhận trước khi xóa
            int confirm = JOptionPane.showConfirmDialog(home.this,
                    "Are you sure you want to delete " + selectedFile.getName() + "?",
                    "Confirm Deletion",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE);

            // Nếu người dùng chọn "Yes", tiến hành xóa
            if (confirm == JOptionPane.YES_OPTION) {
                // Đường dẫn tệp hoặc thư mục trên máy ảo
                String remotePath = remoteDir + "/" + selectedFile.getName();

                try {
                    // Kiểm tra nếu đó là thư mục
                    if (sftpChannel.lstat(remotePath).isDir()) {
                        // Nếu là thư mục, xóa tất cả các tệp trong thư mục trước
                        deleteFilesInDirectory(remotePath);  // Xóa các tệp trong thư mục
                        sftpChannel.rmdir(remotePath);  // Sau đó xóa thư mục chính
                        JOptionPane.showMessageDialog(home.this, "Deleted directory: " + selectedFile.getName());
                    } else {
                        // Nếu là tệp, xóa trực tiếp
                        sftpChannel.rm(remotePath);
                        //                    JOptionPane.showMessageDialog(home.this, "Deleted file: " + selectedFile.getName());
                    }

                    //cập nhật dung lượng lên CSDL
                    updateStorage(RootDir, user_login.getusername());

                    // Tải lại danh sách các file sau khi xóa
                    showstorage(RootDir);
                    loadUserFiles();

                } catch (SftpException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(home.this, "Error deleting: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(home.this, "Please select a file or folder to delete.");
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void newFolderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newFolderButtonActionPerformed
        // TODO add your handling code here:
        //Kiểm tra dung lượng:
        user_storage u_s = new user_storage();
        storageSV usSV = new storageSV();
        u_s = usSV.getUserStorage(user_login.getId());
        if (u_s.getUsedStorage() >= u_s.getMaxStorage()) {
            Storage_Warning();
        } else {
            // Mở hộp thoại yêu cầu người dùng nhập tên thư mục mới
            String folderName = JOptionPane.showInputDialog(home.this, "Enter new folder name:");

            if (folderName != null && !folderName.trim().isEmpty()) {
                // Đường dẫn thư mục mới trên máy ảo
                String remoteNewFolderPath = remoteDir + "/" + folderName;

                try {
                    // Đảm bảo kết nối SFTP đã được thiết lập
                    if (sftpChannel == null || !sftpChannel.isConnected()) {
                        connectSFTP();  // Kết nối SFTP nếu chưa có kết nối
                    }

                    // Tạo thư mục mới trên máy ảo
                    sftpChannel.mkdir(remoteNewFolderPath);

                    // Thông báo thành công
                    //            JOptionPane.showMessageDialog(home.this, "Folder created: " + remoteNewFolderPath);
                    // Tải lại các file người dùng để cập nhật giao diện
                    loadUserFiles();

                    //cập nhật dung lượng lên CSDL
                    updateStorage(RootDir, user_login.getusername());

                } catch (SftpException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(home.this, "Error creating folder on SFTP: " + e.getMessage());
                }
            }
        }
    }//GEN-LAST:event_newFolderButtonActionPerformed

    private void uploadFolderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadFolderButtonActionPerformed
        // TODO add your handling code here:
        //Kiểm tra dung lượng:
        user_storage u_s = new user_storage();
        storageSV usSV = new storageSV();
        u_s = usSV.getUserStorage(user_login.getId());
        if (u_s.getUsedStorage() >= u_s.getMaxStorage()) {
            Storage_Warning();
        } else {
            // Mở JFileChooser để chọn thư mục
            JFileChooser folderChooser = new JFileChooser();
            folderChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (folderChooser.showOpenDialog(home.this) == JFileChooser.APPROVE_OPTION) {

                File folder = folderChooser.getSelectedFile();
                long after_storage = folder_size(folder) + u_s.getUsedStorage();
                System.out.println("asdasdasd" + folder_size(folder) + "asdasd");
                if (after_storage > u_s.getMaxStorage()) {
                    JOptionPane.showMessageDialog(null,
                            "Không thể tải lên do vượt mức dung lượng lưu trữ tối đa!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    // Xây dựng đường dẫn thư mục đích trên máy ảo
                    String remoteFolderPath = remoteDir + "/" + folder.getName();  // Sử dụng remoteDir làm thư mục gốc trên máy ảo

                    try {
                        // Đảm bảo kết nối SFTP đã được thiết lập
                        if (sftpChannel == null || !sftpChannel.isConnected()) {
                            connectSFTP();  // Kết nối SFTP nếu chưa có kết nối
                        }

                        // Kiểm tra và tạo thư mục trên máy ảo (nếu chưa tồn tại)
                        try {
                            sftpChannel.cd(remoteFolderPath);  // Kiểm tra thư mục đã tồn tại trên máy ảo chưa
                        } catch (SftpException e) {
                            // Nếu không tồn tại, tạo thư mục
                            sftpChannel.mkdir(remoteFolderPath);
                        }

                        // Duyệt qua tất cả các tệp và thư mục trong thư mục người dùng
                        uploadFolderToSFTP(folder, remoteFolderPath);
                        //cập nhật dung lượng lên CSDL
                        updateStorage(RootDir, user_login.getusername());

                        //                JOptionPane.showMessageDialog(home.this, "Folder uploaded successfully to: " + remoteFolderPath);
                        // Tải lại các file người dùng để cập nhật giao diện
                        showstorage(RootDir);
                        loadUserFiles();

                        

                    } catch (SftpException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(home.this, "Error uploading folder: " + e.getMessage());
                    }
                }
            }
        }
    }//GEN-LAST:event_uploadFolderButtonActionPerformed

    private void uploadFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_uploadFileButtonActionPerformed
        // TODO add your handling code here:
        //Kiểm tra dung lượng:
        user_storage u_s = new user_storage();
        storageSV usSV = new storageSV();
        u_s = usSV.getUserStorage(user_login.getId());
        if (u_s.getUsedStorage() >= u_s.getMaxStorage()) {
            Storage_Warning();
        } else {
            // Mở JFileChooser để chọn file
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(home.this) == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();

                long after_storage = file.length() + u_s.getUsedStorage();

                if (after_storage > u_s.getMaxStorage()) {
                    JOptionPane.showMessageDialog(null,
                            "Không thể tải lên do vượt mức dung lượng lưu trữ tối đa!",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                } else {
                    // Đường dẫn đích của tệp trong thư mục hiện hành trên máy chủ SFTP
                    String remoteFilePath = remoteDir + "/" + file.getName();

                    try {
                        // Đảm bảo kết nối SFTP đã được thiết lập
                        if (sftpChannel == null || !sftpChannel.isConnected()) {
                            connectSFTP();  // Kết nối SFTP nếu chưa có kết nối
                        }

                        // Tải tệp lên máy chủ SFTP
                        sftpChannel.put(file.getAbsolutePath(), remoteFilePath);

                        //cập nhật dung lượng lên CSDL
                        updateStorage(RootDir, user_login.getusername());

                        // Tải lại các file người dùng để cập nhật giao diện
                        showstorage(RootDir);
                        loadUserFiles();

                    } catch (SftpException e) {
                        e.printStackTrace();
                        JOptionPane.showMessageDialog(home.this, "Error uploading file: " + e.getMessage());
                    }
                }
            }
        }
    }//GEN-LAST:event_uploadFileButtonActionPerformed

    private void profile_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profile_buttonActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new profile().setVisible(true);
    }//GEN-LAST:event_profile_buttonActionPerformed

    private void logout_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logout_buttonActionPerformed
        // TODO add your handling code here:
        int x = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc muốn đăng xuất?");
        if (x == JOptionPane.YES_OPTION) {
            // Dừng scheduler cũ nếu đang chạy
            if (scheduler != null && !scheduler.isShutdown()) {
                scheduler.shutdownNow();
            }
            this.setVisible(false);
            new Cloud().setVisible(true);
        } else {

        }
    }//GEN-LAST:event_logout_buttonActionPerformed

    private void home_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_home_buttonActionPerformed
        // TODO add your handling code here:
        remoteDir = "/cloud/" + userID;
        loadUserFiles();
    }//GEN-LAST:event_home_buttonActionPerformed

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        // TODO add your handling code here:         
        
    try {
        userSV usv = new userSV();
        user u = usv.findUserByUsername(user_login.getusername());

        FolderSV fdsv = new FolderSV();
        Folder fd = fdsv.getFolder(user_login.getId());

        JDialog dialog = new JDialog(this, "Cập nhật", true);
        dialog.setSize(580, 400);
        dialog.setLocationRelativeTo(null);
        dialog.setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 18);
        Font textFont  = new Font("Segoe UI", Font.PLAIN, 17);
        Color labelColor = new Color(102, 204, 255); // màu xanh sáng hơn cho Dark Mode

        // ----- Folder path -----
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblFolderPath = new JLabel("Thư mục sao lưu tự động:");
        lblFolderPath.setFont(labelFont);
        lblFolderPath.setForeground(labelColor);
        panel.add(lblFolderPath, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JTextField txtFolderPath = new JTextField(fd.getFolderPath());
        txtFolderPath.setFont(textFont);
        txtFolderPath.setEditable(false);
        txtFolderPath.setPreferredSize(new Dimension(350, 40));
        panel.add(txtFolderPath, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        JButton btnPath = new JButton("Chọn", UIManager.getIcon("FileView.directoryIcon"));
        btnPath.setFont(textFont);
        btnPath.setPreferredSize(new Dimension(110, 40));
        panel.add(btnPath, gbc);

        btnPath.addActionListener(e2 -> {
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (fc.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                File newFolder = fc.getSelectedFile();
                txtFolderPath.setText(newFolder.getAbsolutePath());
            }
        });

        // ----- Schedule -----
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel lblSchedule = new JLabel("Chu kỳ sao lưu:");
        lblSchedule.setFont(labelFont);
        lblSchedule.setForeground(labelColor);
        panel.add(lblSchedule, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        JComboBox<String> scheduleComboBox = new JComboBox<>(new String[]{"daily", "weekly", "monthly"});
        scheduleComboBox.setFont(textFont);
        scheduleComboBox.setPreferredSize(new Dimension(350, 40));
        scheduleComboBox.setSelectedItem(u.getSchedule());
        panel.add(scheduleComboBox, gbc);

        // ----- Button save/cancel -----
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        JButton btnSave = new JButton("Lưu", UIManager.getIcon("FileView.floppyDriveIcon"));
        JButton btnCancel = new JButton("Hủy", UIManager.getIcon("OptionPane.errorIcon"));
        btnSave.setFont(textFont);
        btnCancel.setFont(textFont);
        btnSave.setPreferredSize(new Dimension(120, 42));
        btnCancel.setPreferredSize(new Dimension(120, 42));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        btnSave.addActionListener(e -> {
            try {
                String newFolderPath = txtFolderPath.getText();
                fd.setFolderPath(newFolderPath);
                fdsv.updateFolder(fd);

                String newSchedule = String.valueOf(scheduleComboBox.getSelectedItem());
                u.setSchedule(newSchedule);
                usv.updateuserschedule(u);

                JOptionPane.showMessageDialog(dialog, "Cập nhật thành công!");
                // Dừng scheduler cũ nếu đang chạy
            if (scheduler != null && !scheduler.isShutdown()) {
                scheduler.shutdownNow();
            }
                startBackupService(user_login.getusername());
                dialog.dispose();
                showtest();
                showSchedule();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(dialog, "Có lỗi xảy ra!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btnCancel.addActionListener(e -> dialog.dispose());

        dialog.add(panel, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);


        dialog.setVisible(true);

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Lỗi mở form cập nhật!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_updateActionPerformed

    // Hàm hỗ trợ thêm dòng
    private int addFormRow(JPanel panel, GridBagConstraints gbc, int row, String labelText, JComponent input, Font labelFont) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel label = new JLabel(labelText);
        label.setFont(labelFont);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 2;
        input.setPreferredSize(new Dimension(300, 30));
        panel.add(input, gbc);

        gbc.gridwidth = 1; // reset lại
        return row + 1;
    }

    // Phương thức để tải thư mục và các tệp trong thư mục lên SFTP
    private void uploadFolderToSFTP(File folder, String remoteFolderPath) throws SftpException {
        // Lặp qua tất cả các tệp và thư mục trong thư mục hiện tại
        for (File file : folder.listFiles()) {
            String remoteFilePath = remoteFolderPath + "/" + file.getName();  // Đường dẫn trên máy ảo

            if (file.isDirectory()) {
                // Nếu là thư mục, gọi đệ quy để tải các thư mục con
                try {
                    sftpChannel.mkdir(remoteFilePath);  // Tạo thư mục con trên máy ảo
                } catch (SftpException e) {
                    // Thư mục đã tồn tại, bỏ qua lỗi này
                }
                // Tiếp tục tải các tệp trong thư mục con
                uploadFolderToSFTP(file, remoteFilePath);
            } else {
                // Nếu là tệp, tải tệp lên máy ảo
                sftpChannel.put(file.getAbsolutePath(), remoteFilePath);  // Tải tệp lên máy ảo
            }
        }
    }

    // Phương thức xóa các tệp trong thư mục
    private void deleteFilesInDirectory(String remoteDir) throws SftpException {
        // Liệt kê các mục trong thư mục
        Vector<ChannelSftp.LsEntry> files = sftpChannel.ls(remoteDir);

        // Xóa tất cả các tệp trong thư mục trước
        for (ChannelSftp.LsEntry entry : files) {
            String entryPath = remoteDir + "/" + entry.getFilename();

            if (entry.getAttrs().isDir()) {
                // Nếu là thư mục, gọi lại phương thức xóa các tệp trong thư mục con
                deleteFilesInDirectory(entryPath);  // Đệ quy để xóa các tệp trong thư mục con
                sftpChannel.rmdir(entryPath);  // Sau khi xóa tệp, xóa thư mục con
            } else {
                // Nếu là tệp, xóa tệp
                sftpChannel.rm(entryPath);
            }
        }

    }

    // Phương thức tải thư mục từ máy ảo về máy tính
    private void downloadFolder(String remoteDir, File folowDir) throws SftpException, IOException {
        // Tạo thư mục đích nếu chưa tồn tại
        if (!folowDir.exists()) {
            folowDir.mkdirs();
        }

        // Liệt kê các tệp và thư mục trong thư mục đích trên máy ảo
        Vector<ChannelSftp.LsEntry> files = sftpChannel.ls(remoteDir);
        for (ChannelSftp.LsEntry entry : files) {
            String remoteFilePath = remoteDir + "/" + entry.getFilename();
            File localFilePath = new File(folowDir, entry.getFilename());

            if (entry.getAttrs().isDir()) {
                // Nếu là thư mục, gọi đệ quy để tải thư mục con
                downloadFolder(remoteFilePath, localFilePath);
            } else {
                // Nếu là tệp, tải tệp về
                sftpChannel.get(remoteFilePath, localFilePath.getAbsolutePath());
            }
        }
    }

    private void showtest() {
//        String pwd = "Đang chọn: " + selectedFile.getName();
//        path.setText(pwd);

        //show folder path
        userSV userService = new userSV();
        user User = userService.findUserByUsername(user_login.getusername());
        Folder folder = userService.getfolder(User.getId());
        String fdpath = folder.getFolderPath();

        //path.setFont(new Font("Arial", Font.PLAIN, 12));
        if (fdpath != null) {
            path.setText("<html>Thư mục sao lưu tự động: <b>" + folder.getFolderPath() + "</b></html>");
        } else {
            path.setText("<html>Thư mục sao lưu tự động: <b>Chưa có</b></html>");
        }

    }

    private void showSchedule() {
        userSV userService = new userSV();
        user User = userService.findUserByUsername(user_login.getusername());

        schedule.setText("<html>Lịch trình sao lưu: <b>" + User.getSchedule() + "</b></html>");
    }

    private void updateStorage(String Dir, String username) {
        long dungluongbyte = getDirectorySize(Dir);

        //tìm user id
        user u = new user();
        userSV usv = new userSV();
        u = usv.findUserByUsername(username);
        int userid = u.getId();

        //cập nhật dung lượng đã dùng lên CSDL
        storageSV ssv = new storageSV();
        ssv.updateUsedStorage(userid, dungluongbyte);

        System.out.println("đã cập nhật dung lượng!");
    }

    private void showstorage(String remoteDir) {
        storageSV ssv = new storageSV();
        long max_storage = ssv.getUserStorage(user_login.getId()).getMaxStorage();
        String dungluongmax = formatstorage(max_storage);
        long dungluongbyte = getDirectorySize(remoteDir);
        String dungluong = formatstorage(dungluongbyte);

        //kiểm tra đổi màu đỏ
        if (ssv.getUserStorage(user_login.getId()).getUsedStorage() >= ssv.getUserStorage(user_login.getId()).getMaxStorage()) {
            showstorage.setText("️⛔ Dung lượng lưu trữ: " + dungluong + " / " + dungluongmax);
            showstorage.setForeground(Color.red);
        } else {
            showstorage.setText("Dung lượng lưu trữ: " + dungluong + " / " + dungluongmax);
            showstorage.setForeground(Color.BLACK);
        }
    }

    // Hàm lấy dung lượng file từ SFTP
    private long getFileSizeFromSFTP(String fileName) {
        long fileSize = 0;
        try {
            // Lấy thông tin tệp từ SFTP
            ChannelSftp.LsEntry fileEntry = null;
            Vector<ChannelSftp.LsEntry> fileList = sftpChannel.ls(remoteDir);

            for (ChannelSftp.LsEntry entry : fileList) {
                if (entry.getFilename().equals(fileName)) {
                    fileEntry = entry;
                    break;
                }
            }

            if (fileEntry != null) {
                fileSize = fileEntry.getAttrs().getSize();  // Lấy dung lượng tệp
            }
        } catch (SftpException e) {
            e.printStackTrace();
        }
        return fileSize;
    }

    // Hàm tính dung lượng của thư mục từ xa (bao gồm các thư mục con)
    private long getDirectorySize(String directoryPath) {
        long totalSize = 0;

        try {
            Vector<ChannelSftp.LsEntry> files = sftpChannel.ls(directoryPath);  // Lấy tất cả các file và thư mục trong thư mục hiện tại

            for (ChannelSftp.LsEntry entry : files) {
                if (!entry.getAttrs().isDir()) {
                    // Nếu là file, cộng dung lượng của file vào tổng
                    totalSize += entry.getAttrs().getSize();
                } else if (!entry.getFilename().equals(".") && !entry.getFilename().equals("..")) {
                    // Nếu là thư mục, đệ quy tính dung lượng của thư mục con
                    totalSize += getDirectorySize(directoryPath + "/" + entry.getFilename());
                }
            }
        } catch (SftpException e) {
            e.printStackTrace();
        }

        return totalSize;
    }

    // Hàm format dung lượng
    private String formatstorage(long fileSize) {
        if (fileSize < 1024) {
            return fileSize + " Bytes";
        } else if (fileSize < 1024 * 1024) {
            double kilobyte = fileSize / 1024.0;
            return String.format("%.2f KB", kilobyte);
        } else if (fileSize < 1024 * 1024 * 1024) {
            double megabyte = fileSize / (1024.0 * 1024);
            return String.format("%.2f MB", megabyte);
        } else if (fileSize < 1024L * 1024L * 1024L * 1024L) {
            double gigabyte = fileSize / (1024.0 * 1024 * 1024);
            return String.format("%.2f GB", gigabyte);
        } else {
            double terabyte = fileSize / (1024.0 * 1024 * 1024 * 1024);
            return String.format("%.2f TB", terabyte);
        }
    }

    private long folder_size(File file) {
        if (file.isFile()) {
            return file.length();
        }

        long size = 0;
        File[] files = file.listFiles();
        if (files != null) {
            for (File f : files) {
                size += folder_size(f);
            }
        }
        return size;
    }

    private void Storage_Warning() {
        JOptionPane.showMessageDialog(
                null,
                "Bạn đã vượt mức dung lượng tối đa. Hãy giải phóng bớt dung lượng lưu trữ!",
                "Cảnh báo",
                JOptionPane.WARNING_MESSAGE
        );

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                new home().setVisible(true);
            }
        });

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton backButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JButton downloadButton;
    private javax.swing.JPanel filePanel;
    private javax.swing.JLabel fullname;
    private javax.swing.JButton home_button;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton logout_button;
    private javax.swing.JButton newFolderButton;
    private javax.swing.JLabel path;
    private javax.swing.JLabel pathLabel;
    private javax.swing.JButton profile_button;
    private javax.swing.JButton reset_button;
    private javax.swing.JLabel schedule;
    private javax.swing.JLabel showstorage;
    private javax.swing.JButton update;
    private javax.swing.JButton uploadFileButton;
    private javax.swing.JButton uploadFolderButton;
    // End of variables declaration//GEN-END:variables
}
