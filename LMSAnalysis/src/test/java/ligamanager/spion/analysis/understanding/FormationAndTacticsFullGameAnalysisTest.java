package ligamanager.spion.analysis.understanding;

import ligamanager.spion.analysis.test.FormationAndTacticsAnalysis;
import ligamanager.spion.common.LmGame;
import ligamanager.spion.analysis.test.AnalysisResultPrinter;
import ligamanager.spion.analysis.test.FindGamesForFormationAndTacticFirstHalf;
import ligamanager.spion.analysis.test.FindGamesForFormationAndTacticFullTime;
import ligamanager.spion.analysis.test.FormationAndTacticsAnalysisResult;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class FormationAndTacticsFullGameAnalysisTest {

//    private static final Logger LOGGER = Logger.getLogger(FormationAndTacticsAnalysisComparisonTest.class);

    @Test
    public void testDifferentIdeas() {
        StringWriter writer = new StringWriter();
        AnalysisResultPrinter printer = new AnalysisResultPrinter(new PrintWriter(writer));
        List<FormationAndTacticsAnalysisResult> results = new ArrayList<>();

        List<LmGame> games = FindGamesForFormationAndTacticFirstHalf.getGamesWithSameFormationAndTactic();
        FormationAndTacticsAnalysisResult result = FormationAndTacticsAnalysis.analyzeFirstHalf("same form. & tactic", games);
        results.add(result);
        printer.printWithDrawsAndLosses(result);


        games = FindGamesForFormationAndTacticFullTime.getGamesWithSameFormationAndTactic();

        result = FormationAndTacticsAnalysis.analyzeFullGameByFirstHalfStrength("same full time", games);
        results.add(result);
        printer.printWithDrawsAndLosses(result);

        games = FindGamesForFormationAndTacticFullTime.getGames442_3offvs451_2sd();
        result = FormationAndTacticsAnalysis.analyzeFullGameByFirstHalfStrength("442-3 vs 451-2", games);
        results.add(result);
        printer.printWithDrawsAndLosses(result);

        games = FindGamesForFormationAndTacticFullTime.getGames451_2sdvs442_3off();
        result = FormationAndTacticsAnalysis.analyzeFullGameByFirstHalfStrength("451-2 vs 442-3", games);
        results.add(result);
        printer.printWithDrawsAndLosses(result);

//        games = FindGamesForFormationAndTacticFirstHalf.getAllGames();
//        results.add(FormationAndTacticsAnalysis.analyzeFirstHalf("all games", games));

//        FindGamesForFormationAndTacticFirstHalf.getGamesWithDifferentFormationAndTactic();
//        results.add(FormationAndTacticsAnalysis.analyzeFirstHalf("different from & tac", games));

        printer.print(results);

        String title = "===== All the tests =====";
        System.out.println(title);
        System.out.println(writer.toString());
    }


}
