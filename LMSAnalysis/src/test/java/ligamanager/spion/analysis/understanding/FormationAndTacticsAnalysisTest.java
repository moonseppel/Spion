package ligamanager.spion.analysis.understanding;

import ligamanager.spion.analysis.test.FormationAndTacticsAnalysis;
import ligamanager.spion.common.LmGame;
import ligamanager.spion.analysis.test.AnalysisResultPrinter;
import ligamanager.spion.analysis.test.FindGamesForFormationAndTacticFirstHalf;
import ligamanager.spion.analysis.test.FormationAndTacticsAnalysisResult;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

public class FormationAndTacticsAnalysisTest {

    private static final Logger LOGGER = Logger.getLogger(FormationAndTacticsAnalysisTest.class);

    @Test
    public void testSameFormationAndTactic() {
        StringWriter writer = new StringWriter();
        List<LmGame> games = FindGamesForFormationAndTacticFirstHalf.getGamesWithSameFormationAndTactic();
        FormationAndTacticsAnalysisResult ret = FormationAndTacticsAnalysis.analyzeFirstHalf("same form. & tactic", games);

        AnalysisResultPrinter subject = new AnalysisResultPrinter(new PrintWriter(writer));
        subject.print(ret);

        String title = "Test for games with same formation and tactic in first half, halftime result";
        System.out.println(title);
        System.out.println(writer.toString());
    }

    @Test
    public void testAll442_3Games() {
        StringWriter writer = new StringWriter();
        List<LmGame> games = FindGamesForFormationAndTacticFirstHalf.getGames442_3vsAny();
        FormationAndTacticsAnalysisResult ret = FormationAndTacticsAnalysis.analyzeFirstHalf("442-3any vs. any", games);

        AnalysisResultPrinter subject = new AnalysisResultPrinter(new PrintWriter(writer));
        subject.print(ret);

        String title = "Test for games with 442-3 as winning formation, halftime result";
        System.out.println(title);
        System.out.println(writer.toString());
    }

    @Test
    public void testAll442_3GamesWithout442_3AsOpposingFormation() {
        StringWriter writer = new StringWriter();
        List<LmGame> games = FindGamesForFormationAndTacticFirstHalf.getGamesWith442_3AsWinningFormationWithout442_3AsOpposingFormation();
        FormationAndTacticsAnalysisResult ret = FormationAndTacticsAnalysis.analyzeFirstHalf("442-3any vs !442-3", games);

        AnalysisResultPrinter subject = new AnalysisResultPrinter(new PrintWriter(writer));
        subject.print(ret);

        String title = "Test for games with 442-3 as winning formation without games where opponent does not play 442-3, halftime result";
        System.out.println(title);
        System.out.println(writer.toString());
    }

    @Test
    public void testAll442_3OffGames() {
        StringWriter writer = new StringWriter();
        List<LmGame> games = FindGamesForFormationAndTacticFirstHalf.getGamesWith442_3OffensiveAsWinningFormation();
        FormationAndTacticsAnalysisResult ret = FormationAndTacticsAnalysis.analyzeFirstHalf("442-3off vs any", games);

        AnalysisResultPrinter subject = new AnalysisResultPrinter(new PrintWriter(writer));
        subject.print(ret);

        String title = "Test for games with 442-3 offensive as winning formation, halftime result";
        System.out.println(title);
        System.out.println(writer.toString());
    }

    @Test
    public void testAll442_3OffGamesWithout442_3AsOpposingFormation() {
        StringWriter writer = new StringWriter();
        List<LmGame> games = FindGamesForFormationAndTacticFirstHalf.getGamesWith442_3OffensiveAsWinningFormationWithout442_3AsOpposingFormation();
        FormationAndTacticsAnalysisResult ret = FormationAndTacticsAnalysis.analyzeFirstHalf("442-3off vs !442-3", games);

        AnalysisResultPrinter subject = new AnalysisResultPrinter(new PrintWriter(writer));
        subject.print(ret);

        String title = "Test for games with 442-3 offensive as winning formation without games where opponent does not play 442-3, halftime result";
        System.out.println(title);
        System.out.println(writer.toString());
    }

    @Test
    @Ignore("This test is just for verification of the basic assumptions")
    public void testEveryGame() {
        StringWriter writer = new StringWriter();
        List<LmGame> games = FindGamesForFormationAndTacticFirstHalf.getAllGames();
        FormationAndTacticsAnalysisResult ret = FormationAndTacticsAnalysis.analyzeFirstHalf("all games", games);

        AnalysisResultPrinter subject = new AnalysisResultPrinter(new PrintWriter(writer));
        subject.print(ret);

        String title = "Test for all games, halftime result";
        System.out.println(title);
        System.out.println(writer.toString());
    }

    @Test
    @Ignore("This test is just for verification of the basic assumptions")
    public void testDifferentFormationAndTactic() {
        StringWriter writer = new StringWriter();
        List<LmGame> games = FindGamesForFormationAndTacticFirstHalf.getGamesWithDifferentFormationAndTactic();
        FormationAndTacticsAnalysisResult ret = FormationAndTacticsAnalysis.analyzeFirstHalf("different from & tac", games);

        AnalysisResultPrinter subject = new AnalysisResultPrinter(new PrintWriter(writer));
        subject.print(ret);

        String title = "Test for games with different formation and tactic, halftime result";
        System.out.println(title);
        System.out.println(writer.toString());
    }


}
