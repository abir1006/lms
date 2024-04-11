package librarysystem;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import business.Address;
import business.LibraryMember;
import dataaccess.DataAccessFacade;

public class AddMemberWindow extends JFrame implements ActionListener {

    public static final AddMemberWindow INSTANCE = new AddMemberWindow();

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    JTextField txtMemId, txtFirstName, txtLastName, txtStreet, txtCity, txtState, txtZip, txtTelephone;
    JRadioButton rbYes, rbNo;
    JButton btnAddMember, btnBack;

    private AddMemberWindow() {
        setBounds(0, 0, 660, 500);
        setTitle("Add Library Members");
        setLayout(null);
        setVisible(true);
        setBackground(Color.gray);
        Util.centerFrameOnDesktop(this);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Two panels
        JPanel p1 = new JPanel();
        p1.setBounds(0, 0, 660, 500);
        p1.setLayout(null);
        add(p1);

        // Left hand side labeling
        JLabel lblId = new JLabel("Member ID");
        lblId.setBounds(40, 40, 100, 20);
        p1.add(lblId);

        JLabel lblFirstName = new JLabel("First Name");
        lblFirstName.setBounds(40, 80, 100, 20);
        p1.add(lblFirstName);

        JLabel lblLastName = new JLabel("Last Name");
        lblLastName.setBounds(40, 120, 100, 20);
        p1.add(lblLastName);

        JLabel lblStreet = new JLabel("Street");
        lblStreet.setBounds(40, 160, 100, 20);
        p1.add(lblStreet);

        JLabel lblCity = new JLabel("City");
        lblCity.setBounds(40, 200, 100, 20);
        p1.add(lblCity);

        JLabel lblState = new JLabel("State");
        lblState.setBounds(40, 240, 100, 20);
        p1.add(lblState);

        JLabel lblZip = new JLabel("Zip Code");
        lblZip.setBounds(40, 280, 100, 20);
        p1.add(lblZip);

        JLabel lblTelephone = new JLabel("Telephone No.");
        lblTelephone.setBounds(40, 320, 100, 20);
        p1.add(lblTelephone);

        // left side text field

        txtMemId = new JTextField();
        txtMemId.setText("1006");
        txtMemId.setBounds(160, 40, 50, 20);
        txtMemId.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        p1.add(txtMemId);


        txtFirstName = new JTextField();
        txtFirstName.setText("Phurpa");
        txtFirstName.setBounds(160, 80, 120, 20);
        txtFirstName.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        p1.add(txtFirstName);

        txtLastName = new JTextField();
        txtLastName.setText("Wangchuk");
        txtLastName.setBounds(160, 120, 120, 20);
        txtLastName.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        p1.add(txtLastName);

        txtStreet = new JTextField();
        txtStreet.setText("1000 North ");
        txtStreet.setBounds(160, 160, 120, 20);
        txtStreet.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        p1.add(txtStreet);

        txtCity = new JTextField();
        txtCity.setText("Fairfield");
        txtCity.setBounds(160, 200, 120, 20);
        txtCity.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        p1.add(txtCity);

        txtState = new JTextField();
        txtState.setText("Iowa");
        txtState.setBounds(160, 240, 120, 20);
        txtState.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        p1.add(txtState);

        txtZip = new JTextField();
        txtZip.setText("52557");
        txtZip.setBounds(160, 280, 120, 20);
        txtZip.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        p1.add(txtZip);

        txtTelephone = new JTextField();
        txtTelephone.setText("6412358795");
        txtTelephone.setBounds(160, 320, 120, 20);
        txtTelephone.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        p1.add(txtTelephone);

        // Buttons
        btnBack = Util.buttonStyle(new JButton("<< Main Menu"));
        btnBack.setBounds(40, 360, 120, 40);
        addBackButtonListener(btnBack);
        p1.add(btnBack);

        p1.add(Box.createHorizontalStrut(20));

        btnAddMember = Util.buttonStyle(new JButton("Add Member"));
        btnAddMember.setBounds(160, 360, 120, 40);
        btnAddMember.addActionListener(this);
        p1.add(btnAddMember);

    }

    private static void addBackButtonListener(JButton butn) {
        butn.addActionListener(evt -> {
            LibrarySystem.hideAllWindows();
           AdminDashboardWindow.INSTANCE.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        // TODO Auto-generated method stub

        if (ae.getSource() == btnAddMember) {
            Address address = new Address(txtStreet.getText(), txtCity.getText(), txtState.getText(), txtZip.getText());
            LibraryMember libraryMember = new LibraryMember(txtMemId.getText(), txtFirstName.getText(),
                    txtLastName.getText(), txtTelephone.getText(), address);
            DataAccessFacade daf = new DataAccessFacade();
            daf.saveNewMember(libraryMember);
            JOptionPane.showMessageDialog(null, "Members added sucessfully");
        }

        else if (ae.getSource() == btnBack) {
            setVisible(false);
//            new ();

        }

    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                new AddMemberWindow();
//            }
//        });
//
//    }
}
