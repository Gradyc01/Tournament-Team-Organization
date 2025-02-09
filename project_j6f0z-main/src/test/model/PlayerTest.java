package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player testPlayer;
    private Player testPlayer2;
    private Player testPlayer3;
    private Player testPlayerOverload;

    @BeforeEach
    void runBefore() {
        testPlayer = new Player("Jimmy", 18);
        testPlayerOverload = new Player("Garrett", 19, true);
        testPlayer2 = new Player("Jimmy", 19);
        testPlayer3 = new Player("Jimmold", 18);

    }

    @Test
    void testConstructor() {
        assertEquals("Jimmy", testPlayer.getName());
        assertEquals(18, testPlayer.getAge());
        assertFalse(testPlayer.hasTeam());
        assertEquals("Garrett", testPlayerOverload.getName());
        assertEquals(19, testPlayerOverload.getAge());
        assertTrue(testPlayerOverload.hasTeam());
    }

    @Test
    void testSetAge() {
        testPlayer.setAge(10);
        assertEquals(10, testPlayer.getAge());
    }

    @Test
    void testSetName() {
        testPlayer.setName("Can");
        assertEquals("Can", testPlayer.getName());
    }

    @Test
    void testSetYearsPlayed() {
        testPlayer.teamChange();
        assertTrue(testPlayer.hasTeam());
    }

    @Test
    void testEquals() {
        assertFalse(testPlayer.equals(new Teams("Grady", 1)));
        assertEquals(testPlayer, testPlayer);
        assertTrue(testPlayer.equals(testPlayer2));
        assertFalse(testPlayer.equals(testPlayer3));
        assertFalse(testPlayer3.equals(testPlayer2));
        assertFalse(testPlayer.equals(null));
        assertEquals(testPlayer.hashCode(), testPlayer.hashCode());
        assertFalse(testPlayer.hashCode() == testPlayer3.hashCode());
        assertEquals(new Player(null, 1).hashCode(), 0);

    }
}