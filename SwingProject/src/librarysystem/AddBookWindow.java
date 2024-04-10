package librarysystem;

import business.ControllerInterface;
import business.SystemController;
import javafx.stage.Screen;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookWindow extends JFrame implements LibWindow {
    private static final long serialVersionUID = 1L;
    public static final AddBookWindow INSTANCE = new AddBookWindow();
    ControllerInterface ci = new SystemController();
    private boolean isInitialized = false;


    private DefaultTableModel model;
    private JTable table;


    private JPanel mainPanel;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel lowerPanel;
    private TextArea textArea;

    private JLabel label1;
    private JButton addBookAuthor;
    private JPanel panel1;
    private JPanel panel5;
    private JLabel successText;
    private JPanel panel3;
    private JLabel label3;
    private JTextField bookTitle;
    private JPanel panel2;
    private JLabel label2;
    private JTextField isbnTextField;
    private JPanel panel4;
    private JButton addAuthorBtn;


    //Singleton class
    private AddBookWindow() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        String[] columnNames = { "ID", "Name", "Book ID" };
        Object[][] data = { { 1, "Book 1", "B001" }, { 2, "Book 2", "B002" }, { 3, "Book 3", "B003" } };
        model = new DefaultTableModel(data, columnNames);

        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel();
        JButton newButton = new JButton("New");

        JTextField nameField = new JTextField(10);
        JTextField bookIDField = new JTextField(10);

        JPanel newEntryPanel = new JPanel();
        newEntryPanel.setBackground(Color.BLUE);
        newEntryPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        newEntryPanel.add(new JLabel("Name: "));
        newEntryPanel.add(nameField);

        newEntryPanel.add(new JLabel("Book ID: "));
        newEntryPanel.add(bookIDField);


        buttonsPanel.add(newButton);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void init() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        defineTopPanel();
        defineMiddlePanel();
        defineLowerPanel();
//        mainPanel.add(topPanel, BorderLayout.NORTH);
//        mainPanel.add(middlePanel, BorderLayout.CENTER);
//        mainPanel.add(lowerPanel, BorderLayout.SOUTH);
        getContentPane().add(mainPanel);
        isInitialized = true;
    }


    @Override
    public boolean isInitialized() {
        // TODO Auto-generated method stub
        return isInitialized;
    }

    @Override
    public void isInitialized(boolean val) {
        isInitialized = val;

    }

    public void defineTopPanel() {
        topPanel = new JPanel();
        JLabel AllIDsLabel = new JLabel("BOOKS");
        Util.adjustLabelFont(AllIDsLabel, Util.DARK_BLUE, true);
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(AllIDsLabel);

    }

    public void defineMiddlePanel() {
        middlePanel = new JPanel();
        FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 25, 25);
        middlePanel.setLayout(fl);
        textArea = new TextArea(8, 20);
        //populateTextArea();
        middlePanel.add(textArea);

//
//        setLayout(new BorderLayout());
//
//        String[] columnNames = { "ID", "Name", "Book ID" };
//        Object[][] data = { { 1, "Book 1", "B001" }, { 2, "Book 2", "B002" }, { 3, "Book 3", "B003" } };
//        model = new DefaultTableModel(data, columnNames);
//
//        table = new JTable(model);
//        JScrollPane scrollPane = new JScrollPane(table);
//        add(scrollPane, BorderLayout.CENTER);
//
//        JTextField nameField = new JTextField(10);
//        JTextField bookIDField = new JTextField(10);
//
//        JPanel newEntryPanel = new JPanel();
//        newEntryPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//
//        newEntryPanel.add(new JLabel("Name: "));
//        newEntryPanel.add(nameField);
//
//        newEntryPanel.add(new JLabel("Book ID: "));
//        newEntryPanel.add(bookIDField);

    }

    public void defineLowerPanel() {

        JButton backToMainButn = new JButton("<= Back to Main");
        backToMainButn.addActionListener(new AddBookWindow.BackToMainListener());
        lowerPanel = new JPanel();
        lowerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        ;
        lowerPanel.add(backToMainButn);
    }

    class BackToMainListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent evt) {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);

        }
    }

    public void setData(String data) {
        textArea.setText(data);
    }
}
