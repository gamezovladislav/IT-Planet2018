package com.simbirsoft.itplanet.view.fx;

import com.simbirsoft.itplanet.entity.PathNode;
import com.simbirsoft.itplanet.entity.PlaygroundState;
import com.simbirsoft.itplanet.entity.type.PlaygroundItemType;
import com.simbirsoft.itplanet.service.PlaygroundLoader;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

public class PlaygroundFileLoader implements PlaygroundLoader {

    private final Stage primaryStage;

    public PlaygroundFileLoader(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @Override
    public Optional<PlaygroundState> load() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(primaryStage);
        Optional<PlaygroundState> result = Optional.empty();
        if( file != null){
            result = Optional.of(new PlaygroundLoadState(file));
        }
        return result;
    }
}
