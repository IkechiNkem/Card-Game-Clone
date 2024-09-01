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

        Card card = new Card("", 1);

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
        Card card = new Card("", 1);
        BufferedImage check = card.loadCardImage("/sprites/cardrip1.png");
        assertNotNull(check);
        assertTrue(imagesAreEqual(cardImg1, check));
    }

    @DisplayName("WHEN a card contains a point, on its border or within it, THEN it should return true, ELSE it " +
            "should be false.")
    @Test
    void containsPoint() {
        Card card = new Card(10, 10);
        Point borderingCorner = new Point(10, 10);
        Point borderingEdgeAlongX = new Point(11, 10);
        Point borderingEdgeAlongY = new Point(10, 11);
        Point inside = new Point(15, 15);
        Point outside = new Point(1, 1);

        assertTrue(card.containsPoint(borderingCorner));
        assertTrue(card.containsPoint(borderingEdgeAlongX));
        assertTrue(card.containsPoint(borderingEdgeAlongY));
        assertTrue(card.containsPoint(inside));
        assertFalse(card.containsPoint(outside));
    }

    @DisplayName("WHEN a card's position is changed, THEN its x and y should change accordingly.")
    @Test
    void changePos() {
        Card card = new Card(10, 10);
        Point targetPos = new Point(50, 40);
        card.changePos(targetPos);
        assertEquals(targetPos.x, card.x);
        assertEquals(targetPos.y, card.y);
    }

    @DisplayName("WHEN a card's image is set, THEN its image should correspond its color.")
    @Test
    void setImage() throws IOException {
        Card red = new Card("Red", 1);
        Card blue = new Card("Blue", 1);
        Card yellow = new Card("Yellow", 1);
        Card green = new Card("Green", 1);

        BufferedImage redImg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                "/cards/red.png")));
        BufferedImage blueImg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                "/cards/blue.png")));
        BufferedImage yellowImg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                "/cards/yellow.png")));
        BufferedImage greenImg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                "/cards/green.png")));

        red.setImage();
        blue.setImage();
        yellow.setImage();
        green.setImage();

        assertTrue(imagesAreEqual(redImg, red.cardImage));
        assertTrue(imagesAreEqual(blueImg, blue.cardImage));
        assertTrue(imagesAreEqual(yellowImg, yellow.cardImage));
        assertTrue(imagesAreEqual(greenImg, green.cardImage));
    }

    @DisplayName("WHEN a card's image is changed, THEN its new image should correspond to the new requested image.")
    @Test
    void changeImage() throws IOException {
        Card card = new Card("Green", 10);
        BufferedImage greenImg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                "/cards/green.png")));
        BufferedImage yellowImg = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                "/cards/yellow.png")));

        card.setImage();
        card.changeImage("Yellow");

        assertTrue(imagesAreEqual(yellowImg, card.cardImage));
    }

    @DisplayName("WHEN a card attacks another, the attacked card's value should only be 0 when the cards are of the " +
            "color, ELSE the card's value should change within the scope of the game's rules.")
    @Test
    void attack() {
        // When ace attacks a non-ace card, the non-ace card should become an ace
        Card attacker = new Card("Red", 1);
        Card attacked = new Card("Red", 10);
        attacker.attack(attacked);
        assertEquals(1, attacked.cardValue);

        // When attacker attacks card and attacker has same or higher value, attacked value is now 0
        attacker = new Card("Red", 1);
        attacked = new Card("Red", 1);
        attacker.attack(attacked);
        assertEquals(0, attacked.cardValue);

        attacker = new Card("Red", 10);
        attacked = new Card("Red", 1);
        attacker.attack(attacked);
        assertEquals(0, attacked.cardValue);

        attacker = new Card("Red", 10);
        attacked = new Card("Red", 5);
        attacker.attack(attacked);
        assertEquals(0, attacked.cardValue);

        // When attacker attacks another card and attacker has lower value, attacked value is reduced but not 0
        attacker = new Card("Red", 2);
        attacked = new Card("Red", 10);
        attacker.attack(attacked);
        assertEquals(8, attacked.cardValue);
    }

    @DisplayName("WHEN a card is hovering up, THEN it should rise 10 pixels.")
    @Test
    void hoverUpAnim() {
        Card card = new Card(0, 10);
        double prog = card.hoverUpAnim(10);
        assertEquals(0, card.y);
        assertEquals(10.2, prog);
    }

    @DisplayName("WHEN a card is hovering down, THEN it should fall 10 pixels.")
    @Test
    void hoverDownAnim() {
        Card card = new Card(0, 0);
        double prog = card.hoverDownAnim(10);
        assertEquals(10, card.y);
        assertEquals(10.2, prog);
    }

    @DisplayName("Testing implementation of the easeOutBack function from https://easings.net/#easeOutBack")
    @Test
    void easeOutBack() {
        Card card = new Card(0, 0);
        double expectedValue = 2108.2798; //Used Desmos with 10 as the input
        assertEquals(expectedValue, card.easeOutBack(10), 0.01);
    }
}