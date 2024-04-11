package librarysystem;

import business.Book;
import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;


public class ViewAllBooksWindow extends JFrame implements LibWindow {
    public static final ViewAllBooksWindow INSTANCE = new ViewAllBooksWindow();

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
    private ViewAllBooksWindow() {
    }

    public void init() {
        mainPanel = new JPanel(new BorderLayout());

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Title");
        tableModel.addColumn("ISBN");
        tableModel.addColumn("NumOfCopy");
        tableModel.addColumn("Num");
        tableModel.addColumn("Isavailable");

        HashMap<String, Book> bookList = ci.getAllBooks();

        for (Book b : bookList.values()) {
            tableModel.addRow(new Object[]{b.getTitle(), b.getIsbn(), b.getCopyNums(), b.getNumCopies(), b.isAvailable()});
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Create a border for the scroll pane
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        scrollPane.setBorder(border); // Set the border to the scroll pane

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        getContentPane().add(mainPanel);
        setSize(660, 500);
    }
}
