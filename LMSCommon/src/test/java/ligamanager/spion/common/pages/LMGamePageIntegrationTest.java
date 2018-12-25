package ligamanager.spion.common.pages;

import ligamanager.spion.common.*;
import ligamanager.spion.reader.TestData;
import ligamanager.spion.common.useCases.BasicActions;
import ligamanager.spion.common.util.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by jpralle on 01.03.2016.
 */
public class LMGamePageIntegrationTest {

    @BeforeClass
    public static void setUp() throws Exception {
        assertTrue(BasicActions.loginAndChooseFirstTeam(TestData.USERNAME, TestData.PASSWORD));
    }

    @AfterClass
    public static void teardown() {
        assertThat(BasicActions.logout(), is(true));
    }

    /**
     * Tested game: https://www.liga-manager.de/spiel_info/?id=1&show_saison=137
     */
    @Test
    public void testGetGameDataForNormalGame() throws Exception {

        int expectedGameId = 1;
        LmGamePage subject = new LmGamePage(expectedGameId, 137);
        subject.navigateToPageAndCheck();

        assertAllGameValues(expectedGameId, subject);
        assertFalse(subject.hasExtraTime());
        assertFalse(subject.hasPenyltyShooting());

        assertEquals(137, subject.getSeasonNumber());
    }

    /**
     * Tested game: https://www.liga-manager.de/spiel_info/?id=248700&show_saison=137
     */
    @Test
    public void testGetGameDataForGameWithExtraTime() throws Exception {

        int expectedGameId = 248700;
        LmGamePage subject = new LmGamePage(expectedGameId, 137);
        subject.navigateToPageAndCheck();

        assertAllGameValues(expectedGameId, subject);
        assertTrue(subject.hasExtraTime());
        assertFalse(subject.hasPenyltyShooting());

        assertEquals(137, subject.getSeasonNumber());
    }

    /**
     * Tested game: https://www.liga-manager.de/spiel_info/?id=248701&show_saison=137
     */
    @Test
    public void testGetGameDataForGameWithExtraTimeAndPenaltyShooting() throws Exception {

        int expectedGameId = 248701;
        LmGamePage subject = new LmGamePage(expectedGameId, 137);
        subject.navigateToPageAndCheck();

        assertAllGameValues(expectedGameId, subject);
        assertTrue(subject.hasExtraTime());
        assertTrue(subject.hasPenyltyShooting());

        assertEquals(137, subject.getSeasonNumber());
    }

    /**
     * Tested game: https://www.liga-manager.de/spiel_info/?id=271355&show_saison=137
     */
    @Test
    public void testGetGameDataForGameWithChangingTactic() throws Exception {

        int expectedGameId = 271355;
        LmGamePage subject = new LmGamePage(expectedGameId, 137);
        subject.navigateToPageAndCheck();

        assertAllGameValues(expectedGameId, subject);
        assertFalse(subject.hasExtraTime());
        assertFalse(subject.hasPenyltyShooting());

        assertEquals(137, subject.getSeasonNumber());

        assertFalse(subject.getHomeTactics().firstHalf.equals(subject.getHomeTactics().secondHalf));
        assertEquals(Tactic.EMPTY, subject.getHomeTactics().extraTime);
        assertEquals(Tactic.EMPTY, subject.getAwayTactics().extraTime);
    }

    /**
     * Tested game: https://www.liga-manager.de/spiel_info/?id=231708&show_saison=137
     */
    @Test
    public void testAmateurGame() throws LmIllegalPageException {

        int expectedGameId = 231708;
        LmGamePage subject = new LmGamePage(expectedGameId, 137);

        try {

            subject.navigateToPageAndCheck();
            fail("An \"LmIllegalGameException\" for an amateur game should have been thrown.");

        } catch (LmIllegalGameException ex) {

            assertEquals(expectedGameId, ex.getGameId());
            assertEquals(137, ex.getSeasonNumber());
            assertEquals(IllegalGameType.AmateurGame, ex.getGameType());
        }

    }

    /**
     * Tested game: https://www.liga-manager.de/spiel_info/?id=1000000&show_saison=137
     */
    @Test
    public void testNotExistingGame() throws LmIllegalPageException {

        int expectedGameId = 1000000;
        LmGamePage subject = new LmGamePage(expectedGameId, 137);

        try {
            subject.navigateToPageAndCheck();

        } catch (LmIllegalGameException ex) {

            assertEquals(expectedGameId, ex.getGameId());
            assertEquals(137, ex.getSeasonNumber());
            assertEquals(IllegalGameType.NoGame, ex.getGameType());
        }
    }

    /**
     * Tested game: https://www.liga-manager.de/de/spiel_info/?id=196279&show_saison=128
     */
    @Ignore("Activate and modify to check strange games")
    @Test
    public void testStrangeGame() throws LmIllegalPageException {

        int expectedGameId = 196279;
        LmGamePage subject = new LmGamePage(expectedGameId, 128);

        try {
            subject.navigateToPageAndCheck();

        } catch (LmIllegalGameException ex) {

            assertEquals(expectedGameId, ex.getGameId());
            assertEquals(128, ex.getSeasonNumber());
            assertEquals(IllegalGameType.AmateurGame, ex.getGameType());
        }
    }

    private void assertAllGameValues(int expectedGameId, LmGamePage subject) {

        GameResult lowestButSetResult = new GameResult(0, 0);

        assertEquals(expectedGameId, subject.getGameId());
        assertTrue(subject.getHomeTeamName() != null && subject.getHomeTeamName().length() > 3);
        assertTrue(subject.getAwayTeamName() != null && subject.getAwayTeamName().length() > 3);
        assertTrue(subject.getSeasonNumber() >= 122);
        assertTrue(subject.getEndResult().getHome() >= 0);
        assertTrue(subject.getEndResult().getAway() >= 0);
        assertTrue(compareAllBy(subject.hasExtraTime(), subject.hasPenyltyShooting(), lowestButSetResult, subject.getResults()));
        assertTrue(areAllSet(subject.hasExtraTime(), subject.getHomeFormations()));
        assertTrue(areAllSet(subject.hasExtraTime(), subject.getAwayFormations()));
        assertTrue(areAllSet(subject.hasExtraTime(), subject.getHomeTactics()));
        assertTrue(areAllSet(subject.hasExtraTime(), subject.getAwayTactics()));
        assertTrue(areAllSet(subject.hasExtraTime(), subject.getStrengthsBeginOfHalfs()));
        assertTrue(areAllSet(subject.hasExtraTime(), subject.getStrengthsEndOfHalfs()));
        assertTrue(areAllSet(subject.hasExtraTime(), subject.getStrengthsAverageOfHalfs()));
    }

    private boolean compareAllBy(boolean hasExtraTime, boolean hasPenaltyShooting, GameResult expected, GameValuesInclPenalties<GameResult> actual) {
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

    private <T extends Emptyable> boolean areAllSet(boolean hasExtraTime, GameValues<T> actual) {
        boolean ret = true;

        ret = ret && !actual.firstHalf.isEmpty();
        ret = ret && !actual.secondHalf.isEmpty();
        if(hasExtraTime) {
            ret = ret && !actual.extraTime.isEmpty();
        } else {
            ret = ret &&  actual.extraTime.isEmpty();
        }

        return ret;
    }

    private boolean isGreaterOrEqual(GameResult expected , GameResult actual) {
        boolean ret = true;

        ret = ret && actual.getHome() >= expected.getHome();
        ret = ret && actual.getAway() >= expected.getAway();

        return ret;
    }


// GameValuesInclPenalties<WebElement> homeStrengthsBeginOfHalfs;
// GameValuesInclPenalties<WebElement> homeStrengthsAverageOfHalfs;
// GameValuesInclPenalties<WebElement> homeStrengthsEndOfHalfs;
// GameValuesInclPenalties<WebElement> awayStrengthsBeginOfHalfs;
// GameValuesInclPenalties<WebElement> awayStrengthsAverageOfHalfs;
// GameValuesInclPenalties<WebElement> awayStrengthsEndOfHalfs;
// GameValuesInclPenalties<WebElement> homeAngriffe;
// GameValuesInclPenalties<WebElement> awayAngriffe;
// GameValuesInclPenalties<WebElement> homeChancen;
// GameValuesInclPenalties<WebElement> awayChancen;
// GameValuesInclPenalties<WebElement> homeBallPossession;
// GameValuesInclPenalties<WebElement> awayBallPossession;
// WebElement homeBallPossessionTotal;
// WebElement awayBallPossessionTotal;
// GameValuesInclPenalties<WebElement> homeZweikaempfe;
// GameValuesInclPenalties<WebElement> awayZweikaempfe;
// WebElement homeZweikaempfeTotal;
// WebElement awayZweikaempfeTotal;

}