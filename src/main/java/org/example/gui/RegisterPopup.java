package org.example.gui;

import org.example.exceptions.EmptyTextFieldException;
import org.example.gui.JTextFieldWithHint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RegisterPopup extends JFrame {

    JFrame fr = this;

    RegisterPopup(){
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setSize(250, 315);

        // center window
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        final JTextFieldWithHint name = new JTextFieldWithHint(" name");
        final JTextFieldWithHint surname = new JTextFieldWithHint(" surname");
        final JTextFieldWithHint birth = new JTextFieldWithHint(" birth (DD-MM-YYYY)");
        final JTextFieldWithHint email = new JTextFieldWithHint(" email");
        final JTextFieldWithHint pass = new JTextFieldWithHint(" password");
        final JButton save = new JButton("Log in");

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
}
