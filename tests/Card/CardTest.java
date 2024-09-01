package Card;

import Main.GamePanel;
import PlayField.PlayField;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    private final GamePanel stableEnvironment = new GamePanel("");
    private final PlayField testPlayField = stableEnvironment.getPlayField();

    @Test
    void loadAnim() throws IOException {
        BufferedImage cardImg1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                "/cards/sprites/cardrip1.png")));
        BufferedImage cardImg2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                "/cards/sprites/cardrip2.png")));
        BufferedImage cardImg3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                "/cards/sprites/cardrip3.png")));

        Card card = new Card("",1);
        card.loadAnim("cardrip", 3, 1);

        assertEquals(card.cardSprites.length, 3);
        assertEquals(card.cardSprites[0], cardImg1);
        assertEquals(card.cardSprites[1], cardImg2);
        assertEquals(card.cardSprites[2], cardImg3);
    }

    @Test
    void loadCardImage() {
    }

    @Test
    void containsPoint() {
    }

    @Test
    void changePos() {
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