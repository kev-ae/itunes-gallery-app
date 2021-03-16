package cs1302.gallery;

import javafx.scene.layout.TilePane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URLEncoder;
import java.net.URL;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Alert;
import java.io.UnsupportedEncodingException;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.MalformedURLException;
import javafx.application.Platform;
import java.util.Random;
import javafx.scene.control.ProgressBar;
import javafx.concurrent.Task;
import javafx.beans.binding.DoubleExpression;
import javafx.beans.property.SimpleDoubleProperty;
import java.util.Arrays;
import com.google.gson.*;

/**
 * Custom component for the gallery app that creates the main content.
 */
public class ImageLoader extends TilePane {

    /** The iTunes covers. */
    private Image[] images;
    /** The viewers for the images. */
    private ImageView[] viewers;
    /** The progressbar of the app. */
    private ProgressBar pBar;
    /** Randomize the numbers. */
    private Random random;
    /** Keep track of the extras and how many are null. */
    private int randTrack;
    /** Array of the images covers.*/
    private JsonElement[] artworkUrl100;
    /** Artwork counter.*/
    private int artCounter;

    /**
     * Constructor that takes the images from iTunes and display them on the
     * galley app.
     */
    public ImageLoader() {
        setUp();
    } // constructor

    /**
     * Second constructor that takes in a LoadingLoader.
     *
     * @param loading the {@code ProgressBar} of the app.
     */
    public ImageLoader(LoadingLoader loading) {
        setLoading(loading);
        setUp();
    } // constructor

    /**
     * Helper method that sets up the default main content.
     */
    public void setUp() {
        randTrack = 0;
        viewers = new ImageView[20];
        artworkUrl100 = new JsonElement[25];
        artCounter = 0;

        // create the viewers and set their sizes
        for (int i = 0; i < viewers.length; i++ ) {
            viewers[i] = new ImageView();
            viewers[i].setFitWidth(128);
            viewers[i].setFitHeight(120);
        } // for

        // load default query and set size
        loadDatabase("rock");
        setUpViewer(1);
        random = new Random();
        this.setPrefColumns(5);
        this.setPrefRows(4);
        this.setMaxWidth(640);
    } // setUp

    /**
     * Get the inputs from the text box and get the images from the iTunes site.
     * Then store the images inside the {@code images} and create the {@code ImageView}
     * Then add all the viewers to this {@code TilePane}.
     *
     * @param textField the text from the search bar.
     */
    public void loadDatabase(String textField) {
        try {
            // get the url and get the results from itunes
            String encoded = URLEncoder.encode(textField, "UTF-8");
            String sUrl = "https://itunes.apple.com/search?term=" + encoded +
                          "&limit=100&media=music";
            URL url = new URL(sUrl);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            JsonElement je = JsonParser.parseReader(reader);
            JsonObject root = je.getAsJsonObject();
            JsonArray results = root.getAsJsonArray("results");

            // checks if there is enough covers, download the images, and bind the progressbar
            if (checkIfEnough(results)) {
                randTrack = 0;
                updateData();
                // download images
                images = Arrays.stream(artworkUrl100)
                    .map(e -> new Image(e.getAsString(), true))
                    .toArray(Image[]::new);
                // bind download to the progessbar
                DoubleExpression totalDownload;
                if (images.length > 0) {
                    totalDownload = new SimpleDoubleProperty(0);
                    for (Image image : images) {
                        totalDownload = totalDownload.add(image.progressProperty());
                    }
                    totalDownload = totalDownload.divide(artworkUrl100.length);
                } else {
                    totalDownload = new SimpleDoubleProperty(1);
                } // if else
                pBar.progressProperty().unbind();
                pBar.progressProperty().bind(totalDownload);
                Thread.sleep(700);
            }
        } catch (UnsupportedEncodingException uee) {
            System.err.println("Error: Unsupported Encoding.");
            uee.printStackTrace();
        } catch (IOException io) {
            System.err.println("Error: IO Exception.");
            io.printStackTrace();
        } catch (InterruptedException ie) {
            System.err.println("Error: interrupted");
            ie.printStackTrace();
        } // try catch
    } // loadDatabase

    /**
     * Method that update the url data to avoid null.
     */
    private void updateData() {
        // count the nulls
        for (int i = 0; i < artworkUrl100.length; i++) {
            if (artworkUrl100 == null) {
                randTrack++;
            }
        } // for
        // deep copy to new array with no null
        JsonElement[] newUrl = new JsonElement[25 - randTrack];
        for (int i = 0; i < newUrl.length; i++) {
            newUrl[i] = artworkUrl100[i];
        } //
        artworkUrl100 = newUrl;
    } // updateData

    /**
     * Checks if there is enough covers. If there is enough, return
     * true else prompt a dialog and return false.
     *
     * @param results the {@code JsonArray} to check.
     * @return if there is enough covers.
     */
    private boolean checkIfEnough(JsonArray results) {
        int counter = 0;
        boolean enough = true;
        artworkUrl100 = new JsonElement[25];
        artCounter = 0;
        // count the results that are not null and is distinct
        for (int i = 0; i < results.size(); i++) {
            JsonObject result = results.get(i).getAsJsonObject();
            JsonElement artwork100 = result.get("artworkUrl100");
            if (artwork100 != null && isDistinct(artwork100)) {
                counter++;
            } // if
        } // for
        // if there is less than 21 covers, display an error and set enough to false
        if (counter < 21) {
            enough = false;
            Platform.runLater(() -> {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Something went wrong.");
                alert.setHeaderText("Search Error");
                alert.setContentText("Can't find 21 or more pictures. Try Again.");
                alert.showAndWait();
            });
        } // if
        return enough;
    } // checkIfEnough

    /**
     * Method that checks if the image url is distinct to the covers in the database.
     *
     * @param url the url to check for distinction.
     * @return true if the url is distinct, false otherwise.
     */
    private boolean isDistinct(JsonElement url) {
        int counter = 0;
        boolean distinct = false;
        // count the number of distinct urls
        for (int i = 0; i < artCounter; i++) {
            if (!url.getAsString().equals(artworkUrl100[i].getAsString())) {
                counter++;
            } // if
        } // for
        // if url is distinct and database is not full, add url to database
        if (counter == artCounter && artworkUrl100[24] == null) {
            artworkUrl100[artCounter] = url;
            artCounter++;
            distinct = true;
        } // if
        return distinct;
    } // isDistinct

    /**
     * Helper method that set up the {@code ImageView} for the app. Displays the covers
     * that was retrieve from iTunes.
     *
     * @param setting either the inital setup or every other setup after.
     */
    public void setUpViewer(int setting) {
        // change the images in the viewers
        for (int i = 0; i < 20; i++) {
            viewers[i].setImage(images[i]);
        } // for
        // 1 is for inital setup only, both add the viewers to the main content
        switch (setting) {
        case 1:
            for (int i = 0; i < viewers.length; i++) {
                this.getChildren().add(viewers[i]);
            } // for
            break;
        case 2:
            Platform.runLater(() -> setViewer());
            break;
        }
    } // setUpViewer

    /**
     * Helper method that set the viewer.
     */
    public void setViewer() {
        for (int i = 0; i < viewers.length; i++) {
            this.getChildren().set(i, viewers[i]);
        } // for
    }

    /**
     * Method that takes a random image from the extra covers
     * in the database and swap with a cover on the main content.
     */
    public void random() {
        int vRand = random.nextInt(20);
        int iRand = random.nextInt(5) + 20 - randTrack;
        Image previous = viewers[vRand].getImage();
        viewers[vRand].setImage(images[iRand]);
        images[vRand] = images[iRand];
        images[iRand] = previous;
        this.getChildren().set(vRand, viewers[vRand]);
    } // random

    /**
     * Method that sets the {@code ProgressBar} of this object.
     *
     * @param loading the {@code ProgressBar} to set.
     */
    public void setLoading(LoadingLoader loading) {
        this.pBar = loading.getPBar();
    } // setLoading

} // ImageLoader
