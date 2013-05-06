package com.jimtang.lineDrawer.model;

import java.util.LinkedList;
import java.util.Queue;

/**
 * User: Jim
 * Date: 05/05/13 17:25
 */

public class GraphDrawer {
    private static final int tileSize = 5;

    private Graph originalGraph;
    private Graph currentGraph;
    private Queue<Tile> nonEmptyTiles;

    public Graph getNextFrame() {
        if (!nonEmptyTiles.isEmpty()) {
            Tile newTile = nonEmptyTiles.remove();
            currentGraph.add(originalGraph.getSubGraph(newTile.x, newTile.y, newTile.w, newTile.h), newTile.x, newTile.y);
        }
         return currentGraph;

//        return currentGraph;
    }

    public GraphDrawer(Graph graph) {
        originalGraph = graph;
        currentGraph = new Graph(graph.getWidth(), graph.getHeight());
        findNonEmptyTiles(graph);
    }

    private void findNonEmptyTiles(Graph graph) {
        nonEmptyTiles = new LinkedList<Tile>();
        for (int tileY = 0; tileY < graph.getHeight() / tileSize; tileY++) {
            int y = tileY * tileSize;
            for (int tileX = 0; tileX < graph.getWidth() / tileSize; tileX++) {
                int x = tileX * tileSize;
                if (!graph.getSubGraph(x, y, tileSize, tileSize).isAllWhite()) {
                    nonEmptyTiles.add(new Tile(x, y, tileSize, tileSize));
                }
            }
        }
    }
}

class Tile {
    public final int x, y, w, h;

    public Tile(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }
}
