package ligamanager.spion.analyzer.pages;

import ligamanager.spion.analyzer.TestData;
import ligamanager.spion.analyzer.useCases.BasicActions;
import ligamanager.spion.analyzer.util.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
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
     * Tested game: http://www.liga-manager.de/inc/spiel_info.php?id=1&show_saison=122
     */
    @Test
    public void testGetGameDataForNormalGame() throws Exception {

        int expectedGameId = 1;
        LmGamePage subject = new LmGamePage(expectedGameId, 122);
        subject.navigateToPageAndCheck();

        assertAllGameValues(expectedGameId, subject);
        assertFalse(subject.hasExtraTime());
        assertFalse(subject.hasPenyltyShooting());

        assertEquals("Pauli Pirates", subject.getHomeTeamName());
        assertEquals("1. FC Magdeburg", subject.getAwayTeamName());
        assertEquals(122, subject.getSeasonNumber());
        assertEquals(new GameResult(4,1), subject.getEndResult());

        assertEquals(GameFormation.FORMATION_442_2, subject.getHomeFormations().firstHalf);
        assertEquals(GameFormation.FORMATION_442_2, subject.getHomeFormations().secondHalf);
        assertEquals(GameFormation.EMPTY, subject.getHomeFormations().extraTime);
        assertEquals(GameFormation.FORMATION_442_3, subject.getAwayFormations().firstHalf);
        assertEquals(GameFormation.FORMATION_442_3, subject.getAwayFormations().secondHalf);
        assertEquals(GameFormation.EMPTY, subject.getAwayFormations().extraTime);

        assertEquals(Tactic.VERY_OFFENSIVE, subject.getHomeTactics().firstHalf);
        assertEquals(Tactic.VERY_OFFENSIVE, subject.getHomeTactics().secondHalf);
        assertEquals(Tactic.EMPTY, subject.getHomeTactics().extraTime);
        assertEquals(Tactic.VERY_OFFENSIVE, subject.getAwayTactics().firstHalf);
        assertEquals(Tactic.VERY_OFFENSIVE, subject.getAwayTactics().secondHalf);
        assertEquals(Tactic.EMPTY, subject.getAwayTactics().extraTime);

        assertEquals(new GameResult(1124,1093), subject.getStrengthsAverageOfHalfs().firstHalf);
        assertEquals(new GameResult(1078,1053), subject.getStrengthsAverageOfHalfs().secondHalf);
    }

    /**
     * Tested game: http://www.liga-manager.de/inc/spiel_info.php?id=280152&show_saison=122
     */
    @Test
    public void testGetGameDataForGameWithExtraTime() throws Exception {

        int expectedGameId = 280152;
        LmGamePage subject = new LmGamePage(expectedGameId, 122);
        subject.navigateToPageAndCheck();

        assertAllGameValues(expectedGameId, subject);
        assertTrue(subject.hasExtraTime());
        assertFalse(subject.hasPenyltyShooting());

        assertEquals("SV Tündern 05", subject.getHomeTeamName());
        assertEquals("SV Donaumoos", subject.getAwayTeamName());
        assertEquals(122, subject.getSeasonNumber());
        assertEquals(new GameResult(0,1), subject.getEndResult());

        assertEquals(GameFormation.FORMATION_343_9, subject.getHomeFormations().firstHalf);
        assertEquals(GameFormation.FORMATION_343_9, subject.getHomeFormations().secondHalf);
        assertEquals(GameFormation.FORMATION_343_9, subject.getHomeFormations().extraTime);
        assertEquals(GameFormation.FORMATION_442_3, subject.getAwayFormations().firstHalf);
        assertEquals(GameFormation.FORMATION_442_3, subject.getAwayFormations().secondHalf);
        assertEquals(GameFormation.FORMATION_442_3, subject.getAwayFormations().extraTime);

        assertEquals(Tactic.VERY_OFFENSIVE, subject.getHomeTactics().firstHalf);
        assertEquals(Tactic.VERY_OFFENSIVE, subject.getHomeTactics().secondHalf);
        assertEquals(Tactic.VERY_OFFENSIVE, subject.getHomeTactics().extraTime);
        assertEquals(Tactic.OFFENSIVE, subject.getAwayTactics().firstHalf);
        assertEquals(Tactic.OFFENSIVE, subject.getAwayTactics().secondHalf);
        assertEquals(Tactic.OFFENSIVE, subject.getAwayTactics().extraTime);

        assertEquals(new GameResult(1115,1071), subject.getStrengthsAverageOfHalfs().firstHalf);
        assertEquals(new GameResult(1083,1039), subject.getStrengthsAverageOfHalfs().secondHalf);
        assertEquals(new GameResult(1020,986), subject.getStrengthsAverageOfHalfs().extraTime);

        assertEquals(new GameResult(1122,1079), subject.getStrengthsBeginOfHalfs().firstHalf);
        assertEquals(new GameResult(1102,1064), subject.getStrengthsBeginOfHalfs().secondHalf);
        assertEquals(new GameResult(1054,1004), subject.getStrengthsBeginOfHalfs().extraTime);

        assertEquals(new GameResult(1101,1054), subject.getStrengthsEndOfHalfs().firstHalf);
        assertEquals(new GameResult(1054,1004), subject.getStrengthsEndOfHalfs().secondHalf);
        assertEquals(new GameResult(917,953), subject.getStrengthsEndOfHalfs().extraTime);
    }

    /**
     * Tested game: http://www.liga-manager.de/inc/spiel_info.php?id=280155&show_saison=122
     */
    @Test
    public void testGetGameDataForGameWithExtraTimeAndPenaltyShooting() throws Exception {

        int expectedGameId = 280155;
        LmGamePage subject = new LmGamePage(expectedGameId, 122);
        subject.navigateToPageAndCheck();

        assertAllGameValues(expectedGameId, subject);
        assertTrue(subject.hasExtraTime());
        assertTrue(subject.hasPenyltyShooting());

        assertEquals("⚜ Behar Reutlingen ⚜", subject.getHomeTeamName());
        assertEquals("Der Club", subject.getAwayTeamName());
        assertEquals(122, subject.getSeasonNumber());
        assertEquals(new GameResult(8,7), subject.getEndResult());

        assertEquals(GameFormation.FORMATION_442_1, subject.getHomeFormations().firstHalf);
        assertEquals(GameFormation.FORMATION_442_1, subject.getHomeFormations().secondHalf);
        assertEquals(GameFormation.FORMATION_442_1, subject.getHomeFormations().extraTime);
        assertEquals(GameFormation.FORMATION_442_2, subject.getAwayFormations().firstHalf);
        assertEquals(GameFormation.FORMATION_352_4, subject.getAwayFormations().secondHalf);
        assertEquals(GameFormation.FORMATION_352_4, subject.getAwayFormations().extraTime);

        assertEquals(Tactic.VERY_OFFENSIVE, subject.getHomeTactics().firstHalf);
        assertEquals(Tactic.VERY_OFFENSIVE, subject.getHomeTactics().secondHalf);
        assertEquals(Tactic.VERY_OFFENSIVE, subject.getHomeTactics().extraTime);
        assertEquals(Tactic.OFFENSIVE, subject.getAwayTactics().firstHalf);
        assertEquals(Tactic.OFFENSIVE, subject.getAwayTactics().secondHalf);
        assertEquals(Tactic.OFFENSIVE, subject.getAwayTactics().extraTime);

        assertEquals(new GameResult(1099,1086), subject.getStrengthsAverageOfHalfs().firstHalf);
        assertEquals(new GameResult(1094,1096), subject.getStrengthsAverageOfHalfs().secondHalf);
        assertEquals(new GameResult(1074,1099), subject.getStrengthsAverageOfHalfs().extraTime);
    }

    /**
     * Tested game: http://www.liga-manager.de/inc/spiel_info.php?id=34193&show_saison=124
     */
    @Test
    public void testGetGameDataForGameWithChangingTactic() throws Exception {

        int expectedGameId = 34193;
        LmGamePage subject = new LmGamePage(expectedGameId, 124);
        subject.navigateToPageAndCheck();

        assertAllGameValues(expectedGameId, subject);
        assertFalse(subject.hasExtraTime());
        assertFalse(subject.hasPenyltyShooting());

        assertEquals("Baile Átha Cliath Clampar", subject.getHomeTeamName());
        assertEquals("Dublin City", subject.getAwayTeamName());
        assertEquals(124, subject.getSeasonNumber());
        assertEquals(new GameResult(1,3), subject.getEndResult());

        assertEquals(GameFormation.FORMATION_352_5, subject.getHomeFormations().firstHalf);
        assertEquals(GameFormation.FORMATION_352_5, subject.getHomeFormations().secondHalf);
        assertEquals(GameFormation.EMPTY, subject.getHomeFormations().extraTime);
        assertEquals(GameFormation.FORMATION_352_6, subject.getAwayFormations().firstHalf);
        assertEquals(GameFormation.FORMATION_352_5, subject.getAwayFormations().secondHalf);
        assertEquals(GameFormation.EMPTY, subject.getAwayFormations().extraTime);

        assertEquals(Tactic.OFFENSIVE, subject.getHomeTactics().firstHalf);
        assertEquals(Tactic.OFFENSIVE, subject.getHomeTactics().secondHalf);
        assertEquals(Tactic.EMPTY, subject.getHomeTactics().extraTime);
        assertEquals(Tactic.VERY_DEFENSIVE, subject.getAwayTactics().firstHalf);
        assertEquals(Tactic.OFFENSIVE, subject.getAwayTactics().secondHalf);
        assertEquals(Tactic.EMPTY, subject.getAwayTactics().extraTime);
    }

    /**
     * Tested game: http://www.liga-manager.de/inc/spiel_info.php?id=204602&show_saison=124
     */
    @Test
    public void testAmateurGame() throws LmIllegalPageException {

        int expectedGameId = 204602;
        LmGamePage subject = new LmGamePage(expectedGameId, 124);

        try {
        subject.navigateToPageAndCheck();

        } catch (LmIllegalGameException ex) {

            assertEquals(204602, ex.getGameId());
            assertEquals(124, ex.getSeasonNumber());
            assertEquals(IllegalGameType.AmateuerGame, ex.getGameType());
        }
    }

    /**
     * Tested game: http://www.liga-manager.de/inc/spiel_info.php?id=1000000&show_saison=124
     */
    @Test
    public void testNotExistingGame() throws LmIllegalPageException {

        int expectedGameId = 1000000;
        LmGamePage subject = new LmGamePage(expectedGameId, 124);

        try {
            subject.navigateToPageAndCheck();

        } catch (LmIllegalGameException ex) {

            assertEquals(1000000, ex.getGameId());
            assertEquals(124, ex.getSeasonNumber());
            assertEquals(IllegalGameType.NoGame, ex.getGameType());
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