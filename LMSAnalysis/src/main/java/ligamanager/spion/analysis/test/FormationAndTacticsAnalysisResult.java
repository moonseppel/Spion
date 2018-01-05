package ligamanager.spion.analysis.test;

import java.util.ArrayList;
import java.util.List;

public class FormationAndTacticsAnalysisResult {

    private static int STRENGTH_STEP_SIZE = 5;

    private String shortName = "[no name]";
    private List<WinsAndLossesCounter> counters;


    public FormationAndTacticsAnalysisResult(String shortName) {

        this.shortName = shortName;

        counters = new ArrayList<>();

        for(int i = 0; i < 50; i = i+STRENGTH_STEP_SIZE) {
            WinsAndLossesCounter counter = new WinsAndLossesCounter(i, i+STRENGTH_STEP_SIZE-1);
            this.counters.add(counter);
        }
    }

    public void addGame(int strengthDiff, GameResultState state) {
        int strengthDiffAbsolute = Math.abs(strengthDiff);

        WinsAndLossesCounter counter = getGameCounterFor(strengthDiffAbsolute);
        if(counter != null){

            switch(state) {
                case WIN:
                    counter.countWin();
                    break;
                case DRAW:
                    counter.countDraw();
                    break;
                case LOSS:
                    counter.countLoss();
                    break;
                default:
                    //don't count.
            }
        }
    }

    public List<WinsAndLossesCounter> getCounters() {
        return this.counters;
    }

    public String getShortName() {
        return shortName;
    }

    private WinsAndLossesCounter getGameCounterFor(int strengthDiff) {
        for(WinsAndLossesCounter counter : this.counters) {
            if(counter.matchesStrength(strengthDiff)) {
                return counter;
            }
        }

        return null;
    }
}
