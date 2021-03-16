package cs1302.gallery;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.Priority;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.*;
import javafx.scene.control.*;

/**
 * Represents an iTunes GalleryApp.
 */
public class GalleryApp extends Application {

    private VBox pane;
    private ImageLoader imagePane;
    private MenuLoader menu;
    private ToolBarLoader toolBar;
    private LoadingLoader loading;

    /**
     * Beginning of the gallery app.
     *
     * {@inheritdoc}
     */
    @Override
    public void start(Stage stage) {
        // set the pane
        pane = new VBox();

        // set the different parts of the app
        loading = new LoadingLoader();
        imagePane = new ImageLoader(loading);
        menu = new MenuLoader();
        toolBar = new ToolBarLoader();
        toolBar.setMainContent(imagePane);

        // have the main content stretch to borders and add the parts to the pane
        pane.setVgrow(imagePane, Priority.ALWAYS);
        pane.getChildren().addAll(menu, toolBar, imagePane, loading);

        // set the scene and stage
        Scene scene = new Scene(pane);
        stage.setMaxWidth(640);
        stage.setMaxHeight(480);
        stage.setResizable(false);
        stage.setTitle("GalleryApp!");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    } // start

} // GalleryApp
