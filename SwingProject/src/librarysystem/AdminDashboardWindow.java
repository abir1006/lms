package librarysystem;

import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import java.awt.*;


public class AdminDashboardWindow extends JFrame implements LibWindow {
    public static final AdminDashboardWindow INSTANCE = new AdminDashboardWindow();

    ControllerInterface ci = new SystemController();

	private boolean isInitialized = false;

	private JPanel mainPanel;
    private  JPanel topPanel;

    private JButton addMemberButton;
    private JButton addBookButton;
    private JButton addBookCopyButton;
    private JButton addUserButton;
    private JButton logOutButton;
    private JButton viewBooksButton;
    private JButton viewMemberButton;
    private JButton viewUsersButton;
    private JButton checkoutRecButton;

    public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	private JTextField messageBar = new JTextField();

    private String message = "NA";
	/* This class is a singleton */
    private AdminDashboardWindow() {}
    
    private final static int width=150;
    private final static int height=60;

    private String role;

    public void setRole(String role) {
        this.role = role;
    }

    public void init() {
        AdminDashboardWindow.INSTANCE.setTitle( role + " Dashboard");
        AdminDashboardWindow.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Util.centerFrameOnDesktop(AdminDashboardWindow.INSTANCE);
        AdminDashboardWindow.INSTANCE.setVisible(true);

        mainPanel =  new JPanel();
        defineTopPanel();

        addMemberButton = Util.buttonStyle(new JButton("Add Member"));
        addMemberButton.setPreferredSize(new Dimension(width,height));
        addMemberButton.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            AddMemberWindow.INSTANCE.setVisible(true);
        });
        mainPanel.add(addMemberButton, BorderLayout.CENTER);

        addBookButton = Util.buttonStyle(new JButton("Add Book"));
        addBookButton.setPreferredSize(new Dimension(width,height));
        addBookButton.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            AddBookWindow.INSTANCE.setVisible(true);
        });
        mainPanel.add(addBookButton, BorderLayout.SOUTH);

        addBookCopyButton = Util.buttonStyle(new JButton("Add Book Copy"));
        addBookCopyButton.setPreferredSize(new Dimension(width,height));
        addBookCopyButton.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            BookCopyWindow.INSTANCE.setVisible(true);
        });
        mainPanel.add(addBookCopyButton);

        checkoutRecButton = Util.buttonStyle(new JButton("Checkout Record"));
        checkoutRecButton.setPreferredSize(new Dimension(width, height));
        checkoutRecButton.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            CheckoutRecordWindow.INSTANCE.setTitle("Checkout Record Book");
            CheckoutRecordWindow.INSTANCE.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            Util.centerFrameOnDesktop(CheckoutRecordWindow.INSTANCE);
            CheckoutRecordWindow.INSTANCE.init();
            CheckoutRecordWindow.INSTANCE.setVisible(true);
        });
        checkoutRecButton.setEnabled(role != "LIBRARIAN" ? false : true);
        mainPanel.add(checkoutRecButton);

        viewBooksButton = Util.buttonStyle(new JButton("View Books"));
        viewBooksButton.setPreferredSize(new Dimension(width, height));
        viewBooksButton.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            ViewAllBooksWindow.INSTANCE.setTitle("Book List");
            ViewAllBooksWindow.INSTANCE.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            Util.centerFrameOnDesktop(ViewAllBooksWindow.INSTANCE);
            ViewAllBooksWindow.INSTANCE.init();
            ViewAllBooksWindow.INSTANCE.setVisible(true);
        });
        mainPanel.add(viewBooksButton);

        viewMemberButton = Util.buttonStyle(new JButton("View Members"));
        viewMemberButton.setPreferredSize(new Dimension(width, height));
        viewMemberButton.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            ViewAllMembersWindow.INSTANCE.setTitle("Member List");
            ViewAllMembersWindow.INSTANCE.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            Util.centerFrameOnDesktop(ViewAllMembersWindow.INSTANCE);
            ViewAllMembersWindow.INSTANCE.init();
            ViewAllMembersWindow.INSTANCE.setVisible(true);
        });
        mainPanel.add(viewMemberButton);

        viewUsersButton = Util.buttonStyle(new JButton("View Users"));
        viewUsersButton.setPreferredSize(new Dimension(width, height));
        viewUsersButton.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            ViewUsersWindow.INSTANCE.setTitle("User List");
            ViewUsersWindow.INSTANCE.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            Util.centerFrameOnDesktop(ViewUsersWindow.INSTANCE);
            ViewUsersWindow.INSTANCE.init();
            ViewUsersWindow.INSTANCE.setVisible(true);
        });
        mainPanel.add(viewUsersButton);


        logOutButton = Util.buttonStyle(new JButton("Logout"));
        logOutButton.setPreferredSize(new Dimension(width, height));
        logOutButton.addActionListener(evt -> {
            LoginWindow.setLoggedIn(false);
            LibrarySystem.hideAllWindows();
            LoginWindow.INSTANCE.setVisible(true);
        });
        mainPanel.add(logOutButton);

        mainPanel.add(topPanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel);
        isInitialized = true;
        pack();
        setSize(660, 500);
        Util.centerFrameOnDesktop(this);
    }

    private void defineTopPanel() {

        topPanel = new JPanel();
        BorderLayout  bl = new BorderLayout();
        bl.setVgap(50);
        topPanel.setLayout(bl);

        JLabel label = new JLabel("You are in Administrator Dashboard");
        Util.adjustLabelFont(label, Util.DARK_BLUE, true);
        topPanel.setLayout(bl);
        topPanel.add(label);

    }

    private static void addMemberButtonListener(JButton btn) {
        btn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            AddBookWindow.INSTANCE.setTitle("Add Member");
            AddBookWindow.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            AddBookWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(AddBookWindow.INSTANCE);
            AddBookWindow.INSTANCE.setVisible(true);
        });
    }


    private static void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            System.out.println("Back clicked");
        });
    }

    private void checkRecordButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            System.out.println("Clicked");
        });
    }
}
