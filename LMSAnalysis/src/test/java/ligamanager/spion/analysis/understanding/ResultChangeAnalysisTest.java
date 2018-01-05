package ligamanager.spion.analysis.understanding;

import ligamanager.spion.analysis.test.AnalysisResultPrinter;
import ligamanager.spion.analysis.test.FindGamesForFormationAndTacticFullTime;
import ligamanager.spion.analysis.test.FormationAndTacticsAnalysis;
import ligamanager.spion.analysis.test.ResultStateChangeAnalysisResult;
import ligamanager.spion.common.LmGame;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class ResultChangeAnalysisTest {

//    private static final Logger LOGGER = Logger.getLogger(FormationAndTacticsAnalysisComparisonTest.class);

    @Test
    public void testDifferentIdeas() {
        StringWriter writer = new StringWriter();
        AnalysisResultPrinter printer = new AnalysisResultPrinter(new PrintWriter(writer));

        List<LmGame> games = FindGamesForFormationAndTacticFullTime.getGamesWithSameFormationAndTactic();
        ResultStateChangeAnalysisResult result = FormationAndTacticsAnalysis.analyzeWinnerChangesFromHalftimeToFullTime(
                "game state changes in same vs same (full time) games", games);

        printer.print(result);

        String title = "===== All the tests =====";
        System.out.println(title);
        System.out.println(writer.toString());
    }


}
