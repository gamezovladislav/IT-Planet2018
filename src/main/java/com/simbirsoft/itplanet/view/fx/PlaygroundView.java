package com.simbirsoft.itplanet.view.fx;

import com.simbirsoft.itplanet.entity.PathNode;
import com.simbirsoft.itplanet.entity.PlaygroundState;
import com.simbirsoft.itplanet.entity.type.PlaygroundItemType;
import com.simbirsoft.itplanet.service.PlaygroundListener;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PlaygroundView implements PlaygroundListener {

    private static final int CELL_SIZE = 50;

    private double animationDelay = 2.0;
    private final Scene scene;
    private final int maxWidth = 12;
    private final int maxHeight = 12;
    private final int width;
    private final int height;

    public PlaygroundView(Stage primaryStage, PlaygroundState playground) {
        width = playground.getWidth() <= maxWidth ? playground.getWidth() : maxWidth;
        height = playground.getHeight() <= maxHeight ? playground.getHeight() : maxHeight;

        Group root = new Group();
        scene = new Scene(root, CELL_SIZE * width, CELL_SIZE * height);

        primaryStage.setScene(scene);
    }

    @Override
    public void onChange(PlaygroundState playground) {
        render(playground);
    }

    @Override
    public void onFinish(PlaygroundState playground) {
        showFinalDialog(playground.getSavedHares(), playground.isBoatOnDestination());
    }

    private void render(PlaygroundState playground) {
        int i0 = getFirstVisibleRow(playground);
        int i1 = i0 + height;
        int j0 = getFirstVisibleColumn(playground);
        int j1 = j0 + width;

        Group root = new Group();
        for (int i = i0; i < i1; i++) {
            for (int j = j0; j < j1; j++) {
                root.getChildren().add(
                        createCellForLandscape(playground, i0, j0, i, j)
                );
                root.getChildren().add(
                        createCellForItem(playground, i0, j0, i, j)
                );
            }
        }
        PauseTransition pause = new PauseTransition(
                Duration.seconds(animationDelay += 0.25)
        );
        pause.setOnFinished(event -> scene.setRoot(root));
        pause.play();
    }

    private PlaygroundCell createCellForItem(PlaygroundState playground, int i0, int j0, int i1, int j1) {
        PlaygroundItemType item = playground.getItem(i1, j1);
        String itemName = getItemName(playground, item);
        Color itemColor = getItemColor(item);

        return new PlaygroundCell(
                itemName, itemColor,
                (j1 - j0) * CELL_SIZE, (i1 - i0) * CELL_SIZE,
                CELL_SIZE, CELL_SIZE
        );
    }

    private String getItemName(PlaygroundState playground, PlaygroundItemType item) {
        String itemName;
        if (item == PlaygroundItemType.MAZAY) {
            itemName = String.format("%s\n+%d", item.getName(), playground.getHaresInBoat());
        } else {
            itemName = item.getName();
        }
        return itemName;
    }

    private Color getItemColor(PlaygroundItemType item) {
        switch (item) {
            case HARE:
                return Color.TRANSPARENT;
            case EMPTY:
                return Color.TRANSPARENT;
            case DESTINATION:
                return Color.RED;
            case MAZAY:
                return Color.GRAY;
            default:
                return Color.PINK;
        }
    }

    private PlaygroundCell createCellForLandscape(PlaygroundState playground, int i0, int j0, int i1, int j1) {
        return new PlaygroundCell(
                getColor(playground, i1, j1),
                (j1 - j0) * CELL_SIZE,
                (i1 - i0) * CELL_SIZE,
                CELL_SIZE,
                CELL_SIZE);
    }

    private void showFinalDialog(int savedHares, boolean boatOnDestination) {
        PauseTransition pause = new PauseTransition(
                Duration.seconds(animationDelay += 0.25)
        );
        pause.setOnFinished(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            if (boatOnDestination) {
                alert.setTitle("Поздравляем!");
                alert.setHeaderText("Мазай добрался");
            } else {
                alert.setTitle("Игра закончена");
                alert.setHeaderText("Мазай не добрался");
            }
            alert.setContentText("Он спас " + savedHares + " зайцев");

            alert.setOnHidden(evt -> Platform.exit());
            alert.show();
        });
        pause.play();
    }

    private Color getColor(PlaygroundState playground, int i, int j) {
        return playground.getLandscape(i, j) <= playground.getCurrentWaterHeight() ? Color.AQUA : Color.GREEN;
    }

    private int getFirstVisibleRow(PlaygroundState playground) {
        PathNode boat = playground.getBoatPosition();
        int row = boat.getRow() - height;
        if (playground.getHeight() > maxHeight) {
            row += 3;
        }
        if (row < 0) {
            row = 0;
        }
        return row;
    }

    private int getFirstVisibleColumn(PlaygroundState playground) {
        PathNode boat = playground.getBoatPosition();
        int column = boat.getColumn() - width;
        if (playground.getWidth() > maxWidth) {
            column += 3;
        }
        if (column < 0) {
            column = 0;
        }
        return column;
    }
}
