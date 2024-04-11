package librarysystem;

import business.Book;
import business.ControllerInterface;
import business.LibraryMember;
import business.SystemController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;


public class ViewAllMembersWindow extends JFrame implements LibWindow {
    public static final ViewAllMembersWindow INSTANCE = new ViewAllMembersWindow();

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
    private ViewAllMembersWindow() {
    }

    public void init() {
        mainPanel = new JPanel(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Member Id");
        tableModel.addColumn("First Name");
        tableModel.addColumn("Last Name");
        tableModel.addColumn("Telephone");

        HashMap<String, LibraryMember> membersList = ci.getAllMembers();

        for (LibraryMember m : membersList.values()) {
            tableModel.addRow(new Object[]{m.getMemberId(), m.getFirstName(), m.getLastName(), m.getTelephone()});
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
