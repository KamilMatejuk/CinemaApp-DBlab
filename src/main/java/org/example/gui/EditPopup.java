package org.example.gui;

import org.example.exceptions.EmptyTextFieldException;
import org.example.panels.Account;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;

public class EditPopup extends JFrame{
    
    JFrame fr = this;

    public EditPopup(String name, final String mail, final String bth, final int userID){
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setSize(250, 350);

        final String parts[] = name.split(" ");
        final JTextFieldWithHint fname = new JTextFieldWithHint(parts[0]);
        final JTextFieldWithHint lname = new JTextFieldWithHint(parts[1]);
        final JTextFieldWithHint birth = new JTextFieldWithHint(bth);
        final JTextFieldWithHint email = new JTextFieldWithHint(mail);
        final JTextFieldWithHint pass = new JTextFieldWithHint("***************");
        final JButton save = new JButton("Save");

        fname.setBounds(25, 25, 200, 30);
        lname.setBounds(25, 65, 200, 30);
        birth.setBounds(25, 105, 200, 30);
        email.setBounds(25, 145, 200, 30);
        pass.setBounds(25, 185, 200, 30);
        save.setBounds(75, 240, 100, 40);

        add(fname);
        add(lname);
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
                String fn;
                String ln;
                String b;
                String e;
                String p;
                try {
                    fn = fname.getTxt();
                } catch (EmptyTextFieldException ex) {
                    fn = parts[0];
                }
                try {
                    ln = lname.getTxt();
                } catch (EmptyTextFieldException ex) {
                    ln = parts[1];
                }
                try {
                    b = birth.getTxt();
                } catch (EmptyTextFieldException ex) {
                    b = bth;
                }
                try {
                    e = email.getTxt();
                } catch (EmptyTextFieldException ex) {
                    e = mail;
                }
                try {
                    p = pass.getTxt();
                } catch (EmptyTextFieldException ex) {
                    p = "";
                }
                update(fn,ln,b,e,p,userID);
                fr.dispatchEvent(new WindowEvent(fr, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    private void update(String fn, String ln, String b, String e, String p, int userID) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/dblab5?noAccessToProcedureBodies=true",
                    "logged",
                    "userPassword1!");

            // create the java statement
            if(p.isEmpty()){
                CallableStatement stUpdate = conn.prepareCall("UPDATE Customer SET email=?,first_name=?,last_name=?,birth=? WHERE ID=?");
                stUpdate.setString(1,e);
                stUpdate.setString(2,fn);
                stUpdate.setString(3,ln);
                stUpdate.setString(4,b);
                stUpdate.setInt(5,userID);
                stUpdate.execute();
                stUpdate.close();
            } else {
                CallableStatement stUpdate = conn.prepareCall("UPDATE Customer SET email=?,first_name=?,last_name=?,password=MD5(?),birth=? WHERE ID=?");
                stUpdate.setString(1,e);
                stUpdate.setString(2,fn);
                stUpdate.setString(3,ln);
                stUpdate.setString(4,p);
                stUpdate.setString(5,b);
                stUpdate.setInt(6,userID);
                stUpdate.execute();
                stUpdate.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
