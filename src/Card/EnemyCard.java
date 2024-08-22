package Card;

import java.awt.*;
import java.awt.image.BufferedImage;

public class EnemyCard extends Card {
    private final BufferedImage originalCardImage;
    private float transparency = 1.0f;
    public Card underCard = null;

    public EnemyCard(String cardColor, int cardValue, int x ,int y) {
        super(cardColor, cardValue,x,y);
        cardSprites = loadAnim("cardrip", 15, 1);
        if (cardColor.equals("Blue")) {
            cardImage = loadCardImage("blue.png");
            this.cardColor = "Blue";
        }
        if (cardColor.equals("Red")) {
            cardImage = loadCardImage("red.png");
            this.cardColor = "Red";
        }
        if (cardColor.equals("Yellow")) {
            cardImage = loadCardImage("yellow.png");
            this.cardColor = "Yellow";
        }
        if (cardColor.equals("Green")) {
            cardImage = loadCardImage("green.png");
            this.cardColor = "Green";
        }
        originalCardImage = cardImage;
        setFace();
    }

    @Override
    public void update() {
        super.update();
        if (cardValue <= 0) {
            dying = true;
        }
        if (spriteNumber == 14) {
            dying = false;
            dead = true;
        }
        if (dying) {
            spriteCounter++;
            if (spriteCounter > 3) {
                spriteNumber++;
                transparency -= ((float) 1 /14);
                spriteCounter = 0;
            }
        }
    }

    public void draw(Graphics2D g2d) {
        if (dying) {
            this.cardImage = cardSprites[spriteNumber];
        }
        Composite oldComposite = g2d.getComposite();
        Composite newComposite = java.awt.AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transparency);
        g2d.setComposite(newComposite);
        super.draw(g2d);
        g2d.setComposite(oldComposite);
    }
}
