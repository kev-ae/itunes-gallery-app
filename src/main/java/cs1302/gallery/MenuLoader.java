package cs1302.gallery;

import javafx.scene.layout.VBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.application.Platform;

/**
 * Custom component of the menu bar. Once click, there is am option to exit
 * program.
 */
public class MenuLoader extends MenuBar {
    /** The menu of the bar. */
    private Menu file;
    private MenuItem exit;

    /**
     * Constuctor that creates the {@code MenuBar} and adds the
     * Action and exit option.
     */
    public MenuLoader() {
        file = new Menu("File");
        exit = new MenuItem("Exit");
        exit.setOnAction(t -> Platform.exit());
        file.getItems().add(exit);
        this.getMenus().add(file);
    } // constructor

} // MenuLoader
