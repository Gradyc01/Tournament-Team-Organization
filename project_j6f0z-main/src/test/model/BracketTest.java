package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.*;

public class BracketTest {
    private Bracket testBracket;
    private Teams Team1;
    private Teams Team2;
    private Teams Team3;
    private Player p1;
    private Player p2;
    private Player p3;

    @BeforeEach
    void runBefore() {
        testBracket = new Bracket("NA finals");
        Team1 = new Teams("Tigers", 1);
        Team2 = new Teams ("Lions", 2);
        Team3 = new Teams("Giants", 3);
        p1 = new Player("player1", 18);
        p2 = new Player("player2", 18);
        p3 = new Player("player1", 20);
    }

    @Test
    void testConstructor() {
        assertEquals("NA finals", testBracket.getBracketName());
        assertEquals(0, testBracket.getTeamBracketSize());
        assertEquals(0, testBracket.getPlayerBracketSize());
    }

    @Test
    void testAddTeam() {
        List<Teams> testTeams = new ArrayList<>();
        testTeams.add(Team1);
        testTeams.add(Team2);
        //=====================================================
        assertTrue(testBracket.addTeam(Team1));
        assertEquals(1, testBracket.getTeamBracketSize());
        //=====================================================
        assertTrue(testBracket.addTeam(Team2));
        assertEquals(2, testBracket.getTeamBracketSize());
        assertEquals(testTeams, testBracket.getTeams());
        //====================================================
        assertFalse(testBracket.addTeam(Team1));
        assertEquals(2, testBracket.getTeamBracketSize());
    }

    @Test
    void testRemoveTeam() {
        testBracket.addTeam(Team1);
        testBracket.addTeam(Team2);
        testBracket.addTeam(Team3);
        //=====================================================
        assertTrue(testBracket.removeTeam(Team2));
        assertEquals(2, testBracket.getTeamBracketSize());
        //=====================================================
        assertFalse(testBracket.removeTeam(Team2));
        assertEquals(2, testBracket.getTeamBracketSize());
        //=====================================================
        assertTrue(testBracket.removeTeam(Team3));
        assertEquals(1, testBracket.getTeamBracketSize());
    }

    @Test
    void testSetBracketName() {
        testBracket.setBracketName("Canada");
        assertEquals("Canada", testBracket.getBracketName());
    }

    @Test
    void testUnique() {
        assertTrue(testBracket.unique(Team1));
        testBracket.addTeam(Team1);
        testBracket.addTeam(Team2);
        Teams test1 = new Teams("Tigers", 10);
        Teams test2 = new Teams ("NotTigers", 1);
        Teams test3 = new Teams ("NotTigers", 10);
        assertFalse(testBracket.unique(test1));
        assertFalse(testBracket.unique(test2));
        assertTrue(testBracket.unique(test3));
    }

    @Test
    void testUniquePlayer() {
        testBracket.addPlayer(p1);
        assertTrue(testBracket.uniquePlayer("player2"));
        assertFalse(testBracket.uniquePlayer("player1"));
    }

    @Test
    void testAddRemovePlayer() {
        List<Player> testPlayer = new ArrayList<>();
        testPlayer.add(p1);
        testBracket.addPlayer(p1);
        assertEquals(1, testBracket.getPlayerBracketSize());
        assertEquals(testPlayer, testBracket.getPlayerPool());
        testBracket.addPlayer(p2);
        testBracket.addPlayer(p3);
        testBracket.removePlayer(p2);
        assertEquals(2, testBracket.getPlayerBracketSize());
    }
}
