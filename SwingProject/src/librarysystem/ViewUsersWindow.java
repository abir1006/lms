package librarysystem;

import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;
import dataaccess.User;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;


public class ViewUsersWindow extends JFrame implements LibWindow {
    public static final ViewUsersWindow INSTANCE = new ViewUsersWindow();

    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;
    public boolean isInitialized() {
        return isInitialized;
    }
    public void isInitialized(boolean val) {
        isInitialized = val;
    }
    private JTextField messageBar = new JTextField();
    JPanel mainPanel;
    /* This class is a singleton */
    private ViewUsersWindow() {
    }

    public void init() {
        mainPanel = new JPanel(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("User Id");
        tableModel.addColumn("Role");

        HashMap<String, User> users = ci.getAllUsers();

        for (User u : users.values()) {
            tableModel.addRow(new Object[]{u.getId(), u.getAuthorization()});
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a border for the scroll pane
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        scrollPane.setBorder(border); // Set the border to the scroll pane

        mainPanel.add(scrollPane, BorderLayout.CENTER);

//        validate();
//        repaint();
        getContentPane().add(mainPanel);
        setSize(660, 500);
    }
}
