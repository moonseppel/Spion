package ligamanager.spion.analysis.test;

import ligamanager.spion.common.GameResult;
import ligamanager.spion.common.LmGame;

import java.util.List;

public class FormationAndTacticsAnalysis {

    public static FormationAndTacticsAnalysisResult analyzeFirstHalf(String shortName, List<LmGame> games) {
        FormationAndTacticsAnalysisResult ret = new FormationAndTacticsAnalysisResult(shortName);

        for (LmGame game : games) {

            GameResult firstHalfAverageStrength = game.strengthAverage.firstHalf;
            GameResult firstHalfResult = game.interimResults.firstHalf;

            GameResultState halfTimeResultState = analyzeGame(firstHalfAverageStrength, firstHalfResult);

            int strengthDifference = Math.abs(firstHalfAverageStrength.getHome() - firstHalfAverageStrength.getAway());
            ret.addGame(strengthDifference, halfTimeResultState);
        }

        return ret;
    }

    public static FormationAndTacticsAnalysisResult analyzeFullGameByFirstHalfStrength(String shortName, List<LmGame> games) {
        FormationAndTacticsAnalysisResult ret = new FormationAndTacticsAnalysisResult(shortName);

        for (LmGame game : games) {

            GameResult firstHalfAverageStrength = game.strengthAverage.firstHalf;
            GameResult endResult = game.endResult;

            GameResultState endResultState = analyzeGame(firstHalfAverageStrength, endResult);

            int strengthDifference = Math.abs(firstHalfAverageStrength.getHome() - firstHalfAverageStrength.getAway());
            ret.addGame(strengthDifference, endResultState);
        }

        return ret;
    }

    public static ResultStateChangeAnalysisResult analyzeWinnerChangesFromHalftimeToFullTime(String shortName, List<LmGame> games) {
        ResultStateChangeAnalysisResult ret = new ResultStateChangeAnalysisResult(shortName);

        for (LmGame game : games) {

            GameResult firstHalfAverageStrength = game.strengthAverage.firstHalf;
            GameResult halfTimeResult = game.interimResults.firstHalf;
            GameResult endResult = game.endResult;

            GameResultState halfTimeResultState = analyzeGame(firstHalfAverageStrength, halfTimeResult);
            GameResultState endResultState = analyzeGame(firstHalfAverageStrength, endResult);

            int strengthDifference = Math.abs(firstHalfAverageStrength.getHome() - firstHalfAverageStrength.getAway());
            ret.addGame(strengthDifference, halfTimeResultState, endResultState);
        }

        return ret;
    }

    private static GameResultState analyzeGame(GameResult strengths, GameResult gameResult) {

        GameResultState gameResultState = GameResultState.DRAW;
        int strongerStrenght;
        int weakerStrength;

        if (strengths.getHome() >= strengths.getAway()) {
            strongerStrenght = strengths.getHome();
            weakerStrength = strengths.getAway();

            if (gameResult.getHome() > gameResult.getAway()) {
                gameResultState = GameResultState.WIN;
            } else if (gameResult.getHome() < gameResult.getAway()) {
                gameResultState = GameResultState.LOSS;
            }

        } else {
            weakerStrength = strengths.getHome();
            strongerStrenght = strengths.getAway();

            if (gameResult.getAway() > gameResult.getHome()) {
                gameResultState = GameResultState.WIN;
            } else if (gameResult.getAway() < gameResult.getHome()) {
                gameResultState = GameResultState.LOSS;
            }
        }

        return gameResultState;
    }
}
