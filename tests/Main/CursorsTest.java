package Main;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

class CursorsTest {

    @DisplayName("WHEN a new Cursors is created, THEN it should not be null and it should contain the panel that it was " +
            "initialized with.")
    @Test
    void testNew(){
        JPanel panel = new JPanel();
        Cursors cursors = new Cursors(panel);
        assertNotNull(cursors);
        assertEquals(panel, cursors.panel);
    }

    @DisplayName("WHEN a Cursors is changed, THEN it should reflect the changes by assigning a new cursor number.")
    @Test
    void changeCursor() {
        JPanel panel = new JPanel();
        Cursors cursors = new Cursors(panel);
        assertEquals(1, cursors.getCursorNumber());
        cursors.changeCursor(10);
        assertEquals(10, cursors.getCursorNumber());
    }
}