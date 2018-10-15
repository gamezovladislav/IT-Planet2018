package com.simbirsoft.itplanet.service;

import com.simbirsoft.itplanet.entity.PathNode;
import com.simbirsoft.itplanet.entity.PlaygroundState;
import com.simbirsoft.itplanet.entity.type.PlaygroundItemType;

import java.util.Queue;
import java.util.Stack;

public final class PlaygroundStrategyPlayer {

    private final PlaygroundState playground;
    private final PlaygroundStrategy playgroundStrategy;

    private static final int INFINITY_DISTANCE = 1000 * 1000 * 1000;
    private static final int[] D_ROW = new int[]{0, 0, 1, -1};
    private static final int[] D_COLUMN = new int[]{1, -1, 0, 0};

    public PlaygroundStrategyPlayer(
            PlaygroundState playground,
            PlaygroundStrategy playgroundStrategy
    ) {
        this.playground = playground;
        this.playgroundStrategy = playgroundStrategy;
    }

    public void play() {
        while (!playground.isAllHaresSaved()) {
            PathNode destination = getNextDestination();
            playground.setDestination(destination.getRow(), destination.getColumn());
            Queue<PathNode> queue = playgroundStrategy.findPath(playground);
            int size = queue.size();
            while (!queue.isEmpty()) {
                PathNode node = queue.poll();
                print(node.getRow() + " " + node.getColumn());
                print("\n");
            }
            playground.updateMap(size);
        }
    }

    private void print(String s) {
        System.out.print(s);
    }

    private void println(String s) {
        print(s + "\n");
    }

    public PathNode getNextDestination() {
        if (playground.isFullBoat()) return playground.getFinish();
        return getPositionBestHare();
    }
    int[][] dist;

    private PathNode getPositionBestHare() {
        dist = new int[playground.getHeight()][playground.getWidth()];
        for (int i = 0; i < playground.getHeight(); i++) {
            for (int j = 0; j < playground.getWidth(); j++) {
                dist[i][j] = INFINITY_DISTANCE;
            }
        }
        PathNode boatPos = playground.getBoatPosition();
        dist[boatPos.getRow()][boatPos.getColumn()] = 0;
        Stack<PathNode> st = new Stack<PathNode>();
        st.push(boatPos);
        while (!st.isEmpty()) {
            PathNode curPos = st.pop();
            for (int i = 0; i < 4; i++) {
                if (playground.canToGo(curPos.getRow() + D_ROW[i], curPos.getColumn() + D_COLUMN[i],
                        getDist(curPos.getRow(), curPos.getColumn()), getDist(curPos.getRow() + D_ROW[i], curPos.getColumn() + D_COLUMN[i])))
                {
                    dist[curPos.getRow() + D_ROW[i]][curPos.getColumn() + D_COLUMN[i]] = 1 + dist[curPos.getRow()][curPos.getColumn()];
                    st.push(new PathNode(curPos.getRow() + D_ROW[i], curPos.getColumn() + D_COLUMN[i]));
                }
            }
        }
        PathNode positionBestHare = null;
        for (int i = 0; i < playground.getHeight(); i++) {
            for (int j = 0; j < playground.getWidth(); j++) {
                if (playground.getItem(i, j) == PlaygroundItemType.HARE) {
                    if (playground.getCurMap(i, j) < dist[i][j]) {
                        if (positionBestHare == null ||
                                dist[positionBestHare.getRow()][positionBestHare.getColumn()] < dist[i][j]) {
                            positionBestHare = new PathNode(i, j);
                        }
                    }
                }
            }
        }
        return positionBestHare;
    }

    private int getDist(int row, int column) {
        if (row < 0 || column < 0) return INFINITY_DISTANCE;
        if (row >= dist.length || column >= dist[0].length) return INFINITY_DISTANCE;
        return dist[row][column];
    }
}
