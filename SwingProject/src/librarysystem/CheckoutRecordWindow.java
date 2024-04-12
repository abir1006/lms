package librarysystem;

import business.Book;
import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CheckoutRecordWindow extends JFrame implements LibWindow {
    public static final CheckoutRecordWindow INSTANCE = new CheckoutRecordWindow();

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

    private JTextField memberId;
	private JTextField Isbn;
	private JLabel label;

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
    private String bookname;
	/* This class is a singleton */
    private CheckoutRecordWindow() {}

    private boolean showCheckBtn = true;
    private boolean showContinueBtn = false;

    public void init() {
        mainPanel = new JPanel();

        defineUpperHalf();
        defineMiddleHalf();
//        createTablePanel();
//        clearFields();

        BorderLayout bl = new BorderLayout();
        bl.setVgap(10);

        mainPanel.setLayout(bl);
        mainPanel.add(upperHalf, BorderLayout.NORTH);
        mainPanel.add(middleHalf, BorderLayout.CENTER);
//        mainPanel.add(tablePanel, BorderLayout.CENTER );

        getContentPane().add(mainPanel);
        isInitialized(true);
        pack();
        setSize(660, 500);
    }

    private void clearFields() {
        Isbn.setText("");
        memberId.setText("");
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
        JLabel loginLabel = new JLabel("Checkout Record");
        Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
        intPanel.add(loginLabel, BorderLayout.CENTER);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(intPanel);
    }

    private void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(2, 1)); // 2 rows, 1 column
        defineLeftTextPanel();
        defineRightTextPanel();
        middlePanel.add(leftTextPanel);
        middlePanel.add(rightTextPanel);
    }

    private void defineLeftTextPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        memberId = new JTextField(10);
//        memberId.setText("1004");
        JLabel label = new JLabel("Member ID");
        panel.add(label, BorderLayout.NORTH);
        panel.add(memberId, BorderLayout.CENTER);

        leftTextPanel = panel;
    }

    private void defineRightTextPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        Isbn = new JTextField(10);
//        Isbn.setText("");
        JLabel label = new JLabel("ISBN ");
        panel.add(label, BorderLayout.NORTH);
        panel.add(Isbn, BorderLayout.CENTER);

        rightTextPanel = panel;
    }

    private void defineLowerPanel() {
        lowerPanel = new JPanel();
        checkButton = Util.buttonStyle(new JButton("Check"));
        checkRecordButtonListener(checkButton);
        lowerPanel.add(checkButton);

        backButton = Util.buttonStyle(new JButton("<< Main Menu"));
        addBackButtonListener(backButton);
        lowerPanel.add(backButton);

//        if(showContinueBtn) {
            continueButton = Util.buttonStyle(new JButton("Continue >>"));
            continueButtonListener(continueButton);
            lowerPanel.add(continueButton);
//        }
    }

    private void continueButtonListener(JButton btnContinue) {
        btnContinue.addActionListener((event) -> {
//            boolean flag = validateInput();
//            if(flag) {
                LibrarySystem.hideAllWindows();
                CheckoutEntryWindow.INSTANCE.setTitle("Checkout Entry Form");
                CheckoutEntryWindow.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                CheckoutEntryWindow.INSTANCE.init();
                Util.centerFrameOnDesktop(CheckoutEntryWindow.INSTANCE);
                CheckoutEntryWindow.INSTANCE.setVisible(true);
                revalidate();
                repaint();
//            }else{
//                Util.centerFrameOnDesktop(CheckoutEntryWindow.INSTANCE);
//                CheckoutEntryWindow.INSTANCE.setVisible(true);
//            }
        });
    }

    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            checkRecord = false;
            LibrarySystem.hideAllWindows();
            AdminDashboardWindow.INSTANCE.setTitle("Main Dashboard");
            AdminDashboardWindow.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            AdminDashboardWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(AdminDashboardWindow.INSTANCE);
            AdminDashboardWindow.INSTANCE.setVisible(true);
//            clearFields();
            revalidate();
            repaint();

       });
    }

    private boolean validateInput(){
//
//        boolean flag = false;
        if(!(memberId.getText().isEmpty() && Isbn.getText().isEmpty())) {
            return true;
        }else{
            JOptionPane.showMessageDialog(this, "All fields are required");
            return false;
        }
//            flag = true;
//            JOptionPane.showMessageDialog(this, "All fields are required");
//        } else
//        if(memberId.getText().isEmpty()){
//            flag = true;
//            JOptionPane.showMessageDialog(this, "MemberId is required");
//        }else if(Isbn.getText().isEmpty()){
//            flag = true;
//            JOptionPane.showMessageDialog(this, "ISBN is required");
//        }
//        return flag;
    }

    private void checkRecordButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            System.out.println(memberId.getText()+"===="+Isbn.getText());
             checkRecord = ci.checkRecord(memberId.getText(), Isbn.getText());
            if(checkRecord){
                message = "Book is IN.";
                // Repaint the frame
                int duration = 0;
                HashMap<String,Book> allBooks = ci.getAllBooks();
                for(Map.Entry<String, Book> b: allBooks.entrySet()){
                    if(b.getValue().getIsbn().equals(Isbn.getText())){
                        System.out.println("Duration=="+b.getValue().getMaxCheckoutLength());
                        duration = b.getValue().getMaxCheckoutLength();
                        bookname = b.getValue().getTitle();
                    }
                }
                new CheckoutEntryWindow(duration,Isbn.getText(), bookname);
                revalidate();
                repaint();
                JOptionPane.showMessageDialog(this, message);
            }else{
//                boolean flag = validateInput();
//                if(!flag) {
                    message = "Book is not found with given criteria.";
                    JOptionPane.showMessageDialog(this, message);
//                }
            }
//            clearFields();
            //Member
            //{1004=Member Info: ID: 1004, name: Ricardo Montalbahn, 641-472-2871 (501 Central, Mountain View, 94707), 1003=Member Info: ID: 1003, name: Sarah Eagleton, 451-234-8811 (42 Dogwood Dr., Fairfield, 52556), 1002=Member Info: ID: 1002, name: Drew Stevens, 702-998-2414 (1435 Channing Ave, Palo Alto, 94301), 1001=Member Info: ID: 1001, name: Andy Rogers, 641-223-2211 (5001 Venice Dr., Los Angeles, 93736)}
            //Book
            //{48-56882=isbn: 48-56882, maxLength: 7, available: true, 28-12331=isbn: 28-12331, maxLength: 7, available: true, 23-11451=isbn: 23-11451, maxLength: 21, available: true, 99-22223=isbn: 99-22223, maxLength: 21, available: true}
            // ISBN: 48-56882, member: 1004

        });
    }
}
