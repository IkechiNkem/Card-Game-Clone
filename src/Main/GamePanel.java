package Main;


import PlayField.PlayField;

import javax.swing.*;
import java.awt.*;


/**
 * A class that represents the game screen. This class contains the dimensions of the game screen.
 */
public class GamePanel extends JPanel implements Runnable {
    // SCREEN SETTINGS
    public static final int WIDTH = 1366;
    public static final int HEIGHT = 768;
    public static int CARD_WIDTH = 160;
    public static int CARD_HEIGHT = 204;
    public static final int CARD_GAP = 50;
    public static final int EDGE_MARGIN = 25;


    final int TARGET_FPS = 240;


    public Cursors cursors = new Cursors(this); // Loads in all the cursors for efficient swapping
    public Thread gameThread;
    public Mouse mouse = new Mouse();
    public PlayField playField;

    public GamePanel() {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.playField = new PlayField(mouse, "wallpaper.jpg", 15, 36, cursors);
    }

    /**
     * <b> For use in testing. </b> <p> Creates a basic game setup with controlled conditions.
     */
    public GamePanel(String ignored) {
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.playField = new PlayField(cursors);
    }

    /**
     * Starts a new game thread that runs the game loop
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * A getter method to return this GamePanel's current PlayField.
     * @return The current PlayField that is in use in this GamePanel
     */
    public PlayField getPlayField() {
        return this.playField;
    }

    @Override
    public void run() {
        double drawGap = 1000000000.0 / TARGET_FPS;
        double delta = 0;
        long prevTime = System.nanoTime();
        long currTime;

        while (gameThread != null) {
            currTime = System.nanoTime();

            delta += (currTime - prevTime) / drawGap;
            prevTime = currTime;

            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    /**
     * Updates the playfield.
     */
    public void update(){
        playField.update();
    }

    /**
     * Paints the sprites to the panel
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        playField.draw(g2d);
        g2d.dispose();
    }
}
