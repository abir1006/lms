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

    private static boolean isLoggedIn = false;

    public static final boolean isLoggedIn(){
        return isLoggedIn;
    }

    public static final boolean setLoggedIn(boolean status){
        return isLoggedIn = status;
    }

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
        JLabel loginLabel = new JLabel("Provide your credentials to login");
        Util.adjustLabelFont(loginLabel, Color.BLUE.darker(), true);
        intPanel.add(loginLabel, BorderLayout.CENTER);
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(intPanel);
    }

    private void defineMiddlePanel() {
        middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(2, 1));

        defineLeftTextPanel();
        defineRightTextPanel();

        middlePanel.add(leftTextPanel);
        middlePanel.add(rightTextPanel);
    }

    private void defineLeftTextPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        username = new JTextField(10);
        username.setText("102");
        username.setPreferredSize(new Dimension(username.getPreferredSize().width, 40)); // Adjust 40 as needed

        JLabel label = new JLabel("Username:");
        panel.add(label, BorderLayout.NORTH);
        panel.add(username, BorderLayout.CENTER);

        leftTextPanel = panel;
    }

    private void defineRightTextPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add padding

        password = new JPasswordField(10);
        password.setText("abc");
        username.setPreferredSize(new Dimension(username.getPreferredSize().width, 40)); // Adjust 40 as needed

        JLabel label = new JLabel("Password:");
        panel.add(label, BorderLayout.NORTH);
        panel.add(password, BorderLayout.CENTER);

        rightTextPanel = panel;
    }



    private void defineLowerPanel() {
        lowerPanel = new JPanel();
        loginButton = Util.buttonStyle(new JButton("Login"));
        addLoginButtonListener(loginButton);
        lowerPanel.add(loginButton);
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
                LibrarySystem.hideAllWindows();
                String role = ci.login(username.getText(), password.getText());
                if(!role.isEmpty()){
                    setLoggedIn(true);
                }
                if(isLoggedIn()) {
                    switch (role) {
                        case "LIBRARIAN", "ADMIN", "BOTH":
                            AdminDashboardWindow.INSTANCE.setTitle(role);
                            AdminDashboardWindow.INSTANCE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            AdminDashboardWindow.INSTANCE.setRole(role);
                            AdminDashboardWindow.INSTANCE.init();
                            Util.centerFrameOnDesktop(AdminDashboardWindow.INSTANCE);
                            AdminDashboardWindow.INSTANCE.setVisible(true);
                            message = "Welcome, "+role+"!";
                            break;
                        default:
                            message = "Invalid Login, Try again!";
                            break;
                    }
                }else{
                        message = "Login again!";
                        JOptionPane.showMessageDialog(this, message);
                    }

            } catch (LoginException e) {
                e.printStackTrace();
            }
            if(username.getText().isEmpty() || password.getText().isEmpty()){
                JOptionPane.showMessageDialog(this, "Please fill all the fields");
            }else {
                if(!isLoggedIn()){
                    message = "Invalid credential, try again!";
                    LoginWindow.INSTANCE.setVisible(true);
                }
                JOptionPane.showMessageDialog(this, message);
            }
        });
    }
}
