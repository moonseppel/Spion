package ligamanager.spion.analyzer.pages;

import ligamanager.spion.analyzer.TestData;
import ligamanager.spion.analyzer.useCases.BasicActions;
import ligamanager.spion.analyzer.util.GameResult;
import ligamanager.spion.analyzer.util.GameValues;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by jpralle on 01.03.2016.
 */
public class LMGamePageIntegrationTest {

    private static int testedSeason = 122;

    @BeforeClass
    public static void setUp() throws Exception {
        BasicActions.loginAndChooseFirstTeam(TestData.USERNAME, TestData.PASSWORD);
    }

    /**
     * Tested game: http://www.liga-manager.de/inc/spiel_info.php?id=1&show_saison=122
     */
    @Test
    public void testGetGameDataForNormalGame() throws Exception {

        int expectedGameId = 1;
        LMGamePage subject = new LMGamePage(expectedGameId, testedSeason);

        assertAllGameValues(expectedGameId, subject);
        assertFalse(subject.hasExtraTime());
        assertFalse(subject.hasPenyltyShooting());

        assertEquals("Pauli Pirates", subject.getHomeTeamName());
        assertEquals("1. FC Magdeburg", subject.getAwayTeamName());
        assertEquals(122, subject.getSeasonNumber());
        assertEquals(new GameResult(4,1), subject.getEndResult());
    }

    /**
     * Tested game: http://www.liga-manager.de/inc/spiel_info.php?id=280152&show_saison=122
     */
    @Test
    public void testGetGameDataForGameWithExtraTime() throws Exception {

        int expectedGameId = 280152; //also 280156 should work
        LMGamePage subject = new LMGamePage(expectedGameId, testedSeason);

        assertAllGameValues(expectedGameId, subject);
        assertTrue(subject.hasExtraTime());
        assertFalse(subject.hasPenyltyShooting());

        assertEquals("SV Tündern 05", subject.getHomeTeamName());
        assertEquals("SV Donaumoos", subject.getAwayTeamName());
        assertEquals(122, subject.getSeasonNumber());
        assertEquals(new GameResult(0,1), subject.getEndResult());
    }

    /**
     * Tested game: http://www.liga-manager.de/inc/spiel_info.php?id=280155&show_saison=122
     */
    @Test
    public void testGetGameDataForGameWithExtraTimeAndPenaltyShooting() throws Exception {

        int expectedGameId = 280155; //also 280156 should work
        LMGamePage subject = new LMGamePage(expectedGameId, testedSeason);

        assertAllGameValues(expectedGameId, subject);
        assertTrue(subject.hasExtraTime());
        assertTrue(subject.hasPenyltyShooting());

        assertEquals("⚜ Behar Reutlingen ⚜", subject.getHomeTeamName());
        assertEquals("Der Club", subject.getAwayTeamName());
        assertEquals(122, subject.getSeasonNumber());
        assertEquals(new GameResult(8,7), subject.getEndResult());
    }

    private void assertAllGameValues(int expectedGameId, LMGamePage subject) {

        GameResult lowestButSetResult = new GameResult(0, 0);

        assertTrue(subject.navigateToPageAndCheck());
        assertEquals(expectedGameId, subject.getGameId());
        assertTrue(subject.getHomeTeamName() != null && subject.getHomeTeamName().length() > 3);
        assertTrue(subject.getAwayTeamName() != null && subject.getAwayTeamName().length() > 3);
        assertTrue(subject.getSeasonNumber() >= 122);
        assertTrue(subject.getEndResult().getHome() >= 0);
        assertTrue(subject.getEndResult().getAway() >= 0);
        assertTrue(compareAllBy(subject.hasExtraTime(), subject.hasPenyltyShooting(), lowestButSetResult, subject.getResults()));
//        assertTrue(compareAllBy(subject.hasExtraTime(), subject.hasPenyltyShooting(), lowestButSetFormation, subject.getHomeFormations()));

    }

    private boolean compareAllBy(boolean hasExtraTime, boolean hasPenaltyShooting, GameResult expected, GameValues<GameResult> actual) {
        boolean ret = true;

        ret = ret && isGreaterOrEqual(expected, actual.firstHalf);
        ret = ret && isGreaterOrEqual(expected, actual.secondHalf);
        if(hasExtraTime) {
            ret = ret && isGreaterOrEqual(expected, actual.extraTime);
        } else {
            ret = ret && actual.extraTime.isEmpty();
        }

        if(hasPenaltyShooting) {
            ret = ret && isGreaterOrEqual(expected, actual.penalityShooting);
        } else {
            ret = ret && actual.penalityShooting.isEmpty();
        }

        return ret;
    }

    private boolean isGreaterOrEqual(GameResult expected , GameResult actual) {
        boolean ret = true;

        ret = ret && actual.getHome() >= expected.getHome();
        ret = ret && actual.getAway() >= expected.getAway();

        return ret;
    }


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