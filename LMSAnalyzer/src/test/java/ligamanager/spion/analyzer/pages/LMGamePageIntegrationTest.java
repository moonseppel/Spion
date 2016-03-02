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
        LMGamePage subject = new LMGamePage(expectedGameId);

        assertTrue(subject.navigateToPageAndCheck());
        assertEquals(expectedGameId, subject.getGameId());
        assertTrue(subject.getHomeTeamName() != null && subject.getHomeTeamName().length() > 3);
        assertTrue(subject.getAwayTeamName() != null && subject.getAwayTeamName().length() > 3);
        assertTrue(subject.getSeasonNumber() >= 122);

//        assertEquals("Pauli Pirates", gamePage.getHomeTeamName());
//        assertEquals("1. FC Magdeburg", gamePage.getAwayTeamName());
//        assertEquals(122, subject.getSeasonNumber());
    }

    @Test
    public void testGetGameDataForGameWithExtraTime() throws Exception {

        int expectedGameId = 280152; //also 280156 should work
        LMGamePage subject = new LMGamePage(expectedGameId);

        assertTrue(subject.navigateToPageAndCheck());
        assertEquals(expectedGameId, subject.getGameId());
        assertTrue(subject.getHomeTeamName() != null && subject.getHomeTeamName().length() > 3);
        assertTrue(subject.getAwayTeamName() != null && subject.getAwayTeamName().length() > 3);
        assertTrue(subject.getSeasonNumber() >= 122);

    }


// WebElement endResult;
// GameValues<WebElement> results;
// GameValues<WebElement> homeStrengthsBeginOfHalfs;
// GameValues<WebElement> homeStrengthsAverageOfHalfs;
// GameValues<WebElement> homeStrengthsEndOfHalfs;
// GameValues<WebElement> awayStrengthsBeginOfHalfs;
// GameValues<WebElement> awayStrengthsAverageOfHalfs;
// GameValues<WebElement> awayStrengthsEndOfHalfs;
// GameValues<WebElement> homeSystems;
// GameValues<WebElement> awaySystems;
// GameValues<WebElement> homeTactics;
// GameValues<WebElement> awayTactocs;
// GameValues<WebElement> homeAngriffe;
// GameValues<WebElement> awayAngriffe;
// GameValues<WebElement> homeChancen;
// GameValues<WebElement> awayChancen;
// GameValues<WebElement> homeBallPossession;
// GameValues<WebElement> awayBallPossession;
// WebElement homeBallPossessionTotal;
// WebElement awayBallPossessionTotal;
// GameValues<WebElement> homeZweikaempfe;
// GameValues<WebElement> awayZweikaempfe;
// WebElement homeZweikaempfeTotal;
// WebElement awayZweikaempfeTotal;

}