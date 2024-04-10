package librarysystem;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

import business.ControllerInterface;

import business.LoginException;
import business.SystemController;
import dataaccess.User;


public class LoginWindow extends JFrame implements LibWindow {
    public static final LoginWindow INSTANCE = new LoginWindow();


    ControllerInterface ci = new SystemController();

	private boolean isInitialized = false;
	
	private JPanel mainPanel;
	private JPanel upperHalf;
	private JPanel middleHalf;
	private JPanel lowerHalf;
	private JPanel container;

	private JPanel topPanel;
	private JPanel middlePanel;
	private JPanel lowerPanel;
	private JPanel leftTextPanel;
	private JPanel rightTextPanel;
	
	private JTextField username;
	private JTextField password;
	private JLabel label;
	private JButton loginButton;
	private JButton logoutButton;
	
	public boolean isInitialized() {
		return isInitialized;
	}
	public void isInitialized(boolean val) {
		isInitialized = val;
	}
	private JTextField messageBar = new JTextField();
	public void clear() {
		messageBar.setText("");
	}

    private String message;
	/* This class is a singleton */
    private LoginWindow () {}
    
    public void init() {     		
    		mainPanel = new JPanel();

    		defineUpperHalf();
    		defineMiddleHalf();
//    		defineLowerHalf();

    		BorderLayout bl = new BorderLayout();
    		bl.setVgap(30);

    		mainPanel.setLayout(bl);
    		mainPanel.add(upperHalf, BorderLayout.NORTH);
    		mainPanel.add(middleHalf, BorderLayout.CENTER);
//    		mainPanel.add(lowerHalf, BorderLayout.SOUTH);
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
    		//middleHalf.add(Box.createRigidArea(new Dimension(0,50)));
    		middleHalf.add(s, BorderLayout.SOUTH);
    	}
    private void defineLowerHalf() {
        lowerHalf = new JPanel();
        lowerHalf.setLayout(new FlowLayout(FlowLayout.LEFT));

        JButton backButton = Util.buttonStyle(new JButton("<= Back to Main"));
        addBackButtonListener(backButton);
        lowerHalf.add(backButton);
    }
    private void defineTopPanel() {
        topPanel = new JPanel();
        JPanel intPanel = new JPanel(new BorderLayout());
        intPanel.add(Box.createRigidArea(new Dimension(0,20)), BorderLayout.NORTH);
        JLabel loginLabel = new JLabel("Login");
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

        username = new JTextField(10);
        JLabel label = new JLabel("Username:");
        panel.add(label, BorderLayout.NORTH);
        panel.add(username, BorderLayout.CENTER);

        leftTextPanel = panel;
    }

    private void defineRightTextPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        password = new JPasswordField(10);
        JLabel label = new JLabel("Password:");
        panel.add(label, BorderLayout.NORTH);
        panel.add(password, BorderLayout.CENTER);

        rightTextPanel = panel;
    }


    private void defineMiddlePanel1() {
        middlePanel=new JPanel();
        middlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        defineLeftTextPanel();
        defineRightTextPanel();
        middlePanel.add(leftTextPanel);
        middlePanel.add(rightTextPanel);
    }

    private void defineLowerPanel() {
        lowerPanel = new JPanel();
        loginButton = Util.buttonStyle(new JButton("Login"));
        addLoginButtonListener(loginButton);
        lowerPanel.add(loginButton);
    }

    private void defineLeftTextPanel1() {
        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

        username = new JTextField(10);
        label = new JLabel("Username");
        label.setFont(Util.makeSmallFont(label.getFont()));
        topText.add(username);
        bottomText.add(label);

        leftTextPanel = new JPanel();
        leftTextPanel.setLayout(new BorderLayout());
        leftTextPanel.add(topText,BorderLayout.NORTH);
        leftTextPanel.add(bottomText,BorderLayout.CENTER);
    }
    private void defineRightTextPanel11() {
        JPanel topText = new JPanel();
        JPanel bottomText = new JPanel();
        topText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));
        bottomText.setLayout(new FlowLayout(FlowLayout.LEFT,5,0));

        password = new JPasswordField(10);
        label = new JLabel("Password");
        label.setFont(Util.makeSmallFont(label.getFont()));
        topText.add(password);
        bottomText.add(label);

        rightTextPanel = new JPanel();
        rightTextPanel.setLayout(new BorderLayout());
        rightTextPanel.add(topText,BorderLayout.NORTH);
        rightTextPanel.add(bottomText,BorderLayout.CENTER);
    }
    	
    private void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
            LibrarySystem.INSTANCE.setVisible(true);
        });
    }

    private void addLoginButtonListener(JButton butn) {

        butn.addActionListener(evt -> {

            //[ 101 :  xyz, LIBRARIAN], 102=[102:abc, ADMIN], 103=[103:111, BOTH]
            try {
                String  role = ci.login(username.getText(), password.getText());
                switch (role) {
                    case "LIBRARIAN":
                        LibrarySystem.hideAllWindows();
                        AddBookWindow.INSTANCE.setVisible(true);
                        message = "Welcome, LIBRARIAN!";
                        break;
                    case "ADMIN":
                        LibrarySystem.hideAllWindows();
                        CheckoutRecordWindow.INSTANCE.init();
                        Util.centerFrameOnDesktop(CheckoutRecordWindow.INSTANCE);
                        message = "Welcome, Admin!";
                        break;
                    case "BOTH":
                        LibrarySystem.hideAllWindows();
                        BookCopyWindow.INSTANCE.setVisible(true);
                        message = "Welcome, Both!";
                        break;
                    default:
                        message = "Invalid Login, Try again!";
                        break;
                }

            } catch (LoginException e) {
                e.printStackTrace();
            }
            if(username.getText().isEmpty() || password.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Please fill all the fields");
            }else {
                JOptionPane.showMessageDialog(this, message);
            }
        });
    }
}
