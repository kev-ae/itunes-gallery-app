package cs1302.gallery;

import javafx.scene.layout.HBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.text.Text;

/**
 * Custom component for the GalleryApp.
 */
public class LoadingLoader extends HBox {

    /** The progress bar for the loading. */
    private ProgressBar pBar;
    /** The coutesy message for iTunes. */
    private Text coutesy;

    /**
     * Constructor that creates the loading area at the bottom of
     * the app.
     */
    public LoadingLoader() {
        pBar = new ProgressBar(0);
        coutesy = new Text("Images provided courtesy of iTunes");
        this.setSpacing(5);
        this.getChildren().addAll(pBar, coutesy);
    } // Constructor

    /**
     * Method that returns the {@code ProgressBar}.
     *
     * @return the {@code ProgressBar}.
     */
    public ProgressBar getPBar() {
        return pBar;
    } // getPBar()

} // LoadingLoader
