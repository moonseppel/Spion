package ligamanager.spion.analysis.test;

import ligamanager.spion.common.GameResult;
import ligamanager.spion.common.GameValues;
import ligamanager.spion.common.GameValuesInclPenalties;
import ligamanager.spion.common.LmGame;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class FormationAndTacticAnalysisResultTest {

    private static final int BASE_TEAM_STRENGTH = 1000;

    @Test
    public void sortSomeResultsTest() {
        List<LmGame> gamesForAnalysis = new ArrayList<>();

        for (int strengthDiff = 0; strengthDiff < 50; strengthDiff++) {
            gamesForAnalysis.add(createSimpleSortableGameObjects(strengthDiff));
        }

        FormationAndTacticsAnalysisResult result = FormationAndTacticsAnalysis.analyzeFirstHalf("sortSomeResultsTest", gamesForAnalysis);

        for (WinsAndLossesCounter counter : result.getCounters()) {
            assertThat(counter.getWins(), is(5));
            assertThat(counter.getTotalGames(), is(5));
            assertThat(counter.getWinPercentage(), is(100.0));
        }

    }

    @Test
    public void gameWithTooHighStrengthDifferenceTest() {
        List<LmGame> gamesForAnalysis = new ArrayList<>();

        LmGame game = new LmGame();

        game.endResult = new GameResult(2, 0);
        GameResult firstHalfResult = new GameResult(1, 0);
        GameResult secondHalfHalfResult = new GameResult(2, 0);
        game.interimResults = new GameValuesInclPenalties<>(firstHalfResult, secondHalfHalfResult, GameResult.EMPTY, GameResult.EMPTY);

        GameResult firstHalfAverageStrength = new GameResult(BASE_TEAM_STRENGTH, BASE_TEAM_STRENGTH - 100);
        GameResult secondHalfAverageStrength = new GameResult(BASE_TEAM_STRENGTH, BASE_TEAM_STRENGTH - 100);
        game.strengthAverage = new GameValues<>(firstHalfAverageStrength, secondHalfAverageStrength, GameResult.EMPTY);

        gamesForAnalysis.add(game);

        FormationAndTacticsAnalysisResult result = FormationAndTacticsAnalysis.analyzeFirstHalf("gameWithTooHighStrengthDifferenceTest", gamesForAnalysis);

        for (WinsAndLossesCounter counter : result.getCounters()) {
            assertThat(counter.getWins(), is(0));
            assertThat(counter.getTotalGames(), is(0));
            assertThat(counter.getWinPercentage(), is(0.0));
        }

    }


    private LmGame createSimpleSortableGameObjects(int strengthDiff) {
        LmGame ret = new LmGame();

        ret.endResult = new GameResult(2, 0);
        GameResult firstHalfResult = new GameResult(1, 0);
        GameResult secondHalfHalfResult = new GameResult(2, 0);
        ret.interimResults = new GameValuesInclPenalties<>(firstHalfResult, secondHalfHalfResult, GameResult.EMPTY, GameResult.EMPTY);

        GameResult firstHalfAverageStrength = new GameResult(BASE_TEAM_STRENGTH, BASE_TEAM_STRENGTH - strengthDiff);
        GameResult secondHalfAverageStrength = new GameResult(BASE_TEAM_STRENGTH, BASE_TEAM_STRENGTH - strengthDiff);
        ret.strengthAverage = new GameValues<>(firstHalfAverageStrength, secondHalfAverageStrength, GameResult.EMPTY);

        return ret;
    }
}
