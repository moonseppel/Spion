package ligamanager.spion.analyzer.pages;

import ligamanager.spion.analyzer.TestData;
import ligamanager.spion.analyzer.useCases.BasicActions;
import org.junit.Before;
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
        LMStartPage startPage = new LMStartPage();

        Optional<LMTeamChoicePage> teamChoicePage = Optional.empty();
        if(startPage.navigateToPageAndCheck()) {
            teamChoicePage = startPage.login(TestData.USERNAME, TestData.PASSWORD);
        }
    }

    @Test
    public void testGetAllTeams() throws Exception {
        List<String> teamNames = new ArrayList<String>();

        LMTeamChoicePage subject = new LMTeamChoicePage();

        if(subject.navigateToPageAndCheck()) {
            teamNames = subject.getAllTeams();
        }

        assertEquals(3, teamNames.size());
    }

    @Test
    public void testSelectTeam() throws Exception {
        boolean foundTeam = false;

        LMTeamChoicePage subject = new LMTeamChoicePage();

        if(subject.navigateToPageAndCheck()) {
            List<String> teamNames = subject.getAllTeams();
            foundTeam = subject.selectTeam(teamNames.size()-1);
        }

        assertTrue(foundTeam);

    }
}