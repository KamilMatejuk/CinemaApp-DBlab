package org.example.gui;

import org.example.exceptions.EmptyTextFieldException;
import org.example.gui.JTextFieldWithHint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.*;

public class RegisterPopup extends JFrame {

    JFrame fr = this;
    JFrame prevFr;

    RegisterPopup(JFrame frame){
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setSize(250, 315);

        // center window
        prevFr = frame;
        int locX = frame.getX() + frame.getWidth()/2 - this.getSize().width/2;
        int locY = frame.getY() + frame.getHeight()/2 - this.getSize().height/2;
        this.setLocation(locX, locY);

        final JTextFieldWithHint name = new JTextFieldWithHint(" name");
        final JTextFieldWithHint surname = new JTextFieldWithHint(" surname");
        final JTextFieldWithHint birth = new JTextFieldWithHint(" birth (YYYY-MM-DD)");
        final JTextFieldWithHint email = new JTextFieldWithHint(" email");
        final JTextFieldWithHint pass = new JTextFieldWithHint(" password");
        final JButton save = new JButton("Create");

        name.setBounds(25, 25, 200, 30);
        surname.setBounds(25, 65, 200, 30);
        birth.setBounds(25, 105, 200, 30);
        email.setBounds(25, 145, 200, 30);
        pass.setBounds(25, 185, 200, 30);
        save.setBounds(75, 225, 100, 40);

        add(name);
        add(surname);
        add(birth);
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
                    String n = name.getTxt();
                    String s = surname.getTxt();
                    String b = birth.getTxt();
                    String e = email.getTxt();
                    String p = pass.getTxt();
                    createClient(n,s,b,e,p);
                    fr.dispatchEvent(new WindowEvent(fr, WindowEvent.WINDOW_CLOSING));
                } catch (EmptyTextFieldException ex) {
                    JOptionPane.showMessageDialog(fr,"Please fill all data","Error",JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void createClient(String name, String surname, String birth, String email, String password) {
        try {
            // connect to db
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/dblab5?noAccessToProcedureBodies=true",
                    "notlogged",
                    "userPassword1!");

            // create the java statement
            CallableStatement stCreateUsr = conn.prepareCall("{? = CALL createCustomer(?,?,?,?,?)}");
            stCreateUsr.registerOutParameter(1, Types.INTEGER);
            stCreateUsr.setString(2,email);
            stCreateUsr.setString(3,name);
            stCreateUsr.setString(4,surname);
            stCreateUsr.setString(5,password);
            stCreateUsr.setDate(6, Date.valueOf(birth));
            stCreateUsr.execute();
            int new_user_id = stCreateUsr.getInt(1);
            stCreateUsr.close();
            if(new_user_id == 0){
                JOptionPane.showMessageDialog(fr,"Something went wrong","Error",JOptionPane.ERROR_MESSAGE);
            } else {
                org.example.panels.Panel.setUserID(new_user_id);
                new Main("logged",prevFr);
                prevFr.setVisible(false);
            }

        } catch (SQLException ex){
            switch (Integer.parseInt(ex.getSQLState())){
                case 77777:
                    JOptionPane.showMessageDialog(fr,"User with this email already exists","Error",JOptionPane.ERROR_MESSAGE);
                    break;
                case 77778:
                    JOptionPane.showMessageDialog(fr,"You have to be atleast 16 years old","Error",JOptionPane.ERROR_MESSAGE);
                    break;
                case 77779:
                    JOptionPane.showMessageDialog(fr,"Type valid email","Error",JOptionPane.ERROR_MESSAGE);
                    break;
            }
        } catch (IllegalArgumentException ex){
            JOptionPane.showMessageDialog(fr,"Type valid date","Error",JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
