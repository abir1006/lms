package librarysystem;

import business.Book;
import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;


public class AddBookWindow extends JFrame implements LibWindow {
    public static final AddBookWindow INSTANCE = new AddBookWindow();

    ControllerInterface ci = new SystemController();

	private boolean isInitialized = false;
    private boolean checkRecord = false;

	private JPanel mainPanel;
	private JPanel upperHalf;
	private JPanel middleHalf;

	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private JPanel leftTextPanel;
	private JPanel rightTextPanel;
    private JPanel tablePanel;
    private JPanel continueButtonPanel;

    private JTextField title;
    private JTextField Isbn;
    private JTextField duration;

    private JButton checkButton;
    private JButton continueButton;
    private JButton backButton;

    private DefaultTableModel model;
    private JTable table;

	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	private JTextField messageBar = new JTextField();

    private String message = "NA";
	/* This class is a singleton */
    private AddBookWindow() {}
    
    public void init() {
        mainPanel = new JPanel();

        defineUpperHalf();
        defineMiddleHalf();
        createTablePanel();

        BorderLayout bl = new BorderLayout();
//        bl.setVgap(10);

        mainPanel.setLayout(bl);
        mainPanel.add(upperHalf, BorderLayout.NORTH);
        mainPanel.add(middleHalf, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.CENTER );

        getContentPane().add(mainPanel);
        isInitialized(true);
        pack();
        setSize(660, 500);
    }

    public void createTablePanel() {
        tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        JSeparator s = new JSeparator();
        s.setOrientation(SwingConstants.HORIZONTAL);
        tablePanel.add(s, BorderLayout.SOUTH);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Title");
        tableModel.addColumn("ISBN");
        tableModel.addColumn("NumOfCopy");
        tableModel.addColumn("Num");
        tableModel.addColumn("Isavailable");

        HashMap<String,Book> bookList = ci.getAllBooks();
        for(Book b: bookList.values()){
            tableModel.addRow(new Object[]{b.getTitle(), b.getIsbn(), b.getCopyNums(), b.getNumCopies(), b.isAvailable()});
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        table.getTableHeader().setVisible(true);

        tablePanel.add(scrollPane, BorderLayout.CENTER); // Add scroll pane with CENTER constraint
    }


    private void defineUpperHalf() {
    		upperHalf = new JPanel();
    		upperHalf.setLayout(new BorderLayout());
            defineTopPanel();
            defineMiddlePanel();
    		defineLowerPanel();

    		upperHalf.add(topPanel, BorderLayout.NORTH);
    		upperHalf.add(middlePanel, BorderLayout.CENTER);
    		upperHalf.add(lowerPanel, BorderLayout.SOUTH);
    	}
    private void defineMiddleHalf() {
        middleHalf = new JPanel();
        middleHalf.setLayout(new BorderLayout());
        JSeparator s = new JSeparator();
        s.setOrientation(SwingConstants.HORIZONTAL);
        middleHalf.add(s, BorderLayout.SOUTH);
    }

    private void defineTopPanel() {
        topPanel = new JPanel();
        JPanel intPanel = new JPanel(new BorderLayout());
        intPanel.add(Box.createRigidArea(new Dimension(0,20)), BorderLayout.NORTH);
        JLabel label = new JLabel("Add New Book");
        Util.adjustLabelFont(label, Color.BLUE.darker(), true);
        intPanel.add(label, BorderLayout.CENTER);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(intPanel);
    }

    private void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout());

        title = new JTextField(10);
        title.setText("Java 8");
        JLabel titleLabel = new JLabel("Title");
        middlePanel.add(titleLabel);
        middlePanel.add(title);

        middlePanel.add(Box.createHorizontalStrut(10));

        Isbn = new JTextField(10);
        Isbn.setText("48-56882");
        JLabel label = new JLabel("ISBN ");
        middlePanel.add(label);
        middlePanel.add(Isbn);

        middlePanel.add(Box.createHorizontalStrut(10));

        Isbn = new JTextField(10);
        Isbn.setText("4");
        JLabel duration = new JLabel("Checkout Length ");
        middlePanel.add(duration, BorderLayout.NORTH);
        middlePanel.add(Isbn, BorderLayout.CENTER);

        middlePanel.add(Box.createHorizontalStrut(10));
    }

    private void defineLowerPanel() {
        lowerPanel = new JPanel();

        backButton = Util.buttonStyle(new JButton("<< Main Menu"));
        addBackButtonListener(backButton);
        lowerPanel.add(backButton);

        middlePanel.add(Box.createHorizontalStrut(10));

        checkButton = Util.buttonStyle(new JButton("Save"));
        checkRecordButtonListener(checkButton);
        lowerPanel.add(checkButton);
    }

    private void continueButtonListener(JButton btnContinue) {
        btnContinue.addActionListener((event) -> {
            LibrarySystem.hideAllWindows();
            CheckoutEntryWindow checkoutEntryWindow = CheckoutEntryWindow.INSTANCE;
            checkoutEntryWindow.init();
            checkoutEntryWindow.setVisible(true);
        });
    }

    private void defineContinuePanel(){
           continueButtonPanel = new JPanel();

            backButton = Util.buttonStyle(new JButton("<< Main Menu"));
            addBackButtonListener(backButton);
            lowerPanel.add(backButton);

            lowerPanel.add(Box.createHorizontalStrut(10));

            continueButton = Util.buttonStyle(new JButton("Save"));
            continueButtonListener(continueButton);
            continueButtonPanel.add(continueButton);
            lowerPanel.add(continueButtonPanel);
    }

    private static void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            System.out.println("Back clicked");
//                message = "Book is In.";
//                revalidate();
//                repaint();
        });
    }

    private void checkRecordButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
             checkRecord = ci.checkRecord(title.getText(), Isbn.getText());
            if(checkRecord){
                message = "Book is In.";
                // Update the UI to show the "Continue" button
                defineContinuePanel();
                // Repaint the frame
                revalidate();
                repaint();

            }else{
                message = "Book is not found with given criteria.";
            }
            //Member
            //{1004=Member Info: ID: 1004, name: Ricardo Montalbahn, 641-472-2871 (501 Central, Mountain View, 94707), 1003=Member Info: ID: 1003, name: Sarah Eagleton, 451-234-8811 (42 Dogwood Dr., Fairfield, 52556), 1002=Member Info: ID: 1002, name: Drew Stevens, 702-998-2414 (1435 Channing Ave, Palo Alto, 94301), 1001=Member Info: ID: 1001, name: Andy Rogers, 641-223-2211 (5001 Venice Dr., Los Angeles, 93736)}
            //Book
            //{48-56882=isbn: 48-56882, maxLength: 7, available: true, 28-12331=isbn: 28-12331, maxLength: 7, available: true, 23-11451=isbn: 23-11451, maxLength: 21, available: true, 99-22223=isbn: 99-22223, maxLength: 21, available: true}
            // ISBN: 48-56882, member: 1004
            JOptionPane.showMessageDialog(this, message);
        });
    }
}
