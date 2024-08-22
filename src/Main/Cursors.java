package Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;


/**
 * A class containing all the possible mouse cursors preloaded and the capability to switch between them
 */
public class Cursors {

    /**
     * The panel whose cursor will be changed
     */
    JPanel panel;

    /**
     * The array containing all the preloaded cursors
     */
    private Cursor[] possibleCursors;

    /**
     * The current cursor image index
     */
    private int cursorNumber;

    /**
     * Creates a new cursor object where all the mouse cursor images are then loaded and converted into Cursor objects
     * <p>
     * Relies on the images in cursors being in numerical order, starting from 1, with no gaps nor leading zeros.
     */
    public Cursors(JPanel panel) {
        this.panel = panel;
        loadCursors();
    }

    /**
     * Loads the cursor from the mouse folder in resources
     */
    private void loadCursors() {
        File[] file; // Will be used to hold the cursor images

        try {
            file = (new File(Objects.requireNonNull(getClass().getResource("/mouse")).toURI()))
                    .listFiles(); // Is necessary as images are in resource folder
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        assert file != null;
        int cursorCount = file.length;

        possibleCursors = new Cursor[cursorCount];

        for (int i = 1; i <= cursorCount; i++) {
            try {
                Image temp = ImageIO.read(
                        Objects.requireNonNull(getClass().getResourceAsStream("/mouse/" + i + ".png")));

                Cursor c = Toolkit.getDefaultToolkit().createCustomCursor(
                        temp, new Point(0, 0), ("cursor" + i));

                possibleCursors[i - 1] = (c);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Changes the cursor to one of the cursors contained in the possibleCursors array
     * @param cursorNum - integer corresponding to the cursor's number
     */
    public void changeCursor(int cursorNum){
        panel.setCursor(possibleCursors[cursorNum - 1]);
        cursorNumber = cursorNum;
    }

    /**
     * A getter method for the current cursor image of this Cursors object.
     * @return The current cursor number.
     */
    public int getCursorNumber(){
        return cursorNumber;
    }
}