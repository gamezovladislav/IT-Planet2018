package com.simbirsoft.itplanet;

import com.simbirsoft.itplanet.entity.PlaygroundState;
import com.simbirsoft.itplanet.service.PlaygroundLoader;
import com.simbirsoft.itplanet.service.PlaygroundStrategy;
import com.simbirsoft.itplanet.service.PlaygroundStrategyPathFinder;
import com.simbirsoft.itplanet.service.PlaygroundStrategyPlayer;
import com.simbirsoft.itplanet.view.fx.PlaygroundFileLoader;
import com.simbirsoft.itplanet.view.fx.PlaygroundLoadState;
import com.simbirsoft.itplanet.view.fx.PlaygroundView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    @Override
    public void start(Stage primaryStage) {
        PlaygroundLoader playgroundLoader = new PlaygroundFileLoader(primaryStage);
        PlaygroundState playground = playgroundLoader.load().get();
        if (playground != null) {
            primaryStage.show();

            PlaygroundStrategy playgroundStrategy = null;
            PlaygroundStrategyPlayer playgroundStrategyPlayer = new PlaygroundStrategyPlayer(playground, playgroundStrategy);

            PlaygroundView playgroundView = new PlaygroundView(primaryStage, playground);

            playgroundStrategyPlayer.play();
        } else {
            logger.log(Level.INFO, "Playground is not loaded");
            Platform.exit();
        }
    }

    public static void main(String[] args) {

//        launch(args);
        if (args.length >= 2) {
            System.out.println(String.valueOf((new File(args[0])).exists()));
            PlaygroundState playground = new PlaygroundLoadState(new File(args[0]));
            PlaygroundStrategy strategy = new PlaygroundStrategyPathFinder();
            PlaygroundStrategyPlayer player = new PlaygroundStrategyPlayer(playground, strategy);
            player.play();
        } else {
            throw new RuntimeException("args.length < 2");
        }
    }
}
