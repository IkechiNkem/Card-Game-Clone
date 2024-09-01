package Card;

import Main.GamePanel;
import PlayField.PlayField;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    private final GamePanel stableEnvironment = new GamePanel("");
    private final PlayField testPlayField = stableEnvironment.getPlayField();

    // Compares image pixels to check if images are "visually" equal
    boolean imagesAreEqual(BufferedImage image1, BufferedImage image2) {
        if (image1.getWidth() != image2.getWidth() || image1.getHeight() != image2.getHeight()) {
            return false;
        }
        for (int x = 1; x < image2.getWidth(); x++) {
            for (int y = 1; y < image2.getHeight(); y++) {
                if (image1.getRGB(x, y) != image2.getRGB(x, y)) {
                    return false;
                }
            }
        }
        return true;
    }

    @DisplayName("WHEN a Card loads the selected animation, THEN the output array should contain the relevant images.")
    @Test
    void loadAnim() throws IOException {
        BufferedImage cardImg1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                "/cards/sprites/cardrip1.png")));
        BufferedImage cardImg2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                "/cards/sprites/cardrip2.png")));
        BufferedImage cardImg3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                "/cards/sprites/cardrip3.png")));

        Card card = new Card("",1);

        BufferedImage[] check = card.loadAnim("cardrip", 3, 1);
        assertEquals(check.length, 3);
        assertTrue(imagesAreEqual(cardImg1, check[0]));
        assertTrue(imagesAreEqual(cardImg2, check[1]));
        assertTrue(imagesAreEqual(cardImg3, check[2]));
    }

    @DisplayName("WHEN a card loads an image, THEN the loaded image should be the same as the image requested.")
    @Test
    void loadCardImage() throws IOException {
        BufferedImage cardImg1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                "/cards/sprites/cardrip1.png")));
        Card card = new Card("",1);
        BufferedImage check = card.loadCardImage("/sprites/cardrip1.png");
        assertNotNull(check);
        assertTrue(imagesAreEqual(cardImg1, check));
    }

    @DisplayName("WHEN a card contains a point, on its boarder or within it, it should return true, else it should be " +
            "false.")
    @Test
    void containsPoint() {
        Card card = new Card(10,10);
        Point borderingCorner = new Point(10,10);
        Point borderingEdgeAlongX = new Point (11, 10);
        Point borderingEdgeAlongY = new Point (10, 11);
        Point inside = new Point(15,15);
        Point outside = new Point(1,1);

        assertTrue(card.containsPoint(borderingCorner));
        assertTrue(card.containsPoint(borderingEdgeAlongX));
        assertTrue(card.containsPoint(borderingEdgeAlongY));
        assertTrue(card.containsPoint(inside));
        assertFalse(card.containsPoint(outside));
    }

    @DisplayName("WHEN a card's position is changed, its  x and y should change accordingly.")
    @Test
    void changePos() {
        Card card = new Card(10,10);
        Point targetPos = new Point(50,40);
        card.changePos(targetPos);
        assertEquals(targetPos.x ,card.x);
        assertEquals(targetPos.y ,card.y);
    }

    @Test
    void setFace() {
    }

    @Test
    void setImage() {
    }

    @Test
    void changeImage() {
    }

    @Test
    void attack() {
    }

    @Test
    void hoverUpAnim() {
    }

    @Test
    void hoverDownAnim() {
    }

    @Test
    void easeOutBack() {
    }

    @Test
    void update() {
    }

    @Test
    void draw() {
    }
}