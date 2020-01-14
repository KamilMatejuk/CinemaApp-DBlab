package org.example.panels;

import org.example.gui.Main;
import org.example.panels.Panel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Account extends Panel {

    private static JScrollPane panel;
    private static JLabel name;
    private static JLabel email;
    private static JLabel birth;
    private static JTextArea movie;

    public static JScrollPane getPanel(){
        createPanelGUI();
        loadData();
        return panel;
    }

    private static void loadData() {
        try {
            // connect to db
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn;
            if(getIsAdmin()){
                conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/dblab5?noAccessToProcedureBodies=true",
                        "logged",
                        "userPassword1!");
            } else {
                conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/dblab5?noAccessToProcedureBodies=true",
                        "admin",
                        "userPassword1!");
            }

            // create the java statement
            CallableStatement stGetCustomer = conn.prepareCall("SELECT email,first_name,last_name,birth FROM Customer WHERE ID=?");
            stGetCustomer.setInt(1,getUserID());
            stGetCustomer.execute();
            ResultSet customers = stGetCustomer.executeQuery();
            while (customers.next()){
                System.out.println(customers.getString("email"));
                name.setText(customers.getString("first_name") + " " + customers.getString("last_name"));
                email.setText(customers.getString("email"));
                birth.setText(customers.getString("birth"));
            }
            stGetCustomer.close();

//            elijahwilliams@wp.pl
            CallableStatement stGetMovies = conn.prepareCall("SELECT movieID FROM Movie_Customer WHERE customerID=?");
            stGetMovies.setInt(1,getUserID());
            stGetMovies.execute();
            ResultSet movies = stGetMovies.executeQuery();
            while (movies.next()){
                System.out.println(movies.getString("movieID"));
            }
            stGetCustomer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createPanelGUI() {
        panel = new JScrollPane();
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        panel.setLayout(null);
        JPanel pnl = new JPanel();
        pnl.setBounds(0,0,1350,715);
        SpringLayout layout = new SpringLayout();
        pnl.setLayout(layout);

        JButton logout = new JButton("Log out");

        //TODO pobrac z bazy dane uzytkownika
        //TODO dodać wylogowywanie
        JLabel titleYourData = new JLabel("Your Data:");
        name = new JLabel("loading...");
        email = new JLabel("loading...");
        birth = new JLabel("loading...");
        JLabel pass = new JLabel("***************");
        JButton editBtn = new JButton("Edit");

        JLabel titleYourMovies = new JLabel("Your Movies:");
//        JTextArea movie = new JTextArea("Currently you dont have any movies booked.\nSearch for movies and book them, then you'll see them here.");
        JTextArea movie = new JTextArea("loading...");

        pnl.add(logout);
        pnl.add(titleYourData);
        pnl.add(name);
        pnl.add(email);
        pnl.add(birth);
        pnl.add(pass);
        pnl.add(editBtn);
        pnl.add(titleYourMovies);
        pnl.add(movie);

        logout.setFont(dataFont);
        titleYourData.setFont(titleFont);
        name.setFont(dataFont);
        email.setFont(dataFont);
        birth.setFont(dataFont);
        pass.setFont(dataFont);
        editBtn.setFont(btnFont);
        titleYourMovies.setFont(titleFont);
        movie.setFont(dataFont);

        //horizontal
        layout.putConstraint(SpringLayout.EAST, logout, 0, SpringLayout.EAST, pnl);
        layout.putConstraint(SpringLayout.WEST, titleYourData, 100, SpringLayout.WEST, pnl);
        layout.putConstraint(SpringLayout.WEST, name, 150, SpringLayout.WEST, pnl);
        layout.putConstraint(SpringLayout.WEST, email, 150, SpringLayout.WEST, pnl);
        layout.putConstraint(SpringLayout.WEST, birth, 150, SpringLayout.WEST, pnl);
        layout.putConstraint(SpringLayout.WEST, pass, 150, SpringLayout.WEST, pnl);
        layout.putConstraint(SpringLayout.WEST, editBtn, 150, SpringLayout.WEST, pnl);
        layout.putConstraint(SpringLayout.WEST, titleYourMovies, 100, SpringLayout.WEST, pnl);
        layout.putConstraint(SpringLayout.WEST, movie, 150, SpringLayout.WEST, pnl);
        //vertical
        layout.putConstraint(SpringLayout.NORTH, logout, 0, SpringLayout.NORTH, pnl);
        layout.putConstraint(SpringLayout.NORTH, titleYourData, 100, SpringLayout.NORTH, pnl);
        layout.putConstraint(SpringLayout.NORTH, name, 45, SpringLayout.NORTH, titleYourData);
        layout.putConstraint(SpringLayout.NORTH, email, 35, SpringLayout.NORTH, name);
        layout.putConstraint(SpringLayout.NORTH, birth, 35, SpringLayout.NORTH, email);
        layout.putConstraint(SpringLayout.NORTH, pass, 35, SpringLayout.NORTH, birth);
        layout.putConstraint(SpringLayout.NORTH, editBtn, 30, SpringLayout.NORTH, pass);
        layout.putConstraint(SpringLayout.NORTH, titleYourMovies, 80, SpringLayout.NORTH, editBtn);
        layout.putConstraint(SpringLayout.NORTH, movie, 45, SpringLayout.NORTH, titleYourMovies);
        //look
        movie.setBackground(new Color(0,0,0,0));

        if(getIsAdmin()){
            JLabel titleAdminStuff = new JLabel("Admin Tools:");
            JLabel movieLbl = new JLabel("Movie");
            JButton addMovieBtn = new JButton("+");
            JButton deleteMovieBtn = new JButton("-");
            JLabel userLbl = new JLabel("User");
            JButton deleteUserBtn = new JButton("-");
            JLabel backupLbl = new JLabel("Backup");
            JButton makeBackupBtn = new JButton("Make");
            JButton recoveryBtn = new JButton("Recovery");

            pnl.add(titleAdminStuff);
            pnl.add(movieLbl);
            pnl.add(addMovieBtn);
            pnl.add(deleteMovieBtn);
            pnl.add(userLbl);
            pnl.add(deleteUserBtn);
            pnl.add(backupLbl);
            pnl.add(makeBackupBtn);
            pnl.add(recoveryBtn);

            titleAdminStuff.setFont(titleFont);
            movieLbl.setFont(dataFont);
            addMovieBtn.setFont(btnFont);
            deleteMovieBtn.setFont(btnFont);
            userLbl.setFont(dataFont);
            deleteUserBtn.setFont(btnFont);
            backupLbl.setFont(dataFont);
            makeBackupBtn.setFont(btnFont);
            recoveryBtn.setFont(btnFont);

            //hotizontal
            layout.putConstraint(SpringLayout.WEST, titleAdminStuff, 100, SpringLayout.WEST, pnl);
            layout.putConstraint(SpringLayout.WEST, movieLbl, 150, SpringLayout.WEST, pnl);
            layout.putConstraint(SpringLayout.WEST, addMovieBtn, 250, SpringLayout.WEST, pnl);
            layout.putConstraint(SpringLayout.WEST, deleteMovieBtn, 300, SpringLayout.WEST, pnl);
            layout.putConstraint(SpringLayout.WEST, userLbl, 150, SpringLayout.WEST, pnl);
            layout.putConstraint(SpringLayout.WEST, deleteUserBtn, 250, SpringLayout.WEST, pnl);
            layout.putConstraint(SpringLayout.WEST, backupLbl, 150, SpringLayout.WEST, pnl);
            layout.putConstraint(SpringLayout.WEST, makeBackupBtn, 250, SpringLayout.WEST, pnl);
            layout.putConstraint(SpringLayout.WEST, recoveryBtn, 375, SpringLayout.WEST, pnl);
            //vertical
            layout.putConstraint(SpringLayout.NORTH, titleAdminStuff, 80, SpringLayout.NORTH, movie);
            layout.putConstraint(SpringLayout.NORTH, movieLbl, 45, SpringLayout.NORTH, titleAdminStuff);
            layout.putConstraint(SpringLayout.NORTH, addMovieBtn, 45, SpringLayout.NORTH, titleAdminStuff);
            layout.putConstraint(SpringLayout.NORTH, deleteMovieBtn, 45, SpringLayout.NORTH, titleAdminStuff);
            layout.putConstraint(SpringLayout.NORTH, userLbl, 35, SpringLayout.NORTH, addMovieBtn);
            layout.putConstraint(SpringLayout.NORTH, deleteUserBtn, 35, SpringLayout.NORTH, addMovieBtn);
            layout.putConstraint(SpringLayout.NORTH, backupLbl, 35, SpringLayout.NORTH, deleteUserBtn);
            layout.putConstraint(SpringLayout.NORTH, makeBackupBtn, 35, SpringLayout.NORTH, deleteUserBtn);
            layout.putConstraint(SpringLayout.NORTH, recoveryBtn, 35, SpringLayout.NORTH, deleteUserBtn);
            //look
            editBtn.setPreferredSize(new Dimension(250, 35));
            makeBackupBtn.setPreferredSize(new Dimension(125, 35));
            recoveryBtn.setPreferredSize(new Dimension(125, 35));
            for(final JButton b : new ArrayList<>(Arrays.asList(addMovieBtn, deleteMovieBtn, deleteUserBtn))){
                b.setPreferredSize(new Dimension(50, 35));
                b.setBackground(new Color(0,0,0,0));
                b.setBorder(new EmptyBorder(0,0,0,0));
            }
        }

        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                org.example.panels.Panel.getMainFrame().setVisible(false);
                org.example.panels.Panel.getStartFrame().setVisible(true);
            }
        });

        //TODO przy dodawaniu filmow zrobić osobno dodanie nowego tytułu i osobno dodanie po prostu godziny do istniejących filmow z bazy
        panel.add(pnl);
    }
}
