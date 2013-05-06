package com.jimtang.lineDrawer.view;

import com.jimtang.lineDrawer.controller.DrawerController;

import javax.swing.*;
import java.awt.*;

public class ImagePanel extends JPanel {
    private DrawerController drawerController;

    public ImagePanel() {
        drawerController = new DrawerController();
        drawerController.registerListener(this);
        drawerController.setStrokeSize(10);
        drawerController.start(200);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(drawerController.getImage(), 0, 0, null);
    }

}