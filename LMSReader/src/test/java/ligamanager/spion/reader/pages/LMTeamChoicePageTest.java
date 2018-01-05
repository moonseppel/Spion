package ligamanager.spion.reader.pages;

import ligamanager.spion.reader.TestData;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created by jpralle on 01.03.2016.
 */
public class LMTeamChoicePageTest {

    @BeforeClass
    public static void setUp() throws Exception {
        LmStartPage startPage = new LmStartPage();

        Optional<LmTeamChoicePage> teamChoicePage = Optional.empty();
        startPage.navigateToPageAndCheck();
        teamChoicePage = startPage.login(TestData.USERNAME, TestData.PASSWORD);

    }

    @Test
    public void testGetAllTeams() throws Exception {
        List<String> teamNames = new ArrayList<String>();

        LmTeamChoicePage subject = new LmTeamChoicePage();

        subject.navigateToPageAndCheck();
        teamNames = subject.getAllTeams();

        assertEquals(1, teamNames.size());
    }

    @Test
    public void testSelectTeam() throws Exception {
        boolean foundTeam = false;

        LmTeamChoicePage subject = new LmTeamChoicePage();

        subject.navigateToPageAndCheck();
        List<String> teamNames = subject.getAllTeams();
        foundTeam = subject.selectTeam(teamNames.size()-1);

        assertTrue(foundTeam);

    }
}