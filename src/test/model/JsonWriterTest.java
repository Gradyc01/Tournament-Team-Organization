package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JsonWriterTest {
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
    void testWriterInvalidFile() {
        try {
            Bracket bracket;
            bracket = new Bracket("New Bracket");

            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyBracket() {
        try {
            Bracket bracket = new Bracket("BracketLeague");
            JsonWriter writer = new JsonWriter("./data/testEmptyBracket.json");
            writer.open();
            writer.write(bracket);
            writer.close();

            JsonReader reader = new JsonReader("./data/testEmptyBracket.json");
            bracket = reader.read();
            assertEquals("BracketLeague", bracket.getBracketName());
            assertEquals(0, bracket.getTeamBracketSize());
            assertEquals(0, bracket.getPlayerBracketSize());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterBracket() {
        try {
            Bracket bracket = new Bracket("LeagueOfBrackets");
            JsonWriter writer = new JsonWriter("./data/testBracket.json");
            bracket.addTeam(Team1);
            bracket.addTeam(Team2);
            bracket.addTeam(Team3);
            bracket.addPlayer(p1);
            bracket.addPlayer(p2);
            bracket.addPlayer(p3);
            bracket.addPlayer(p4);
            writer.open();
            writer.write(bracket);
            writer.close();

            JsonReader reader = new JsonReader("./data/testBracket.json");
            Bracket newBracket = reader.read();
            assertEquals("LeagueOfBrackets", bracket.getBracketName());
            List<Teams> teamList = newBracket.getTeams();
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
            fail("Exception should not have been thrown");
        }
    }
}
