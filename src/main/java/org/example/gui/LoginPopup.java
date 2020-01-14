package org.example.gui;

import org.example.exceptions.EmptyTextFieldException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

public class LoginPopup extends JFrame {

    JFrame fr = this;
    JFrame prevFr;

    LoginPopup(JFrame frame){
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setSize(250, 210);

        // center window
        prevFr = frame;
        int locX = frame.getX() + frame.getWidth()/2 - this.getSize().width/2;
        int locY = frame.getY() + frame.getHeight()/2 - this.getSize().height/2;
        this.setLocation(locX, locY);

        final JTextFieldWithHint email = new JTextFieldWithHint(" email");
        final JTextFieldWithHint pass = new JTextFieldWithHint(" password");
        final JButton save = new JButton("Log in");

        email.setBounds(25, 25, 200, 30);
        pass.setBounds(25, 65, 200, 30);
        save.setBounds(75, 120, 100, 40);

        add(email);
        add(pass);
        add(save);

        setVisible(true);

        // set focus on button
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                super.windowOpened(e);
                save.requestFocus();
                save.setFocusPainted(false);
            }
        });
        // log in
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String e = email.getTxt();
                    String p = pass.getTxt();
                    checkPassword(e,p);
                    fr.dispatchEvent(new WindowEvent(fr, WindowEvent.WINDOW_CLOSING));
                } catch (EmptyTextFieldException ex) {
                    JOptionPane.showMessageDialog(fr,
                            "Please fill all data",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void checkPassword(String email, String pass) {
        try {
            // connect to db
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/dblab5?noAccessToProcedureBodies=true",
                    "notlogged",
                    "userPassword1!");

            // create the java statement
            CallableStatement stCheckPass = conn.prepareCall("{? = CALL checkPassword(?,?)}");
            stCheckPass.registerOutParameter(1,Types.INTEGER);
            stCheckPass.setString(2,email);
            stCheckPass.setString(3,pass);
            stCheckPass.execute();
            int user_id = stCheckPass.getInt(1);
            stCheckPass.close();
            if(user_id == 0){
                JOptionPane.showMessageDialog(fr,
                            "Wrong email or password",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
            } else {
                org.example.panels.Panel.setUserID(user_id);
                CallableStatement stCheckType = conn.prepareCall("{? = CALL checkAccoutType(?)}");
                stCheckType.registerOutParameter(1,Types.VARCHAR);
                stCheckType.setInt(2,user_id);
                stCheckType.execute();
                String accType = stCheckType.getString(1);
                System.out.println(accType);
                stCheckType.close();
                if(accType.equals("admin")){
                    new Main("admin");
                } else {
                    new Main("logged");
                }
                prevFr.setVisible(false);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
