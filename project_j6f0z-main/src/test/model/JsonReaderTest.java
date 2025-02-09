package model;

import org.json.JSONObject;
import org.junit.jupiter.api.*;
import persistence.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    Teams Team1;
    Teams Team2;
    Teams Team3;
    Player p1;
    Player p2;
    Player p3;
    Player p4;

    @BeforeEach
    void runBefore() {
        Team1 = new Teams("1", 1);
        Team2 = new Teams ("2", 2);
        Team3 = new Teams("3", 3);
        p1 = new Player("1", 1);
        p2 = new Player("2", 2);
        p3 = new Player("3", 3);
        p4 = new Player("4", 4);
        Team2.addTeamMembers(p2);
        Team1.suspend();
    }
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/notAThing.json");
        try {
            Bracket bracket = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyBracket() {
        JsonReader reader = new JsonReader("./data/blankBracket.json");
        try {
            Bracket bracket = reader.read();
            assertEquals("New Bracket", bracket.getBracketName());
            assertEquals(0, bracket.getTeamBracketSize());
            assertEquals(0, bracket.getPlayerBracketSize());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneral() {
        JsonReader reader = new JsonReader("./data/testBracket.json");
        try {
            Bracket bracket = reader.read();
            assertEquals("LeagueOfBrackets", bracket.getBracketName());
            assertEquals(4, bracket.getPlayerBracketSize());
            assertEquals(3, bracket.getTeamBracketSize());
            List<Teams> teamList = bracket.getTeams();
            assertEquals("1", teamList.get(0).getTeamName());
            assertEquals("2", teamList.get(1).getTeamName());
            assertEquals("3", teamList.get(2).getTeamName());
            List<Player> playerList = teamList.get(1).getTeamMembers();
            assertEquals(p2.getName(), playerList.get(0).getName());
            assertEquals(p2.getAge(), playerList.get(0).getAge());
            assertEquals(p2.hasTeam(), playerList.get(0).hasTeam());
            assertEquals(0, teamList.get(0).getTeamSize());
            assertEquals(0, teamList.get(2).getTeamSize());
            assertEquals(2, teamList.get(1).getSeeding());
            assertTrue(teamList.get(0).isSuspended());
            assertFalse(teamList.get(2).isSuspended());



        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testRead() {
        JsonReader reader = new JsonReader("./data/testBracket.json");
        try {
            Bracket bracket = reader.read();
            assertEquals("LeagueOfBrackets", bracket.getBracketName());
        } catch (IOException e) {
            fail();
        }
    }


}
