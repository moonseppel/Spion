package ligamanager.spion.analysis.test;

import ligamanager.spion.common.GameResult;
import ligamanager.spion.common.LmGame;
import ligamanager.spion.understanding.understanding.FormationAndTacticsAnalysisResult;

import java.util.List;

public class FormationAndTacticsAnalysis {

    public static FormationAndTacticsAnalysisResult analyzeFirstHalf(List<LmGame> games) {
        FormationAndTacticsAnalysisResult ret = new FormationAndTacticsAnalysisResult();

        for(LmGame game : games) {
            int stronerStrenght, weakerStrength;
            boolean strongerTeamWonGame;

            GameResult firstHalfAverageStrength = game.strengthAverage.firstHalf;
            GameResult firstHalfResult = game.interimResults.firstHalf;

            if(firstHalfAverageStrength.getHome() >= firstHalfAverageStrength.getAway()) {
                stronerStrenght = firstHalfAverageStrength.getHome();
                weakerStrength = firstHalfAverageStrength.getAway();
                strongerTeamWonGame = firstHalfResult.getHome() > firstHalfResult.getAway();

            } else {
                weakerStrength = firstHalfAverageStrength.getHome();
                stronerStrenght = firstHalfAverageStrength.getAway();
                strongerTeamWonGame = firstHalfResult.getAway() > firstHalfResult.getHome();
            }

            ret.addGame(stronerStrenght - weakerStrength, strongerTeamWonGame);
        }

        return ret;
    }
}
