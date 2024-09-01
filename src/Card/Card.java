package Card;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Card {
    public String cardColor;
    public int cardValue;
    protected BufferedImage[] cardSprites;
    protected BufferedImage cardImage;
    protected BufferedImage cardFace;
    public int x, y;

    protected double animProgress = 0;
    public boolean hovered;
    public boolean moveable;
    public boolean dead;
    protected boolean attacking = false;
    protected boolean hovering = false;
    public boolean dying = false;

    public int spriteCounter = 0;
    public int spriteNumber = 1;

    protected Card (String cardColor, int cardValue, int x, int y) {
        assert (cardValue > 0);

        this.cardColor = cardColor;
        this.cardValue = cardValue;
        this.x = x;
        this.y = y;
        hovered = false;
        moveable = true;
        dead = false;
    }

    protected Card (String cardColor, int cardValue) {
        assert (cardValue > 0);

        this.cardColor = cardColor;
        this.cardValue = cardValue;
        hovered = false;
        moveable = true;
        dead = false;
    }

    protected Card (int x, int y) {
        this.x = x;
        this.y = y;
        hovered = false;
        moveable = true;
        dead = false;
    }

    /**
     * A helper method that loads sprite animations from the resources folder (specifically from res/cards/sprites).
     * @param spriteName - the prefix of the sprite files to be extracted
     * @param numImg - the number of sprites in the animation
     * @param startingNum - the starting index of the sprite animation.
     * @return - an array of BufferedImages that has {@code numImg} sprites
     */
    protected BufferedImage[] loadAnim(String spriteName, int numImg, int startingNum) {
        BufferedImage[] images = new BufferedImage[numImg];

        int imgNum = startingNum;
        int count = 0;

        while (imgNum < startingNum + numImg && count < numImg){
            try {
                images[count] = ImageIO.read(
                        Objects.requireNonNull(getClass().getResourceAsStream(
                                "/cards/sprites/"+ spriteName + imgNum + ".png")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            imgNum++;
            count++;
        }

        return images;
    }

    /**
     * A helper method that loads an image from the resources folder.
     * @param imgName - the name of the card image to be extracted. (Include the file extension).
     * @return - the requested image
     */
    protected BufferedImage loadCardImage(String imgName) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/cards/" + imgName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public Boolean containsPoint(Point p){
        return (p.x >= this.x) && (p.y >= this.y) &&
                (p.x <= this.x + GamePanel.CARD_WIDTH) &&
                (p.y <= this.y + GamePanel.CARD_HEIGHT);
    }

    public void changePos(Point p){
        this.x = p.x;
        this.y = p.y;
    }

    public void setFace() {
        this.cardFace = loadCardImage("faces/" + cardValue + "_" + cardColor + ".png");
    }

    protected void setImage () {
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
    }

    public void changeImage(String cardColor) {
        this.cardColor = cardColor;
        setImage();
    }

    public void attack (Card target){
        if (this.cardValue == 1 && target.cardValue != 1){
            target.cardValue = 1;
            target.setFace();
            return;
        }
        else {
            target.cardValue -= this.cardValue;
        }
        if (target.cardValue < 1) {
            target.cardValue = 0;
        } else {
            target.setFace();
        }
    }

    private void attackAnim() {

    }

    protected double hoverUpAnim(double progress) {
        this.y -= 10;
        return (progress + .2);
    }

    protected double hoverDownAnim(double progress) {
        this.y += 10;
        return (progress + .2);
    }

    public double easeOutBack(double progress) {
        double c1 = 1.70158;
        double c3 = c1 + 1;
        return (1.0 + c3 * Math.pow(progress - 1.0, 3) + c1 * Math.pow(progress - 1.0, 2));
    }

    public void update(){

    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(cardImage, x, y, GamePanel.CARD_WIDTH, GamePanel.CARD_HEIGHT, null);
        if (this.cardFace != null) {
            g2d.drawImage(cardFace,
                    (x + 10),
                    (y + 15),
                    (int) (GamePanel.CARD_WIDTH * .88),
                    (int) (GamePanel.CARD_HEIGHT * .88),
                    null);
        }
    }
}
