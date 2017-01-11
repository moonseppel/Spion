package ligamanager.spion.analyzer.pages.partPages.gamePage.gameHalfs;

import ligamanager.spion.analyzer.util.GameFormation;
import ligamanager.spion.analyzer.util.GameResult;
import ligamanager.spion.analyzer.util.GameValues;
import ligamanager.spion.analyzer.util.Tactic;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by jpralle on 14.05.2016.
 */
public class ExtraTimeGamePagePart extends HalfGamePagePart {

    private static final String xpathToExtraTimeElement = "//*[@id=\"content_popup\"]/div[1]/table[2]/tbody/tr[3]";

    public ExtraTimeGamePagePart(WebDriver driver) {
        super(driver, xpathToExtraTimeElement);
    }

    @Override
    public void parseValues() {
        parseStrengths(true);
    }

    @Override
    public void saveHomeFormationTo(GameValues<GameFormation> homeFormationsForSaving) {
        //this is the same value as in the second half and set there
    }

    @Override
    public void saveAwayFormationTo(GameValues<GameFormation> awayFormationsForSaving) {
        //this is the same value as in the second half and set there
    }

    @Override
    public void saveHomeTacticTo(GameValues<Tactic> homeTacticsForSaving) {
        //this is the same value as in the second half and set there
    }

    @Override
    public void saveAwayTacticTo(GameValues<Tactic> awayTacticsForSaving) {
        //this is the same value as in the second half and set there
    }

    @Override
    public void saveStrengthsBeginOfHalfTo(GameValues<GameResult> strengthsBeginOfHalfs) {
        strengthsBeginOfHalfs.extraTime = halfsStrengthsBeginOfHalf;
    }

    @Override
    public void saveStrengthsEndOfHalfTo(GameValues<GameResult> strengthsEndOfHalfs) {
        strengthsEndOfHalfs.extraTime = halfsStrengthsEndOfHalf;
    }

    @Override
    public void saveStrengthsAvergageOfHalfTo(GameValues<GameResult> strengthsAverageOfHalfs) {
        strengthsAverageOfHalfs.extraTime = halfsStrengthsAverageOfHalf;
    }
}
