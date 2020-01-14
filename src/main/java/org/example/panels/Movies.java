package org.example.panels;

import org.example.panels.Panel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Movies extends Panel {

    private static JScrollPane panel;
    private static JPanel moviesPnl;
    private static ArrayList<String> categories;
    private static ArrayList<String> languages;
    private static ArrayList<String> actors;
    private static Connection conn;

    static JComboBox filterCategoryChoose;
    static JComboBox filterLanguageChoose;
    static JTextField filterReleaseChoose;
    static JComboBox filterActorChoose;
    static JSlider filterRatingChoose;

    public static JScrollPane getPanel(){
        getOptions();
        createPanelGUI();
        loadData("---","---",0,"---",1);
        return panel;
    }

    private static void getOptions() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            if (!getIsLoggedIn()) {
                conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/dblab5?noAccessToProcedureBodies=true",
                        "notlogged",
                        "userPassword1!");
            } else if(!getIsAdmin()) {
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

            Statement stCat = conn.createStatement();
            ResultSet rsCat = stCat.executeQuery("SELECT DISTINCT category FROM Movie");
            categories = new ArrayList<>();
            categories.add("---");
            while(rsCat.next()){
                categories.add(rsCat.getString("category"));
            }

            Statement stLan = conn.createStatement();
            ResultSet rsLan = stLan.executeQuery("SELECT DISTINCT language FROM Movie");
            languages = new ArrayList<>();
            languages.add("---");
            while(rsLan.next()){
                languages.add(rsLan.getString("language"));
            }

            Statement stAct = conn.createStatement();
            ResultSet rsAct = stAct.executeQuery("SELECT DISTINCT first_name,last_name FROM Actor");
            actors = new ArrayList<>();
            actors.add("---");
            while(rsAct.next()){
                actors.add(rsAct.getString("first_name") +" "+ rsAct.getString("last_name"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadData(String category, String language, int release, String actor, int rating) {
        try {
            String actorCond;
            String ratingActorCond;
            String categoryLanguageCond;
            String allCond;
            if(actor.equals("---")) {
                actorCond = "SELECT ID FROM Movie";
            } else {
                String names[] = actor.split(" ");
                actorCond = "SELECT ID FROM Movie WHERE ID IN (SELECT movieID FROM Movie_Actor JOIN Actor ON Movie_Actor.ActorID=Actor.ID WHERE  Actor.first_name='"+names[0]+"' AND Actor.last_name='"+names[1]+"')";
            }
            ratingActorCond = "SELECT * FROM Movie JOIN Rating ON Rating.movieID=Movie.ID WHERE Rating.avg>="+rating+" AND Movie.ID IN ("+actorCond+")";
            categoryLanguageCond = "AND Movie.relese>"+release;
            categoryLanguageCond = (category.equals("---") ? categoryLanguageCond : categoryLanguageCond+" AND category='"+category+"'");
            categoryLanguageCond = (language.equals("---") ? categoryLanguageCond : categoryLanguageCond+" AND language=\'"+language+"\'");
            allCond = ratingActorCond+" "+categoryLanguageCond;

            ArrayList<JPanel> panels = new ArrayList<>();
            Statement stGetMovies = conn.createStatement();
            final ResultSet rsMovies = stGetMovies.executeQuery(allCond);
            while(rsMovies.next()){
                final int id = rsMovies.getInt("MovieID");
                JPanel tempPanel = new JPanel();
                tempPanel.setLayout(null);
                JLabel title = new JLabel(rsMovies.getString("Movie.title"));
                JLabel time = new JLabel(rsMovies.getInt("Movie.length") + " min");
                JTextArea desc = new JTextArea(rsMovies.getString("Movie.description"));
                JLabel rate = new JLabel("rating: " + rsMovies.getString("Rating.avg").substring(0,4));
                tempPanel.add(title);
                tempPanel.add(time);
                tempPanel.add(desc);
                tempPanel.add(rate);
                title.setFont(dataFont);
                time.setFont(dataFont);
                desc.setFont(dataFont);
                rate.setFont(dataFont);
                title.setBounds(0,0,500,50);
                time.setBounds(500,0,200,50);
                desc.setBounds(0,50,700,75);
                rate.setBounds(0,125,500,50);
                desc.setWrapStyleWord(true);
                desc.setBackground(new Color(0,0,0,0));

                if(getIsLoggedIn()) {
                    JButton rateBtn = new JButton("rate");
                    tempPanel.add(rateBtn);
                    rateBtn.setFont(btnFont);
                    rateBtn.setBounds(500, 125, 200, 50);
                    rateBtn.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            try {
                                Object[] possibilities = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
                                int r = (int) JOptionPane.showInputDialog(
                                        getMainFrame(),
                                        "Rate on a scale of 1-10",
                                        "Rating Dialog",
                                        JOptionPane.PLAIN_MESSAGE,
                                        null,
                                        possibilities,
                                        10);

                                CallableStatement stRate = conn.prepareCall("{? = CALL rate(?,?)}");
                                stRate.registerOutParameter(1, Types.DOUBLE);
                                stRate.setInt(2, r);
                                stRate.setInt(3, id);
                                stRate.execute();
                                double rate = stRate.getDouble(1);
                                stRate.close();
                                System.out.println(rate);

                                String category = filterCategoryChoose.getSelectedItem().toString();
                                String language = filterLanguageChoose.getSelectedItem().toString();
                                int release;
                                String actor = filterActorChoose.getSelectedItem().toString();
                                int rating = filterRatingChoose.getValue();
                                try {
                                    release = Integer.parseInt(filterReleaseChoose.getText());
                                } catch (NumberFormatException nfe) {
                                    release = 0;
                                }
                                loadData(category, language, release, actor, rating);

                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

                panels.add(tempPanel);
            }
            int c = 0;
            for( JPanel p : panels){
                moviesPnl.add(p);
                p.setBounds(100,c*300,750,300);
                c++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createPanelGUI() {
        panel = new JScrollPane();
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        panel.setLayout(null);
        JPanel pnl = new JPanel();
        pnl.setBounds(0,0,1350,715);
        pnl.setLayout(null);
        JPanel filtersPnl = new JPanel();
        moviesPnl = new JPanel();
        pnl.add(filtersPnl);
        pnl.add(moviesPnl);
        int filtersWidth = 400;
        filtersPnl.setBounds(0,0,filtersWidth,715);
        moviesPnl.setBounds(350,0,1350-filtersWidth, 715);
        filtersPnl.setLayout(null);
        filtersPnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        JLabel filterCategoryLbl = new JLabel("Category:");
        filterCategoryChoose = new JComboBox(categories.toArray());
        JLabel filterLanguageLbl = new JLabel("Language:");
        filterLanguageChoose = new JComboBox(languages.toArray());
        JLabel filterReleaseLbl = new JLabel("Released after:");
        filterReleaseChoose = new JTextField();
        JLabel filterActorLbl = new JLabel("Actor:");
        filterActorChoose = new JComboBox(actors.toArray());
        final JLabel filterRatingLbl = new JLabel("Ratings ≥ 1");
        filterRatingChoose = new JSlider(SwingConstants.HORIZONTAL, 1, 10, 1);
        JButton filterBtn = new JButton("Filter");

        filtersPnl.add(filterCategoryLbl);
        filtersPnl.add(filterCategoryChoose);
        filtersPnl.add(filterLanguageLbl);
        filtersPnl.add(filterLanguageChoose);
        filtersPnl.add(filterReleaseLbl);
        filtersPnl.add(filterReleaseChoose);
        filtersPnl.add(filterActorLbl);
        filtersPnl.add(filterActorChoose);
        filtersPnl.add(filterRatingLbl);
        filtersPnl.add(filterRatingChoose);
        filtersPnl.add(filterBtn);

        filterCategoryLbl.setFont(dataFont);
        filterCategoryChoose.setFont(dataFont);
        filterLanguageLbl.setFont(dataFont);
        filterLanguageChoose.setFont(dataFont);
        filterReleaseLbl.setFont(dataFont);
        filterReleaseChoose.setFont(dataFont);
        filterActorLbl.setFont(dataFont);
        filterActorChoose.setFont(dataFont);
        filterRatingLbl.setFont(dataFont);
        filterRatingChoose.setFont(dataFont);
        filterBtn.setFont(dataFont);

        int padding = 20;
        int compHeight = 50;
        filterCategoryLbl.setBounds(padding, padding, filtersWidth - 2*padding, compHeight);
        filterCategoryChoose.setBounds(padding, padding + compHeight, filtersWidth - 2*padding, compHeight);
        filterLanguageLbl.setBounds(padding, padding + 2*compHeight, filtersWidth - 2*padding, compHeight);
        filterLanguageChoose.setBounds(padding, padding + 3*compHeight, filtersWidth - 2*padding, compHeight);
        filterReleaseLbl.setBounds(padding, padding + 4*compHeight, filtersWidth - 2*padding, compHeight);
        filterReleaseChoose.setBounds(padding, padding + 5*compHeight, filtersWidth - 2*padding, compHeight);
        filterActorLbl.setBounds(padding, padding + 6*compHeight, filtersWidth - 2*padding, compHeight);
        filterActorChoose.setBounds(padding, padding + 7*compHeight, filtersWidth - 2*padding, compHeight);
        filterRatingLbl.setBounds(padding, padding + 8*compHeight, filtersWidth - 2*padding, compHeight);
        filterRatingChoose.setBounds(padding, padding + 9*compHeight, filtersWidth - 2*padding, compHeight);
        filterBtn.setBounds((int)(0.2*filtersWidth), 11*compHeight, (int)(0.6*filtersWidth), compHeight);

        ((JLabel) filterCategoryChoose.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel) filterLanguageChoose.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        ((JLabel) filterActorChoose.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        filterReleaseChoose.setHorizontalAlignment(SwingConstants.CENTER);

        filterRatingChoose.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent changeEvent) {
                filterRatingLbl.setText("Ratings ≥ " + ((JSlider) changeEvent.getSource()).getValue());
            }
        });

        filterBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String category = filterCategoryChoose.getSelectedItem().toString();
                String language = filterLanguageChoose.getSelectedItem().toString();
                int release;
                String actor = filterActorChoose.getSelectedItem().toString();
                int rating = filterRatingChoose.getValue();
                try {
                    release = Integer.parseInt(filterReleaseChoose.getText());
                } catch (NumberFormatException nfe){
                    release = 0;
                }
                loadData(category,language,release,actor,rating);
            }
        });

        panel.add(pnl);
    }

}
