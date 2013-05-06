package com.jimtang.lineDrawer.model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * User: Jim
 * Date: 05/05/13 17:25
 */

public class GraphDrawer {
    private static final int TILE_SIZE = 2;

    private Graph originalGraph;
    private int xTiles, yTiles;
    private TileGraph tiledGraph;
    private Graph currentGraph;
    private Pixel tip;


    public Graph draw(int strokeLength) {
        if (tip == null) {
            tip = findAStartPoint();
            if (tip == null) {
                return currentGraph;
            }
        }
        Pixel strokeEnd = bfsCopy(tip, strokeLength);
        if (tip.equals(strokeEnd)) {
            tip = null;
            return draw(strokeLength);
        }
        return currentGraph;
    }

    private boolean[][] usedPixels;

    /**
     * Copy all adjacent pixels within radius from the origin to currentGraph
     *
     * @param origin
     * @param radius
     * @return the furthest pixel
     */
    private Pixel bfsCopy(Pixel origin, int radius) {
        Queue<Pixel> candidatePixels = new LinkedList<Pixel>();
        Pixel furthestPixel = origin;
        candidatePixels.add(origin);
        while (!candidatePixels.isEmpty()) {
            Pixel currentPixel = candidatePixels.remove();
            for (int x = Math.max(currentPixel.x - 1, 0); x <= Math.min(currentPixel.x + 1, originalGraph.getWidth()); x++) {
                for (int y = Math.max(currentPixel.y - 1, 0); y <= Math.min(currentPixel.y + 1, originalGraph.getHeight()); y++) {
                    if (usedPixels[x][y] || originalGraph.isWhite(x, y)) {
                        continue;
                    }
                    Pixel borderPixel = new Pixel(x, y);
                    double distance = origin.distanceTo(borderPixel);
                    if (distance < radius) {
                        usedPixels[x][y] = true;
                        candidatePixels.add(borderPixel);
                        currentGraph.setColor(x, y, originalGraph.getColor(x, y));
                        if (distance > furthestPixel.distanceTo(origin)) {
                            furthestPixel = borderPixel;
                        }
                    }
                }
            }
        }
        return furthestPixel;
    }

    private boolean shouldGo(Pixel t) {
        return t != null && tiledGraph.isNonEmptyTile(tip) && !tiledGraph.isUsed(t);
    }

    public GraphDrawer(Graph graph) {
        originalGraph = graph;
        currentGraph = new Graph(graph.getWidth(), graph.getHeight());
        usedPixels = new boolean[graph.getWidth()][graph.getHeight()];
    }

    private Pixel findAStartPoint() {
        for (int x = 0; x < originalGraph.getWidth(); x++) {
            for (int y = 0; y < originalGraph.getHeight(); y++) {
                if (!usedPixels[x][y] && !originalGraph.isWhite(x, y)) {
                    return new Pixel(x, y);
                }
            }
        }
        return null;
    }

    private void initTiledGraphs(Graph graph) {
        xTiles = (int) Math.ceil(graph.getWidth() / TILE_SIZE);
        yTiles = (int) Math.ceil(graph.getHeight() / TILE_SIZE);
        tiledGraph = new TileGraph(xTiles, yTiles);
        for (int tileY = 0; tileY < yTiles; tileY++) {
            int y = tileY * TILE_SIZE;
            for (int tileX = 0; tileX < xTiles; tileX++) {
                int x = tileX * TILE_SIZE;
                tiledGraph.set(tileX, tileY, !graph.getSubGraph(x, y, TILE_SIZE, TILE_SIZE).isAllWhite());
            }
        }
        tip = tiledGraph.findNextStartPoint();
    }

    private class TileGraph {
        private boolean[][] tiledGraph;
        private boolean[][] usedTiles;

        public TileGraph(int xTiles, int yTiles) {
            tiledGraph = new boolean[xTiles][yTiles];
            usedTiles = new boolean[xTiles][yTiles];
        }

        public void set(int x, int y, boolean isNonEmpty) {
            tiledGraph[x][y] = isNonEmpty;
        }

        public boolean isNonEmptyTile(Pixel t) {
            return tiledGraph[t.x][t.y];
        }

        public boolean isUsed(Pixel t) {
            return usedTiles[t.x][t.y];
        }

        public void use(Pixel next) {
            usedTiles[next.x][next.y] = true;
        }

        public Pixel findNextStartPoint() {
            for (int x = 0; x < xTiles; x++) {
                for (int y = 0; y < yTiles; y++) {
                    if (tiledGraph[x][y] && !usedTiles[x][y]) {
                        return new Pixel(x, y);
                    }
                }
            }
            return null;
        }
    }

    private class Pixel {
        public final int x, y;

        public Pixel(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public boolean isAtRightEdge() {
            return x == xTiles;
        }

        public boolean isAtLeftEdge() {
            return x == 0;
        }

        public boolean isAtTopEdge() {
            return y == 0;
        }

        public boolean isAtBottomEdge() {
            return y == yTiles;
        }

        public double distanceTo(Pixel that) {
            int dx = this.x - that.x;
            int dy = this.y - that.y;
            return Math.sqrt(dx * dx + dy * dy);
        }

        public Square toSquare() {
            return new Square(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE);
        }
    }

}

