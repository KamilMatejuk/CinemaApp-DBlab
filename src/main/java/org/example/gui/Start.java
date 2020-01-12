package org.example.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Start
{
    public static void main( String[] args )
    {
        startupGUI();
    }

    private static void startupGUI() {
        JFrame frame = new JFrame("Cinema App");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(700, 400);
        frame.setResizable(false);
        // center window
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-frame.getSize().height/2);

        JButton loginBtn = new JButton("Log in");
        JButton registerBtn = new JButton("Register");
        JLabel noAccountLbl = new JLabel("Continue without account");

        loginBtn.setBounds(100,80, 200, 150);
        registerBtn.setBounds(400, 80, 200, 150);
        noAccountLbl.setBounds(100, 290, 500, 20);

        styleBtn(loginBtn);
        styleBtn(registerBtn);
        styleLink(noAccountLbl);

        frame.add(loginBtn);
        frame.add(registerBtn);
        frame.add(noAccountLbl);

        frame.setVisible(true);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame popup = new LoginPopup();
            }
        });
        registerBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFrame popup = new RegisterPopup();
            }
        });
        noAccountLbl.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                JFrame main = new Main();
            }
            public void mousePressed(MouseEvent mouseEvent) {}
            public void mouseReleased(MouseEvent mouseEvent) {}
            public void mouseEntered(MouseEvent mouseEvent) {}
            public void mouseExited(MouseEvent mouseEvent) {}
        });
    }

    private static void styleLink(JLabel lbl) {
        lbl.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setForeground(Color.ORANGE);
    }

    private static void styleBtn(JButton btn){
        btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        btn.setHorizontalAlignment(SwingConstants.CENTER);
        btn.setFocusPainted(false);
        btn.setBackground(Color.ORANGE);
        btn.setForeground(Color.WHITE);
    }
}
