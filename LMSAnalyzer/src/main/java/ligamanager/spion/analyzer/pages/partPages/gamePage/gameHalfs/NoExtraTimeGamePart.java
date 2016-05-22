package ligamanager.spion.analyzer.pages.partPages.gamePage.gameHalfs;

import ligamanager.spion.analyzer.util.GameFormation;
import ligamanager.spion.analyzer.util.GameValues;
import ligamanager.spion.analyzer.util.Tactic;
import org.openqa.selenium.WebDriver;

/**
 * Created by jpralle on 22.05.2016.
 */
public class NoExtraTimeGamePart extends HalfGamePagePart {

    private static final String xpathToFirstHalfElement = null;

    public NoExtraTimeGamePart(WebDriver driver) {
        super(driver, xpathToFirstHalfElement);
    }

    @Override
    public void parseValues() {
        halfsHomeFormation = GameFormation.EMPTY;
        halfsAwayFormation = GameFormation.EMPTY;
        halfsHomeTactic = Tactic.EMPTY;
        halfsAwayTactic = Tactic.EMPTY;
    }

    @Override
    public void saveHomeFormationTo(GameValues<GameFormation> homeFormationsForSaving) {
        homeFormationsForSaving.extraTime = GameFormation.EMPTY;
    }

    @Override
    public void saveAwayFormationTo(GameValues<GameFormation> awayFormationsForSaving) {
        awayFormationsForSaving.extraTime = GameFormation.EMPTY;
    }

    @Override
    public void saveHomeTacticTo(GameValues<Tactic> homeTacticsForSaving) {
        homeTacticsForSaving.extraTime = Tactic.EMPTY;
    }

    @Override
    public void saveAwayTacticTo(GameValues<Tactic> awayTacticsForSaving) {
        awayTacticsForSaving.extraTime = Tactic.EMPTY;
    }
}
