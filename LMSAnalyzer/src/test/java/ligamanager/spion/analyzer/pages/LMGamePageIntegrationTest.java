package ligamanager.spion.analyzer.pages;

import ligamanager.spion.analyzer.TestData;
import ligamanager.spion.analyzer.useCases.BasicActions;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by jpralle on 01.03.2016.
 */
public class LMGamePageIntegrationTest {

    @BeforeClass
    public static void setUp() throws Exception {
        BasicActions.loginAndChooseFirstTeam(TestData.USERNAME, TestData.PASSWORD);
    }

    @Test
    public void testGetGameDataForNormalGame() throws Exception {
        int expectedGameId = 1;
        LMGamePage gamePage = new LMGamePage(expectedGameId);

        assertTrue(gamePage.navigateToPageAndCheck());
        assertEquals(expectedGameId, gamePage.getGameId());
        assertTrue(gamePage.getHomeTeamName() != null && gamePage.getHomeTeamName().length() > 3);
//        assertEquals("Pauli Pirates", gamePage.getHomeTeamName());
        assertTrue(gamePage.getAwayTeamName() != null && gamePage.getAwayTeamName().length() > 3);
//        assertEquals("1. FC Magdeburg", gamePage.getAwayTeamName());


    }
}