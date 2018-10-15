package com.simbirsoft.itplanet.view.fx;

import com.simbirsoft.itplanet.entity.PathNode;
import com.simbirsoft.itplanet.entity.PlaygroundState;
import com.simbirsoft.itplanet.entity.type.PlaygroundItemType;
import com.simbirsoft.itplanet.service.InputReader;
import sun.awt.image.PNGImageDecoder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Stack;

public class PlaygroundLoadState implements PlaygroundState {


    private int width;
    private int height;
    private PathNode boatPosition;
    private int currentCountHare;
    private int allCountHares;
    private int[][] landScape;
    private int stepCount;
    private int boatCapacity;
    private int currentCountSavedHares;
    private char[][] curMap;
    private char[][] type;
    private int speedWater;
    private PathNode finish;

    public PlaygroundLoadState(File input) {
        InputReader in = null;
        try {
            in = new InputReader(new InputStreamReader(new FileInputStream(input)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.boatCapacity = in.nextInt();
        this.speedWater = in.nextInt();
        this.height = in.nextInt();
        this.width = in.nextInt();
        landScape = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                landScape[i][j] = in.nextInt();
            }
        }
        type = new char[height][];
        curMap = new char[height][width];
        for (int i = 0; i < height; i++) {
            type[i] = in.next().toCharArray();
            for (int j = 0; j < width; j++) {
                curMap[i][j] = type[i][j];
                if (type[i][j] == 'U') {
                    allCountHares++;
                }
                if (type[i][j] == 'F') {
                    finish = new PathNode(i, j);
                }
                if (type[i][j] == 'S') {
                    curMap[i][j] = 'B';
                    boatPosition = new PathNode(i, j);
                }
            }
        }
        currentCountSavedHares = 0;
        currentCountHare = 0;
        stepCount = 0;
    }

    @Override
    public PathNode getBoatPosition() {
        PathNode position = null;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (curMap[i][j] == 'B') {
                    position = new PathNode(i, j);
                    break;
                }
            }
        }
        return position;
    }

    @Override
    public boolean isAllHaresSaved() {
        return currentCountSavedHares == allCountHares;
    }

    @Override
    public boolean isFullBoat() {
        return currentCountHare == boatCapacity;
    }

    @Override
    public boolean isBoatOnDestination() {
        return getItem(getBoatPosition().getRow() + 1, getBoatPosition().getColumn()) == PlaygroundItemType.DESTINATION ||
                getItem(getBoatPosition().getRow() - 1, getBoatPosition().getColumn()) == PlaygroundItemType.DESTINATION ||
                getItem(getBoatPosition().getRow(), getBoatPosition().getColumn() + 1) == PlaygroundItemType.DESTINATION ||
                getItem(getBoatPosition().getRow(), getBoatPosition().getColumn() - 1) == PlaygroundItemType.DESTINATION;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getLandscape(int row, int col) {
        if (row < 0 || col < 0) return -1;
        if (row >= height || col >= width) return -1;
        return landScape[row][col];
    }

    @Override
    public int getCurMap(int row, int col) {
        return curMap[row][col];
    }

    @Override
    public PlaygroundItemType getItem(int row, int col) {
        if (row < 0 || col < 0) return PlaygroundItemType.EMPTY;
        if (row >= height || col >= width) return PlaygroundItemType.EMPTY;
        return PlaygroundItemType.valueOf(curMap[row][col]);
    }

    @Override
    public int getCurrentWaterHeight() {
        return stepCount * speedWater;
    }

    @Override
    public PathNode getDestination() {
        PathNode position = null;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (curMap[i][j] == 'F') {
                    position = new PathNode(i, j);
                    break;
                }
            }
        }
        return position;
    }

    @Override
    public void setDestination(int row, int column) {
        curMap[row][column] = 'D';
    }

    @Override
    public int getHaresInBoat() {
        return currentCountHare;
    }

    @Override
    public int getSavedHares() {
        return currentCountSavedHares;
    }

    @Override
    public int getNumberOfMoves() {
        return stepCount;
    }

    public boolean canToGo(int row, int column, int delta, int dist) {
        if (row < 0 || column < 0) return false;
        if (row >= height || column >= width) return false;
        return getCurMap(row, column) <= getCurrentWaterHeight() + delta &&
                delta + 1 < dist;
    }

    public int getSpeedWater() {
        return speedWater;
    }

    public void updateMap() {
        updateMap(1);
    }

    public PathNode getFinish() {
        return finish;
    }

    public void updateMap(int time) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (curMap[i][j] == 'U' && getCurrentWaterHeight() + time >= landScape[i][j]) {
                    curMap[i][j] = '-';
                    allCountHares--;
                }
            }
        }
        stepCount += time;
    }
}
