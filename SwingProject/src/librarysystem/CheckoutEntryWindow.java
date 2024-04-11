package librarysystem;

import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;


public class CheckoutEntryWindow extends JFrame implements LibWindow {
    public static final CheckoutEntryWindow INSTANCE = new CheckoutEntryWindow();

    ControllerInterface ci = new SystemController();

	private boolean isInitialized = false;
    private boolean checkRecord = false;

	private JPanel mainPanel;
	private JPanel upperHalf;
	private JPanel middleHalf;
	private JPanel continuePanel;

	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private JPanel leftTextPanel;
	private JPanel rightTextPanel;

	private JTextField checkoutDate;
	private JTextField checkoutDueDate;
    private JTextField IsbnNo;
    private JTextField bookTitle;

    private JLabel label;

    private JButton addEntryButton;
    private JButton backButton;

	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	private JTextField messageBar = new JTextField();

    private int duration;
    private String Isbn;
    private String bookname;
    private String message = "NA";
	/* This class is a singleton */
    private CheckoutEntryWindow() {}

    public CheckoutEntryWindow(int duration, String Isbn, String bookname){
        this.duration = duration;
        this.Isbn = Isbn;
        this.bookname = bookname;

        System.out.println("duration =="+duration);
        System.out.println("Isbn =="+Isbn);
        System.out.println("bookname =="+bookname);
        System.out.println("Added date=="+String.valueOf(LocalDate.now().plusDays(duration)));
    }

    public void init() {
        mainPanel = new JPanel();
        defineUpperHalf();
        defineMiddleHalf();

        BorderLayout bl = new BorderLayout();
        bl.setVgap(10);
        mainPanel.setLayout(bl);
        mainPanel.add(upperHalf, BorderLayout.NORTH);
        mainPanel.add(middleHalf, BorderLayout.CENTER); // Add middleHalf panel to the center
        getContentPane().add(mainPanel);

        isInitialized(true);
        pack();
        setSize(660, 500);
        centerFrameOnDesktop(this); // Center the frame on the desktop
    }

    public static void centerFrameOnDesktop(Component f) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        int height = toolkit.getScreenSize().height;
        int width = toolkit.getScreenSize().width;
        int frameHeight = f.getSize().height;
        int frameWidth = f.getSize().width;
        f.setLocation(((width - frameWidth) / 2), (height - frameHeight) / 3);
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
//        intPanel.add(Box.createRigidArea(new Dimension(0,20)), BorderLayout.NORTH);
        JLabel loginLabel = new JLabel("Checkout Entry Record");
        Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
        intPanel.add(loginLabel, BorderLayout.CENTER);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(intPanel);
    }

    private void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding

        // Checkout Date Label and Text Field
        JLabel checkoutLabel = new JLabel("Checkout Date:");
        checkoutDate = new JTextField(10);
        checkoutDate.setText(checkoutDate.getText());
        gbc.gridx = 0;
        gbc.gridy = 0;
        middlePanel.add(checkoutLabel, gbc);
        gbc.gridx = 1;
        middlePanel.add(checkoutDate, gbc);

        // ISBN Label and Text Field
        JLabel isbnLabel = new JLabel("ISBN:");
        IsbnNo = new JTextField(10);
        IsbnNo.setText(Isbn);
        gbc.gridx = 0;
        gbc.gridy = 1;
        middlePanel.add(isbnLabel, gbc);
        gbc.gridx = 1;
        middlePanel.add(IsbnNo, gbc);

        // Book Title Label and Text Field
        JLabel bookLabel = new JLabel("Title:");
        bookTitle = new JTextField(10);
        bookTitle.setText(bookname);
        gbc.gridx = 0;
        gbc.gridy = 2;
        middlePanel.add(bookLabel, gbc);
        gbc.gridx = 1;
        middlePanel.add(bookTitle, gbc);

        JLabel durationLabel = new JLabel("Max duration:");
        JTextField durationText = new JTextField(10);
        durationText.setText(String.valueOf(duration));
        gbc.gridx = 0;
        gbc.gridy = 3;
        middlePanel.add(durationLabel, gbc);
        gbc.gridx = 1;
        middlePanel.add(durationText, gbc);
    }


    private void defineLeftTextPanel()  {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        try {
            checkoutDate = new JTextField(10);
            checkoutDate.setText(LocalDate.now().toString());
            JLabel label = new JLabel("Checkout Date");
            panel.add(label, BorderLayout.NORTH);
            panel.add(checkoutDate, BorderLayout.CENTER);

            MaskFormatter dateFormatter = new MaskFormatter("####-##-##"); // Year-Month-Day format
            dateFormatter.setPlaceholderCharacter('_'); // Placeholder character for unfilled positions

            // Create a JFormattedTextField with the MaskFormatter
//            JFormattedTextField checkoutDate = new JFormattedTextField(dateFormatter);
            JFormattedTextField checkoutDate = new JFormattedTextField("2024-10-10");
            checkoutDate.setColumns(10);
/////
            // Create checkoutDate with MaskFormatter as shown before
//            JFormattedTextField checkoutDate = new JFormattedTextField(dateFormatter);
//            checkoutDate.setColumns(10);

// Create checkoutDueDate
            JFormattedTextField checkoutDueDate = new JFormattedTextField(dateFormatter);
            checkoutDueDate.setColumns(10);

// Add a DocumentListener to checkoutDate to detect changes
            checkoutDate.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                    updateDueDate();
                }

                private void updateDueDate() {
                    try {
                        // Get the raw text from the JFormattedTextField
                        String checkoutDateString = checkoutDate.getText();

                        // Parse the raw text into a Date object using SimpleDateFormat
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date checkoutJavaDate = dateFormat.parse(checkoutDateString);

                        // Add the duration to the parsed date
                        // Assuming duration is an integer representing days
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(checkoutJavaDate);
                        calendar.add(Calendar.DATE, duration);

                        System.out.println("tt==="+calendar.getTime());
                        // Format the resulting date and set it to checkoutDueDate
                        String dueDateString = dateFormat.format(calendar.getTime());
                        checkoutDueDate.setText(dueDateString);

                    } catch (ParseException ex) {
                        // Handle parsing errors
                        ex.printStackTrace();
                    }
                }
            });
            //////
//            checkoutDueDate.setText(checkoutDate+this.duration);
//        panel.add(new JLabel("Date:"));
//        panel.add(checkoutDate);

//        JLabel label = new JLabel("Checkout Date");
            panel.add(label, BorderLayout.NORTH);
            panel.add(checkoutDate, BorderLayout.CENTER);

            leftTextPanel = panel;
        }catch (Exception e){

        }

    }

    private void defineRightTextPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding
        checkoutDueDate = new JTextField(10);
        checkoutDueDate.setText("2024-10-20");
        checkoutDueDate.setEditable(false);
        checkoutDueDate.setBackground(Color.LIGHT_GRAY);

        JLabel label = new JLabel("Due Date");
        panel.add(label, BorderLayout.NORTH);
        panel.add(checkoutDueDate, BorderLayout.CENTER);
        rightTextPanel = panel;
    }

    private void defineLowerPanel() {
        lowerPanel = new JPanel();

        backButton = Util.buttonStyle(new JButton("<< Main Menu"));
        addBackButtonListener(backButton);
        lowerPanel.add(backButton);

        lowerPanel.add(Box.createHorizontalStrut(10));

        addEntryButton = Util.buttonStyle(new JButton("Enter"));
        addBookEntryRecordButtonListener(addEntryButton);
        lowerPanel.add(addEntryButton);
    }


    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            AdminDashboardWindow.INSTANCE.setTitle("Main Dashboard");
            AdminDashboardWindow.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            AdminDashboardWindow.INSTANCE.init();
            Util.centerFrameOnDesktop(AdminDashboardWindow.INSTANCE);
            AdminDashboardWindow.INSTANCE.setVisible(true);
        });
    }

    private void addBookEntryRecordButtonListener(JButton butn) {
        butn.addActionListener(evt ->  {
                System.out.println("checkoutDate=="+checkoutDate.getText());
                System.out.println("duration=="+this.duration);
        });
    }
}
