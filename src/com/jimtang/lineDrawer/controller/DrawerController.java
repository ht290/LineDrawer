package com.jimtang.lineDrawer.controller;

import com.jimtang.lineDrawer.model.Graph;
import com.jimtang.lineDrawer.model.GraphDrawer;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * User: Jim
 * Date: 06/05/13 18:11
 */
public class DrawerController {
    private GraphDrawer drawer;
    private Thread updateThread;
    private long frameLengthMillis;
    private List<JPanel> listeners;
    private Graph currentGraph;
    private int strokeSize = 10;

    public DrawerController() {
        try {
            drawer = new GraphDrawer(new Graph("resources/edgar_1_md.gif"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        listeners = new LinkedList<JPanel>();

        updateThread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    currentGraph = drawer.draw(strokeSize);
                    for (JPanel listener : listeners) {
                        listener.repaint();
                    }
                    try {
                        Thread.sleep(frameLengthMillis);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };
    }

    public void start(long frameLengthMillis) {
        this.frameLengthMillis = frameLengthMillis;
        updateThread.start();
    }

    public void setStrokeSize(int strokeSize) {
        this.strokeSize = strokeSize;
    }

    public void registerListener(JPanel panel) {
        listeners.add(panel);
    }

    public BufferedImage getImage() {
        return currentGraph.getImage();
    }

}
