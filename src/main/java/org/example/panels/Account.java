package org.example.panels;

import org.example.gui.EditPopup;
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
    private static String name="", email="", birth="", movie="";
    private static Connection conn;

    public static JScrollPane getPanel(){
        loadData();
        createPanelGUI();
        return panel;
    }

    public static void loadData() {
        try {
            // connect to db
            Class.forName("com.mysql.cj.jdbc.Driver");
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
                name = customers.getString("first_name") + " " + customers.getString("last_name");
                email = customers.getString("email");
                birth = customers.getString("birth");
            }
            stGetCustomer.close();

            movie = "Currently you dont have any movies booked.\nSearch for movies and book them, then you'll see them here.";
            CallableStatement stGetMovies = conn.prepareCall("SELECT movieID FROM Movie_Customer WHERE customerID=?");
            stGetMovies.setInt(1,getUserID());
            stGetMovies.execute();
            ResultSet movies = stGetMovies.executeQuery();
            while (movies.next()){
                int movie_id = movies.getInt("movieID");
                CallableStatement stGetHours = conn.prepareCall("SELECT movieID, cinemaID, hourID FROM Movie_Cinema_Hour WHERE ID=?");
                stGetHours.setInt(1,movie_id);
                stGetHours.execute();
                ResultSet hours = stGetHours.executeQuery();
                while (hours.next()){
                    int m_id = hours.getInt("movieID");
                    int c_id = hours.getInt("cinemaID");
                    int h_id = hours.getInt("hourID");
                    String movieTitle = "";
                    String cinemaName = "";
                    String date = "";

                    CallableStatement stMovieName = conn.prepareCall("SELECT title FROM Movie WHERE ID=?");
                    stMovieName.setInt(1,m_id);
                    stMovieName.execute();
                    ResultSet m = stMovieName.executeQuery();
                    while (m.next()){
                        movieTitle = m.getString("title");
                    }
                    stMovieName.close();

                    CallableStatement stCinemaName = conn.prepareCall("SELECT name,city FROM Cinema WHERE ID=?");
                    stCinemaName.setInt(1,c_id);
                    stCinemaName.execute();
                    ResultSet c = stCinemaName.executeQuery();
                    while (c.next()){
                        cinemaName = c.getString("name") + " (" + c.getString("city") + ")";
                    }
                    stCinemaName.close();

                    CallableStatement stHour = conn.prepareCall("SELECT date_hour FROM Hour WHERE ID=?");
                    stHour.setInt(1,h_id);
                    stHour.execute();
                    ResultSet h = stHour.executeQuery();
                    while (h.next()){
                        date = h.getString("date_hour").substring(0,10) + " godz " + h.getString("date_hour").substring(11,16);
                    }
                    stHour.close();

                    if(movie.substring(0,4).equals("Curr")){
                        movie = "";
                    }
                    movie = movie + movieTitle +"\n "+ cinemaName +"\n "+ date + "\n\n";
                }
                stGetHours.close();
            }
            stGetMovies.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createPanelGUI() {
        panel = new JScrollPane();
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        panel.setLayout(null);
        JPanel pnl = new JPanel();
        pnl.setBounds(0,0,1350,2000);
        SpringLayout layout = new SpringLayout();
        pnl.setLayout(layout);

        JButton logout = new JButton("Log out");

        JLabel titleYourData = new JLabel("Your Data:");
        JLabel nameLbl = new JLabel(name);
        JLabel emailLbl = new JLabel(email);
        JLabel birthLbl = new JLabel(birth);
        JLabel pass = new JLabel("***************");
        JButton editBtn = new JButton("Edit");

        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                editData();
            }
        });

        JLabel titleYourMovies = new JLabel("Your Movies:");
        JTextArea movieTxt = new JTextArea(movie);
        movieTxt.setEditable(false);

        pnl.add(logout);
        pnl.add(titleYourData);
        pnl.add(nameLbl);
        pnl.add(emailLbl);
        pnl.add(birthLbl);
        pnl.add(pass);
        pnl.add(editBtn);
        pnl.add(titleYourMovies);
        pnl.add(movieTxt);

        logout.setFont(dataFont);
        titleYourData.setFont(titleFont);
        nameLbl.setFont(dataFont);
        emailLbl.setFont(dataFont);
        birthLbl.setFont(dataFont);
        pass.setFont(dataFont);
        editBtn.setFont(btnFont);
        titleYourMovies.setFont(titleFont);
        movieTxt.setFont(dataFont);

        //horizontal
        layout.putConstraint(SpringLayout.EAST, logout, 0, SpringLayout.EAST, pnl);
        layout.putConstraint(SpringLayout.WEST, titleYourData, 100, SpringLayout.WEST, pnl);
        layout.putConstraint(SpringLayout.WEST, nameLbl, 150, SpringLayout.WEST, pnl);
        layout.putConstraint(SpringLayout.WEST, emailLbl, 150, SpringLayout.WEST, pnl);
        layout.putConstraint(SpringLayout.WEST, birthLbl, 150, SpringLayout.WEST, pnl);
        layout.putConstraint(SpringLayout.WEST, pass, 150, SpringLayout.WEST, pnl);
        layout.putConstraint(SpringLayout.WEST, editBtn, 150, SpringLayout.WEST, pnl);
        layout.putConstraint(SpringLayout.WEST, titleYourMovies, 350, SpringLayout.EAST, titleYourData);
        layout.putConstraint(SpringLayout.WEST, movieTxt, 400, SpringLayout.EAST, titleYourData);
        //vertical
        layout.putConstraint(SpringLayout.NORTH, logout, 0, SpringLayout.NORTH, pnl);
        layout.putConstraint(SpringLayout.NORTH, titleYourData, 100, SpringLayout.NORTH, pnl);
        layout.putConstraint(SpringLayout.NORTH, nameLbl, 45, SpringLayout.NORTH, titleYourData);
        layout.putConstraint(SpringLayout.NORTH, emailLbl, 35, SpringLayout.NORTH, nameLbl);
        layout.putConstraint(SpringLayout.NORTH, birthLbl, 35, SpringLayout.NORTH, emailLbl);
        layout.putConstraint(SpringLayout.NORTH, pass, 35, SpringLayout.NORTH, birthLbl);
        layout.putConstraint(SpringLayout.NORTH, editBtn, 30, SpringLayout.NORTH, pass);
        layout.putConstraint(SpringLayout.NORTH, titleYourMovies, 100, SpringLayout.NORTH, pnl);
        layout.putConstraint(SpringLayout.NORTH, movieTxt, 45, SpringLayout.NORTH, titleYourMovies);
        //look
        movieTxt.setBackground(new Color(0,0,0,0));

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
            layout.putConstraint(SpringLayout.NORTH, titleAdminStuff, 30, SpringLayout.SOUTH, editBtn);
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

        //TODO checkpossibleseats na repertuarze i kupowanie miejsc
        //TODO zrobic edycje danych uzytkownika
        //TODO dodawanie i usuwanie filmow(transakcje),
        //TODO backup,
        //TODO usuwanie uzytkownika
        //TODO przy dodawaniu filmow zrobić osobno dodanie nowego tytułu i osobno dodanie po prostu godziny do istniejących filmow z bazy
        panel.add(pnl);
    }

    private static void editData() {
        new EditPopup(name,email,birth, getUserID());
    }
}
