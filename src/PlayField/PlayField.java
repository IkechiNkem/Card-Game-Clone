package PlayField;

import Card.Card;
import Card.CardHolder;
import Card.EnemyCard;
import Card.PlayerCard;
import Main.Cursors;
import Main.GamePanel;
import Main.Mouse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import static Main.GamePanel.*;

// TODO Animate card place, Animate card attack, add settings button, add difficulties
public class PlayField {
    BufferedImage[] backgrounds;
    ArrayList<PlayerCard> playerCards = new ArrayList<>();
    ArrayList<EnemyCard> enemyCards = new ArrayList<>();
    Stack<PlayerCard> deck = new Stack<>();
    public CardHolder[] placeHolders = new CardHolder[11];
    Random rand = new Random();
    String[] colors = new String[]{"Red", "Blue", "Green", "Yellow"};

    PlayerCard activeCard = null;
    CardHolder activeCardOriginalPlaceholder = null;
    PlayerCard hoveredCard = null;

    public boolean gameOver = false;

    Mouse mouseListener;
    Cursors cursors;

    /**
     * Creates a new playfield with the specified number of cards in the deck and number of enemies.
     * @param mouseListener the mouseListener to be used to recognize mouse inputs.
     * @param backgroundImgName the name of the image to be used as the background (include file extension).
     * @param numEnemies the number of enemy cards to be used (must be a multiple of 3).
     * @param numDeck the number of cards in the deck (must be a multiple of 3).
     * @param cursors the cursor manager
     */
    public PlayField(Mouse mouseListener, String backgroundImgName, int numEnemies, int numDeck, Cursors cursors) {
        assert (numEnemies % 3 == 0);
        assert (numDeck % 3 == 0);

        this.mouseListener = mouseListener;
        this.cursors = cursors;
        cursors.changeCursor(1); // Sets cursor to its default state


        backgrounds = new BufferedImage[3];
        backgrounds[0] = loadBackground(backgroundImgName);
        defaultSetup();
        cardBuilders(1, numEnemies, numDeck);
        cardBuilders(2, numEnemies, numDeck);

    }

    /**
     * Creates a new PlayField with the specified number of cards in the deck and number of enemies.
     * @param cursors the cursor manager
     */
    public PlayField(Cursors cursors) {
        this.mouseListener = new Mouse();
        this.cursors = cursors;
        cursors.changeCursor(1); // Sets cursor to its default state

        backgrounds = new BufferedImage[3];
        backgrounds[0] = loadBackground("wallpaper.jpg");
        testSetup();
        cardBuilders(1, 6, 6);
        cardBuilders(2, 6, 6);
    }

    private void cardBuilders(int which, int numEnemies, int numDeck) {
        if (which == 1) {
            while (deck.size() < numDeck) {
                deck.push(new PlayerCard(colors[rand.nextInt(0, 3)], rand.nextInt(1, 11)));
            }

            //Adding player cards from deck to hand
            CardHolder hand1 = placeHolders[0];
            CardHolder hand2 = placeHolders[1];
            CardHolder hand3 = placeHolders[2];

            hand1.covered = true;
            hand2.covered = true;
            hand3.covered = true;

            hand1.recentCard = deck.pop();
            hand2.recentCard = deck.pop();
            hand3.recentCard = deck.pop();

            hand1.recentCard.changePos(new Point(hand1.x, hand1.y));
            ((PlayerCard) hand1.recentCard).setHoverParameters(20);

            hand2.recentCard.changePos(new Point(hand2.x, hand2.y));
            ((PlayerCard) hand2.recentCard).setHoverParameters(20);

            hand3.recentCard.changePos(new Point(hand3.x, hand3.y));
            ((PlayerCard) hand3.recentCard).setHoverParameters(20);

        } else if (which == 2) {
            placeEnemyCard(0, numEnemies);
            placeEnemyCard(1, numEnemies);
            placeEnemyCard(2, numEnemies);
        }
    }

    private void placeEnemyCard(int place, int numCards) {
        EnemyCard previousCard = null;
        for (int i = place; i < 30; i+=3) {
            CardHolder placeHolder = placeHolders[i % 3];

            EnemyCard card = new EnemyCard(
                    colors[rand.nextInt(0,3)],
                    rand.nextInt(1, 11),
                    placeHolder.x,
                    placeHolder.y);
            card.underCard = previousCard;
            enemyCards.add(card);
            if (i >= 27){
                placeHolder.recentCard = card;
                placeHolder.covered = true;
            }
            previousCard = card;
        }
    }

    private void defaultSetup() {
        // Enemy card slots
        placeHolders[0] = new CardHolder("Enemy", (WIDTH / 2) - (CARD_WIDTH / 2), 50);
        placeHolders[1] = new CardHolder("Enemy", placeHolders[0].x - CARD_WIDTH - CARD_GAP, 50);
        placeHolders[2] = new CardHolder("Enemy", placeHolders[0].x + CARD_WIDTH + CARD_GAP, 50);

        // Attack card slots
        placeHolders[3] = new CardHolder(
                "Player", placeHolders[0].x, placeHolders[0].y + CARD_HEIGHT + 40);
        placeHolders[4] = new CardHolder(
                "Player", placeHolders[0].x - CARD_WIDTH - CARD_GAP, placeHolders[3].y);
        placeHolders[5] = new CardHolder(
                "Player", placeHolders[0].x + CARD_WIDTH + CARD_GAP, placeHolders[3].y);

        // Temp Pile Slots
        placeHolders[6] = new CardHolder(
                "Trash", WIDTH - CARD_WIDTH - EDGE_MARGIN, placeHolders[4].y + (CARD_HEIGHT / 2) + 100);

        // Player Hand Slots
        placeHolders[7] = new CardHolder(
                "Trash", (WIDTH / 2) - (CARD_WIDTH / 2), HEIGHT - CARD_HEIGHT - EDGE_MARGIN);
        placeHolders[8] = new CardHolder(
                "Trash", placeHolders[7].x - CARD_WIDTH - 10, placeHolders[7].y);
        placeHolders[9] = new CardHolder(
                "Trash", placeHolders[7].x + CARD_WIDTH + 10, placeHolders[7].y);

        // Deck Placeholder
        placeHolders[10] = new CardHolder(
                "Back", 25, placeHolders[6].y
        );
    }

    public void testSetup() {
        playerCards.add(new PlayerCard(
                "Red", 2, (WIDTH / 2) - (CARD_WIDTH / 2), HEIGHT - CARD_HEIGHT - EDGE_MARGIN));
        playerCards.add(new PlayerCard(
                "Blue", 1, playerCards.getFirst().x - CARD_WIDTH - 10, playerCards.getFirst().y));
        playerCards.add(new PlayerCard(
                "Yellow", 9, playerCards.getFirst().x + CARD_WIDTH + 10, playerCards.getFirst().y));

        // Enemy Cards
        placeHolders[0] = new CardHolder("Enemy", (WIDTH / 2) - (CARD_WIDTH / 2), 50);
        placeHolders[1] = new CardHolder("Enemy", placeHolders[0].x - CARD_WIDTH - CARD_GAP, 50);
        placeHolders[2] = new CardHolder("Enemy", placeHolders[0].x + CARD_WIDTH + CARD_GAP, 50);

        enemyCards.add(new EnemyCard("Red", 3, placeHolders[1].x, placeHolders[1].y));
        placeHolders[1].covered = true;
        placeHolders[1].recentCard = enemyCards.getFirst();

        // Attack cards
        placeHolders[3] = new CardHolder(
                "Player", placeHolders[0].x, placeHolders[0].y + CARD_HEIGHT + 40);
        placeHolders[4] = new CardHolder(
                "Player", placeHolders[0].x - CARD_WIDTH - CARD_GAP, placeHolders[3].y);
        placeHolders[5] = new CardHolder(
                "Player", placeHolders[0].x + CARD_WIDTH + CARD_GAP , placeHolders[3].y);

        // Temp Pile
        placeHolders[6] = new CardHolder(
                "Trash", WIDTH - CARD_WIDTH - EDGE_MARGIN, placeHolders[4].y + (CARD_HEIGHT / 2) + 100);

        // Player hand
        placeHolders[7] = new CardHolder(
                "Trash", playerCards.getFirst().x, playerCards.getFirst().y);
        placeHolders[8] = new CardHolder(
                "Trash", playerCards.get(1).x, playerCards.get(1).y);
        placeHolders[9] = new CardHolder(
                "Trash", playerCards.get(2).x, playerCards.get(2).y);

        placeHolders[7].recentCard = playerCards.getFirst();
        placeHolders[8].recentCard = playerCards.get(1);
        placeHolders[9].recentCard = playerCards.get(2);

        // Deck Placeholder
        placeHolders[10] = new CardHolder(
                "Back", 25, placeHolders[6].y
        );

        placeHolders[7].covered = true;
        placeHolders[8].covered = true;
        placeHolders[9].covered = true;
        placeHolders[10].covered = true;
    }

    private BufferedImage loadBackground(String imgName) {
        try {
            return ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(
                    "/wallpaper/" + imgName)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Changes the current cursor image depending on specific requirements.
     */
    private void cursorUpdater () {
        if (activeCard == null && hoveredCard == null && cursors.getCursorNumber() != 1) {
            cursors.changeCursor(1);
        } else if (hoveredCard != null && activeCard == null && cursors.getCursorNumber() != 2) {
            cursors.changeCursor(2);
        } else if (activeCard != null && cursors.getCursorNumber() != 3) {
            cursors.changeCursor(3);
        }
    }

    /**
     * Checks all placeholder cards for the presence of their card. If their card is dead or null, their fields will be
     * changed accordingly
     */
    private void placeHolderCleanUp() {
        CardHolder deck = placeHolders[10];
        for (CardHolder placeHolder : placeHolders) {
            if (placeHolder.recentCard == null) {
                placeHolder.covered = false;
            } else if (placeHolder.recentCard.dead) {
                placeHolder.covered = false;
            }
        }
    }

    /**
     * Calls the update method on all the player and enemy cards
     */
    private void updateCards() {
        for (PlayerCard playerCard : playerCards) {
            playerCard.update();
        }
        for (EnemyCard enemyCard : enemyCards){
            enemyCard.update();
        }
    }

    /**
     * Checks to see if the game over conditions are satisfied, if so, gameOver is set to true.
     */
    private void checkGameOver() {
        if (deck.isEmpty() && playerHandEmpty()) {
            gameOver = true;
        }
    }

    /**
     * Searches both the player and enemy cards for any that are dead and removes them from their respective locations.
     */
    private void removeDeadCards() {
        Stack<Card> deadCards = new Stack<>();
        for (EnemyCard enemyCard : enemyCards) {
            if (enemyCard.dead) {
                deadCards.push(enemyCard);
            }
        }
        for (PlayerCard playerCard : playerCards) {
            playerCard.update();
            if (playerCard.dead) {
                deadCards.push(playerCard);
            }
        }
        while (!deadCards.isEmpty()) {
            Card card = deadCards.pop();
            if (card instanceof PlayerCard) {
                playerCards.remove(card);
            } else if (card instanceof EnemyCard) {
                enemyCards.remove(card);
            }
        }
    }

    /**
     * Manager method to handle the behavior of mouse clicks.
     */
    private void handleMouseInput() {
        if (mouseListener.pressed) {
            updateActiveCardPosition();
        } else if (activeCard != null) {
            handleCardPlacement();
        }
    }

    /**
     * Helper method to that the active card and change it's position based on where the mouse currently is.
     */
    private void updateActiveCardPosition() {
        if (activeCard == null && hoveredCard != null) {
            activateCard();
        } else if (activeCard != null) {
            activeCard.changePos(new Point(mouseListener.x - (CARD_WIDTH / 2), mouseListener.y - (CARD_HEIGHT / 2)));
            updateHoveredPlaceholders();
        }
    }

    /**
     * Helper method to convert the card that the mouse is hovering over, into the active (selected) card.
     */
    private void activateCard() {
        activeCard = hoveredCard;
        for (CardHolder placeHolder : placeHolders) {
            if (placeHolder.recentCard == activeCard) {
                placeHolder.covered = false;
                activeCardOriginalPlaceholder = placeHolder;
                break;
            }
        }
    }

    /**
     * Updates the {@code placeHolders}'s {@code hovered} field based on the position of the mouse.
     */
    private void updateHoveredPlaceholders() {
        for (int i = 3; i < 7; i++) {
            placeHolders[i].hovered = placeHolders[i].containsPoint(new Point(mouseListener.x, mouseListener.y - 50));
        }
    }

    /**
     * Handles the placement of the active card onto the active placeholder
     */
    private void handleCardPlacement() {
        boolean placed = false;
        for (int i = 3; i < 7; i++) {
            if (tryPlaceCard(i)) {
                placed = true;
                break;
            }
        }
        if (!placed) {
            returnActiveCardToOldPos();
        }
    }

    /**
     * A helper function that attempts to place the active card onto the placeholder containing the mouse's location.
     * @param i the index of the placeholder card to attempt placement onto.
     * @return A boolean specifying whether the placement was successful or not.
     */
    private boolean tryPlaceCard(int i) {
        Point mousePosPadded = new Point(mouseListener.x, mouseListener.y - 50);
        CardHolder cardHolder = placeHolders[i];
        if (cardHolder.containsPoint(mousePosPadded)) {
            if (cardHolder.covered) {
                if (i == 6) {
                    cardHolder.recentCard.dead = true;
                    cardHolder.recentCard = activeCard;
                    placeActiveCard(new Point(cardHolder.x, cardHolder.y));
                    ((PlayerCard) cardHolder.recentCard).setHoverParameters(20);
                    cardHolder.hovered = false;
                    cardHolder.recentCard.hovered = false;
                    return true;
                } else {
                    cardHolder.hovered = false;
                    return false;
                }
            } else if (i == 3 || i == 4 || i == 5) {
                if (placeHolders[i - 3].covered) {
                    cardHolder.recentCard = activeCard;
                    placeActiveCard(new Point(cardHolder.x, cardHolder.y));
                    cardHolder.covered = true;
                    cardHolder.hovered = false;
                    return true;
                }
            } else if (i == 6){
                cardHolder.recentCard = activeCard;
                placeActiveCard(new Point(cardHolder.x, cardHolder.y));
                ((PlayerCard) cardHolder.recentCard).setHoverParameters(20);
                cardHolder.covered = true;
                cardHolder.hovered = false;
                return true;
            }
        }
        cardHolder.hovered = false;
        return false;
    }

    /**
     * Helper function to be used when placement of an Active card fails. Returns the active card to where it was before
     * the placement was attempted.
     */
    private void returnActiveCardToOldPos(){
        assert activeCard != null;
        assert activeCardOriginalPlaceholder != null;
            activeCard.changePos(new Point(activeCardOriginalPlaceholder.x,activeCardOriginalPlaceholder.y));
            activeCardOriginalPlaceholder.covered = true;
            activeCard = null;
            activeCardOriginalPlaceholder = null;
            hoveredCard = null;
    }

    /**
     * Helper function to place the active card at a given point.
     * @param p the point at which the active card will be placed.
     */
    private void placeActiveCard(Point p){
        assert activeCard != null;
        activeCard.changePos(p);
        activeCard = null;
        activeCardOriginalPlaceholder = null;
        hoveredCard = null;
    }

    /**
     * A helper function that manages the combat between two opposing cards.
     */
    private void combatProgression() {
        int i = 3;
        while (i < 6) {
            CardHolder cardHolder = placeHolders[i];
            if (cardHolder.covered) {
                CardHolder opponent = placeHolders[i - 3];
                if (opponent.covered) {
                    cardHolder.recentCard.attack(opponent.recentCard);
                    if (opponent.recentCard.cardValue == 0) {
                        if (cardHolder.recentCard.cardColor.equals(opponent.recentCard.cardColor)) {
                            opponent.recentCard.dying = true;
                            opponent.recentCard = ((EnemyCard) opponent.recentCard).underCard;
                        } else {
                            opponent.recentCard.cardValue = 1;
                            opponent.recentCard.setFace();
                        }
                    }
                    killCard(cardHolder.recentCard, i);
                    cardHolder.recentCard = null;
                    cardHolder.covered = false;
                }
            }
            i++;
        }
    }

    /**
     * A helper function that is used to quickly kill a card if it has been used to attack.
     * @param card The card to be killed.
     * @param placeHolderPos The placeholder on which the card is on.
     */
    private void killCard (Card card, int placeHolderPos) {
        if (card instanceof PlayerCard) {
            playerCards.remove(card);
            placeHolders[placeHolderPos].recentCard = null;
            placeHolders[placeHolderPos].covered = false;
        }
        else if (card instanceof EnemyCard){
            enemyCards.remove(card);
            placeHolders[placeHolderPos].recentCard = null;
            placeHolders[placeHolderPos].covered = false;
        }
    }

    /**
     * Attempts to draw cards from the deck if the player has no active cards and if their hand is empty. If deck is
     * empty, the image of the deck is removed.
     */
    private void tryDrawCardsFromDeck() {
        if (playerHandEmpty() && activeCard == null ) {
            if (!deck.isEmpty()) {
                int i = 7;
                while (i < 10) {
                    PlayerCard handCard = deck.pop();
                    placeHolders[i].recentCard = handCard;
                    placeHolders[i].covered = true;
                    handCard.x = placeHolders[i].x;
                    handCard.y = placeHolders[i].y;
                    handCard.setHoverParameters(20);
                    playerCards.add(handCard);
                    i++;
                }
                if (deck.isEmpty()){
                    placeHolders[10].changeImage("Blank");
                }
            }
        }
    }

    /**
     * A helper function that checks to see if the player's hand is empty
     * @return a boolean specifying whether the player's hand is (returns true) or isn't (returns false) empty.
     */
    private boolean playerHandEmpty() {
        for (int i = 7; i < 10; i++){
            if (placeHolders[i].covered){
                return false;
            }
        }
        return true;
    }


    /**
     * Updates the {@code hoveredCard} field with new information on the nearest card to the mouse.
     */
    private void updateHoveredCard() {
        boolean found = false;
        for (PlayerCard playerCard : playerCards) {
            if (playerCard.containsPoint(new Point(mouseListener.x, mouseListener.y))) {
                hoveredCard = playerCard;
                playerCard.hovered = true;
                found = true;
            }
            else {
                playerCard.hovered = false;
            }
        }
        if (!found) {hoveredCard = null;}
    }


    public void update() {
        cursorUpdater();
        updateHoveredCard();
        combatProgression();
        removeDeadCards();
        handleMouseInput();
        updateCards();
        placeHolderCleanUp();
        tryDrawCardsFromDeck();
        checkGameOver();
    }

    public void draw(Graphics2D g2d){
        g2d.drawImage(backgrounds[0],0,0, WIDTH, GamePanel.HEIGHT, null);

        for (CardHolder card : placeHolders) {
            if (card != null && !card.dead){
                card.draw(g2d);
            }
        }
        for (PlayerCard player : playerCards) {
            if (player != null && !player.dead){
                player.draw(g2d);
            }
        }
        for (EnemyCard enemyCard : enemyCards){
            if (enemyCard != null && !enemyCard.dead){
                enemyCard.draw(g2d);
            }
        }
        if (activeCard != null){
            activeCard.draw(g2d);
        }
    }
}
