package Card;

import java.awt.*;

public class CardHolder extends Card {
    public boolean covered = false;
    public Card recentCard = null;

    public CardHolder(String cardType, int x, int y) {
        super(x,y);
        this.cardColor = cardType;
        if (cardColor.equals("Player")) {
            this.cardImage = loadCardImage("attack.png");
        }
        if (cardColor.equals("Enemy")) {
            this.cardImage = loadCardImage("dead.png");
        }
        if (cardColor.equals("Trash")) {
            this.cardImage = loadCardImage("discard.png");
        }
        if (cardColor.equals("Back")) {
            this.cardImage = loadCardImage("back.png");
        }

    }


    @Override
    public void draw(Graphics2D g2d) {
        if (cardColor.equals("Back")) {
            super.draw(g2d);
        } else {
            Composite oldComposite = g2d.getComposite();
            Composite newComposite = oldComposite;
            if (hovered) {
                newComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f);
            } else {
                newComposite = java.awt.AlphaComposite.getInstance(AlphaComposite.SRC_OVER, .75F);
            }
            g2d.setComposite(newComposite);
            super.draw(g2d);
            g2d.setComposite(oldComposite);
        }
    }
}
