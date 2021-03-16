package cs1302.gallery;

import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.control.TextField;
import javafx.scene.control.Separator;
import javafx.scene.layout.Priority;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.scene.layout.TilePane;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.concurrent.Task;

/**
 * Custom component for the toolbar in GalleryApp.
 */
public class ToolBarLoader extends HBox {

    // button for random feature
    private Button random;
    // the text next to the searchbar
    private Text searchLabel;
    // the separator next to the searchbar
    private Separator separator;
    // button for update main contain
    private Button update;
    // the search bar
    private TextField urlField;
    // the image loader of the app
    private ImageLoader imagePane;
    // the keyframe for two seconds
    private KeyFrame keyFrame;
    // the timeline that process the random feature
    private Timeline timeline;
    // the progressbar of the app
    private LoadingLoader pBar;

    /**
     * Constructor that creates the toolbar of the app and add
     * {@code button}, {@code textfield}, and {@code label}.
     */
    public ToolBarLoader() {
        // set up the random image feature for the pause/play button
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        random = new Button("Pause");
        random.setOnAction(this::toggleRandom);

        // set up the search bar
        searchLabel = new Text("Search Query:");
        VBox.setVgrow(searchLabel, Priority.ALWAYS);
        urlField = new TextField("rock");
        separator = new Separator(Orientation.VERTICAL);

        // set the update button to take the search query and change the main content
        update = new Button("Update Images");
        update.setOnAction(this::loadImage);
        this.setSpacing(5);
        this.setPadding(new Insets(5, 5, 5, 10));
        this.setAlignment(Pos.CENTER_LEFT);
        this.getChildren().addAll(random, separator, searchLabel, urlField, update);
    }

    /**
     * Set up the random feature of the app and play it.
     *
     * @param content the main content of the app.
     */
    public void setMainContent(ImageLoader content) {
        imagePane = content;
        EventHandler<ActionEvent> handler = e -> imagePane.random();
        keyFrame = new KeyFrame(Duration.seconds(2), handler);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    } // setMainContent

    /**
     * Method that takes the text in {@code TextField} and
     * load it into the main contain.
     *
     * @param e source event.
     */
    private void loadImage(ActionEvent e) {
        Runnable newThread = () -> {
            imagePane.loadDatabase(urlField.getText());
            imagePane.setUpViewer(2);
        };
        Thread t = new Thread(newThread);
        t.setDaemon(true);
        t.start();
    } // loadImage

    /**
     * Method that toggle the play and pause function.
     *
     * @param e source event.
     */
    private void toggleRandom(ActionEvent e) {
        if (random.getText().equals("Pause")) {
            random.setText("Play");
            timeline.stop();
        } else {
            random.setText("Pause");
            timeline.play();
        } // if else
    } // toggleRandom

} // ToolBarLoader
