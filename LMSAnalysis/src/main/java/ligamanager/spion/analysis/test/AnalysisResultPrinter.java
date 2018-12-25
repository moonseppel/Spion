package ligamanager.spion.analysis.test;

import java.io.PrintWriter;
import java.util.List;

public class AnalysisResultPrinter {

    private final PrintWriter writer;

    public AnalysisResultPrinter(PrintWriter writer) {
        this.writer = writer;
    }

    public void print(FormationAndTacticsAnalysisResult analysisResult) {
        writer.write("\n");
        writer.write("Short name of analysis: " + analysisResult.getShortName() + "\n");
        writer.write("StrDiff | Win%  | Total Games | Wins    \n" +
                "--------|-------|-------------|---------\n");


        for (WinsAndLossesCounter counter : analysisResult.getCounters()) {

            writer.write(String.format("%1$3s", counter.getMinStrength()));
            writer.write("-");
            writer.write(String.format("%1$3s", counter.getMaxStrength()));

            writer.write(" | ");

            String winPercentage = String.format("%3.1f", counter.getWinPercentage());
            writer.write(String.format("%1$5s", winPercentage));

            writer.write(" | ");

            writer.write(String.format("%1$11s", counter.getTotalGames()));

            writer.write(" | ");

            writer.write(String.format("%1$7s", counter.getWins()));

            writer.write(" \n");
        }

    }

    public void printWithDrawsAndLosses(FormationAndTacticsAnalysisResult analysisResult) {
        writer.write("\n");
        writer.write("Short name of analysis: " + analysisResult.getShortName() + "\n");
        writer.write("StrDiff | Win%  | Draw% | Lost% | Total Games\n" +
                "--------|-------|-------|-------|------------\n");


        for (WinsAndLossesCounter counter : analysisResult.getCounters()) {

            writer.write(String.format("%1$3s", counter.getMinStrength()));
            writer.write("-");
            writer.write(String.format("%1$3s", counter.getMaxStrength()));

            writer.write(" | ");

            String winPercentage = String.format("%3.1f", counter.getWinPercentage());
            writer.write(String.format("%1$5s", winPercentage));

            writer.write(" | ");

            String drawPercentage = String.format("%3.1f", counter.getDrawPercentage());
            writer.write(String.format("%1$5s", drawPercentage));

            writer.write(" | ");

            String lossPercentage = String.format("%3.1f", counter.getLossPercentage());
            writer.write(String.format("%1$5s", lossPercentage));

            writer.write(" | ");

            writer.write(String.format("%1$11s", counter.getTotalGames()));

            writer.write(" \n");
        }

    }


    public void print(List<FormationAndTacticsAnalysisResult> results) {
        writer.write("\n");

        if (results == null || results.size() < 1) {
            writer.write("*** ERROR: No results provided ***\n");
        }

        writer.write("StrDiff");

        for (FormationAndTacticsAnalysisResult result : results) {
            writer.write(" | ");
            writer.write(String.format("%1$20s", result.getShortName()));
        }

        writer.write("\n");

        List<WinsAndLossesCounter> referenceCounters = results.get(0).getCounters();

        for (int i = 0; i < referenceCounters.size(); i++) {

            WinsAndLossesCounter referenceCounter = referenceCounters.get(i);

            writer.write(String.format("%1$3s", referenceCounter.getMinStrength()));
            writer.write("-");
            writer.write(String.format("%1$3s", referenceCounter.getMaxStrength()));

            for (FormationAndTacticsAnalysisResult result : results) {

                writer.write(" | ");

                WinsAndLossesCounter counter = result.getCounters().get(i);

                if ((counter.getMinStrength() != referenceCounter.getMinStrength())
                        || counter.getMaxStrength() != referenceCounter.getMaxStrength()) {

                    writer.write(String.format("%1$20s", "ERROR: wrong StrDiff"));

                } else {

                    writer.write("    ");

                    String winPercentage = String.format("%3.1f", counter.getWinPercentage());
                    writer.write(String.format("%1$5s", winPercentage));

                    if (counter.getTotalGames() < 2000) {
                        writer.write(" (" + String.format("%1$4s", counter.getTotalGames()) + ")");
                    } else {
                        writer.write("       ");
                    }

                    writer.write("    ");
                }

            }

            writer.write("\n");
        }

        writer.write("\n");
        writer.write("Number in parenthesis shows total number of games for this category if the number is lower than 2000.\n");

    }

    public void print(ResultStateChangeAnalysisResult result) {
        writer.write("\n");
        writer.write("Short name of analysis: " + result.getShortName() + "\n");
        writer.write("StrDiff |  WinToWin  | WinToDraw  | WinToLoss  | DrawToWin  | DrawToDraw | DrawToLoss | LossToWin  | LossToDraw | LossToLoss | Total Games\n" +
                "--------|------------|------------|------------|------------|------------|------------|------------|------------|------------|------------\n");


        for (ResultStateChangeCounter counter : result.getCounters()) {

            writer.write(String.format("%1$3s", counter.getMinStrength()));
            writer.write("-");
            writer.write(String.format("%1$3s", counter.getMaxStrength()));

            writer.write(" | ");

            String percentage = String.format("%3.1f", counter.getPersistentWinPercentage());
            writer.write(String.format("%1$10s", percentage));

            writer.write(" | ");

            percentage = String.format("%3.1f", counter.getWinToDrawPercentage());
            writer.write(String.format("%1$10s", percentage));

            writer.write(" | ");

            percentage = String.format("%3.1f", counter.getWinToLossPercentage());
            writer.write(String.format("%1$10s", percentage));

            writer.write(" | ");

            percentage = String.format("%3.1f", counter.getDrawToWinPercentage());
            writer.write(String.format("%1$10s", percentage));

            writer.write(" | ");

            percentage = String.format("%3.1f", counter.getPersistentDrawPercentage());
            writer.write(String.format("%1$10s", percentage));

            writer.write(" | ");

            percentage = String.format("%3.1f", counter.getDrawToLossPercentage());
            writer.write(String.format("%1$10s", percentage));

            writer.write(" | ");

            percentage = String.format("%3.1f", counter.getLossToWinPercentage());
            writer.write(String.format("%1$10s", percentage));

            writer.write(" | ");

            percentage = String.format("%3.1f", counter.getLossToDrawPercentage());
            writer.write(String.format("%1$10s", percentage));

            writer.write(" | ");

            percentage = String.format("%3.1f", counter.getPersistentLossPercentage());
            writer.write(String.format("%1$10s", percentage));

            writer.write(" | ");

            writer.write(String.format("%1$11s", counter.getTotalGames()));

            writer.write(" \n");
        }

    }
}
