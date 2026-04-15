/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import model.user;
import service.userSV;
import model.Folder;
import service.FolderSV;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractAction;
import javax.swing.JProgressBar;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import model.user_storage;
import service.storageSV;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author ASUS
 */
public class admin extends javax.swing.JFrame {

    /**
     * Creates new form admin
     */
    
    //private double Server_storage = 109951162777600L;
        //private double Server_storage = 10737418240L;
        private double Server_storage = 26843545600L;
        
    public admin() {
        initComponents();
        this.setLocationRelativeTo(null);
        users_manage.setBackground(defaultColor);
        set_lich.setBackground(defaultColor);
        accept_btn.setBackground(defaultColor);
        storage_manager.setBackground(defaultColor);

        // Thêm tiêu đề
        title.setFont(new Font("Times New Roman", Font.BOLD, 25));
        title.setForeground(new java.awt.Color(255, 255, 255));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Khoảng cách trên-dưới

        users_manageActionPerformed(null);

    }

    // Định nghĩa màu
    Color defaultColor = Color.WHITE; // Màu mặc định
    Color selectedColor = new Color(0, 242, 255);     // Màu sáng khi được chọn

    // Hàm để làm nút sáng màu
    private void highlightButton(JButton selectedButton) {
        // Đặt tất cả các nút về màu mặc định
        users_manage.setBackground(defaultColor);
        set_lich.setBackground(defaultColor);
        accept_btn.setBackground(defaultColor);
        storage_manager.setBackground(defaultColor);

        // Đặt màu sáng cho nút được chọn
        selectedButton.setBackground(selectedColor);
    }

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
        users_manage = new javax.swing.JButton();
        set_lich = new javax.swing.JButton();
        accept_btn = new javax.swing.JButton();
        storage_manager = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        main_panel = new javax.swing.JPanel();
        jDesktopPane1 = new javax.swing.JDesktopPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        content_panel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(1100, 650));
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
        fullname.setText("Administrator");
        fullname.setToolTipText("");
        fullname.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel2.add(fullname, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 0, 260, 40));

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
        profile_button.setIcon(new javax.swing.ImageIcon("T:\\JavaNetbeans\\Cloud\\img\\Male User.png")); // NOI18N
        profile_button.setBorder(null);
        profile_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                profile_buttonActionPerformed(evt);
            }
        });
        jPanel2.add(profile_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 0, -1, 40));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 40));

        jPanel3.setBackground(new java.awt.Color(0, 153, 255));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 10));

        users_manage.setText("Users Manage");
        users_manage.setPreferredSize(new java.awt.Dimension(110, 40));
        users_manage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                users_manageActionPerformed(evt);
            }
        });
        jPanel3.add(users_manage);

        set_lich.setText("Schedule");
        set_lich.setPreferredSize(new java.awt.Dimension(110, 40));
        set_lich.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                set_lichActionPerformed(evt);
            }
        });
        jPanel3.add(set_lich);

        accept_btn.setText("Accept Folder");
        accept_btn.setPreferredSize(new java.awt.Dimension(110, 40));
        accept_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accept_btnActionPerformed(evt);
            }
        });
        jPanel3.add(accept_btn);

        storage_manager.setText("Storage");
        storage_manager.setPreferredSize(new java.awt.Dimension(110, 40));
        storage_manager.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                storage_managerActionPerformed(evt);
            }
        });
        jPanel3.add(storage_manager);

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 130, 540));

        jPanel4.setBackground(new java.awt.Color(0, 153, 255));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("Title");
        jPanel4.add(title, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 20, 490, 40));

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 1100, 70));

        main_panel.setBackground(new java.awt.Color(255, 255, 255));
        main_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jDesktopPane1.setBackground(new java.awt.Color(255, 255, 255));
        jDesktopPane1.setPreferredSize(new java.awt.Dimension(920, 570));
        jDesktopPane1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        content_panel.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout content_panelLayout = new javax.swing.GroupLayout(content_panel);
        content_panel.setLayout(content_panelLayout);
        content_panelLayout.setHorizontalGroup(
            content_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 968, Short.MAX_VALUE)
        );
        content_panelLayout.setVerticalGroup(
            content_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 568, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(content_panel);
        content_panel.setBackground(java.awt.Color.WHITE);

        jDesktopPane1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 30, 970, 540));

        main_panel.add(jDesktopPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 570));

        jPanel1.add(main_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 80, 970, 570));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1100, 650));

        getAccessibleContext().setAccessibleName("Mega Space");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logout_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logout_buttonActionPerformed
        // TODO add your handling code here:
        int x = JOptionPane.showConfirmDialog(rootPane, "Bạn có chắc muốn đăng xuất?");
        if (x == JOptionPane.YES_OPTION) {
            this.setVisible(false);
            new Cloud().setVisible(true);
        } else {

        }
    }//GEN-LAST:event_logout_buttonActionPerformed

    private void profile_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_profile_buttonActionPerformed
        // TODO add your handling code here:
        this.setVisible(false);
        new profile().setVisible(true);
    }//GEN-LAST:event_profile_buttonActionPerformed

    private void users_manageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_users_manageActionPerformed
        // TODO add your handling code here:              

        highlightButton(users_manage);
        content_panel.removeAll();
        content_panel.setLayout(new BorderLayout());

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(defaultColor);
        searchPanel.setPreferredSize(new Dimension(680, 40));
        JTextField txtSearch = new JTextField(30);
        JButton btnSearch = new JButton("Tìm");
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        title.setText("QUẢN LÝ NGƯỜI DÙNG");

        // Tạo bảng hiển thị thông tin KHÔNG có cột User ID
        JTable userTable = new JTable(new DefaultTableModel(
                new Object[]{"User Name", "Họ tên", "Chức vụ", "Chu kỳ sao lưu", "Folder Path", "Last Backup"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        userTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        userTable.setRowHeight(35);

        // Căn giữa các cột phù hợp
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        userTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer); // Chu kỳ sao lưu
        userTable.getColumnModel().getColumn(5).setCellRenderer(centerRenderer); // Last Backup
        userTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer); //  User Name

        // Điều chỉnh độ rộng các cột
        userTable.getColumnModel().getColumn(0).setPreferredWidth(80); // User Name
        userTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Họ Tên
        userTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Chức vụ
        userTable.getColumnModel().getColumn(3).setPreferredWidth(100); // Chu kỳ sao lưu
        userTable.getColumnModel().getColumn(4).setPreferredWidth(200); // Folder Path
        userTable.getColumnModel().getColumn(5).setPreferredWidth(130); // Last Backup

        JScrollPane scrollPane = new JScrollPane(userTable);

        userSV userService = new userSV();
        FolderSV folderService = new FolderSV();
        List<user> users = userService.getAllUsers();
        DefaultTableModel model = (DefaultTableModel) userTable.getModel();

        userTable.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0), "deleteUser");

        userTable.getActionMap().put("deleteUser", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = userTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(content_panel, "Vui lòng chọn một người dùng để xóa!");
                    return;
                }

                String username = (String) model.getValueAt(selectedRow, 0); // User Name
                int confirm = JOptionPane.showConfirmDialog(content_panel,
                        "Bạn có chắc chắn muốn xóa người dùng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    userSV userservice = new userSV();
                    userservice.deleteUser(username);
                    List<user> users_update = userservice.getAllUsers();
                    refreshUserTable(users_update, model);
                    JOptionPane.showMessageDialog(content_panel, "Người dùng đã được xóa!");
                }
            }
        });

        // Thêm dữ liệu vào bảng
        for (user user : users) {
            Folder folder = folderService.getFolder(user.getId());
            model.addRow(new Object[]{
                user.getusername(),
                user.getFullname(),
                user.getRole(),
                user.getSchedule(),
                folder != null ? folder.getFolderPath() : "N/A",
                folder != null ? folder.getLastBackup() : ""
            });
        }

        // Xử lý tìm kiếm
        btnSearch.addActionListener(e -> {
            String keyword = txtSearch.getText().trim().toLowerCase();
            List<user> filteredUsers = users.stream()
                    .filter(u -> u.getusername().toLowerCase().contains(keyword)
                    || u.getFullname().toLowerCase().contains(keyword)
                    || u.getRole().toLowerCase().contains(keyword))
                    .toList();
            refreshUserTable(filteredUsers, model);
        });

        // Nút thêm người dùng
        JButton btnAddUser = new JButton("Thêm người dùng");
        btnAddUser.setBackground(defaultColor);
        btnAddUser.addActionListener(e -> showAddUserForm());

        // Nút xóa người dùng
        JButton btnDelete = new JButton("Xóa");
        btnDelete.setBackground(defaultColor);
        btnDelete.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(content_panel, "Vui lòng chọn một người dùng để xóa!");
                return;
            }

            String username = (String) model.getValueAt(selectedRow, 0); // User Name
            int confirm = JOptionPane.showConfirmDialog(content_panel,
                    "Bạn có chắc chắn muốn xóa người dùng này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                userSV userservice = new userSV();
                userservice.deleteUser(username);
                List<user> users_update = userservice.getAllUsers();
                refreshUserTable(users_update, model);
                JOptionPane.showMessageDialog(content_panel, "Người dùng đã được xóa!");
            }
        });

        // Nút sửa người dùng
        JButton btnEdit = new JButton("Sửa");
        btnEdit.setBackground(defaultColor);
        btnEdit.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(content_panel, "Vui lòng chọn một người dùng để sửa!");
                return;
            }

            String username = (String) model.getValueAt(selectedRow, 0); // User Name
            userSV userSVedit = new userSV();
            user userToEdit = userSVedit.findUserByUsername(username);

            if (userToEdit != null) {
                showEditUserForm(userToEdit, model);
            }
        });

        // Panel chứa nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(defaultColor);
        buttonPanel.add(btnAddUser);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(searchPanel);

        // Thêm các thành phần vào content_panel
        content_panel.add(scrollPane, BorderLayout.CENTER);
        content_panel.add(buttonPanel, BorderLayout.NORTH);

        userTable.requestFocusInWindow();
        content_panel.revalidate();
        content_panel.repaint();
    }//GEN-LAST:event_users_manageActionPerformed
// Hàm nạp lại dữ liệu bảng

    private void refreshUserTable(List<user> users, DefaultTableModel model) {
        model.setRowCount(0); // Xóa dữ liệu cũ

        FolderSV folderService = new FolderSV(); // Khởi tạo Folder service để lấy thông tin thư mục

        for (user u : users) {
            Folder folder = folderService.getFolder(u.getId()); // Lấy thông tin thư mục từ user ID

            model.addRow(new Object[]{
                u.getusername(), // User Name
                u.getFullname(), // Họ tên
                u.getRole(), // Chức vụ
                u.getSchedule(), // Chu kỳ sao lưu
                folder != null ? folder.getFolderPath() : "N/A", // Folder Path
                folder != null ? folder.getLastBackup() : "" // Last Backup
            });
        }
    }

// Hàm hiển thị form sửa
    private void showEditUserForm(user userToEdit, DefaultTableModel model) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
            //UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 15));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JDialog editUserDialog = new JDialog(this, "Cập nhật người dùng", true);
        editUserDialog.setSize(600, 400); // Form không quá to
        editUserDialog.setLocationRelativeTo(null);
        editUserDialog.setLayout(new BorderLayout());

        // Tạo các input
        JTextField txtUserName = new JTextField(userToEdit.getusername());
        JPasswordField txtPassword = new JPasswordField();
        JTextField txtFullName = new JTextField(userToEdit.getFullname());
        JTextField txtRole = new JTextField(userToEdit.getRole());

        // Điều chỉnh chiều rộng của ô nhập liệu
        Dimension inputSize = new Dimension(800, 35); // Đã thay đổi chiều rộng thành 800px
        txtUserName.setPreferredSize(inputSize);
        txtPassword.setPreferredSize(inputSize);
        txtFullName.setPreferredSize(inputSize);
        txtRole.setPreferredSize(inputSize);

        // Layout chính
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        GridBagConstraints gbc = new GridBagConstraints();

        // Điều chỉnh khoảng cách giữa các phần tử
        gbc.insets = new Insets(5, 5, 5, 5); // Giảm khoảng cách giữa label và input field

        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Cho phép ô nhập liệu trải rộng

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Color labelColor = new Color(0, 102, 204);

        int row = 0;

        // Tên đăng nhập
        gbc.gridx = 0;
        gbc.gridy = row;
        JLabel labelUserName = createStyledLabel("Tên đăng nhập:", labelFont, labelColor);
        labelUserName.setPreferredSize(new Dimension(150, 35)); // Giới hạn chiều rộng của label
        gbc.weightx = 0.1;  // Đảm bảo label có không gian hợp lý
        mainPanel.add(labelUserName, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridwidth = 2; // Kéo dài ô nhập liệu để nó trải rộng
        gbc.weightx = 1.0;  // Cho phép ô nhập liệu kéo dài
        mainPanel.add(txtUserName, gbc);

        // Mật khẩu
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelPassword = createStyledLabel("Mật khẩu mới:", labelFont, labelColor);
        labelPassword.setPreferredSize(new Dimension(150, 35)); // Giới hạn chiều rộng của label
        gbc.weightx = 0.1;  // Đảm bảo label có không gian hợp lý
        mainPanel.add(labelPassword, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(txtPassword, gbc);

        // Họ tên
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelFullName = createStyledLabel("Họ tên:", labelFont, labelColor);
        labelFullName.setPreferredSize(new Dimension(150, 35)); // Giới hạn chiều rộng của label
        gbc.weightx = 0.1;  // Đảm bảo label có không gian hợp lý
        mainPanel.add(labelFullName, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(txtFullName, gbc);

        // Chức vụ
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.EAST;
        JLabel labelRole = createStyledLabel("Chức vụ:", labelFont, labelColor);
        labelRole.setPreferredSize(new Dimension(150, 35)); // Giới hạn chiều rộng của label
        gbc.weightx = 0.1;  // Đảm bảo label có không gian hợp lý
        mainPanel.add(labelRole, gbc);
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(txtRole, gbc);

        // Nút
        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");

        btnSave.setBackground(new Color(0, 153, 76));
        btnSave.setForeground(Color.WHITE);
        btnCancel.setBackground(new Color(204, 0, 0));
        btnCancel.setForeground(Color.WHITE);

        btnSave.setPreferredSize(new Dimension(100, 40));
        btnCancel.setPreferredSize(new Dimension(100, 40));

        // Hover cho nút
        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSave.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSave.setBackground(new Color(0, 128, 64));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSave.setBackground(new Color(0, 153, 76));
            }
        });

        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnCancel.setBackground(new Color(153, 0, 0));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnCancel.setBackground(new Color(204, 0, 0));
            }
        });

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        // Xử lý sự kiện
        btnSave.addActionListener(e -> {
            userToEdit.setusername(txtUserName.getText().trim());

            String newPassword = new String(txtPassword.getPassword()).trim();
            String newHashPass = BCrypt.hashpw(newPassword, BCrypt.gensalt());
            if (!newPassword.isEmpty()) {
                userToEdit.setPassword(newHashPass); // Chỉ cập nhật nếu có nhập
            }

            userToEdit.setFullname(txtFullName.getText().trim());
            userToEdit.setRole(txtRole.getText().trim());

            userSV userService = new userSV();
            if (userService.checkUsername(userToEdit.getusername())) {
                JOptionPane.showMessageDialog(this, "User Name đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }else{
                userService.updateUser(userToEdit);

            editUserDialog.dispose();

            List<user> users = userService.getAllUsers();
            refreshUserTable(users, model);
            JOptionPane.showMessageDialog(editUserDialog, "Cập nhật thành công!");
            }
            
        });

        btnCancel.addActionListener(e -> editUserDialog.dispose());

        //Set phông to ra
        Font customFont = new Font("Segoe UI", Font.PLAIN, 15);
        txtUserName.setFont(customFont);
        txtPassword.setFont(customFont);
        txtFullName.setFont(customFont);
        txtRole.setFont(customFont);
        labelUserName.setFont(labelFont);
        labelPassword.setFont(labelFont);
        labelFullName.setFont(labelFont);
        labelRole.setFont(labelFont);
        btnSave.setFont(customFont);
        btnCancel.setFont(customFont);

        // Hiển thị
        editUserDialog.add(mainPanel, BorderLayout.CENTER);
        editUserDialog.add(buttonPanel, BorderLayout.SOUTH);
        editUserDialog.setVisible(true);
    }

// Label có style
    private JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        return label;
    }

    private void showAddUserForm() {
        JDialog addUserDialog = new JDialog(this, "➕ Thêm Người Dùng Mới", true);
        addUserDialog.setLayout(new BorderLayout());
        addUserDialog.setSize(600, 450);
        addUserDialog.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        JTextField txtUserName = new JTextField();
        JPasswordField txtPassword = new JPasswordField();
        JTextField txtFullName = new JTextField();
        JTextField txtRole = new JTextField();
        JComboBox<String> cbSchedule = new JComboBox<>(new String[]{"daily", "weekly", "monthly"});
        JComboBox<String> cbKind = new JComboBox<>(new String[]{"Admin", "Máy trạm", "Máy chủ CSDL"});

        txtUserName.setFont(fieldFont);
        txtPassword.setFont(fieldFont);
        txtFullName.setFont(fieldFont);
        txtRole.setFont(fieldFont);
        cbSchedule.setFont(fieldFont);
        cbKind.setFont(fieldFont);

        int row = 0;
        row = addFormRow(mainPanel, gbc, row, "Tên đăng nhập:", txtUserName, labelFont);
        row = addFormRow(mainPanel, gbc, row, "Mật khẩu:", txtPassword, labelFont);
        row = addFormRow(mainPanel, gbc, row, "Họ tên:", txtFullName, labelFont);
        row = addFormRow(mainPanel, gbc, row, "Chức vụ:", txtRole, labelFont);
        row = addFormRow(mainPanel, gbc, row, "Chu kỳ sao lưu:", cbSchedule, labelFont);
//        row = addFormRow(mainPanel, gbc, row, "Loại người dùng:", cbKind, labelFont); // ✅ thêm loại người dùng

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);

        JButton btnSave = new JButton("Lưu");
        JButton btnCancel = new JButton("Hủy");

        btnSave.setFont(labelFont);
        btnCancel.setFont(labelFont);

        btnSave.setBackground(new Color(0, 153, 76));
        btnSave.setForeground(Color.WHITE);
        btnCancel.setBackground(new Color(204, 0, 0));
        btnCancel.setForeground(Color.WHITE);

        btnSave.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCancel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnSave.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnSave.setBackground(new Color(0, 128, 64));
            }

            public void mouseExited(MouseEvent e) {
                btnSave.setBackground(new Color(0, 153, 76));
            }
        });

        btnCancel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btnCancel.setBackground(new Color(153, 0, 0));
            }

            public void mouseExited(MouseEvent e) {
                btnCancel.setBackground(new Color(204, 0, 0));
            }
        });

        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);

        btnSave.addActionListener(e -> {
            String userName = txtUserName.getText().trim();
            String password = new String(txtPassword.getPassword()).trim();
            String fullName = txtFullName.getText().trim();
            String role = txtRole.getText().trim();
            String schedule = (String) cbSchedule.getSelectedItem();
            int kind = 1; // ✅ Lấy giá trị loại người dùng (0-2)

            if (userName.isEmpty() || password.isEmpty() || fullName.isEmpty() || role.isEmpty()) {
                JOptionPane.showMessageDialog(addUserDialog, "Vui lòng điền đầy đủ thông tin!");
                return;
            }

            userSV userService = new userSV();
            if (userService.checkUsername(userName)) {
                JOptionPane.showMessageDialog(addUserDialog, "User Name đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            } else {
                String HashPass = BCrypt.hashpw(password, BCrypt.gensalt());
                user newUser = new user(userName, fullName, HashPass, role, schedule, kind);

                userService.addUser(newUser);

                addUserDialog.dispose();
                JOptionPane.showMessageDialog(addUserDialog, "Người dùng mới đã được thêm!");
                users_manageActionPerformed(null);

                // Tạo thư mục lưu trữ
                user us = userService.findUserByUsername(userName);
                storageSV storagesv = new storageSV();
                storagesv.CreateStorage(us.getId());

                //tạo mới folder
                Folder newFolder = new Folder(us.getId(), "", "", null);
                FolderSV fsv = new FolderSV();
                fsv.addFolder(newFolder);
            }
        });

        btnCancel.addActionListener(e -> addUserDialog.dispose());

        addUserDialog.add(mainPanel, BorderLayout.CENTER);
        addUserDialog.add(buttonPanel, BorderLayout.SOUTH);
        addUserDialog.setVisible(true);
    }

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

    private void listEditableUsers() {
        content_panel.removeAll(); // Xóa giao diện hiện tại
        content_panel.setLayout(new BorderLayout());

        // Tạo bảng với các cột có thể chỉnh sửa
        DefaultTableModel model = new DefaultTableModel(new Object[]{
            "ID", "Tên đăng nhập", "Họ tên", "Schedule", "Folder Path", "Folder Name"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Chỉ cho phép chỉnh sửa cột Schedule, Folder Path, và Folder Name
                return column == 3 || column == 4 || column == 5;
            }
        };

        JTable userTable = new JTable(model);

        // Cài đặt ComboBox cho cột Schedule
        JComboBox<String> scheduleComboBox = new JComboBox<>(new String[]{"daily", "weekly", "monthly"});
        userTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(scheduleComboBox));

        // Lấy danh sách người dùng từ cơ sở dữ liệu
        userSV userService = new userSV();
        FolderSV folderService = new FolderSV();
        List<user> users = userService.getAllUsers();

        for (user user : users) {
            Folder folder = folderService.getFolder(user.getId());
            model.addRow(new Object[]{
                user.getId(),
                user.getusername(),
                user.getFullname(),
                user.getSchedule(),
                folder != null ? folder.getFolderPath() : "",
                folder != null ? folder.getFolderName() : ""
            });
        }

        JScrollPane scrollPane = new JScrollPane(userTable);
        content_panel.add(scrollPane, BorderLayout.CENTER);

        // Lắng nghe sự kiện chỉnh sửa ô trong bảng
        model.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();

            // Lấy dữ liệu từ hàng và cột được chỉnh sửa
            int userId = (int) model.getValueAt(row, 0);

            // Nếu chỉnh sửa cột Schedule
            if (column == 3) {
                String newSchedule = (String) model.getValueAt(row, column);
                user user = userService.findUserByUsername(String.valueOf(userId));
                if (user != null) {
                    user.setSchedule(newSchedule);
                    userService.updateUser(user);
                    JOptionPane.showMessageDialog(content_panel, "Schedule đã được cập nhật!");
                }
            }

            // Nếu chỉnh sửa cột Folder Path hoặc Folder Name
            if (column == 4 || column == 5) {
                String newFolderPath = (String) model.getValueAt(row, 4);
                String newFolderName = (String) model.getValueAt(row, 5);
                Folder folder = folderService.getFolder(userId);

                if (folder == null) {
                    folder = new Folder(userId, newFolderPath, newFolderName, null);
                    folderService.addFolder(folder);
                    JOptionPane.showMessageDialog(content_panel, "Thư mục mới đã được thêm!");
                } else {
                    if (column == 4) {
                        folder.setFolderPath(newFolderPath);
                    }
                    if (column == 5) {
                        folder.setFolderName(newFolderName);
                    }
                    folderService.updateFolder(folder);
                    JOptionPane.showMessageDialog(content_panel, "Thư mục đã được cập nhật!");
                }
            }
        });

        content_panel.revalidate();
        content_panel.repaint();
    }

    private void set_lichActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_set_lichActionPerformed
        // TODO add your handling code here:                   
        highlightButton(set_lich); // Làm nút sáng lên
        content_panel.removeAll(); // Xóa nội dung cũ
        content_panel.setLayout(new BorderLayout());

// Panel tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(defaultColor);
        searchPanel.setPreferredSize(new Dimension(680, 40));
        JTextField txtSearch = new JTextField(30);
        JButton btnSearch = new JButton("Tìm");
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        title.setText("QUẢN LÝ LỊCH TRÌNH SAO LƯU");

// Tạo bảng để chỉnh sửa Schedule (không có cột User ID)
        DefaultTableModel model = new DefaultTableModel(new Object[]{
            "User Name", "Họ tên", "Chức vụ", "Chu kỳ sao lưu"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Chỉ cho phép chỉnh sửa cột Schedule
            }
        };

        JTable scheduleTable = new JTable(model);
        scheduleTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        scheduleTable.setRowHeight(35);

// Căn giữa cột chu kỳ sao lưu
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        scheduleTable.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);

// Điều chỉnh độ rộng các cột
        scheduleTable.getColumnModel().getColumn(0).setPreferredWidth(100); // User Name
        scheduleTable.getColumnModel().getColumn(1).setPreferredWidth(200); // Họ tên
        scheduleTable.getColumnModel().getColumn(2).setPreferredWidth(300); // Chức vụ
        scheduleTable.getColumnModel().getColumn(3).setPreferredWidth(150); // Chu kỳ sao lưu

// Cài đặt ComboBox cho cột Schedule
        JComboBox<String> scheduleComboBox = new JComboBox<>(new String[]{"daily", "weekly", "monthly"});
        scheduleTable.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(scheduleComboBox));

        JScrollPane scrollPane = new JScrollPane(scheduleTable);

// Lấy danh sách người dùng từ cơ sở dữ liệu
        userSV userService = new userSV();
        List<user> users = userService.getAllUsers();
        for (user user : users) {
            model.addRow(new Object[]{
                user.getusername(),
                user.getFullname(),
                user.getRole(),
                user.getSchedule()
            });
        }

// Xử lý nút Tìm kiếm
        btnSearch.addActionListener(e -> {
            String keyword = txtSearch.getText().trim().toLowerCase();
            List<user> filteredUsers = users.stream()
                    .filter(u -> u.getusername().toLowerCase().contains(keyword)
                    || u.getFullname().toLowerCase().contains(keyword)
                    || u.getRole().toLowerCase().contains(keyword))
                    .toList();
            refresh_schedule_table(filteredUsers, model);
        });

// Lắng nghe sự kiện chỉnh sửa Schedule
        model.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (column == 3) {
                String username = (String) model.getValueAt(row, 0);
                String newSchedule = (String) model.getValueAt(row, 3);

                user userToUpdate = userService.findUserByUsername(username);
                if (userToUpdate != null) {
                    userToUpdate.setSchedule(newSchedule);
                    userService.updateuserschedule(userToUpdate);
                }
            }
        });

// Tạo panel chứa tìm kiếm
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        buttonPanel.setPreferredSize(new Dimension(0, 40));
        buttonPanel.setBackground(defaultColor);
        buttonPanel.add(searchPanel, BorderLayout.EAST);

// Thêm vào content panel
        content_panel.add(scrollPane, BorderLayout.CENTER);
        content_panel.add(buttonPanel, BorderLayout.NORTH);

        content_panel.revalidate();
        content_panel.repaint();


    }//GEN-LAST:event_set_lichActionPerformed

    private void refresh_schedule_table(List<user> users, DefaultTableModel model) {
        model.setRowCount(0); // Xóa dữ liệu cũ

        for (user u : users) {
            model.addRow(new Object[]{
                u.getId(), // User ID
                u.getusername(), // User Name
                u.getFullname(), // Họ tên
                u.getRole(), // Chức vụ
                u.getSchedule() // Chu kỳ sao lưu
            });
        }
    }


    private void accept_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accept_btnActionPerformed
        // TODO add your handling code here:                
        // Làm sáng nút Accept Folder
        highlightButton(accept_btn);
        content_panel.removeAll(); // Xóa nội dung cũ
        content_panel.setLayout(new BorderLayout());

        // Panel tìm kiếm
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchPanel.setBackground(defaultColor);
        searchPanel.setPreferredSize(new Dimension(680, 40));
        JTextField txtSearch = new JTextField(30);
        JButton btnSearch = new JButton("Tìm");
//    searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(txtSearch);
        searchPanel.add(btnSearch);

        title.setText("QUẢN LÝ THƯ MỤC SAO LƯU");

        // Tạo bảng hiển thị thông tin người dùng
        DefaultTableModel model = new DefaultTableModel(new Object[]{
            "User ID", "User Name", "Họ Tên", "Chức vụ", "Folder Path"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Chỉ cho phép chỉnh sửa cột Folder Path
                return column == 4;
            }
        };

        JTable folderTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(folderTable);
        //content_panel.add(scrollPane, BorderLayout.CENTER);

        // Thay đổi font chữ và chiều cao hàng
        folderTable.setFont(new Font("Times New Roman", Font.PLAIN, 16)); // Font Arial, cỡ chữ 16
        folderTable.setRowHeight(35); // Chiều cao hàng 25 pixel

        //căn giữa cột
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        // Áp dụng cho cột "User ID" (cột đầu tiên, chỉ số 0)
        folderTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        // Điều chỉnh độ rộng các cột
        folderTable.getColumnModel().getColumn(0).setPreferredWidth(70);  // User ID
        folderTable.getColumnModel().getColumn(1).setPreferredWidth(100); // User Name
        folderTable.getColumnModel().getColumn(2).setPreferredWidth(200); // Họ Tên
        folderTable.getColumnModel().getColumn(3).setPreferredWidth(300); // Chức vụ
        folderTable.getColumnModel().getColumn(4).setPreferredWidth(250); // folder path

        // Lấy danh sách người dùng từ cơ sở dữ liệu
        userSV userService = new userSV();
        FolderSV folderService = new FolderSV();
        List<user> users = userService.getAllClient();

        for (user user : users) {
            Folder folder = folderService.getFolder(user.getId());
            model.addRow(new Object[]{
                user.getId(), // User ID
                user.getusername(), // User Name
                user.getFullname(), // Họ Tên
                user.getRole(), // Chức vụ
                folder != null ? folder.getFolderPath() : "N/A" // Đường dẫn folder
            });
        }

        // Xử lý nút Tìm kiếm
        btnSearch.addActionListener(e -> {
            String keyword = txtSearch.getText().trim().toLowerCase();
            List<user> filteredUsers = users.stream()
                    .filter(u -> u.getusername().toLowerCase().contains(keyword)
                    || u.getFullname().toLowerCase().contains(keyword)
                    || u.getRole().toLowerCase().contains(keyword))
                    .toList();
            refresh_accept_table(filteredUsers, model);
        });

        // Lắng nghe sự kiện chỉnh sửa cột Folder Path
        model.addTableModelListener(e -> {
            int row = e.getFirstRow();
            int column = e.getColumn();
            if (column == 4) { // Nếu cột Folder Path được chỉnh sửa
                int userId = (int) model.getValueAt(row, 0);
                String newFolderPath = (String) model.getValueAt(row, 4);

                // Lấy thông tin thư mục
                Folder folder = folderService.getFolder(userId);
                if (folder == null) {
                    // Nếu thư mục chưa tồn tại, thêm mới
                    folder = new Folder(userId, newFolderPath, "", null);
                    folderService.addFolder(folder);
                    //JOptionPane.showMessageDialog(content_panel, "Thư mục mới đã được thêm!");
                } else {
                    // Nếu thư mục đã tồn tại, cập nhật đường dẫn
                    folder.setFolderPath(newFolderPath);
                    folderService.updateFolder(folder);
                    JOptionPane.showMessageDialog(content_panel, "Đường dẫn folder đã được cập nhật!");
                }
            }
        });

        // Tạo JPanel chứa các nút
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Căn nút về bên phải
        // Chỉ định chiều cao là 40, chiều rộng tối đa
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        buttonPanel.setPreferredSize(new Dimension(0, 40)); // tùy chọn
        buttonPanel.setBackground(defaultColor);
        buttonPanel.add(searchPanel, BorderLayout.EAST);

        // Thêm các thành phần vào content_panel
        content_panel.add(scrollPane, BorderLayout.CENTER);
        content_panel.add(buttonPanel, BorderLayout.NORTH);

        content_panel.revalidate();
        content_panel.repaint();
    }//GEN-LAST:event_accept_btnActionPerformed

    private void refresh_accept_table(List<user> users, DefaultTableModel model) {
        model.setRowCount(0); // Xóa dữ liệu cũ

        FolderSV folderService = new FolderSV(); // Khởi tạo Folder service

        for (user u : users) {
            Folder folder = folderService.getFolder(u.getId()); // Lấy thông tin thư mục

            model.addRow(new Object[]{
                u.getId(), // User ID
                u.getusername(), // User Name
                u.getFullname(), // Họ tên
                u.getRole(), // Chức vụ
                folder != null ? folder.getFolderPath() : "N/A" // Folder Path
            });
        }
    }

    // Renderer cho progress bar
// Custom ProgressRenderer
class ProgressRenderer extends JProgressBar implements TableCellRenderer {
    public ProgressRenderer() {
        super(0, 100);
        setStringPainted(true);
        setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2)); // padding
        
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
                                                   boolean isSelected, boolean hasFocus, int row, int column) {
        int progress = 0;
        if (value instanceof Number) {
            progress = (int) Math.ceil(((Number) value).doubleValue()); // làm tròn lên
        }
        setValue(progress);

        // Đổi màu theo mức %
        if (progress > 90) {
            setForeground(Color.RED); // nguy hiểm
            setFont(getFont().deriveFont(Font.BOLD)); // chữ in đậm
        } else if (progress > 70) {
            setForeground(Color.ORANGE); // cảnh báo
            setFont(getFont().deriveFont(Font.BOLD)); // chữ in đậm
        } else {
            setForeground(new Color(76, 175, 80)); // xanh lá
            setFont(getFont().deriveFont(Font.BOLD)); // chữ in đậm
        }

        return this;
    }
}
    
    private JLabel labelTotalStorage;
    private JLabel labelAllocated;
    private JLabel labelUsed;
    private JLabel labelRemaining;
    
        private JProgressBar progressTotalStorage;
private JProgressBar progressAllocated;
private JProgressBar progressUsed;
    
    private void storage_managerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_storage_managerActionPerformed
     // làm sáng nút button
    highlightButton(storage_manager);
    content_panel.removeAll();
    content_panel.setLayout(new BorderLayout());

    // Panel tìm kiếm
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    searchPanel.setBackground(defaultColor);
    searchPanel.setPreferredSize(new Dimension(870, 35));
    JTextField txtSearch = new JTextField(30);
    JButton btnSearch = new JButton("Tìm");
    searchPanel.add(txtSearch);
    searchPanel.add(btnSearch);

    title.setText("QUẢN LÝ DUNG LƯỢNG NGƯỜI DÙNG");

    // ===== BẢNG USERS =====
    JTable userTable = new JTable(new DefaultTableModel(
            new Object[]{"User Name", "Họ tên", "Chức vụ", "Dung lượng tối đa", "Dung Lượng đã dùng", "Dung lượng còn lại", "   "}, 0
    ) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    });

    userTable.setFont(new Font("Times New Roman", Font.PLAIN, 16));
    userTable.setRowHeight(35);

    DefaultTableCellRenderer paddedRightRenderer = new DefaultTableCellRenderer() {
        private final Border padding = BorderFactory.createEmptyBorder(0, 0, 0, 7);
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            setHorizontalAlignment(JLabel.RIGHT);
            setBorder(padding);
            return c;
        }
    };

    // Áp dụng renderer cho các cột số
    userTable.getColumnModel().getColumn(0).setPreferredWidth(10);
    userTable.getColumnModel().getColumn(2).setPreferredWidth(100);
    userTable.getColumnModel().getColumn(3).setCellRenderer(paddedRightRenderer);
    userTable.getColumnModel().getColumn(4).setCellRenderer(paddedRightRenderer);
    userTable.getColumnModel().getColumn(5).setCellRenderer(paddedRightRenderer);
    userTable.getColumnModel().getColumn(6).setPreferredWidth(40);

    JScrollPane scrollPane = new JScrollPane(userTable);

    // ===== KHỞI TẠO SUMMARY COMPONENTS NẾU CHƯA =====
    if (labelTotalStorage == null) labelTotalStorage = new JLabel();
    if (labelAllocated == null)    labelAllocated = new JLabel();
    if (labelUsed == null)         labelUsed = new JLabel();
    if (labelRemaining == null)    labelRemaining = new JLabel();

    if (progressTotalStorage == null) progressTotalStorage = new JProgressBar(0, 100);
    if (progressAllocated == null)    progressAllocated = new JProgressBar(0, 100);
    if (progressUsed == null)         progressUsed = new JProgressBar(0, 100);

    // Hiệu chỉnh progress bars chung
    for (JProgressBar pb : new JProgressBar[]{progressTotalStorage, progressAllocated, progressUsed}) {
        pb.setStringPainted(true);
        pb.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 8));
        pb.setFont(pb.getFont().deriveFont(Font.BOLD));
    }

    // ===== NẠP DỮ LIỆU VÀ REFRESH BẢNG (refreshStorageTable cập nhật cả summary) =====
    storageSV storageService = new storageSV();
    List<user_storage> user_storages = storageService.getAllStorage(); // toàn bộ dữ liệu master
    DefaultTableModel model = (DefaultTableModel) userTable.getModel();
    refreshStorageTable(user_storages, model); // sẽ cập nhật cả summary và các progress bar

    // Gán renderer cho cột "Tiến độ" (per-user)
    userTable.getColumnModel().getColumn(6).setCellRenderer(new ProgressRenderer());

    // ===== TỔNG QUAN MÁY CHỦ: 3 CỘT =====
    // Cột 1: Tổng máy chủ + Đã dùng
    JPanel colLeft = new JPanel();
    colLeft.setLayout(new BoxLayout(colLeft, BoxLayout.Y_AXIS));
    colLeft.add(labelTotalStorage);
    colLeft.add(Box.createVerticalStrut(6));
    colLeft.add(labelUsed);

    // Cột 2: Đã cấp + Còn lại
    JPanel colMid = new JPanel();
    colMid.setLayout(new BoxLayout(colMid, BoxLayout.Y_AXIS));
    colMid.add(labelAllocated);
    colMid.add(Box.createVerticalStrut(6));
    colMid.add(labelRemaining);

    // Cột 3: 3 thanh progress (Tổng, Đã cấp, Đã dùng)
    JPanel colRight = new JPanel();
    colRight.setLayout(new BoxLayout(colRight, BoxLayout.Y_AXIS));
    colRight.add(progressTotalStorage);
    colRight.add(Box.createVerticalStrut(1));
    colRight.add(progressAllocated);
    colRight.add(Box.createVerticalStrut(1));
    colRight.add(progressUsed);

    JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 10, 5));
    // Tạo border có tiêu đề
TitledBorder border = BorderFactory.createTitledBorder("Tổng quan máy chủ");
border.setTitleFont(new Font("Times New Roman", Font.BOLD, 20));
// Đổi màu chữ tiêu đề
border.setTitleColor(Color.BLUE); // bạn đổi BLUE thành màu bạn muốn, ví dụ Color.RED
summaryPanel.setFont(new Font("Times New Roman", Font.BOLD, 18));
summaryPanel.setBorder(border);

int summaryHeight = 130; // chỉnh số px theo ý bạn

summaryPanel.setPreferredSize(new Dimension(0, summaryHeight)); // đảm bảo chiều cao tối thiểu
summaryPanel.setMinimumSize(new Dimension(0, summaryHeight));   // tránh bị ép nhỏ hơn

// đảm bảo scrollPane chiếm phần còn lại: đặt preferred của scrollPane (tùy tình huống)
scrollPane.setPreferredSize(new Dimension(0, content_panel.getHeight() - summaryHeight));

    summaryPanel.add(colLeft);
    summaryPanel.add(colMid);
    summaryPanel.add(colRight);

    // ===== XỬ LÝ TÌM KIẾM =====
    btnSearch.addActionListener(e -> {
        String keyword = txtSearch.getText().trim().toLowerCase();
        List<user_storage> filteredUsers = user_storages.stream()
                .filter(u -> u.getusername().toLowerCase().contains(keyword)
                        || u.getFullname().toLowerCase().contains(keyword)
                        || u.getRole().toLowerCase().contains(keyword))
                .toList();
        refreshStorageTable(filteredUsers, model);
    });

    // ===== NÚT CẬP NHẬT GIỮ NGUYÊN LOGIC CỦA BẠN =====
    JButton btnUpdate = new JButton("Cập nhật");
    btnUpdate.setBackground(defaultColor);
    btnUpdate.addActionListener(e -> {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(content_panel, "Vui lòng chọn một người dùng để sửa!");
            return;
        }
        String username = (String) model.getValueAt(selectedRow, 0);
        userSV userService = new userSV();
        user userBasic = userService.findUserByUsername(username);
        if (userBasic == null) {
            JOptionPane.showMessageDialog(content_panel, "Không tìm thấy người dùng!");
            return;
        }
        storageSV ss = new storageSV();
        user_storage userToEdit = ss.getUserStorage(userBasic.getId());
        if (userToEdit != null) {
            showUpdateForm(userToEdit, model);
        } else {
            JOptionPane.showMessageDialog(content_panel, "Không tìm thấy thông tin dung lượng của người dùng!");
        }
    });

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    buttonPanel.setBackground(defaultColor);
    buttonPanel.add(btnUpdate);
    buttonPanel.add(searchPanel);

    // ADD vào main panel
    content_panel.add(summaryPanel, BorderLayout.SOUTH);
    content_panel.add(scrollPane, BorderLayout.CENTER);
    content_panel.add(buttonPanel, BorderLayout.NORTH);

    // sau đó đặt divider/resize sau khi layout hoàn tất
SwingUtilities.invokeLater(() -> {
    content_panel.revalidate();
    content_panel.repaint();
});
    
    userTable.requestFocusInWindow();
    content_panel.revalidate();
    content_panel.repaint();
    }//GEN-LAST:event_storage_managerActionPerformed

    // Hàm hiển thị form cập nhật dung lượng người dùng
    private void showUpdateForm(user_storage userToEdit, DefaultTableModel model) {
    try {
        UIManager.setLookAndFeel(new FlatLightLaf());
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    JDialog dialog = new JDialog(this, "Cập nhật dung lượng tối đa", true);
    dialog.setSize(520, 380);
    dialog.setLocationRelativeTo(this);
    dialog.setLayout(new BorderLayout());

    JPanel panel = new JPanel(new GridBagLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(8, 8, 8, 8);
    gbc.fill = GridBagConstraints.HORIZONTAL;

    Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
    Color labelColor = new Color(0, 102, 204);

    // Username (readonly)
    gbc.gridx = 0; gbc.gridy = 0;
    JLabel lblUsername = new JLabel("Tên đăng nhập:");
    lblUsername.setFont(labelFont); lblUsername.setForeground(labelColor);
    panel.add(lblUsername, gbc);

    gbc.gridx = 1;
    JTextField txtUsername = new JTextField(userToEdit.getusername());
    txtUsername.setEditable(false);
    panel.add(txtUsername, gbc);

    // Fullname (readonly)
    gbc.gridx = 0; gbc.gridy = 1;
    JLabel lblFullname = new JLabel("Họ tên:");
    lblFullname.setFont(labelFont); lblFullname.setForeground(labelColor);
    panel.add(lblFullname, gbc);

    gbc.gridx = 1;
    JTextField txtFullname = new JTextField(userToEdit.getFullname());
    txtFullname.setEditable(false);
    panel.add(txtFullname, gbc);

    // Role (readonly)
    gbc.gridx = 0; gbc.gridy = 2;
    JLabel lblRole = new JLabel("Chức vụ:");
    lblRole.setFont(labelFont); lblRole.setForeground(labelColor);
    panel.add(lblRole, gbc);

    gbc.gridx = 1;
    JTextField txtRole = new JTextField(userToEdit.getRole());
    txtRole.setEditable(false);
    panel.add(txtRole, gbc);

    // Used Storage (readonly, formatted)
    gbc.gridx = 0; gbc.gridy = 3;
    JLabel lblUsedStorage = new JLabel("Dung lượng đã sử dụng:");
    lblUsedStorage.setFont(labelFont); lblUsedStorage.setForeground(labelColor);
    panel.add(lblUsedStorage, gbc);

    gbc.gridx = 1;
    String formattedUsed = formatstorage(userToEdit.getUsedStorage());
    JTextField txtUsedStorage = new JTextField(formattedUsed);
    txtUsedStorage.setEditable(false);
    panel.add(txtUsedStorage, gbc);

    // Max Storage (editable + combo box)
    gbc.gridx = 0; gbc.gridy = 4;
    JLabel lblMaxStorage = new JLabel("Dung lượng tối đa:");
    lblMaxStorage.setFont(labelFont); lblMaxStorage.setForeground(labelColor);
    panel.add(lblMaxStorage, gbc);

    gbc.gridx = 1;
    JPanel storagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
    JTextField txtMaxStorage = new JTextField(10);

    String[] units = {"Bytes", "KB", "MB", "GB", "TB"};
    JComboBox<String> comboUnits = new JComboBox<>(units);

    // Chuyển từ bytes sang giá trị + đơn vị để hiển thị ban đầu
    long bytes = userToEdit.getMaxStorage();
    double displayValue = bytes;
    int unitIndex = 0;
    while (displayValue >= 1024.0 && unitIndex < units.length - 1) {
        displayValue /= 1024.0;
        unitIndex++;
    }
    // Hiển thị với 2 chữ số thập phân nếu có phần thập
    if (displayValue == Math.floor(displayValue)) {
        txtMaxStorage.setText(String.format("%.0f", displayValue));
    } else {
        txtMaxStorage.setText(String.format("%.2f", displayValue));
    }
    comboUnits.setSelectedIndex(unitIndex);

    storagePanel.add(txtMaxStorage);
    storagePanel.add(comboUnits);
    panel.add(storagePanel, gbc);

    // Nút Lưu và Hủy
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    JButton btnSave = new JButton("Lưu");
    JButton btnCancel = new JButton("Hủy");
    buttonPanel.add(btnSave);
    buttonPanel.add(btnCancel);

    // Xử lý nút Lưu (có kiểm tra quota)
    btnSave.addActionListener(e -> {
        String rawInput = txtMaxStorage.getText().trim();
        if (rawInput.isEmpty()) {
            JOptionPane.showMessageDialog(dialog, "Vui lòng nhập giá trị dung lượng.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double inputValue;
        try {
            inputValue = Double.parseDouble(rawInput.replace(",", "."));
            if (inputValue < 0) throw new NumberFormatException();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "Vui lòng nhập số hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedUnit = (String) comboUnits.getSelectedItem();
        // Chuyển về bytes (dùng double trong tính toán tránh overflow tạm thời)
        double multiplier;
        switch (selectedUnit) {
            case "KB": multiplier = Math.pow(1024.0, 1); break;
            case "MB": multiplier = Math.pow(1024.0, 2); break;
            case "GB": multiplier = Math.pow(1024.0, 3); break;
            case "TB": multiplier = Math.pow(1024.0, 4); break;
            default:   multiplier = 1.0; break;
        }

        double newMaxDouble = inputValue * multiplier;
        if (newMaxDouble > Long.MAX_VALUE) {
            JOptionPane.showMessageDialog(dialog, "Giá trị quá lớn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        long newMaxStorageBytes = (long) Math.round(newMaxDouble);

        // 1) Không cho set nhỏ hơn used
        long used = userToEdit.getUsedStorage();
        if (newMaxStorageBytes < used) {
            JOptionPane.showMessageDialog(dialog,
                    "Dung lượng mới phải >= dung lượng đã dùng (" + formatstorage(used) + ").",
                    "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2) Kiểm tra tổng allocated trên server (từ DB) -> không vượt Server_storage
        storageSV service = new storageSV();
        List<user_storage> all = service.getAllStorage();
        long totalAllocated = 0L;
        long oldMaxForUser = userToEdit.getMaxStorage();
        for (user_storage uu : all) {
            totalAllocated += uu.getMaxStorage();
        }
        long afterTotal = totalAllocated - oldMaxForUser + newMaxStorageBytes;
        long serverMax = (long) Server_storage;

        if (afterTotal > serverMax) {
            long remain = Math.max(0L, serverMax - (totalAllocated - oldMaxForUser));
            JOptionPane.showMessageDialog(dialog,
                    "Không thể cấp dung lượng này — vượt quá tổng bộ nhớ máy chủ.\nDung lượng còn lại có thể cấp: " + formatstorage(remain),
                    "Không thể cấp", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Nếu OK -> cập nhật object và gọi service (DAO)
        userToEdit.setMaxStorage(newMaxStorageBytes);
        boolean updated = service.updateUserStorage(userToEdit);
        if (updated) {
            dialog.dispose();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

            // Refresh bảng: lấy lại toàn bộ dữ liệu từ DB và cập nhật model
            List<user_storage> list = service.getAllStorage();
            refreshStorageTable(list, model);
        } else {
            JOptionPane.showMessageDialog(dialog, "Cập nhật thất bại, vui lòng thử lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    });

    btnCancel.addActionListener(e -> dialog.dispose());

    dialog.add(panel, BorderLayout.CENTER);
    dialog.add(buttonPanel, BorderLayout.SOUTH);
    dialog.setResizable(false);
    dialog.setVisible(true);
}


    // Hàm nạp lại dữ liệu vào bảng storage + cập nhật tổng quan máy chủ
private void refreshStorageTable(List<user_storage> list, DefaultTableModel model) {
    model.setRowCount(0);

    // populate bảng (dùng list truyền vào, có thể là toàn bộ hoặc filtered)
    for (user_storage u : list) {
        long max_storage = u.getMaxStorage();
        long used_storage = u.getUsedStorage();
        long remaining = Math.max(0L, max_storage - used_storage);

        int percent = 0;
        if (max_storage > 0) {
            percent = (int) Math.ceil((used_storage * 100.0) / max_storage);
            percent = Math.max(0, Math.min(100, percent));
        }

        model.addRow(new Object[]{
                u.getusername(),
                u.getFullname(),
                u.getRole(),
                formatstorage(max_storage),
                formatstorage(used_storage),
                formatstorage(remaining),
                percent
        });
    }

    // TÍNH TỔNG DỮ LIỆU TỪ DB (luôn lấy toàn bộ để summary chính xác)
    storageSV storageService = new storageSV();
    List<user_storage> allUsers = storageService.getAllStorage();

    long totalAllocated = 0L;
    long totalUsed = 0L;
    for (user_storage uu : allUsers) {
        totalAllocated += uu.getMaxStorage();
        totalUsed += uu.getUsedStorage();
    }

    long serverMax = (long) Server_storage;
    long remainingAlloc = Math.max(0L, serverMax - totalAllocated);

    // Cập nhật label
    if (labelTotalStorage != null) labelTotalStorage.setText("Dung lượng máy chủ (Tối đa): " + formatstorage(serverMax));
    if (labelAllocated != null)    labelAllocated.setText("Tổng dung lượng đã cấp: " + formatstorage(totalAllocated));
    if (labelUsed != null)         labelUsed.setText("Tổng dung lượng đã dùng thực tế: " + formatstorage(totalUsed));
    if (labelRemaining != null)    labelRemaining.setText("Còn có thể cấp: " + formatstorage(remainingAlloc));

    // Cập nhật progress theo % (0..100)
    int pctTotal = 100;
    int pctAllocated = (serverMax > 0) ? (int) Math.min(100, Math.ceil(totalAllocated * 100.0 / serverMax)) : 0;
    int pctUsed      = (serverMax > 0) ? (int) Math.min(100, Math.ceil(totalUsed      * 100.0 / serverMax)) : 0;

    if (progressTotalStorage != null) {
        progressTotalStorage.setMaximum(100);
        progressTotalStorage.setValue(pctTotal);
        progressTotalStorage.setString(formatstorage(serverMax));
        progressTotalStorage.setToolTipText("Dung lượng tổng");
        // màu cho tổng (xanh dương)
        progressTotalStorage.setForeground(new Color(33, 150, 243));
    }
    if (progressAllocated != null) {
        progressAllocated.setMaximum(100);
        progressAllocated.setValue(pctAllocated);
        progressAllocated.setString(formatstorage(totalAllocated) + " (" + pctAllocated + "%)");
        progressAllocated.setToolTipText("Dung lượng đã cấp");
        // màu allocated (cam), đỏ nếu >=90%
        progressAllocated.setForeground(pctAllocated >= 90 ? Color.RED : new Color(255, 165, 0));
    }
    if (progressUsed != null) {
        progressUsed.setMaximum(100);
        progressUsed.setValue(pctUsed);
        progressUsed.setString(formatstorage(totalUsed) + " (" + pctUsed + "%)");
        progressUsed.setToolTipText("Dung người dùng đã sử dụng");
        // màu used (đỏ nếu >=90% else xanh lá)
        progressUsed.setForeground(pctUsed >= 90 ? Color.RED : new Color(76, 175, 80));
    }

    // đảm bảo repaint
    if (model != null) model.fireTableDataChanged();
}




    private String formatstorage(long Size) {
        if (Size < 1024) {
            return Size + " B";
        } else if (Size < 1024 * 1024) {
            double kilobyte = Size / 1024.0;
            return String.format("%.2f KB", kilobyte);
        } else if (Size < 1024 * 1024 * 1024) {
            double megabyte = Size / (1024.0 * 1024);
            return String.format("%.2f MB", megabyte);
        } else if (Size < 1024L * 1024L * 1024L * 1024L) {
            double gigabyte = Size / (1024.0 * 1024 * 1024);
            return String.format("%.2f GB", gigabyte);
        } else {
            double terabyte = Size / (1024.0 * 1024 * 1024 * 1024);
            return String.format("%.2f TB", terabyte);
        }
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
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accept_btn;
    private javax.swing.JPanel content_panel;
    private javax.swing.JLabel fullname;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton logout_button;
    private javax.swing.JPanel main_panel;
    private javax.swing.JButton profile_button;
    private javax.swing.JButton set_lich;
    private javax.swing.JButton storage_manager;
    private javax.swing.JLabel title;
    private javax.swing.JButton users_manage;
    // End of variables declaration//GEN-END:variables
}
