package org.example.panels;

import javax.swing.*;
import java.awt.*;

public class Panel {

    private static boolean isLoggedIn = false;
    private static boolean isAdmin = false;
    private static int userID = 0;
    private static JFrame mainFrame;
    private static JFrame startFrame;

    static Font titleFont = new Font(Font.SANS_SERIF, Font.BOLD, 30);
    static Font dataFont = new Font(Font.SANS_SERIF, Font.PLAIN, 25);
    static Font btnFont = new Font(Font.SANS_SERIF, Font.BOLD, 15);

    public static void setIsLoggedIn(boolean b){ isLoggedIn = b; }
    public static void setIsAdmin(boolean b){ isAdmin = b; }
    public static void setUserID(int i){ userID = i; }
    public static void setMainFrame(JFrame f){ mainFrame = f; }
    public static void setStartFrame(JFrame f){ startFrame = f; }

    public static boolean getIsLoggedIn(){ return isLoggedIn; }
    public static boolean getIsAdmin(){ return isAdmin; }
    public static int getUserID(){ return userID; }
    public static JFrame getMainFrame(){ return mainFrame; }
    public static JFrame getStartFrame(){ return startFrame; }

}
