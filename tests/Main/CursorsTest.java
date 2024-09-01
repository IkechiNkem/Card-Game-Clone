package Main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class CursorsTest {

    @DisplayName("WHEN a new Cursors is created, THEN it should not be null and it should contain the panel that it was initi " +
            "or hovering, and it's x and y coordinates should both be 0")
    @Test
    void testNew(){
        JPanel panel = new JPanel();
        Cursors cursors = new Cursors(panel);
        assertNotNull(cursors);
        assertEquals(panel, cursors.panel);
    }

    @Test
    void changeCursor() {
        JPanel panel = new JPanel();
        Cursors cursors = new Cursors(panel);
        assertEquals(1, cursors.getCursorNumber());
        cursors.changeCursor(10);
        assertEquals(10, cursors.getCursorNumber());
    }
}