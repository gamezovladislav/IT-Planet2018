package com.simbirsoft.itplanet.service;

import com.simbirsoft.itplanet.entity.PathNode;
import com.simbirsoft.itplanet.entity.PlaygroundState;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Stack;

public class PlaygroundStrategyPathFinder implements PlaygroundStrategy {

    private static final int INFINITY_DISTANCE = 1000 * 1000 * 1000;
    private static final int[] D_ROW = new int[]{0, 0, 1, -1};
    private static final int[] D_COLUMN = new int[]{1, -1, 0, 0};


    int[][] dist;
    @Override
    public Queue<PathNode> findPath(PlaygroundState playground) {
        Queue<PathNode> path = new ArrayDeque<PathNode>();
        PathNode destination = playground.getDestination();
        PathNode boat = playground.getBoatPosition();
        dist = new int[playground.getHeight()][playground.getWidth()];
        for (int i = 0; i < dist.length; i++) {
            for (int j = 0; j < dist[0].length; j++) {
                dist[i][j] = INFINITY_DISTANCE;
            }
        }
        dist[boat.getRow()][boat.getColumn()] = 0;
        Stack<PathNode> st = new Stack<PathNode>();
        st.push(boat);
        while (!st.isEmpty()) {
            PathNode curPos = st.pop();
            for (int i = 0; i < 4; i++) {
                if (playground.canToGo(curPos.getRow() + D_ROW[i], curPos.getColumn() + D_COLUMN[i],
                        dist[curPos.getRow()][curPos.getColumn()], getDist(curPos.getRow() + D_ROW[i], curPos.getColumn() + D_COLUMN[i])))
                {
                    dist[curPos.getRow() + D_ROW[i]][curPos.getColumn() + D_COLUMN[i]] = 1 + dist[curPos.getRow()][curPos.getColumn()];
                    st.push(new PathNode(curPos.getRow() + D_ROW[i], curPos.getColumn() + D_COLUMN[i]));
                }
            }
        }
        int curRow = destination.getRow();
        int curColumn = destination.getColumn();
        int time = dist[curRow][curColumn];
        while (dist[curRow][curColumn] != 0) {
            for (int i = 0; i < 4; i++) {
                if (isExist(playground, curRow + D_ROW[i], curColumn + D_COLUMN[i])) {
                    if (1+dist[curRow][curColumn] == dist[curRow + D_ROW[i]][curColumn + D_COLUMN[i]]) {
                        curRow += D_ROW[i];
                        curColumn += D_COLUMN[i];
                        ((ArrayDeque<PathNode>) path).addLast(new PathNode(curRow, curColumn));
                    }
                }
            }
        }
        playground.updateMap(time);
        return path;
    }

    private int getDist(int row, int column) {
        if (row < 0 || column < 0) return INFINITY_DISTANCE;
        if (row >= dist.length || column >= dist[0].length) return INFINITY_DISTANCE;
        return dist[row][column];
    }

    private boolean isExist(PlaygroundState playground, int row, int column) {
        return playground.getLandscape(row, column) >= 0;
    }
}
