package ligamanager.spion.analyzer.pages.partPages.gamePage.gameHalfs;

import ligamanager.spion.analyzer.util.GameFormation;
import ligamanager.spion.analyzer.util.GameResult;
import ligamanager.spion.analyzer.util.GameValues;
import ligamanager.spion.analyzer.util.Tactic;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.StringTokenizer;

/**
 * Created by jpralle on 14.05.2016.
 */
public class FirstHalfGamePagePart extends HalfGamePagePart {

    private static final String xpathToFirstHalfElement = "//*[@id=\"content_popup\"]/div[1]/table[2]/tbody/tr[1]";

    public FirstHalfGamePagePart(WebDriver driver) {
        super(driver, xpathToFirstHalfElement);
    }

    @Override
    public void parseValues() {

        parseFormationAndTactic();
        parseStrengths();
    }

    @Override
    public void saveHomeFormationTo(GameValues<GameFormation> homeFormationsForSaving) {
        homeFormationsForSaving.firstHalf = halfsHomeFormation;
    }

    @Override
    public void saveAwayFormationTo(GameValues<GameFormation> awayFormationsForSaving) {
        awayFormationsForSaving.firstHalf = halfsAwayFormation;
    }

    @Override
    public void saveHomeTacticTo(GameValues<Tactic> homeTacticsForSaving) {
        homeTacticsForSaving.firstHalf = halfsHomeTactic;
    }

    @Override
    public void saveAwayTacticTo(GameValues<Tactic> awayTacticsForSaving) {
        awayTacticsForSaving.firstHalf = halfsAwayTactic;
    }

    @Override
    public void saveStrengthsBeginOfHalfTo(GameValues<GameResult> strengthsBeginOfHalfs) {
        strengthsBeginOfHalfs.firstHalf = halfsStrengthsBeginOfHalf;
    }

    @Override
    public void saveStrengthsEndOfHalfTo(GameValues<GameResult> strengthsEndOfHalfs) {
        strengthsEndOfHalfs.firstHalf = halfsStrengthsEndOfHalf;
    }

    @Override
    public void saveStrengthsAvergageOfHalfTo(GameValues<GameResult> strengthsAverageOfHalfs) {
        strengthsAverageOfHalfs.firstHalf = halfsStrengthsAverageOfHalf;
    }
}
