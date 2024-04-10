package librarysystem;

import business.ControllerInterface;
import business.SystemController;

import javax.swing.*;
import java.awt.*;


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

	private JTextField memberId;
	private JTextField Isbn;
	private JLabel label;

    private JButton checkButton;
    private JButton continueButton;
    private JButton backButton;

	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	private JTextField messageBar = new JTextField();

    private String message = "NA";
	/* This class is a singleton */
    private CheckoutRecordWindow() {}
    
    public void init() {
        CheckoutRecordWindow.INSTANCE.setTitle("Checkout Record Form");
        CheckoutRecordWindow.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CheckoutRecordWindow.INSTANCE.setVisible(true);

        mainPanel = new JPanel();

        defineUpperHalf();
        defineMiddleHalf();

        BorderLayout bl = new BorderLayout();
        bl.setVgap(30);

        mainPanel.setLayout(bl);
        mainPanel.add(upperHalf, BorderLayout.NORTH);
        mainPanel.add(middleHalf, BorderLayout.CENTER);
        getContentPane().add(mainPanel);
        isInitialized(true);
        pack();
        setSize(660, 500);
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
        memberId.setText("1004");
        JLabel label = new JLabel("Member ID");
        panel.add(label, BorderLayout.NORTH);
        panel.add(memberId, BorderLayout.CENTER);

        leftTextPanel = panel;
    }

    private void defineRightTextPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        Isbn = new JTextField(10);
        Isbn.setText("48-56882");
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
            JPanel additionalButtonPanel = new JPanel();

            backButton = Util.buttonStyle(new JButton("<< Main Menu"));
            addBackButtonListener(backButton);
            lowerPanel.add(backButton);

            lowerPanel.add(Box.createHorizontalStrut(10));

            continueButton = Util.buttonStyle(new JButton("Continue >>"));
            continueButtonListener(continueButton);
            additionalButtonPanel.add(continueButton);
            lowerPanel.add(additionalButtonPanel);
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
             checkRecord = ci.checkRecord(memberId.getText(), Isbn.getText());
            if(checkRecord){
                message = "Book is In.";
                // Update the UI to show the "Continue" button
                defineContinuePanel();
                // Repaint the frame
                revalidate();
                repaint();
            }
            //Member
            //{1004=Member Info: ID: 1004, name: Ricardo Montalbahn, 641-472-2871 (501 Central, Mountain View, 94707), 1003=Member Info: ID: 1003, name: Sarah Eagleton, 451-234-8811 (42 Dogwood Dr., Fairfield, 52556), 1002=Member Info: ID: 1002, name: Drew Stevens, 702-998-2414 (1435 Channing Ave, Palo Alto, 94301), 1001=Member Info: ID: 1001, name: Andy Rogers, 641-223-2211 (5001 Venice Dr., Los Angeles, 93736)}
            //Book
            //{48-56882=isbn: 48-56882, maxLength: 7, available: true, 28-12331=isbn: 28-12331, maxLength: 7, available: true, 23-11451=isbn: 23-11451, maxLength: 21, available: true, 99-22223=isbn: 99-22223, maxLength: 21, available: true}

            // ISBN: 48-56882, member: 1004
            //LibrarySystem.hideAllWindows();
            JOptionPane.showMessageDialog(this, message);
        });
    }
}