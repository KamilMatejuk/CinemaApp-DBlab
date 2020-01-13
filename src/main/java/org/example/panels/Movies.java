package org.example.panels;

import org.example.panels.Panel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Movies extends Panel {

    private static JScrollPane panel;

    public static JScrollPane getPanel(){
        createPanelGUI();
        return panel;
    }

    private static void createPanelGUI() {
        panel = new JScrollPane();
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder());
        panel.setLayout(null);
        JPanel pnl = new JPanel();
        pnl.setBounds(0,0,1350,715);
        pnl.setLayout(null);
        JPanel filtersPnl = new JPanel();
        JPanel moviesPnl = new JPanel();
        pnl.add(filtersPnl);
        pnl.add(moviesPnl);
        int filtersWidth = 400;
        filtersPnl.setBounds(0,0,filtersWidth,715);
        moviesPnl.setBounds(350,0,1350-filtersWidth, 715);
        filtersPnl.setLayout(null);
        filtersPnl.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        ArrayList<String> categories = new ArrayList<>(Arrays.asList("---","Action","Comedy"));
        ArrayList<String> languages = new ArrayList<>(Arrays.asList("---","EN dubbing", "EN lector", "EN subtitles", "PL dubbing"));
        ArrayList<String> actors = new ArrayList<>(Arrays.asList("---","Will Smith","Morgan Freeman"));
        JLabel filterCategoryLbl = new JLabel("Category:");
        final JComboBox filterCategoryChoose = new JComboBox(categories.toArray());
        JLabel filterLanguageLbl = new JLabel("Language:");
        final JComboBox filterLanguageChoose = new JComboBox(languages.toArray());
        JLabel filterReleaseLbl = new JLabel("Released after:");
        final JTextField filterReleaseChoose = new JTextField();
        JLabel filterActorLbl = new JLabel("Actor:");
        final JComboBox filterActorChoose = new JComboBox(actors.toArray());
        final JLabel filterRatingLbl = new JLabel("Ratings ≥ 1");
        final JSlider filterRatingChoose = new JSlider(SwingConstants.HORIZONTAL, 1, 10, 1);
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
                System.out.println(category +" "+ language +" "+ release +" "+ actor +" "+ rating);
            }
        });

        panel.add(pnl);
    }
}
