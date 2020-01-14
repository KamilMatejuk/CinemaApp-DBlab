package org.example.gui;

import org.example.panels.Account;
import org.example.panels.Movies;
import org.example.panels.Panel;
import org.example.panels.Repertoire;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends JFrame {

    JFrame frame = this;
    JScrollPane panel;

    Main(String type, JFrame fr){
        if(type.equals("notlogged")){
            org.example.panels.Panel.setIsLoggedIn(false);
            org.example.panels.Panel.setIsAdmin(false);
        }
        else if(type.equals("logged")){
            org.example.panels.Panel.setIsLoggedIn(true);
            org.example.panels.Panel.setIsAdmin(false);
        }
        else if(type.equals("admin")){
            org.example.panels.Panel.setIsLoggedIn(true);
            org.example.panels.Panel.setIsAdmin(true);
        }
        org.example.panels.Panel.setMainFrame(this);
        org.example.panels.Panel.setStartFrame(fr);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setSize(1400, 900);
        // center window
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        JButton tab1 = new JButton("Repertoire");
        JButton tab2 = new JButton("Movies");
        JButton tab3 = new JButton("My Account");

        if(Panel.getIsLoggedIn()){
            styleMenuBar(new ArrayList<>(Arrays.asList(tab1, tab2, tab3)));
            panel = Account.getPanel();
        } else {
            styleMenuBar(new ArrayList<>(Arrays.asList(tab1, tab2)));
            panel = Repertoire.getPanel();
        }
        frame.add(panel);
        panel.setBounds(25,125,1350,715);
        setVisible(true);

        tab1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.remove(panel);
                panel = Repertoire.getPanel();
                frame.add(panel);
                panel.setBounds(25,125,1350,715);
                frame.setVisible(true);
            }
        });
        tab2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.remove(panel);
                panel = Movies.getPanel();
                frame.add(panel);
                panel.setBounds(25,125,1350,715);
                frame.setVisible(true);
            }
        });
        tab3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.remove(panel);
                panel = Account.getPanel();
                frame.add(panel);
                panel.setBounds(25,125,1350,715);
                frame.setVisible(true);
            }
        });
    }

    private void styleMenuBar(ArrayList<JButton> btns){
        int padding = 0;
        int count = btns.size();
        int i = 0;
        for(JButton btn : btns){
            frame.add(btn);
            btn.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 25));
            btn.setHorizontalAlignment(SwingConstants.CENTER);
            btn.setFocusPainted(false);
            btn.setBackground(Color.ORANGE);
            int width = (this.getWidth() - padding*(count-1)) / count;
            btn.setBounds(i,0,width, 100);
            i += width + padding;
        }
    }
}
