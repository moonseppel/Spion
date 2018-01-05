package ligamanager.spion.analysis.test;

import java.util.ArrayList;
import java.util.List;

public class ResultStateChangeAnalysisResult {

    private String shortName = "[no name]";
    private List<ResultStateChangeCounter> counters;


    public ResultStateChangeAnalysisResult(String shortName) {

        this.shortName = shortName;

        counters = new ArrayList<>();

        for(int i = 0; i < 50; i = i+5) {
            ResultStateChangeCounter counter = new ResultStateChangeCounter(i, i+4);
            this.counters.add(counter);
        }
    }

    public void addGame(int strengthDiff, GameResultState startState, GameResultState endState) {
        int strengthDiffAbsolute = Math.abs(strengthDiff);

        ResultStateChangeCounter counter = getGameCounterFor(strengthDiffAbsolute);
        if(counter != null) {

            switch (startState) {
                case WIN:
                    switch (endState) {
                        case WIN:
                            counter.countPersistentWin();
                            break;
                        case DRAW:
                            counter.countWinToDraw();
                            break;
                        case LOSS:
                            counter.countWinToLoss();
                            break;
                        default:
                            //don't count.
                    }
                    break;
                case DRAW:
                    switch (endState) {
                        case WIN:
                            counter.countDrawToWin();
                            break;
                        case DRAW:
                            counter.countPersistentDraw();
                            break;
                        case LOSS:
                            counter.countDrawToLoss();
                            break;
                        default:
                            //don't count.
                    }
                    break;
                case LOSS:
                    switch (endState) {
                        case WIN:
                            counter.countLossToWin();
                            break;
                        case DRAW:
                            counter.countLossToDraw();
                            break;
                        case LOSS:
                            counter.countPersistentLoss();
                            break;
                        default:
                            //don't count.
                    }
                    break;
                default:
                    //don't count.
            }

        }
    }

    public List<ResultStateChangeCounter> getCounters() {
        return this.counters;
    }

    public String getShortName() {
        return shortName;
    }

    private ResultStateChangeCounter getGameCounterFor(int strengthDiff) {
        for(ResultStateChangeCounter counter : this.counters) {
            if(counter.matchesStrength(strengthDiff)) {
                return counter;
            }
        }

        return null;
    }
}
