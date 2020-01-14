package org.example.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class Repertoire extends Panel {

    private static JScrollPane panel;
    private static JTextArea movies;
    private static ArrayList<String> cinemas;
    private static Connection conn;

    public static JScrollPane getPanel(){
        getOptions();
        createPanelGUI();
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
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT name,city FROM Cinema");
            cinemas = new ArrayList<>();
            cinemas.add("---");
            while(rs.next()){
                cinemas.add(rs.getString("name") + " - " + rs.getString("city"));
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadData(String option) {
        if(option.equals("---")) {
            movies.setText("");
        } else {
            movies.setText("");
            String parts[] = option.split(" - ");
            String name = parts[0];
            String city = parts[1];
            try {
                CallableStatement stGetCinemaId = conn.prepareCall("SELECT ID FROM Cinema WHERE name=? AND city=?");
                stGetCinemaId.setString(1,name);
                stGetCinemaId.setString(2,city);
                stGetCinemaId.execute();
                ResultSet ids = stGetCinemaId.executeQuery();
                while (ids.next()){
                    int id = ids.getInt("ID");
                    CallableStatement stGetHours = conn.prepareCall("SELECT movieID,hourID FROM Movie_Cinema_Hour WHERE cinemaID=?");
                    stGetHours.setInt(1,id);
                    stGetHours.execute();
                    ResultSet hours = stGetHours.executeQuery();
                    while (hours.next()){
                        int m_id = hours.getInt("movieID");
                        int h_id = hours.getInt("hourID");
                        String movieTitle = "";
                        String date = "";
                        String rating = "";

                        CallableStatement stMovieName = conn.prepareCall("SELECT title FROM Movie WHERE ID=?");
                        stMovieName.setInt(1,m_id);
                        stMovieName.execute();
                        ResultSet m = stMovieName.executeQuery();
                        while (m.next()){
                            movieTitle = m.getString("title");
                        }
                        stMovieName.close();

                        CallableStatement stHour = conn.prepareCall("SELECT date_hour FROM Hour WHERE ID=?");
                        stHour.setInt(1,h_id);
                        stHour.execute();
                        ResultSet h = stHour.executeQuery();
                        while (h.next()){
                            date = h.getString("date_hour").substring(0,10) + " godz " + h.getString("date_hour").substring(11,16);
                        }
                        stHour.close();

                        CallableStatement stRating = conn.prepareCall("SELECT avg FROM Rating WHERE movieID=?");
                        stRating.setInt(1,m_id);
                        stRating.execute();
                        ResultSet r = stRating.executeQuery();
                        while (r.next()){
                            rating = r.getString("avg").substring(0,5);
                        }
                        stRating.close();

                        movies.setText(movies.getText() + movieTitle + "\n" + date + "          rating: " + rating + "\n\n------------------------------------------------------------------------------------\n\n");
                    }
                    stGetHours.close();
                }
                ids.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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

        final JComboBox cinemasCombo = new JComboBox(cinemas.toArray());

        pnl.add(cinemasCombo);
        movies = new JTextArea("");
        movies.setEditable(false);
        movies.setFont(dataFont);
        movies.setBackground(new Color(0,0,0,0));
        pnl.add(movies);

        Font dataFont = new Font(Font.SANS_SERIF, Font.PLAIN, 25);
        cinemasCombo.setFont(dataFont);
        ((JLabel) cinemasCombo.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        layout.putConstraint(SpringLayout.WEST, cinemasCombo, 200, SpringLayout.WEST, pnl);
        layout.putConstraint(SpringLayout.EAST, cinemasCombo, -200, SpringLayout.EAST, pnl);
        layout.putConstraint(SpringLayout.NORTH, cinemasCombo, 0, SpringLayout.NORTH, pnl);
        layout.putConstraint(SpringLayout.WEST, movies, 300, SpringLayout.WEST, pnl);
        layout.putConstraint(SpringLayout.EAST, movies, -300, SpringLayout.EAST, pnl);
        layout.putConstraint(SpringLayout.NORTH, movies, 100, SpringLayout.SOUTH, cinemasCombo);

        panel.add(pnl);

        cinemasCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loadData(cinemasCombo.getSelectedItem().toString());
            }
        });
    }
}
