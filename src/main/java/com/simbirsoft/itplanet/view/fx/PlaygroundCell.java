package com.simbirsoft.itplanet.view.fx;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class PlaygroundCell extends StackPane {

    PlaygroundCell(Color color, double x, double y, double width, double height) {
        this("", color, x, y, width, height);
    }

    PlaygroundCell(String name, double x, double y, double width, double height) {
        this(name, Color.TRANSPARENT, x, y, width, height);
    }

    PlaygroundCell(String name, Color color, double x, double y, double width, double height) {

        // create rectangle
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setStroke(Color.BLACK);
        rectangle.setFill(color);

        // create label
        Label label = new Label(name);

        // set position
        setTranslateX(x);
        setTranslateY(y);

        getChildren().addAll(rectangle, label);
    }

}
