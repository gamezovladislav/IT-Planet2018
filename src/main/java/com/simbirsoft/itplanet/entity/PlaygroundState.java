package com.simbirsoft.itplanet.entity;

import com.simbirsoft.itplanet.entity.type.PlaygroundItemType;

public interface PlaygroundState {

    PathNode getBoatPosition();

    boolean isAllHaresSaved();

    boolean isFullBoat();

    boolean isBoatOnDestination();

    int getWidth();

    int getHeight();

    int getLandscape(int row, int col);

    int getCurMap(int row, int col);

    PlaygroundItemType getItem(int row, int col);

    int getCurrentWaterHeight();

    PathNode getDestination();

    int getHaresInBoat();

    int getSavedHares();

    int getNumberOfMoves();

    PathNode getFinish();

    boolean canToGo(int i, int i1, int i2, int i3);

    void updateMap(int time);

    void updateMap();

    void setDestination(int row, int column);
}
