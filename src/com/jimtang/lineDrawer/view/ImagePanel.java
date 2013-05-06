package com.jimtang.lineDrawer.view;

import com.jimtang.lineDrawer.model.Graph;
import com.jimtang.lineDrawer.model.GraphDrawer;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ImagePanel extends JPanel {
    private GraphDrawer graphDrawer;

    public ImagePanel() {
        try {
            graphDrawer = new GraphDrawer(new Graph("resources/edgar_1_md.gif"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(graphDrawer.getNextFrame().getImage(), 0, 0, null);
    }

}