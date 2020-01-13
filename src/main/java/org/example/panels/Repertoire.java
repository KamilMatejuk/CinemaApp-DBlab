package org.example.panels;

import org.example.panels.Panel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Repertoire extends Panel {

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
        SpringLayout layout = new SpringLayout();
        pnl.setLayout(layout);

        //TODO pobrac dane kin
        final ArrayList<String> cinemas = new ArrayList<>(Arrays.asList("-----","Wrocław - Arkady", "Wrocław - Pasaz Grunwaldzki", "Wrocław - Magnolia", "Warszawa - Galeria"));
        final JComboBox cinemasCombo = new JComboBox(cinemas.toArray());

        pnl.add(cinemasCombo);

        Font dataFont = new Font(Font.SANS_SERIF, Font.PLAIN, 25);
        cinemasCombo.setFont(dataFont);
        ((JLabel) cinemasCombo.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        //TODO dla wybranego kina pobrac filmy

        layout.putConstraint(SpringLayout.WEST, cinemasCombo, 200, SpringLayout.WEST, pnl);
        layout.putConstraint(SpringLayout.EAST, cinemasCombo, -200, SpringLayout.EAST, pnl);
        layout.putConstraint(SpringLayout.NORTH, cinemasCombo, 0, SpringLayout.NORTH, pnl);

        panel.add(pnl);

        cinemasCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                System.out.println(cinemasCombo.getSelectedItem().toString());
            }
        });
    }
}
