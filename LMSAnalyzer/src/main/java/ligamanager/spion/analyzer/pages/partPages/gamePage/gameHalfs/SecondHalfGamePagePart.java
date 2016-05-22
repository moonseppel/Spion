package ligamanager.spion.analyzer.pages.partPages.gamePage.gameHalfs;

import ligamanager.spion.analyzer.util.GameFormation;
import ligamanager.spion.analyzer.util.GameValues;
import ligamanager.spion.analyzer.util.Tactic;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by jpralle on 14.05.2016.
 */
public class SecondHalfGamePagePart extends HalfGamePagePart {

    private static final String xpathToSecondHalfElement = "//*[@id=\"content_chat\"]/div[1]/table[2]/tbody/tr[2]";

    public SecondHalfGamePagePart(WebDriver driver) {
        super(driver, xpathToSecondHalfElement);
    }

    @Override
    public void parseValues() {
        String xpathToHomeFormation = ".//td[2]/font";
        String xpathToAwayFormation = ".//td[5]/font";

        parseFormation(xpathToHomeFormation, xpathToAwayFormation);
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
}
