package ligamanager.spion.analysis.test;

import ligamanager.spion.analysis.understanding.FormationAndTacticsAnalysisTest;
import ligamanager.spion.common.GameResult;
import ligamanager.spion.common.GameValues;
import ligamanager.spion.common.GameValuesInclPenalties;
import ligamanager.spion.common.LmGame;
import ligamanager.spion.understanding.understanding.FormationAndTacticsAnalysisResult;
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

        for(int strengthDiff = 0; strengthDiff <= 20; strengthDiff = strengthDiff+5) {
            gamesForAnalysis.add(creaTeSortableGame(strengthDiff));
        }

        FormationAndTacticsAnalysisResult result = FormationAndTacticsAnalysis.analyzeFirstHalf(gamesForAnalysis);

        assertThat(result.getWinPercentageStrengthDifference0(), is(100.0));
        assertThat(result.getWinPercentageStrengthDifference5(), is(100.0));
        assertThat(result.getWinPercentageStrengthDifference10(), is(100.0));
        assertThat(result.getWinPercentageStrengthDifference15(), is(100.0));
        assertThat(result.getWinPercentageStrengthDifference20(), is(100.0));

        assertThat(result.getGameCountStrengthDifference0(), is(1));
        assertThat(result.getGameCountStrengthDifference5(), is(1));
        assertThat(result.getGameCountStrengthDifference10(), is(1));
        assertThat(result.getGameCountStrengthDifference15(), is(1));
        assertThat(result.getGameCountStrengthDifference20(), is(1));
    }

    private LmGame creaTeSortableGame(int strengthDiff) {
        LmGame ret = new LmGame();

        ret.endResult = new GameResult(2, 0);
        GameResult firstHalfResult = new GameResult(1, 0);
        GameResult secondHalfHalfResult = new GameResult(2, 0);
        GameResult extraTimeResult = new GameResult(-1, -1);
        GameResult penaltyResult = new GameResult(-1, -1);
        ret.interimResults = new GameValuesInclPenalties<>(firstHalfResult, secondHalfHalfResult, extraTimeResult, penaltyResult);

        GameResult firstHalfAverageStrength = new GameResult(BASE_TEAM_STRENGTH, BASE_TEAM_STRENGTH+strengthDiff);
        GameResult secondHalfAverageStrength = new GameResult(BASE_TEAM_STRENGTH, BASE_TEAM_STRENGTH+strengthDiff);
        GameResult extraTimeAverageStrength = new GameResult(-1 , -1);
        ret.strengthAverage = new GameValues<>(firstHalfAverageStrength, secondHalfAverageStrength, extraTimeAverageStrength);

        return ret;
    }
}
