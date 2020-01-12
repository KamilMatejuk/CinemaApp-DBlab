package org.example.gui;

import org.example.exceptions.EmptyTextFieldException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class JTextFieldWithHint extends JTextField implements FocusListener {

    private final String hint;
    private boolean showingHint;

    public JTextFieldWithHint(final String hint) {
        super(hint);
        this.hint = hint;
        this.showingHint = true;
        super.setForeground(new Color(0,0,0,100));
        super.addFocusListener(this);
    }

    @Override
    public void focusGained(FocusEvent e) {
        if(this.getText().equals(hint) || this.getText().isEmpty()) {
            super.setText("");
            super.setForeground(new Color(0,0,0,255));
            showingHint = false;
        }
    }
    @Override
    public void focusLost(FocusEvent e) {
        if(this.getText().equals(hint) || this.getText().isEmpty()) {
            super.setText(hint);
            super.setForeground(new Color(0,0,0,100));
            showingHint = true;
        }
    }

    public String getTxt() throws EmptyTextFieldException {
        if(showingHint){
            throw new EmptyTextFieldException("TextField you tried to read is empty");
        } else {
            return super.getText();
        }
    }
}
