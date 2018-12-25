package ligamanager.spion.analysis.test;

import ligamanager.spion.common.GameResult;
import ligamanager.spion.common.GameValues;
import ligamanager.spion.common.GameValuesInclPenalties;
import ligamanager.spion.common.LmGame;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class AnalysisResultPrinterTest {

    private static final Logger LOGGER = Logger.getLogger(AnalysisResultPrinterTest.class);

    private static final int BASE_TEAM_STRENGTH = 1000;

    @Test
    public void printSomeResultsTest() {

        String expected =
                "StrDiff | Win%  | Total Games | Wins    \n" +
                        "--------|-------|-------------|---------\n" +
                        "  0-  4 | 100,0 |           5 |       5 \n" +
                        "  5-  9 | 100,0 |           5 |       5 \n" +
                        " 10- 14 | 100,0 |           5 |       5 \n" +
                        " 15- 19 | 100,0 |           5 |       5 \n" +
                        " 20- 24 | 100,0 |           5 |       5 \n" +
                        " 25- 29 | 100,0 |           5 |       5 \n" +
                        " 30- 34 | 100,0 |           5 |       5 \n" +
                        " 35- 39 | 100,0 |           5 |       5 \n" +
                        " 40- 44 | 100,0 |           5 |       5 \n" +
                        " 45- 49 | 100,0 |           5 |       5 \n";

        List<LmGame> gamesForAnalysis = new ArrayList<>();

        for (int strengthDiff = 0; strengthDiff < 50; strengthDiff++) {
            gamesForAnalysis.add(createSimpleSortableGameObjects(strengthDiff));
        }

        FormationAndTacticsAnalysisResult result = FormationAndTacticsAnalysis.analyzeFirstHalf("printSomeResultsTest", gamesForAnalysis);

        for (WinsAndLossesCounter counter : result.getCounters()) {
            assertThat(counter.getWins(), is(5));
            assertThat(counter.getTotalGames(), is(5));
            assertThat(counter.getWinPercentage(), is(100.0));
        }

        StringWriter writer = new StringWriter();
        AnalysisResultPrinter printer = new AnalysisResultPrinter(new PrintWriter(writer));
        printer.print(result);

        LOGGER.info(writer.toString());

        assertThat(writer.toString(), is(expected));
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
