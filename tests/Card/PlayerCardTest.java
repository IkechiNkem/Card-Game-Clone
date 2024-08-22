package Card;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerCardTest {

    @DisplayName("WHEN a new PlayerCard is created, THEN it should not be attacking, dead, covered, hovered, " +
            "or hovering, and it's x and y coordinates should both be 0")
    @Test
    void testNew() {
        String cardColor = "Red";
        int cardValue = 4;
        PlayerCard card = new PlayerCard(cardColor, cardValue);

        assertEquals(0, card.x);
        assertEquals(0, card.y);
        assertEquals(cardColor, card.cardColor);
        assertEquals(cardValue, card.cardValue);

        // Test other constructor
        card = new PlayerCard(cardColor, cardValue, 100, 100);
        assertEquals(100, card.x);
        assertEquals(100, card.y);
        assertEquals(cardColor, card.cardColor);
        assertEquals(cardValue, card.cardValue);
    }

    @Test
    void setHoverParameters() {
        String cardColor = "Red";
        int cardValue = 4;
        PlayerCard card = new PlayerCard(cardColor, cardValue, 100, 100);
        card.setHoverParameters(30);
        assertEquals(100, card.INITIAL_Y);
        assertEquals(70, card.target_y);
        card.hovered = true;
        for (int i = 0; i < 100; i++) {
            card.update();
        }
        assertEquals(100, card.INITIAL_Y);
        assertEquals(70, card.y);
    }
}