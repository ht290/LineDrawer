package com.jimtang.lineDrawer.model;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * User: Jim
 * Date: 05/05/13 16:32
 */
public class Graph {
    private BufferedImage image;
    private Graphics2D graphics2D;
    private static final int INT_RGB_WHITE = -1;

    public BufferedImage getImage() {
        return image;
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public Graph getSubGraph(int x, int y, int w, int h) {
        return new Graph(image.getSubimage(x, y, w, h));
    }

    public Graph getSubGraph(Square s) {
        return getSubGraph(s.x, s.y, s.l, s.l);
    }

    public boolean isAllWhite() {
        boolean allWhite = true;
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if (image.getRGB(x, y) != INT_RGB_WHITE) {
                    allWhite = false;
                }
            }
        }
        return allWhite;
    }

    public void add(Graph g, int x, int y) {
        image.createGraphics().drawImage(g.getImage(), x, y, null);
    }

    public void add(Graph g, Square square) {
        add(g, square.x, square.y);
    }

    public boolean isWhite(int x, int y) {
        return image.getRGB(x, y) == INT_RGB_WHITE;
    }

    public int getColor(int x, int y) {
        return image.getRGB(x, y);
    }

    public void setColor(int x, int y, int color) {
        image.setRGB(x, y, color);
    }

    public Graph(String path) throws IOException {
        this(ImageIO.read(new File(path)));
    }

    public Graph(int width, int height) {
        this(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB));
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, width, height);
    }

    public Graph(BufferedImage image) {
        this.image = image;
        this.graphics2D = image.createGraphics();
    }
}
