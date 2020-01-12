package org.example.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Main extends JFrame {

    private boolean isLoggedIn = true;
    private boolean isAdmin = true;
    JFrame frame = this;
    JScrollPane repertoireJPanel, moviesJPanel, accountJPanel, panel;

    Main(){
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
        createPanelRepertoire();
        createPanelMovies();
        createPanelMyAccount();

        if(isLoggedIn){
            styleMenuBar(new ArrayList<>(Arrays.asList(tab1, tab2, tab3)));
        } else {
            styleMenuBar(new ArrayList<>(Arrays.asList(tab1, tab2)));
        }

        panel = moviesJPanel;
        add(panel);
        panel.setBounds(25,125,1350,715);
        setVisible(true);

        tab1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.remove(panel);
                panel = repertoireJPanel;
                frame.add(panel);
                panel.setBounds(25,125,1350,715);
                frame.repaint();
            }
        });
        tab2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.remove(panel);
                panel = moviesJPanel;
                frame.add(panel);
                panel.setBounds(25,125,1350,715);
                frame.repaint();
            }
        });
        tab3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                frame.remove(panel);
                panel = accountJPanel;
                frame.add(panel);
                panel.setBounds(25,125,1350,715);
                frame.repaint();
            }
        });
    }

    // TODO NWM CZEMU TEDWIE FUNKCJE WYGLADAJA IDENTYCZNIE A WIDAC TYLKO REPERTUAR !!!
    private void createPanelRepertoire() {
        repertoireJPanel = new JScrollPane();
//        repertoireJPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//        repertoireJPanel.setLayout(null);
//        JPanel pnl = new JPanel();
//        pnl.setBounds(0,0,1350,715);
//        SpringLayout layout = new SpringLayout();
//        pnl.setLayout(layout);
//
//        //TODO pobrac dane kin
//        ArrayList<String> cinemas = new ArrayList<>(Arrays.asList("-----","Wrocław - Arkady", "Wrocław - Pasaz Grunwaldzki", "Wrocław - Magnolia", "Warszawa - Galeria"));
//        JComboBox cinemasCombo = new JComboBox(cinemas.toArray());
//
//        pnl.add(cinemasCombo);
//
//        Font dataFont = new Font(Font.SANS_SERIF, Font.PLAIN, 25);
//        cinemasCombo.setFont(dataFont);
//        ((JLabel) cinemasCombo.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
//
//        //TODO dla wybranego kina pobrac filmy
//
//        layout.putConstraint(SpringLayout.WEST, cinemasCombo, 200, SpringLayout.WEST, pnl);
//        layout.putConstraint(SpringLayout.EAST, cinemasCombo, -200, SpringLayout.EAST, pnl);
//
//        repertoireJPanel.add(pnl);
        repertoireJPanel.setBackground(Color.RED);
    }

    private void createPanelMovies() {
        moviesJPanel = new JScrollPane();
//        moviesJPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
//        moviesJPanel.setLayout(null);
//        JPanel pnl = new JPanel();
//        pnl.setBounds(0,0,1350,715);
//        SpringLayout layout = new SpringLayout();
//        pnl.setLayout(layout);

//        ArrayList<String> categories = new ArrayList<>(Arrays.asList("---","Action","Comedy"));
//        ArrayList<String> languages = new ArrayList<>(Arrays.asList("---","English","Polish"));
//        ArrayList<String> actors = new ArrayList<>(Arrays.asList("---","Will Smith","Morgan Freeman"));
//        JLabel filterCategoryLbl = new JLabel("Category");
//        JComboBox filterCategoryChoose = new JComboBox(categories.toArray());
//        JLabel filterLanguageLbl = new JLabel("Language");
//        JComboBox filterLanguageChoose = new JComboBox(languages.toArray());
//        JLabel filterReleaseLbl = new JLabel("Release Date");
//        JTextField filterReleaseChoose = new JTextField();
//        JLabel filterActorLbl = new JLabel("Actor");
//        JComboBox filterActorChoose = new JComboBox(actors.toArray());
//        JLabel filterRatingLbl = new JLabel("Ratings");
//        JSlider filterRatingChoose = new JSlider(SwingConstants.HORIZONTAL, 1, 10, 1);
//        JButton filterBtn = new JButton("Filter");
//
//        pnl.add(filterCategoryLbl);
//        pnl.add(filterCategoryChoose);
//        pnl.add(filterLanguageLbl);
//        pnl.add(filterLanguageChoose);
//        pnl.add(filterReleaseLbl);
//        pnl.add(filterReleaseChoose);
//        pnl.add(filterActorLbl);
//        pnl.add(filterActorChoose);
//        pnl.add(filterRatingLbl);
//        pnl.add(filterRatingChoose);
//        pnl.add(filterBtn);
//
//        filterCategoryLbl.setFont(dataFont);
//        filterCategoryChoose.setFont(dataFont);
//        filterLanguageLbl.setFont(dataFont);
//        filterLanguageChoose.setFont(dataFont);
//        filterReleaseLbl.setFont(dataFont);
//        filterReleaseChoose.setFont(dataFont);
//        filterActorLbl.setFont(dataFont);
//        filterActorChoose.setFont(dataFont);
//        filterRatingLbl.setFont(dataFont);
//        filterRatingChoose.setFont(dataFont);
//        filterBtn.setFont(dataFont);

//        moviesJPanel.add(pnl);
        moviesJPanel.setBackground(Color.RED);
    }

    private void createPanelMyAccount() {
        accountJPanel = new JScrollPane();
        accountJPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        accountJPanel.setLayout(null);
        JPanel pnl = new JPanel();
        pnl.setBounds(0,0,1350,715);
        SpringLayout layout = new SpringLayout();
        pnl.setLayout(layout);

        JButton logout = new JButton("Log out");

        //TODO pobrac z bazy dane uzytkownika
        //TODO dodać wylogowywanie
        JLabel titleYourData = new JLabel("Your Data:");
        JLabel name = new JLabel("John Smith");
        JLabel email = new JLabel("johnsmith@mail.com");
        JLabel birth = new JLabel("01-01-1990");
        JLabel pass = new JLabel("***************");
        JButton editBtn = new JButton("Edit");

        JLabel titleYourMovies = new JLabel("Your Movies:");
        JTextArea movie = new JTextArea("Currently you dont have any movies booked.\nSearch for movies and book them, then you'll see them here.");

        pnl.add(logout);
        pnl.add(titleYourData);
        pnl.add(name);
        pnl.add(email);
        pnl.add(birth);
        pnl.add(pass);
        pnl.add(editBtn);
        pnl.add(titleYourMovies);
        pnl.add(movie);

        Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 30);
        Font dataFont = new Font(Font.SANS_SERIF, Font.PLAIN, 25);
        Font btnFont = new Font(Font.SANS_SERIF, Font.BOLD, 15);
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

        if(isAdmin){
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

        accountJPanel.add(pnl);
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
