package ligamanager.spion.common.pages.partPages.gamePage.gameHalfs;

import ligamanager.spion.common.GameFormation;
import ligamanager.spion.common.GameResult;
import ligamanager.spion.common.GameValues;
import ligamanager.spion.common.Tactic;
import org.openqa.selenium.WebDriver;

/**
 * Created by jpralle on 14.05.2016.
 */
public class SecondHalfGamePagePart extends HalfGamePagePart {

    private static final String xpathToSecondHalfElement = "//*[@id=\"content_popup\"]/div[1]/table[2]/tbody/tr[2]";

    public SecondHalfGamePagePart(WebDriver driver) {
        super(driver, xpathToSecondHalfElement);
    }

    @Override
    public void parseValues() {

        parseFormationAndTactic();
        parseStrengths();
    }

    @Override
    public void saveHomeFormationTo(GameValues<GameFormation> homeFormationsForSaving) {
        homeFormationsForSaving.secondHalf = halfsHomeFormation;
        homeFormationsForSaving.extraTime = halfsHomeFormation;
    }

    @Override
    public void saveAwayFormationTo(GameValues<GameFormation> awayFormationsForSaving) {
        awayFormationsForSaving.secondHalf = halfsAwayFormation;
        awayFormationsForSaving.extraTime = halfsAwayFormation;
    }

    @Override
    public void saveHomeTacticTo(GameValues<Tactic> homeTacticsForSaving) {
        homeTacticsForSaving.secondHalf = halfsHomeTactic;
        homeTacticsForSaving.extraTime = halfsHomeTactic;
    }

    @Override
    public void saveAwayTacticTo(GameValues<Tactic> awayTacticsForSaving) {
        awayTacticsForSaving.secondHalf = halfsAwayTactic;
        awayTacticsForSaving.extraTime = halfsAwayTactic;
    }

    @Override
    public void saveStrengthsBeginOfHalfTo(GameValues<GameResult> strengthsBeginOfHalfs) {
        strengthsBeginOfHalfs.secondHalf = halfsStrengthsBeginOfHalf;
    }

    @Override
    public void saveStrengthsEndOfHalfTo(GameValues<GameResult> strengthsEndOfHalfs) {
        strengthsEndOfHalfs.secondHalf = halfsStrengthsEndOfHalf;
    }

    @Override
    public void saveStrengthsAvergageOfHalfTo(GameValues<GameResult> strengthsAverageOfHalfs) {
        strengthsAverageOfHalfs.secondHalf = halfsStrengthsAverageOfHalf;
    }
}
