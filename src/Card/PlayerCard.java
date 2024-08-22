package Card;

import java.awt.*;

public class PlayerCard extends Card{
    private int counter = 0;
    public boolean attackingDone = false;
    public int target_y;  // Target position when hovered
    public int INITIAL_Y; // Initial position when not hovered

    public PlayerCard(String cardColor, int cardValue) {
        super(cardColor, cardValue);
        this.INITIAL_Y = y;
        setImage();
        setFace();
    }

    public PlayerCard(String cardColor, int cardValue, int x ,int y) {
        super(cardColor, cardValue,x,y);
        this.INITIAL_Y = y;
        this.target_y = y + 10;
        setImage();
        setFace();
    }

    public void setHoverParameters(int hoverDistance) {
        this.INITIAL_Y = this.y;
        this.target_y = this.y - hoverDistance;
    }


    @Override
    public void draw(Graphics2D g2d) {
        super.draw(g2d);
    }

    @Override
    public void update() {
        counter++;
        if (counter == 3) {
            hoverCheck();
            attackCheck();
            counter = 0;
        }
    }

    private void hoverCheck() {
        if (hovered && y > target_y) {
            hovering = true;
            y--;
        } else if (!hovered && y < INITIAL_Y) {
            hovering = true;
            y++;
        } else {
            hovering = false;
        }
    }

    private void attackCheck() {
        if (attacking && y > target_y) {
            y--;
        } if (y <= INITIAL_Y) {
            attackingDone = true;
            attacking = false;
        }
    }
}
