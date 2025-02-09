package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TeamsTest {
    private Teams testTeams;
    private Player p1;
    private Player p2;
    private Player p3;
    @BeforeEach
    void runBefore() {
        p1 = new Player("player1", 18);
        p2 = new Player("player2", 19);
        p3 = new Player("player3", 20);
        testTeams = new Teams("Tigers", 3);
    }

    @Test
    void testConstructor() {
        assertEquals("Tigers", testTeams.getTeamName());
        assertEquals(3, testTeams.getSeeding());
        assertFalse(testTeams.isSuspended());
        assertEquals(0, testTeams.getTeamSize());
    }

    @Test
    void testAddTeamMembers() {
        List<Player> players = new ArrayList<>();
        p3.teamChange();
        //=================================================
        assertTrue(testTeams.addTeamMembers(p1));
        assertEquals(1, testTeams.getTeamSize());
        players.add(p1);
        assertTrue(p1.hasTeam());
        assertEquals(players, testTeams.getTeamMembers());
        // =================================================
        assertTrue(testTeams.addTeamMembers(p2));
        players.add(p2);
        assertTrue(p2.hasTeam());
        assertEquals(players, testTeams.getTeamMembers());
        assertEquals(2, testTeams.getTeamSize());
        // =================================================
        assertFalse(testTeams.addTeamMembers(p2));
        assertEquals(players, testTeams.getTeamMembers());
        assertEquals(2, testTeams.getTeamSize());
        //=================================================
        assertFalse(testTeams.addTeamMembers(p3));
        assertEquals(players, testTeams.getTeamMembers());
        assertEquals(2, testTeams.getTeamSize());
    }

    @Test
    void testRemoveTeamMembers() {
        List<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p3);
        testTeams.addTeamMembers(p1);
        testTeams.addTeamMembers(p2);
        testTeams.addTeamMembers(p3);
        assertTrue(testTeams.removeTeamMembers(p2));
        assertFalse(p2.hasTeam());
        assertEquals(2, testTeams.getTeamSize());
        assertEquals(players, testTeams.getTeamMembers());
        // =================================================
        assertFalse(testTeams.removeTeamMembers(p2));
        assertEquals(2, testTeams.getTeamSize());
    }

    @Test
    void testSuspend() {
        testTeams.suspend();
        assertTrue(testTeams.isSuspended());
        testTeams.suspend();
        assertFalse(testTeams.isSuspended());
    }

    @Test
    void testSetSeeding() {
        testTeams.setSeeding(100);
        assertEquals(100, testTeams.getSeeding());
    }

    @Test
    void testSetName() {
        testTeams.setTeamName("Lions");
        assertEquals("Lions", testTeams.getTeamName());
    }
}
