package ligamanager.spion.analysis.understanding;

import ligamanager.spion.analysis.test.FormationAndTacticsAnalysis;
import ligamanager.spion.common.LmGame;
import ligamanager.spion.analysis.test.AnalysisResultPrinter;
import ligamanager.spion.analysis.test.FindGamesForFormationAndTacticFirstHalf;
import ligamanager.spion.analysis.test.FormationAndTacticsAnalysisResult;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class FormationAndTacticsAnalysisComparisonTest {

//    private static final Logger LOGGER = Logger.getLogger(FormationAndTacticsAnalysisComparisonTest.class);

    @Test
    public void testDifferentIdeas() {
        StringWriter writer = new StringWriter();
        AnalysisResultPrinter printer = new AnalysisResultPrinter(new PrintWriter(writer));
        List<FormationAndTacticsAnalysisResult> results = new ArrayList<>();

        List<LmGame> games = FindGamesForFormationAndTacticFirstHalf.getGamesWithSameFormationAndTactic();
        FormationAndTacticsAnalysisResult result = FormationAndTacticsAnalysis.analyzeFirstHalf("same form. & tactic", games);

        printer.printWithDrawsAndLosses(result);

        results.add(result);

        games = FindGamesForFormationAndTacticFirstHalf.getGames352_6vs442_1();
        results.add(FormationAndTacticsAnalysis.analyzeFirstHalf("352-6any vs 442-1any", games));

        games = FindGamesForFormationAndTacticFirstHalf.getGames442_3vsAny();
        results.add(FormationAndTacticsAnalysis.analyzeFirstHalf("442-3any vs. any", games));

        games = FindGamesForFormationAndTacticFirstHalf.getGames442_3vs442_3();
        result = FormationAndTacticsAnalysis.analyzeFirstHalf("442-3any vs. 442-3any", games);

        printer.printWithDrawsAndLosses(result);

        results.add(result);

        games = FindGamesForFormationAndTacticFirstHalf.getGamesWith442_3AsWinningFormationWithout442_3AsOpposingFormation();
        results.add(FormationAndTacticsAnalysis.analyzeFirstHalf("442-3any vs !442-3", games));

//        games = FindGamesForFormationAndTacticFirstHalf.getGamesWith343_1AsWinningFormation();
//        results.add(FormationAndTacticsAnalysis.analyzeFirstHalf("343-1any vs any", games));

//        games = FindGamesForFormationAndTacticFirstHalf.getGamesWith442_3OffensiveAsWinningFormation();
//        results.add(FormationAndTacticsAnalysis.analyzeFirstHalf("442-3off vs any", games));

//        games = FindGamesForFormationAndTacticFirstHalf.getGamesWith442_3OffensiveAsWinningFormationWithout442_3AsOpposingFormation();
//        results.add(FormationAndTacticsAnalysis.analyzeFirstHalf("442-3off vs !442-3", games));

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
