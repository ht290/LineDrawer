package com.jimtang.lineDrawer.view;

import javax.swing.*;
import java.awt.*;

/**
 * User: Jim
 * Date: 05/05/13 16:01
 */
public class GUI {

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("Line Drawer");
        frame.setPreferredSize(new Dimension(500, 700));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        ImagePanel imagePanel = new ImagePanel();
        imagePanel.setOpaque(true); //content panes must be opaque
        frame.setContentPane(imagePanel);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
}
